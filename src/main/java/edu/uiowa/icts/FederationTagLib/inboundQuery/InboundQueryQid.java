package edu.uiowa.icts.FederationTagLib.inboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundQueryQid extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundQueryQid.class);


	public int doStartTag() throws JspException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			if (!theInboundQuery.commitNeeded) {
				pageContext.getOut().print(theInboundQuery.getQid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for qid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for qid tag ");
		}
		return SKIP_BODY;
	}

	public int getQid() throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			return theInboundQuery.getQid();
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundQuery for qid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for qid tag ");
		}
	}

	public void setQid(int qid) throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			theInboundQuery.setQid(qid);
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for qid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for qid tag ");
		}
	}

}
