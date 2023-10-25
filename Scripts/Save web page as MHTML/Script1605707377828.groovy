import com.github.kklisura.cdt.protocol.commands.Emulation
import com.github.kklisura.cdt.protocol.commands.Page
import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.kazurayam.cdp.ChromeDevToolsProtocolSupport
import com.kms.katalon.core.driver.DriverType
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.util.KeywordUtil

/**
 * Origin:
 * https://github.com/katalon-studio/katalon-studio-chrome-devtools-protocol-plugin/blob/master/Scripts/Capture%20full%20page/Script1572950577748.groovy
 */
WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)
WebUI.navigateToUrl('http://demoaut.katalon.com/')

switch ((WebUIDriverType)DriverFactory.getExecutedBrowser()) {
	case WebUIDriverType.CHROME_DRIVER:
	case WebUIDriverType.HEADLESS_DRIVER:
		ChromeDevToolsService cdts = ChromeDevToolsProtocolSupport.getService(DriverFactory.getWebDriver())
		saveWebPageAsMHTML(cdts, 'tmp/snapshot.mht')
		break
	default:
		DriverType dt = DriverFactory.getExecutedBrowser()
		KeywordUtil.markFailed("Browser is ${(WebUIDriverType)dt}; this doesn't support Chrome DevTools Protocol")
		break
}
WebUI.closeBrowser()

def saveWebPageAsMHTML(ChromeDevToolsService devToolsService, String outputFileName) {
	Page page = devToolsService.getPage()
	double width = 960
	double height = WebUI.executeJavaScript('return document.body.scrollHeight', null)
	Emulation emulation = devToolsService.getEmulation()
	emulation.setDeviceMetricsOverride(Double.valueOf(width).intValue(), Double.valueOf(height).intValue(), 1.0, Boolean.FALSE)
	emulation.setScrollbarsHidden(Boolean.TRUE)
	// convert the loaded web page into an instance of MHTML
	String mhtml = page.captureSnapshot()
	save(outputFileName, mhtml)
	emulation.setScrollbarsHidden(Boolean.FALSE)
}

def save(String filename, String data) {
	FileOutputStream fos = null
	try {
		File file = new File(filename)
		fos = new FileOutputStream(file)
		fos.write(data.getBytes())
	}
	catch (IOException e) {
		e.printStackTrace()
	}
	finally {
		if (fos != null) {
			try {
				fos.flush()
				fos.close()
			}
			catch (IOException e) {
				e.printStackTrace()
			}
		}
	}
	
}
