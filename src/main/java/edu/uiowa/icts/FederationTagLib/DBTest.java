package edu.uiowa.icts.FederationTagLib;

import javax.servlet.jsp.JspWriter;
import java.sql.Statement;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@SuppressWarnings("serial")
public class DBTest extends FederationTagLibBodyTagSupport {

	private static final Log log = LogFactory.getLog( DBTest.class );

	public int doStartTag() throws JspException {
		try { 
			JspWriter out = pageContext.getOut();
			Statement statement = getConnection().createStatement();
			boolean rs = statement.execute("select now()");
			if (rs) { 
				out.print("SUCCESS");
			} else {
				out.print("FAILED");
			}
		} catch ( Exception e ) {
			log.error( "Connection Failed", e );
			throw new JspTagException("Connection Failed: " + e);
		} finally {
			freeConnection();
		}
		return SKIP_BODY;
	}
}