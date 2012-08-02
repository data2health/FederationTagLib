package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteName extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SiteName.class);


	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (!theSite.commitNeeded) {
				pageContext.getOut().print(theSite.getName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Site for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for name tag ");
		}
		return SKIP_BODY;
	}

	public String getName() throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getName();
		} catch (Exception e) {
			log.error(" Can't find enclosing Site for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for name tag ");
		}
	}

	public void setName(String name) throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setName(name);
		} catch (Exception e) {
			log.error("Can't find enclosing Site for name tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for name tag ");
		}
	}

}
