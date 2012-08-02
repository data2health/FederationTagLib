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
public class ResponseDeleter extends FederationTagLibBodyTagSupport {
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

	private static final Log log = LogFactory.getLog(ResponseDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

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


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from federation.response where 1=1"
                                                        + (sid == 0 ? "" : " and sid = ? ")
                                                        + (qid == 0 ? "" : " and qid = ? "));
            if (sid != 0) stat.setInt(webapp_keySeq++, sid);
            if (qid != 0) stat.setInt(webapp_keySeq++, qid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Response deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Response deleter");
        } finally {
            freeConnection();
        }

        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		clearServiceState();
		return super.doEndTag();
	}

    private void clearServiceState() {
        sid = 0;
        qid = 0;
        parentEntities = new Vector<FederationTagLibTagSupport>();

        this.rs = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
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
