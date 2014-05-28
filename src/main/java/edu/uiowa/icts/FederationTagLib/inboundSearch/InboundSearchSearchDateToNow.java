package edu.uiowa.icts.FederationTagLib.inboundSearch;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

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
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing InboundSearch for searchDate tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
			}

		}
		return SKIP_BODY;
	}

	public Date getSearchDate() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			return theInboundSearch.getSearchDate();
		} catch (Exception e) {

			log.error("Can't find enclosing InboundSearch for searchDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing InboundSearch for searchDate tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
			}

		}
	}

	public void setSearchDate() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setSearchDateToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing InboundSearch for searchDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing InboundSearch for searchDate tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing InboundSearch for searchDate tag ");
			}

		}
	}
}