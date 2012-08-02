package edu.uiowa.icts.FederationTagLib.inboundSearch;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundSearchIpAddress extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundSearchIpAddress.class);


	public int doStartTag() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			if (!theInboundSearch.commitNeeded) {
				pageContext.getOut().print(theInboundSearch.getIpAddress());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for ipAddress tag ");
		}
		return SKIP_BODY;
	}

	public String getIpAddress() throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			return theInboundSearch.getIpAddress();
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundSearch for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for ipAddress tag ");
		}
	}

	public void setIpAddress(String ipAddress) throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setIpAddress(ipAddress);
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for ipAddress tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for ipAddress tag ");
		}
	}

}
