package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteLastValidationToNow extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(SiteLastValidationToNow.class);


	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setLastValidationToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing Site for lastValidation tag ", e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Site for lastValidation tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for lastValidation tag ");
			}

		}
		return SKIP_BODY;
	}

	public Date getLastValidation() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getLastValidation();
		} catch (Exception e) {

			log.error("Can't find enclosing Site for lastValidation tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Site for lastValidation tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for lastValidation tag ");
			}

		}
	}

	public void setLastValidation() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setLastValidationToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing Site for lastValidation tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Site for lastValidation tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Site for lastValidation tag ");
			}

		}
	}
}