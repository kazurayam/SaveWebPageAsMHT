
import com.github.kklisura.cdt.protocol.commands.Page
import com.github.kklisura.cdt.protocol.commands.Runtime
import com.github.kklisura.cdt.protocol.types.runtime.Evaluate
import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.katalon.cdp.CdpUtils
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Dumps the HTML from Page in Chrome browser.
 * 
 * The Original is
 * https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/DumpHtmlFromPageExample.java
 */

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)

/** Get DevTools service to a tab in Chrome browser
 */
ChromeDevToolsService devToolsService = CdpUtils.getService()

/** Get indivisual CDP commands
 */
Page page = devToolsService.getPage()
Runtime runtime = devToolsService.getRuntime()

page.enable()
runtime.enable()

/** Wait for on load event
 */
page.onLoadEventFired({ event ->
	// Evaluate javascript
	Evaluate evaluation = runtime.evaluate("document.documentElement.outerHTML")
	println "${evaluation.getResult().getValue()}"
	devToolsService.close()
})

/** Navigate to the Application Under Test
 */
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/')

devToolsService.waitUntilClosed()

// finito
WebUI.closeBrowser()

