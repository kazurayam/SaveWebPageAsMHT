import com.github.kklisura.cdt.protocol.commands.Network
import com.github.kklisura.cdt.protocol.commands.Page
import com.github.kklisura.cdt.protocol.commands.Runtime
import com.github.kklisura.cdt.protocol.events.network.RequestWillBeSent
import com.github.kklisura.cdt.protocol.types.runtime.Evaluate
import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.katalon.cdp.CdpUtils
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * This test case script demonstrates how to let Chrome browser
 * inform it of HTTP requests from Chrome to URLs. 
 * 
 * Example output:
 * 

request: GET http://demoaut-mimic.kazurayam.com/
request: GET https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css
request: GET https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css
request: GET http://demoaut.katalon.com//css/theme.css
request: GET https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css
request: GET https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic
request: GET https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.3/jquery.min.js
request: GET https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js
request: GET https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js
request: GET http://demoaut.katalon.com//js/theme.js
request: GET chrome-extension://hdholmmobnfnkjgnbmgakgjlbkpjdgkj/content/wait.js
request: GET https://katalon-demo-cura.herokuapp.com//css/theme.css
request: GET https://katalon-demo-cura.herokuapp.com//js/theme.js
request: GET https://katalon-demo-cura.herokuapp.com//img/header.jpg
request: GET https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/fonts/fontawesome-webfont.woff2?v=4.7.0
request: GET https://fonts.gstatic.com/s/sourcesanspro/v14/6xK3dSBYKcSV-LCoeQqfX1RYOo3qOK7lujVj9w.woff2
request: GET https://fonts.gstatic.com/s/sourcesanspro/v14/6xKydSBYKcSV-LCoeQqfX1RYOo3ig4vwlxdu3cOWxw.woff2

 * 
 * The Original is
 * https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/LogRequestsExample.java
 */

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)

/** Get DevTools service to a tab in Chrome browser
 */
ChromeDevToolsService devToolsService = CdpUtils.getService()

/** Get indivisual CDP commands
 */
Page page = devToolsService.getPage()
Network network = devToolsService.getNetwork()
Runtime runtime = devToolsService.getRuntime()

page.enable()
network.enable()
runtime.enable()

/** Log requests with onRequestWillBeSent event handler
 */
network.onRequestWillBeSent({ RequestWillBeSent event ->
	println "request: ${event.getRequest().getMethod()} ${event.getRequest().getUrl()}"
})

/** Wait for on load event
 */
page.onLoadEventFired({ event ->
	// Evaluate javascript
	devToolsService.close()
})

/** Navigate to the Application Under Test
 */
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/')

devToolsService.waitUntilClosed()

// finito
WebUI.closeBrowser()

