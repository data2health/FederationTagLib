package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteBootstrapUrl extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(SiteBootstrapUrl.class);

	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (!theSite.commitNeeded) {
				pageContext.getOut().print(theSite.getBootstrapUrl());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Site for bootstrapUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Site for bootstrapUrl tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for bootstrapUrl tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getBootstrapUrl() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getBootstrapUrl();
		} catch (Exception e) {
			log.error("Can't find enclosing Site for bootstrapUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Site for bootstrapUrl tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for bootstrapUrl tag ");
			}
		}
	}

	public void setBootstrapUrl(String bootstrapUrl) throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setBootstrapUrl(bootstrapUrl);
		} catch (Exception e) {
			log.error("Can't find enclosing Site for bootstrapUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Site for bootstrapUrl tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for bootstrapUrl tag ");
			}
		}
	}

}
