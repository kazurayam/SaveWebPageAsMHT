import org.openqa.selenium.WebDriver

import com.github.kklisura.cdt.protocol.commands.Network
import com.github.kklisura.cdt.protocol.commands.Page
import com.github.kklisura.cdt.protocol.events.network.RequestWillBeSent
import com.github.kklisura.cdt.protocol.events.network.ResponseReceived
import com.github.kklisura.cdt.protocol.events.page.LoadEventFired
import com.github.kklisura.cdt.protocol.types.network.Response
import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.kazurayam.cdp.ChromeDevToolsProtocolSupport
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * Studied 
 * https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/LogRequestsExample.java
 */

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)

/** Get DevTools service to a tab in Chrome browser
 */
WebDriver driver = DriverFactory.getWebDriver()
ChromeDevToolsService devToolsService = ChromeDevToolsProtocolSupport.getService(driver)

/** Get indivisual CDP commands
 */
Page page = devToolsService.getPage()
Network network = devToolsService.getNetwork()
page.enable()
network.enable()

/** Log requests */
network.onRequestWillBeSent({ RequestWillBeSent event ->
	println "request : ${event.getRequest().getMethod()} ${event.getRequest().getUrl()}"
})

/** Log responses */
network.onResponseReceived({ ResponseReceived event ->
	Response resp = event.getResponse()
	println "response: ${resp.getStatus()} ${resp.getStatusText()} ${resp.getUrl()}"
})

/** Wait for on load event */
page.onLoadEventFired({ LoadEventFired devent ->
	// Evaluate javascript
	devToolsService.close()
})

/** Navigate to the Application Under Test */
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/')

devToolsService.waitUntilClosed()

// finito
WebUI.delay(3)
WebUI.closeBrowser()

