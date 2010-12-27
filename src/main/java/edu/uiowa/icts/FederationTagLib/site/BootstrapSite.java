package edu.uiowa.icts.FederationTagLib.site;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class BootstrapSite extends FederationTagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);

            HttpURLConnection theConnection = (HttpURLConnection) (new URL(theSite.getBootstrapUrl())).openConnection();
            theConnection.setConnectTimeout(10000);
            theConnection.setReadTimeout(10000);
            
            Document document = (new SAXReader()).read(theConnection.getInputStream());
            String name = document.selectSingleNode( "//site-description/name" ).getText();
            String aggregateQueryUrl = document.selectSingleNode( "//site-description/aggregate-query" ).getText();

            theConnection.disconnect();
            
            theSite.setName(name);
            theSite.setAggregateQueryUrl(aggregateQueryUrl);
            theSite.setLastValidationToNow();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Site for BootstrapSite tag ");
		}
		return SKIP_BODY;
	}

}
