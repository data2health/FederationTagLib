package edu.uiowa.icts.FederationTagLib.inboundSearch;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundSearchSearchDateToNow extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundSearchSearchDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setSearchDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundSearch for searchDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getSearchDate() throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			return theInboundSearch.getSearchDate();
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for searchDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
		}
	}

	public void setSearchDate( ) throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setSearchDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for searchDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
		}
	}

}
