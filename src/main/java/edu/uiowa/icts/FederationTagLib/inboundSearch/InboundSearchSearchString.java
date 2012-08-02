package edu.uiowa.icts.FederationTagLib.inboundSearch;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class InboundSearchSearchString extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(InboundSearchSearchString.class);


	public int doStartTag() throws JspException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			if (!theInboundSearch.commitNeeded) {
				pageContext.getOut().print(theInboundSearch.getSearchString());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for searchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchString tag ");
		}
		return SKIP_BODY;
	}

	public String getSearchString() throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			return theInboundSearch.getSearchString();
		} catch (Exception e) {
			log.error(" Can't find enclosing InboundSearch for searchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchString tag ");
		}
	}

	public void setSearchString(String searchString) throws JspTagException {
		try {
			InboundSearch theInboundSearch = (InboundSearch)findAncestorWithClass(this, InboundSearch.class);
			theInboundSearch.setSearchString(searchString);
		} catch (Exception e) {
			log.error("Can't find enclosing InboundSearch for searchString tag ", e);
			throw new JspTagException("Error: Can't find enclosing InboundSearch for searchString tag ");
		}
	}

}
