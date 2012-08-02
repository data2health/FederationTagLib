package edu.uiowa.icts.FederationTagLib.site;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class SiteAggregateQueryUrl extends FederationTagLibTagSupport {
	private static final Log log = LogFactory.getLog(SiteAggregateQueryUrl.class);


	public int doStartTag() throws JspException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			if (!theSite.commitNeeded) {
				pageContext.getOut().print(theSite.getAggregateQueryUrl());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Site for aggregateQueryUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for aggregateQueryUrl tag ");
		}
		return SKIP_BODY;
	}

	public String getAggregateQueryUrl() throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			return theSite.getAggregateQueryUrl();
		} catch (Exception e) {
			log.error(" Can't find enclosing Site for aggregateQueryUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for aggregateQueryUrl tag ");
		}
	}

	public void setAggregateQueryUrl(String aggregateQueryUrl) throws JspTagException {
		try {
			Site theSite = (Site)findAncestorWithClass(this, Site.class);
			theSite.setAggregateQueryUrl(aggregateQueryUrl);
		} catch (Exception e) {
			log.error("Can't find enclosing Site for aggregateQueryUrl tag ", e);
			throw new JspTagException("Error: Can't find enclosing Site for aggregateQueryUrl tag ");
		}
	}

}
