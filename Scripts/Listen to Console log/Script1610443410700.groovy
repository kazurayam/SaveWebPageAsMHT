import java.time.format.DateTimeFormatter

import com.github.kklisura.cdt.protocol.commands.Page
import com.github.kklisura.cdt.protocol.commands.Runtime
import com.github.kklisura.cdt.protocol.events.runtime.ConsoleAPICalled
import com.github.kklisura.cdt.protocol.events.runtime.ConsoleAPICalledType
import com.github.kklisura.cdt.protocol.types.runtime.RemoteObject
import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kazurayam.cdp.ChromeDevToolsProtocolSupport
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

/**
 * This is a Test Case script that only works in Katalon Studio.
 * You are supposed to open Chrome browser for this to run.
 * 
 * This script opens browser, navigates to a web page.
 * This script listens to the event that "console.log() in JavaScript was called".
 * It prints the log message passed from browser into the Katalon's Log view.
 * 
 * You need to install the Chrome DevTools Protocol Integration plugin
 * into you Katalon Studio. The plugin is available at
 * https://store.katalon.com/product/144/Chrome-DevTools-Protocol-Integration
 */
Gson gson = new GsonBuilder().setPrettyPrinting().create()

WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)

/** Get DevTools Service to a tab in Chrome browser opened by Katalon Studio */
WebDriver driver = DriverFactory.getWebDriver()
ChromeDevToolsService devToolsService = ChromeDevToolsProtocolSupport.getService(driver)

/** Get indivisual CDP commands */
Page page = devToolsService.getPage()
page.enable()
Runtime runtime = devToolsService.getRuntime()
runtime.enable()

/** Listen to the events that JavaScript console.log(msg) was 
 * called on the web page
 */
runtime.onConsoleAPICalled({ ConsoleAPICalled event ->
	// println gson.toJson(event)     // for debug
	ConsoleAPICalledType type = event.getType()
	List<RemoteObject> args = event.getArgs()
	for (rm in args) {
		KeywordUtil.logInfo(">>>${type.toString()} ${rm.getValue()}")
	}
})

/** Wait for on load event */
page.onLoadEventFired({ event ->
	devToolsService.close()
})


/** Navigate to the Application Under Test */
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/')
devToolsService.waitUntilClosed()
WebUI.delay(3)
WebUI.closeBrowser()
