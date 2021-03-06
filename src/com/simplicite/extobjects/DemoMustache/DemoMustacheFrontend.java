package com.simplicite.extobjects.DemoMustache;

import com.simplicite.util.AppLog;
import com.simplicite.util.ExternalObject;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.Parameters;
import com.simplicite.webapp.web.BootstrapWebPage;

/**
 * Mustache(R) templating custom frontend UI
 */
public class DemoMustacheFrontend extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		try {
			boolean pub = isPublic();
			setDecoration(!pub);
			String render = getName() + ".render(" + params.toJSONObject().put("bannerURL", HTMLTool.getResourceImageURL(this, "BANNER")).put("pub", pub).toString() + ");";
			if (pub) { // Public page version (standalone Bootstrap page)
				BootstrapWebPage wp = new BootstrapWebPage(params.getRoot(), getDisplay());
				wp.setFavicon(HTMLTool.getResourceIconURL(this, "FAVICON"));
				wp.appendAjax(true);
				wp.appendMustache();
				wp.appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
				wp.appendCSSInclude(HTMLTool.getResourceCSSURL(this, "STYLES"));
				wp.append(HTMLTool.getResourceHTMLContent(this, "HTML"));
				wp.setReady(render);
				return wp.toString();
			} else { // Private page version
				addMustache();
				return javascript(render);
			}
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}
