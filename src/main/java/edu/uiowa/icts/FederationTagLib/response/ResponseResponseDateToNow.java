package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseResponseDateToNow extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResponseResponseDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setResponseDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for responseDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for responseDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getResponseDate() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getResponseDate();
		} catch (Exception e) {
			log.error("Can't find enclosing Response for responseDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for responseDate tag ");
		}
	}

	public void setResponseDate( ) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setResponseDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing Response for responseDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for responseDate tag ");
		}
	}

}
