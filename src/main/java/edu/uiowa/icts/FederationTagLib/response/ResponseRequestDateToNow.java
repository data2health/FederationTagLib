package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
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
			throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getRequestDate() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getRequestDate();
		} catch (Exception e) {
			log.error("Can't find enclosing Response for requestDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
		}
	}

	public void setRequestDate( ) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setRequestDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing Response for requestDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for requestDate tag ");
		}
	}

}
