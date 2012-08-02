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

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;
import edu.uiowa.icts.FederationTagLib.FederationTagLibBodyTagSupport;
import edu.uiowa.icts.FederationTagLib.site.Site;
import edu.uiowa.icts.FederationTagLib.outboundQuery.OutboundQuery;

@SuppressWarnings("serial")
public class ResponseIterator extends FederationTagLibBodyTagSupport {
    int sid = 0;
    int qid = 0;
    Date requestDate = null;
    Date responseDate = null;
    int hitCount = 0;
    String populationType = null;
    String previewUrl = null;
    String resultsUrl = null;
    Date clickDate = null;
	Vector<FederationTagLibTagSupport> parentEntities = new Vector<FederationTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ResponseIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useSite = false;
   boolean useOutboundQuery = false;

	public static String responseCountBySite(String sid) throws JspTagException {
		int count = 0;
		ResponseIterator theIterator = new ResponseIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from federation.response where 1=1"
						+ " and sid = ?"
						);

			stat.setInt(1,Integer.parseInt(sid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Response iterator", e);
			throw new JspTagException("Error: JDBC error generating Response iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean siteHasResponse(String sid) throws JspTagException {
		return ! responseCountBySite(sid).equals("0");
	}

	public static String responseCountByOutboundQuery(String qid) throws JspTagException {
		int count = 0;
		ResponseIterator theIterator = new ResponseIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from federation.response where 1=1"
						+ " and qid = ?"
						);

			stat.setInt(1,Integer.parseInt(qid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Response iterator", e);
			throw new JspTagException("Error: JDBC error generating Response iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean outboundQueryHasResponse(String qid) throws JspTagException {
		return ! responseCountByOutboundQuery(qid).equals("0");
	}

	public static Boolean responseExists (String sid, String qid) throws JspTagException {
		int count = 0;
		ResponseIterator theIterator = new ResponseIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from federation.response where 1=1"
						+ " and sid = ?"
						+ " and qid = ?"
						);

			stat.setInt(1,Integer.parseInt(sid));
			stat.setInt(2,Integer.parseInt(qid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Response iterator", e);
			throw new JspTagException("Error: JDBC error generating Response iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean siteOutboundQueryExists (String sid, String qid) throws JspTagException {
		int count = 0;
		ResponseIterator theIterator = new ResponseIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from federation.response where 1=1"
						+ " and sid = ?"
						+ " and qid = ?"
						);

			stat.setInt(1,Integer.parseInt(sid));
			stat.setInt(2,Integer.parseInt(qid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating Response iterator", e);
			throw new JspTagException("Error: JDBC error generating Response iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
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


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (sid == 0 ? "" : " and sid = ?")
                                                        + (qid == 0 ? "" : " and qid = ?")
                                                        +  generateLimitCriteria());
            if (sid != 0) stat.setInt(webapp_keySeq++, sid);
            if (qid != 0) stat.setInt(webapp_keySeq++, qid);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT federation.response.sid, federation.response.qid from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (sid == 0 ? "" : " and sid = ?")
                                                        + (qid == 0 ? "" : " and qid = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (sid != 0) stat.setInt(webapp_keySeq++, sid);
            if (qid != 0) stat.setInt(webapp_keySeq++, qid);
            rs = stat.executeQuery();

            if (rs.next()) {
                sid = rs.getInt(1);
                qid = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Response iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Response iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("federation.response");
       if (useSite)
          theBuffer.append(", federation.site");
       if (useOutboundQuery)
          theBuffer.append(", federation.outbound_query");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useSite)
          theBuffer.append(" and site.sid = response.null");
       if (useOutboundQuery)
          theBuffer.append(" and outbound_query.qid = response.null");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "sid,qid";
        }
    }

    private String generateLimitCriteria() {
        if (limitCriteria > 0) {
            return " limit " + limitCriteria;
        } else {
            return "";
        }
    }

    public int doAfterBody() throws JspTagException {
        try {
            if (rs.next()) {
                sid = rs.getInt(1);
                qid = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Response", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Response");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending Response iterator",e);
            throw new JspTagException("Error: JDBC error ending Response iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        sid = 0;
        qid = 0;
        parentEntities = new Vector<FederationTagLibTagSupport>();

        this.rs = null;
        this.stat = null;
        this.sortCriteria = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public int getLimitCriteria() {
        return limitCriteria;
    }

    public void setLimitCriteria(int limitCriteria) {
        this.limitCriteria = limitCriteria;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }


   public boolean getUseSite() {
        return useSite;
    }

    public void setUseSite(boolean useSite) {
        this.useSite = useSite;
    }

   public boolean getUseOutboundQuery() {
        return useOutboundQuery;
    }

    public void setUseOutboundQuery(boolean useOutboundQuery) {
        this.useOutboundQuery = useOutboundQuery;
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
}
