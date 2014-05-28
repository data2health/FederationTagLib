package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseRequestDateToNow extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(ResponseRequestDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setRequestDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for requestDate tag ", e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Response for requestDate tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
			}

		}
		return SKIP_BODY;
	}

	public Date getRequestDate() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getRequestDate();
		} catch (Exception e) {

			log.error("Can't find enclosing Response for requestDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Response for requestDate tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
			}

		}
	}

	public void setRequestDate() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setRequestDateToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing Response for requestDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Response for requestDate tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
			}

		}
	}
}