package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseHitCount extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(ResponseHitCount.class);

	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				pageContext.getOut().print(theResponse.getHitCount());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for hitCount tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for hitCount tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for hitCount tag ");
			}

		}
		return SKIP_BODY;
	}

	public int getHitCount() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getHitCount();
		} catch (Exception e) {
			log.error("Can't find enclosing Response for hitCount tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for hitCount tag ");
				parent.doEndTag();
				return 0;
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for hitCount tag ");
			}
		}
	}

	public void setHitCount(int hitCount) throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setHitCount(hitCount);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for hitCount tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Response for hitCount tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for hitCount tag ");
			}
		}
	}

}
