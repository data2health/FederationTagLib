package edu.uiowa.icts.FederationTagLib.outboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class OutboundQueryQueryDateToNow extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(OutboundQueryQueryDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			theOutboundQuery.setQueryDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing OutboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getQueryDate() throws JspTagException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			return theOutboundQuery.getQueryDate();
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryDate tag ");
		}
	}

	public void setQueryDate( ) throws JspTagException {
		try {
			OutboundQuery theOutboundQuery = (OutboundQuery)findAncestorWithClass(this, OutboundQuery.class);
			theOutboundQuery.setQueryDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing OutboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing OutboundQuery for queryDate tag ");
		}
	}

}
