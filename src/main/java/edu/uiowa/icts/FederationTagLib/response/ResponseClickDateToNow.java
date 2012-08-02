package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseClickDateToNow extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResponseClickDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setClickDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getClickDate() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getClickDate();
		} catch (Exception e) {
			log.error("Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
	}

	public void setClickDate( ) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setClickDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
	}

}
