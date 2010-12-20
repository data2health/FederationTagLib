package edu.uiowa.icts.FederationTagLib.site;

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

public class Site extends FederationTagLibTagSupport {

	static Site currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log =LogFactory.getLog(Site.class);

	Vector<FederationTagLibTagSupport> parentEntities = new Vector<FederationTagLibTagSupport>();

	int sid = 0;
	String name = null;
	String bootstrapUrl = null;
	String aggregateQueryUrl = null;
	Date lastValidation = null;
	String ipAddress = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			SiteIterator theSiteIterator = (SiteIterator)findAncestorWithClass(this, SiteIterator.class);

			if (theSiteIterator != null) {
				sid = theSiteIterator.getSid();
			}

			if (theSiteIterator == null && sid == 0) {
				// no sid was provided - the default is to assume that it is a new Site and to generate a new sid
				sid = Sequence.generateID();
				log.debug("generating new Site " + sid);
				insertEntity();
			} else {
				// an iterator or sid was provided as an attribute - we need to load a Site from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select name,bootstrap_url,aggregate_query_url,last_validation,ip_address from federation.site where sid = ?");
				stmt.setInt(1,sid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (name == null)
						name = rs.getString(1);
					if (bootstrapUrl == null)
						bootstrapUrl = rs.getString(2);
					if (aggregateQueryUrl == null)
						aggregateQueryUrl = rs.getString(3);
					if (lastValidation == null)
						lastValidation = rs.getTimestamp(4);
					if (ipAddress == null)
						ipAddress = rs.getString(5);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error retrieving sid " + sid);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update federation.site set name = ?, bootstrap_url = ?, aggregate_query_url = ?, last_validation = ?, ip_address = ? where sid = ?");
				stmt.setString(1,name);
				stmt.setString(2,bootstrapUrl);
				stmt.setString(3,aggregateQueryUrl);
				stmt.setTimestamp(4,lastValidation == null ? null : new java.sql.Timestamp(lastValidation.getTime()));
				stmt.setString(5,ipAddress);
				stmt.setInt(6,sid);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
				log.debug("generating new Site " + sid);
			}

			if (name == null)
				name = "";
			if (bootstrapUrl == null)
				bootstrapUrl = "";
			if (aggregateQueryUrl == null)
				aggregateQueryUrl = "";
			if (ipAddress == null)
				ipAddress = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into federation.site(sid,name,bootstrap_url,aggregate_query_url,last_validation,ip_address) values (?,?,?,?,?,?)");
			stmt.setInt(1,sid);
			stmt.setString(2,name);
			stmt.setString(3,bootstrapUrl);
			stmt.setString(4,aggregateQueryUrl);
			stmt.setTimestamp(5,lastValidation == null ? null : new java.sql.Timestamp(lastValidation.getTime()));
			stmt.setString(6,ipAddress);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
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

	public String getName () {
		if (commitNeeded)
			return "";
		else
			return name;
	}

	public void setName (String name) {
		this.name = name;
		commitNeeded = true;
	}

	public String getActualName () {
		return name;
	}

	public String getBootstrapUrl () {
		if (commitNeeded)
			return "";
		else
			return bootstrapUrl;
	}

	public void setBootstrapUrl (String bootstrapUrl) {
		this.bootstrapUrl = bootstrapUrl;
		commitNeeded = true;
	}

	public String getActualBootstrapUrl () {
		return bootstrapUrl;
	}

	public String getAggregateQueryUrl () {
		if (commitNeeded)
			return "";
		else
			return aggregateQueryUrl;
	}

	public void setAggregateQueryUrl (String aggregateQueryUrl) {
		this.aggregateQueryUrl = aggregateQueryUrl;
		commitNeeded = true;
	}

	public String getActualAggregateQueryUrl () {
		return aggregateQueryUrl;
	}

	public Date getLastValidation () {
		return lastValidation;
	}

	public void setLastValidation (Date lastValidation) {
		this.lastValidation = lastValidation;
		commitNeeded = true;
	}

	public Date getActualLastValidation () {
		return lastValidation;
	}

	public void setLastValidationToNow ( ) {
		this.lastValidation = new java.util.Date();
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

	public static int sidValue() throws JspException {
		try {
			return currentInstance.getSid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function sidValue()");
		}
	}

	public static String nameValue() throws JspException {
		try {
			return currentInstance.getName();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function nameValue()");
		}
	}

	public static String bootstrapUrlValue() throws JspException {
		try {
			return currentInstance.getBootstrapUrl();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function bootstrapUrlValue()");
		}
	}

	public static String aggregateQueryUrlValue() throws JspException {
		try {
			return currentInstance.getAggregateQueryUrl();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function aggregateQueryUrlValue()");
		}
	}

	public static Date lastValidationValue() throws JspException {
		try {
			return currentInstance.getLastValidation();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function lastValidationValue()");
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
		name = null;
		bootstrapUrl = null;
		aggregateQueryUrl = null;
		lastValidation = null;
		ipAddress = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<FederationTagLibTagSupport>();

	}

}
