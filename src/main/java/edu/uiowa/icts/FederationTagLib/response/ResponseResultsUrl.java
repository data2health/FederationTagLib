package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseResultsUrl extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(ResponseResultsUrl.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				pageContext.getOut().print(theResponse.getResultsUrl());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for resultsUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for resultsUrl tag ");
		}
		return SKIP_BODY;
	}

	public String getResultsUrl() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getResultsUrl();
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for resultsUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for resultsUrl tag ");
		}
	}

	public void setResultsUrl(String resultsUrl) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setResultsUrl(resultsUrl);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for resultsUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for resultsUrl tag ");
		}
	}

}
