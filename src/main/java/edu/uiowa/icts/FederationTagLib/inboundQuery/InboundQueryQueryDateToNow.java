package edu.uiowa.icts.FederationTagLib.inboundQuery;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundQueryQueryDateToNow extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundQueryQueryDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			theInboundQuery.setQueryDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getQueryDate() throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			return theInboundQuery.getQueryDate();
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryDate tag ");
		}
	}

	public void setQueryDate( ) throws JspTagException {
		try {
			InboundQuery theInboundQuery = (InboundQuery)findAncestorWithClass(this, InboundQuery.class);
			theInboundQuery.setQueryDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing InboundQuery for queryDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundQuery for queryDate tag ");
		}
	}

}
