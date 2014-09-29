/*
 * Created on Sep 10, 2010
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.uiowa.icts.FederationTagLib.search;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.servlet.jsp.JspTagException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

class SearchThread implements Runnable {
    Search theParent = null;
    int sid = 0;
    String siteName = null;
    String queryURL = null;
    String queryTerm = null;
    
    SearchThread(Search theParent, int sid, String siteName, String queryURL, String queryTerm) {
        this.theParent = theParent;
        this.sid = sid;
        this.siteName = siteName;
        this.queryURL = queryURL;
        this.queryTerm = queryTerm;
        System.out.println("spawning " + siteName + " search thread for " + queryTerm);
    }

    @Override
    public void run() {
        int hitCount = 0;
        String resultType = null;
        String previewURL = null;
        String resultsURL = null;
        Date requestDate = null;
        Date responseDate = null;
        
        System.out.println("running " + siteName + " search thread for " + queryTerm);
        requestDate = new java.util.Date();
        
        try {
//            URL theIndexFile = new URL(queryURL + queryTerm);
//            URLConnection theIndexConnection = theIndexFile.openConnection();
//            BufferedReader IODesc = new BufferedReader(new InputStreamReader(theIndexConnection.getInputStream()));
//            String line = null;
//            while ((line = IODesc.readLine()) != null) {
//                System.out.println("\t" + sid + "\t" + line);
//            }

            HttpURLConnection theConnection = (HttpURLConnection) (new URL(queryURL + queryTerm)).openConnection();
            theConnection.setConnectTimeout(10000);
            theConnection.setReadTimeout(10000);
            
            Document document = (new SAXReader()).read(theConnection.getInputStream());
            hitCount = Integer.parseInt(document.selectSingleNode( "//aggregation-result/count" ).getText());
            resultType = document.selectSingleNode( "//aggregation-result/population-type" ).getText();
            previewURL = document.selectSingleNode( "//aggregation-result/preview-URL" ).getText();
            resultsURL = document.selectSingleNode( "//aggregation-result/search-results-URL" ).getText();
            responseDate = new java.util.Date();

            theConnection.disconnect();

            System.out.println("\t" + sid + "\tcount: " + hitCount);
            System.out.println("\t" + sid + "\tpopulation type: " + resultType);
            System.out.println("\t" + sid + "\tpreview URL: " + previewURL);
            System.out.println("\t" + sid + "\tresults URL: " + resultsURL);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("returning " + siteName + " search thread for " + queryTerm);
        try {
			theParent.collectSearchResponse(sid, hitCount, resultType, previewURL, resultsURL, requestDate, responseDate);
		} catch (JspTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
