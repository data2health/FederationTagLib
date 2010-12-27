package edu.uiowa.icts.FederationTagLib.search;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.icts.FederationTagLib.outboundQuery.OutboundQuery;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class Search extends FederationTagLibTagSupport {

    static Search currentInstance = null;
    
    int sid = 0;
    int qid = 0;
    Date requestDate = null;
    Date responseDate = null;
    int hitCount = 0;
    Date clickDate = null;
    
    int responseTarget = 0;
    int responseCount = 0;

    public int doStartTag() throws JspException {
        currentInstance = this;
        try {
            OutboundQuery theOutboundQuery = (OutboundQuery) findAncestorWithClass(this, OutboundQuery.class);
            if (theOutboundQuery == null) {
                throw new JspTagException("Error: No nesting OutBoundQuery for search ");
            }
            
            qid = theOutboundQuery.getQid();

            // Retrieve site definitions and spawn search threads
            String siteName = null;
            String aggregateQueryUrl = null;
            PreparedStatement stmt = getConnection().prepareStatement("select sid,name,aggregate_query_url from federation.site");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sid = rs.getInt(1);
                siteName = rs.getString(2);
                aggregateQueryUrl = rs.getString(3);
                (new Thread(new SearchThread(this, sid, siteName, aggregateQueryUrl, URLEncoder.encode(theOutboundQuery.getActualQueryString())))).start();
                responseTarget++;
            }
            stmt.close();
            
            while (responseCount < responseTarget)
                Thread.sleep(500);
            
            System.out.println("search completed for " + theOutboundQuery.getActualQueryString());
        } catch (SQLException e) {
            e.printStackTrace();
            freeConnection();
            throw new JspTagException("Error: JDBC error retrieving sid " + sid);
        } catch (InterruptedException e) {
            e.printStackTrace();
            freeConnection();
            throw new JspTagException("Error: exception thrown by sleeping search " + qid);
        }
        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException {
        currentInstance = null;
        clearServiceState();
        return super.doEndTag();
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public void setRequestDateToNow() {
        this.requestDate = new java.util.Date();
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public void setResponseDateToNow() {
        this.responseDate = new java.util.Date();
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public Date getClickDate() {
        return clickDate;
    }

    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    public void setClickDateToNow() {
        this.clickDate = new java.util.Date();
    }

    public static int sidValue() throws JspException {
        try {
            return currentInstance.getSid();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function sidValue()");
        }
    }

    public static int qidValue() throws JspException {
        try {
            return currentInstance.getQid();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function qidValue()");
        }
    }

    public static Date requestDateValue() throws JspException {
        try {
            return currentInstance.getRequestDate();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function requestDateValue()");
        }
    }

    public static Date responseDateValue() throws JspException {
        try {
            return currentInstance.getResponseDate();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function responseDateValue()");
        }
    }

    public static int hitCountValue() throws JspException {
        try {
            return currentInstance.getHitCount();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function hitCountValue()");
        }
    }

    public static Date clickDateValue() throws JspException {
        try {
            return currentInstance.getClickDate();
        } catch (Exception e) {
            throw new JspTagException("Error in tag function clickDateValue()");
        }
    }

    private void clearServiceState() {
        sid = 0;
        qid = 0;
        requestDate = null;
        responseDate = null;
        hitCount = 0;
        clickDate = null;
        responseTarget = 0;
        responseCount = 0;
    }
    
    synchronized void collectSearchResponse(int sid, int hitCount, String resultType, String previewURL, String resultsURL, Date requestDate, Date responseDate) throws JspTagException {
		try {
			PreparedStatement stmt = getConnection().prepareStatement("insert into federation.response(sid,qid,request_date,response_date,hit_count,population_type,preview_url,results_url,click_date) values (?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1,sid);
			stmt.setInt(2,qid);
			stmt.setTimestamp(3,requestDate == null ? null : new java.sql.Timestamp(requestDate.getTime()));
			stmt.setTimestamp(4,responseDate == null ? null : new java.sql.Timestamp(responseDate.getTime()));
			stmt.setInt(5,hitCount);
			stmt.setString(6,resultType);
			stmt.setString(7,previewURL);
			stmt.setString(8,resultsURL);
			stmt.setTimestamp(9,clickDate == null ? null : new java.sql.Timestamp(clickDate.getTime()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			freeConnection();
			throw new JspTagException("Error: IOException while writing to the user");
		}

		responseCount++;
    }
    
}
