package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteSid extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SiteSid.class);


	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (!theSite.commitNeeded) {
				pageContext.getOut().print(theSite.getSid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Site for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for sid tag ");
		}
		return SKIP_BODY;
	}

	public int getSid() throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getSid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Site for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for sid tag ");
		}
	}

	public void setSid(int sid) throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setSid(sid);
		} catch (Exception e) {
			log.error("Can't find enclosing Site for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for sid tag ");
		}
	}

}
