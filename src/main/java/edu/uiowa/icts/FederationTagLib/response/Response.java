package edu.uiowa.icts.FederationTagLib.response;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.icts.FederationTagLib.site.Site;
import edu.uiowa.icts.FederationTagLib.outboundQuery.OutboundQuery;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;
import edu.uiowa.icts.FederationTagLib.Sequence;

@SuppressWarnings("serial")
public class Response extends FederationTagLibTagSupport {

	static Response currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Response.class);

	Vector<FederationTagLibTagSupport> parentEntities = new Vector<FederationTagLibTagSupport>();

	int sid = 0;
	int qid = 0;
	Date requestDate = null;
	Date responseDate = null;
	int hitCount = 0;
	String populationType = null;
	String previewUrl = null;
	String resultsUrl = null;
	Date clickDate = null;

	private String var = null;

	private Response cachedResponse = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (theSite!= null)
				parentEntities.addElement(theSite);
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			if (theOutboundQuery!= null)
				parentEntities.addElement(theOutboundQuery);

			if (theSite == null) {
			} else {
				sid = theSite.getSid();
			}
			if (theOutboundQuery == null) {
			} else {
				qid = theOutboundQuery.getQid();
			}

			ResponseIterator theResponseIterator = (ResponseIterator)findAncestorWithClass(this, ResponseIterator.class);

			if (theResponseIterator != null) {
				sid = theResponseIterator.getSid();
				qid = theResponseIterator.getQid();
			}

			if (theResponseIterator == null && theSite == null && theOutboundQuery == null && sid == 0) {
				// no sid was provided - the default is to assume that it is a new Response and to generate a new sid
				sid = Sequence.generateID();
				insertEntity();
			} else if (theResponseIterator == null && theSite != null && theOutboundQuery == null) {
				// an sid was provided as an attribute - we need to load a Response from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select qid,request_date,response_date,hit_count,population_type,preview_url,results_url,click_date from federation.response where sid = ?");
				stmt.setInt(1,sid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (qid == 0)
						qid = rs.getInt(1);
					if (requestDate == null)
						requestDate = rs.getTimestamp(2);
					if (responseDate == null)
						responseDate = rs.getTimestamp(3);
					if (hitCount == 0)
						hitCount = rs.getInt(4);
					if (populationType == null)
						populationType = rs.getString(5);
					if (previewUrl == null)
						previewUrl = rs.getString(6);
					if (resultsUrl == null)
						resultsUrl = rs.getString(7);
					if (clickDate == null)
						clickDate = rs.getTimestamp(8);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theResponseIterator == null && theSite == null && theOutboundQuery != null) {
				// an sid was provided as an attribute - we need to load a Response from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select sid,request_date,response_date,hit_count,population_type,preview_url,results_url,click_date from federation.response where qid = ?");
				stmt.setInt(1,qid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (sid == 0)
						sid = rs.getInt(1);
					if (requestDate == null)
						requestDate = rs.getTimestamp(2);
					if (responseDate == null)
						responseDate = rs.getTimestamp(3);
					if (hitCount == 0)
						hitCount = rs.getInt(4);
					if (populationType == null)
						populationType = rs.getString(5);
					if (previewUrl == null)
						previewUrl = rs.getString(6);
					if (resultsUrl == null)
						resultsUrl = rs.getString(7);
					if (clickDate == null)
						clickDate = rs.getTimestamp(8);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or sid was provided as an attribute - we need to load a Response from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select request_date,response_date,hit_count,population_type,preview_url,results_url,click_date from federation.response where sid = ? and qid = ?");
				stmt.setInt(1,sid);
				stmt.setInt(2,qid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (requestDate == null)
						requestDate = rs.getTimestamp(1);
					if (responseDate == null)
						responseDate = rs.getTimestamp(2);
					if (hitCount == 0)
						hitCount = rs.getInt(3);
					if (populationType == null)
						populationType = rs.getString(4);
					if (previewUrl == null)
						previewUrl = rs.getString(5);
					if (resultsUrl == null)
						resultsUrl = rs.getString(6);
					if (clickDate == null)
						clickDate = rs.getTimestamp(7);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving sid " + sid, e);
			throw new JspTagException("Error: JDBC error retrieving sid " + sid);
		} finally {
			freeConnection();
		}

		Response currentResponse = (Response) pageContext.getAttribute("tag_response");
		if(currentResponse != null){
			cachedResponse = currentResponse;
		}
		currentResponse = this;
		pageContext.setAttribute((var == null ? "tag_response" : var), currentResponse);

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(this.cachedResponse != null){
			pageContext.setAttribute((var == null ? "tag_response" : var), this.cachedResponse);
		}else{
			pageContext.removeAttribute((var == null ? "tag_response" : var));
			this.cachedResponse = null;
		}

		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update federation.response set request_date = ?, response_date = ?, hit_count = ?, population_type = ?, preview_url = ?, results_url = ?, click_date = ? where sid = ? and qid = ?");
				stmt.setTimestamp(1,requestDate == null ? null : new java.sql.Timestamp(requestDate.getTime()));
				stmt.setTimestamp(2,responseDate == null ? null : new java.sql.Timestamp(responseDate.getTime()));
				stmt.setInt(3,hitCount);
				stmt.setString(4,populationType);
				stmt.setString(5,previewUrl);
				stmt.setString(6,resultsUrl);
				stmt.setTimestamp(7,clickDate == null ? null : new java.sql.Timestamp(clickDate.getTime()));
				stmt.setInt(8,sid);
				stmt.setInt(9,qid);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			if (populationType == null)
				populationType = "";
			if (previewUrl == null)
				previewUrl = "";
			if (resultsUrl == null)
				resultsUrl = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into federation.response(sid,qid,request_date,response_date,hit_count,population_type,preview_url,results_url,click_date) values (?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1,sid);
			stmt.setInt(2,qid);
			stmt.setTimestamp(3,requestDate == null ? null : new java.sql.Timestamp(requestDate.getTime()));
			stmt.setTimestamp(4,responseDate == null ? null : new java.sql.Timestamp(responseDate.getTime()));
			stmt.setInt(5,hitCount);
			stmt.setString(6,populationType);
			stmt.setString(7,previewUrl);
			stmt.setString(8,resultsUrl);
			stmt.setTimestamp(9,clickDate == null ? null : new java.sql.Timestamp(clickDate.getTime()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
	}

	public int getSid () {
		return sid;
	}

	public void setSid (int sid) {
		this.sid = sid;
	}

	public int getActualSid () {
		return sid;
	}

	public int getQid () {
		return qid;
	}

	public void setQid (int qid) {
		this.qid = qid;
	}

	public int getActualQid () {
		return qid;
	}

	public Date getRequestDate () {
		return requestDate;
	}

	public void setRequestDate (Date requestDate) {
		this.requestDate = requestDate;
		commitNeeded = true;
	}

	public Date getActualRequestDate () {
		return requestDate;
	}

	public void setRequestDateToNow ( ) {
		this.requestDate = new java.util.Date();
		commitNeeded = true;
	}

	public Date getResponseDate () {
		return responseDate;
	}

	public void setResponseDate (Date responseDate) {
		this.responseDate = responseDate;
		commitNeeded = true;
	}

	public Date getActualResponseDate () {
		return responseDate;
	}

	public void setResponseDateToNow ( ) {
		this.responseDate = new java.util.Date();
		commitNeeded = true;
	}

	public int getHitCount () {
		return hitCount;
	}

	public void setHitCount (int hitCount) {
		this.hitCount = hitCount;
		commitNeeded = true;
	}

	public int getActualHitCount () {
		return hitCount;
	}

	public String getPopulationType () {
		if (commitNeeded)
			return "";
		else
			return populationType;
	}

	public void setPopulationType (String populationType) {
		this.populationType = populationType;
		commitNeeded = true;
	}

	public String getActualPopulationType () {
		return populationType;
	}

	public String getPreviewUrl () {
		if (commitNeeded)
			return "";
		else
			return previewUrl;
	}

	public void setPreviewUrl (String previewUrl) {
		this.previewUrl = previewUrl;
		commitNeeded = true;
	}

	public String getActualPreviewUrl () {
		return previewUrl;
	}

	public String getResultsUrl () {
		if (commitNeeded)
			return "";
		else
			return resultsUrl;
	}

	public void setResultsUrl (String resultsUrl) {
		this.resultsUrl = resultsUrl;
		commitNeeded = true;
	}

	public String getActualResultsUrl () {
		return resultsUrl;
	}

	public Date getClickDate () {
		return clickDate;
	}

	public void setClickDate (Date clickDate) {
		this.clickDate = clickDate;
		commitNeeded = true;
	}

	public Date getActualClickDate () {
		return clickDate;
	}

	public void setClickDateToNow ( ) {
		this.clickDate = new java.util.Date();
		commitNeeded = true;
	}

	public String getVar () {
		return var;
	}

	public void setVar (String var) {
		this.var = var;
	}

	public String getActualVar () {
		return var;
	}

	public static Integer sidValue() throws JspException {
		try {
			return currentInstance.getSid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function sidValue()");
		}
	}

	public static Integer qidValue() throws JspException {
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

	public static Integer hitCountValue() throws JspException {
		try {
			return currentInstance.getHitCount();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function hitCountValue()");
		}
	}

	public static String populationTypeValue() throws JspException {
		try {
			return currentInstance.getPopulationType();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function populationTypeValue()");
		}
	}

	public static String previewUrlValue() throws JspException {
		try {
			return currentInstance.getPreviewUrl();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function previewUrlValue()");
		}
	}

	public static String resultsUrlValue() throws JspException {
		try {
			return currentInstance.getResultsUrl();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function resultsUrlValue()");
		}
	}

	public static Date clickDateValue() throws JspException {
		try {
			return currentInstance.getClickDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function clickDateValue()");
		}
	}

	private void clearServiceState () {
		sid = 0;
		qid = 0;
		requestDate = null;
		responseDate = null;
		hitCount = 0;
		populationType = null;
		previewUrl = null;
		resultsUrl = null;
		clickDate = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<FederationTagLibTagSupport>();
		this.var = null;

	}

}
