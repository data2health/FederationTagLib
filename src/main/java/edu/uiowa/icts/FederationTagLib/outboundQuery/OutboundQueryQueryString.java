package edu.uiowa.icts.FederationTagLib.outboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class OutboundQueryQueryString extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(OutboundQueryQueryString.class);

	public int doStartTag() throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			if (!theOutboundQuery.commitNeeded) {
				pageContext.getOut().print(theOutboundQuery.getQueryString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for queryString tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for queryString tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryString tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getQueryString() throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			return theOutboundQuery.getQueryString();
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for queryString tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for queryString tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryString tag ");
			}
		}
	}

	public void setQueryString(String queryString) throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			theOutboundQuery.setQueryString(queryString);
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for queryString tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for queryString tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryString tag ");
			}
		}
	}

}
