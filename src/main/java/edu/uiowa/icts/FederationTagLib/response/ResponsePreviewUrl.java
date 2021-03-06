package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponsePreviewUrl extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(ResponsePreviewUrl.class);

	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				pageContext.getOut().print(theResponse.getPreviewUrl());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for previewUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for previewUrl tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for previewUrl tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getPreviewUrl() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getPreviewUrl();
		} catch (Exception e) {
			log.error("Can't find enclosing Response for previewUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for previewUrl tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for previewUrl tag ");
			}
		}
	}

	public void setPreviewUrl(String previewUrl) throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setPreviewUrl(previewUrl);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for previewUrl tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for previewUrl tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for previewUrl tag ");
			}
		}
	}

}
