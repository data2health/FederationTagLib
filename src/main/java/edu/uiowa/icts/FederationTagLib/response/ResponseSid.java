package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseSid extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResponseSid.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				pageContext.getOut().print(theResponse.getSid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for sid tag ");
		}
		return SKIP_BODY;
	}

	public int getSid() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getSid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for sid tag ");
		}
	}

	public void setSid(int sid) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setSid(sid);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for sid tag ");
		}
	}

}
