package edu.uiowa.icts.FederationTagLib.inboundSearch;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundSearchSid extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundSearchSid.class);


	public int doStartTag() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			if (!theInboundSearch.commitNeeded) {
				pageContext.getOut().print(theInboundSearch.getSid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for sid tag ");
		}
		return SKIP_BODY;
	}

	public int getSid() throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			return theInboundSearch.getSid();
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundSearch for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for sid tag ");
		}
	}

	public void setSid(int sid) throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setSid(sid);
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for sid tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for sid tag ");
		}
	}

}
