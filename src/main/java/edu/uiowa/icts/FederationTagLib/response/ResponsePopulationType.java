package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponsePopulationType extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResponsePopulationType.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				pageContext.getOut().print(theResponse.getPopulationType());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for populationType tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for populationType tag ");
		}
		return SKIP_BODY;
	}

	public String getPopulationType() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getPopulationType();
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for populationType tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for populationType tag ");
		}
	}

	public void setPopulationType(String populationType) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setPopulationType(populationType);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for populationType tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for populationType tag ");
		}
	}

}
