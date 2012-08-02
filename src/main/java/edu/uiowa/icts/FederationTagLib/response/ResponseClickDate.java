package edu.uiowa.icts.FederationTagLib.response;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.icts.FederationTagLib.FederationTagLibTagSupport;

@SuppressWarnings("serial")
public class ResponseClickDate extends FederationTagLibTagSupport {
	String type = "DATE";
	String dateStyle = "DEFAULT";
	String timeStyle = "DEFAULT";
	String pattern = null;
	private static final Log log = LogFactory.getLog(ResponseClickDate.class);


	public int doStartTag() throws JspException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			if (!theResponse.commitNeeded) {
				String resultString = null;
				if (theResponse.getClickDate() == null) {
					resultString = "";
				} else {
					if (pattern != null) {
						resultString = (new SimpleDateFormat(pattern)).format(theResponse.getClickDate());
					} else if (type.equals("BOTH")) {
						resultString = DateFormat.getDateTimeInstance(formatConvert(dateStyle),formatConvert(timeStyle)).format(theResponse.getClickDate());
					} else if (type.equals("TIME")) {
						resultString = DateFormat.getTimeInstance(formatConvert(timeStyle)).format(theResponse.getClickDate());
					} else { // date
						resultString = DateFormat.getDateInstance(formatConvert(dateStyle)).format(theResponse.getClickDate());
					}
				}
				pageContext.getOut().print(resultString);
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getClickDate() throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			return theResponse.getClickDate();
		} catch (Exception e) {
			log.error(" Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
	}

	public void setClickDate(Date clickDate) throws JspTagException {
		try {
			Response theResponse = (Response)findAncestorWithClass(this, Response.class);
			theResponse.setClickDate(clickDate);
		} catch (Exception e) {
			log.error("Can't find enclosing Response for clickDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing Response for clickDate tag ");
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toUpperCase();
	}

	public String getDateStyle() {
		return dateStyle;
	}

	public void setDateStyle(String dateStyle) {
		this.dateStyle = dateStyle.toUpperCase();
	}

	public String getTimeStyle() {
		return timeStyle;
	}

	public void setTimeStyle(String timeStyle) {
		this.timeStyle = timeStyle.toUpperCase();
	}

	public static int formatConvert(String stringValue) {
		if (stringValue.equals("SHORT"))
			return DateFormat.SHORT;
		if (stringValue.equals("MEDIUM"))
			return DateFormat.MEDIUM;
		if (stringValue.equals("LONG"))
			return DateFormat.LONG;
		if (stringValue.equals("FULL"))
			return DateFormat.FULL;
		return DateFormat.DEFAULT;
	}

}
