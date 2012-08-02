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
import edu.uiowa.icts.FederationTagLib.FederationTagLibBodyTagSupport;

@SuppressWarnings("serial")
public class SiteDeleter extends FederationTagLibBodyTagSupport {
    int sid = 0;
    String name = null;
    String bootstrapUrl = null;
    String aggregateQueryUrl = null;
    Date lastValidation = null;
    String ipAddress = null;
	Vector<FederationTagLibTagSupport> parentEntities = new Vector<FederationTagLibTagSupport>();

	private static final Log log = LogFactory.getLog(SiteDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {



        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from federation.site where 1=1"
                                                        + (sid == 0 ? "" : " and sid = ? "));
            if (sid != 0) stat.setInt(webapp_keySeq++, sid);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating Site deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Site deleter");
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
}
