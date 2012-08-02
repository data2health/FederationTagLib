package edu.uiowa.icts.FederationTagLib.inboundSearch;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;
import edu.uiowa.icts.FederationTagLib.Sequence;

@SuppressWarnings("serial")
public class InboundSearch extends FederationTagLibTagSupport {

	static InboundSearch currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(InboundSearch.class);

	Vector<FederationTagLibTagSupport> parentEntities = new Vector<FederationTagLibTagSupport>();

	int sid = 0;
	String searchString = null;
	Date searchDate = null;
	String ipAddress = null;

	private String var = null;

	private InboundSearch cachedInboundSearch = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			InboundSearchIterator theInboundSearchIterator = (InboundSearchIterator)findAncestorWithClass(this, InboundSearchIterator.class);

			if (theInboundSearchIterator != null) {
				sid = theInboundSearchIterator.getSid();
			}

			if (theInboundSearchIterator == null && sid == 0) {
				// no sid was provided - the default is to assume that it is a new InboundSearch and to generate a new sid
				sid = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or sid was provided as an attribute - we need to load a InboundSearch from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select search_string,search_date,ip_address from federation.inbound_search where sid = ?");
				stmt.setInt(1,sid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (searchString == null)
						searchString = rs.getString(1);
					if (searchDate == null)
						searchDate = rs.getTimestamp(2);
					if (ipAddress == null)
						ipAddress = rs.getString(3);
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

		InboundSearch currentInboundSearch = (InboundSearch) pageContext.getAttribute("tag_inboundSearch");
		if(currentInboundSearch != null){
			cachedInboundSearch = currentInboundSearch;
		}
		currentInboundSearch = this;
		pageContext.setAttribute((var == null ? "tag_inboundSearch" : var), currentInboundSearch);

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(this.cachedInboundSearch != null){
			pageContext.setAttribute((var == null ? "tag_inboundSearch" : var), this.cachedInboundSearch);
		}else{
			pageContext.removeAttribute((var == null ? "tag_inboundSearch" : var));
			this.cachedInboundSearch = null;
		}

		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update federation.inbound_search set search_string = ?, search_date = ?, ip_address = ? where sid = ?");
				stmt.setString(1,searchString);
				stmt.setTimestamp(2,searchDate == null ? null : new java.sql.Timestamp(searchDate.getTime()));
				stmt.setString(3,ipAddress);
				stmt.setInt(4,sid);
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
			if (sid == 0) {
				sid = Sequence.generateID();
				log.debug("generating new InboundSearch " + sid);
			}

			if (searchString == null)
				searchString = "";
			if (ipAddress == null)
				ipAddress = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into federation.inbound_search(sid,search_string,search_date,ip_address) values (?,?,?,?)");
			stmt.setInt(1,sid);
			stmt.setString(2,searchString);
			stmt.setTimestamp(3,searchDate == null ? null : new java.sql.Timestamp(searchDate.getTime()));
			stmt.setString(4,ipAddress);
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

	public String getSearchString () {
		if (commitNeeded)
			return "";
		else
			return searchString;
	}

	public void setSearchString (String searchString) {
		this.searchString = searchString;
		commitNeeded = true;
	}

	public String getActualSearchString () {
		return searchString;
	}

	public Date getSearchDate () {
		return searchDate;
	}

	public void setSearchDate (Date searchDate) {
		this.searchDate = searchDate;
		commitNeeded = true;
	}

	public Date getActualSearchDate () {
		return searchDate;
	}

	public void setSearchDateToNow ( ) {
		this.searchDate = new java.util.Date();
		commitNeeded = true;
	}

	public String getIpAddress () {
		if (commitNeeded)
			return "";
		else
			return ipAddress;
	}

	public void setIpAddress (String ipAddress) {
		this.ipAddress = ipAddress;
		commitNeeded = true;
	}

	public String getActualIpAddress () {
		return ipAddress;
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

	public static String searchStringValue() throws JspException {
		try {
			return currentInstance.getSearchString();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function searchStringValue()");
		}
	}

	public static Date searchDateValue() throws JspException {
		try {
			return currentInstance.getSearchDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function searchDateValue()");
		}
	}

	public static String ipAddressValue() throws JspException {
		try {
			return currentInstance.getIpAddress();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function ipAddressValue()");
		}
	}

	private void clearServiceState () {
		sid = 0;
		searchString = null;
		searchDate = null;
		ipAddress = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<FederationTagLibTagSupport>();
		this.var = null;

	}

}
