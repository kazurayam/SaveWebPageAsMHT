package com.katalon.cdp

import org.openqa.selenium.HasCapabilities
import org.openqa.selenium.WebDriver

import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.github.kklisura.cdt.services.ChromeService
import com.github.kklisura.cdt.services.impl.ChromeServiceImpl
import com.github.kklisura.cdt.services.types.ChromeTab
import com.kms.katalon.core.webui.driver.DriverFactory

public class CdpUtils {
	
	public static String[] getServiceEndpoint() {
		WebDriver driver = DriverFactory.getWebDriver()
		
		HasCapabilities hc = ((driver) as HasCapabilities)

		Map<?, ?> m = hc.getCapabilities().asMap()

		m = m.get('goog:chromeOptions')

		String da = m.get('debuggerAddress')

		String[] daElements = da.split(':')
		
		return daElements
	}

	public static ChromeDevToolsService getService() {
		
		WebDriver driver = DriverFactory.getWebDriver()

		String[] daElements = getServiceEndpoint()

		String host = daElements[0]

		int port = Integer.parseInt(daElements[1])

		ChromeService cs = new ChromeServiceImpl(host, port)

		ChromeTab t = cs.getTabs().find({
			ChromeTab tab = ((it) as ChromeTab)

			if (tab.getUrl().equals(driver.getCurrentUrl())) {
				return tab
			}
		})

		ChromeDevToolsService cdts = cs.createDevToolsService(t)

		return cdts
	}
}