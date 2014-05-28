package edu.uiowa.icts.FederationTagLib.outboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class OutboundQueryQid extends FederationTagLibTagSupport {

	private static final Log log = LogFactory.getLog(OutboundQueryQid.class);

	public int doStartTag() throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			if (!theOutboundQuery.commitNeeded) {
				pageContext.getOut().print(theOutboundQuery.getQid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for qid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for qid tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for qid tag ");
			}

		}
		return SKIP_BODY;
	}

	public int getQid() throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			return theOutboundQuery.getQid();
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for qid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for qid tag ");
				parent.doEndTag();
				return 0;
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for qid tag ");
			}
		}
	}

	public void setQid(int qid) throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			theOutboundQuery.setQid(qid);
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for qid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing OutboundQuery for qid tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing OutboundQuery for qid tag ");
			}
		}
	}

}
