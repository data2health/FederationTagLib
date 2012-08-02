package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteIpAddress extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SiteIpAddress.class);


	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (!theSite.commitNeeded) {
				pageContext.getOut().print(theSite.getIpAddress());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Site for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for ipAddress tag ");
		}
		return SKIP_BODY;
	}

	public String getIpAddress() throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getIpAddress();
		} catch (Exception e) {
			log.error(" Can't find enclosing Site for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for ipAddress tag ");
		}
	}

	public void setIpAddress(String ipAddress) throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setIpAddress(ipAddress);
		} catch (Exception e) {
			log.error("Can't find enclosing Site for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for ipAddress tag ");
		}
	}

}
