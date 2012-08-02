package edu.uiowa.icts.FederationTagLib.inboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundQueryQueryString extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundQueryQueryString.class);


	public int doStartTag() throws JspException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			if (!theInboundQuery.commitNeeded) {
				pageContext.getOut().print(theInboundQuery.getQueryString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for queryString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryString tag ");
		}
		return SKIP_BODY;
	}

	public String getQueryString() throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			return theInboundQuery.getQueryString();
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundQuery for queryString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryString tag ");
		}
	}

	public void setQueryString(String queryString) throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			theInboundQuery.setQueryString(queryString);
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for queryString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryString tag ");
		}
	}

}
