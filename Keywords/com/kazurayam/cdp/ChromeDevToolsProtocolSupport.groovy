package com.kazurayam.cdp

import org.openqa.selenium.HasCapabilities
import org.openqa.selenium.WebDriver

import com.github.kklisura.cdt.services.ChromeDevToolsService
import com.github.kklisura.cdt.services.ChromeService
import com.github.kklisura.cdt.services.impl.ChromeServiceImpl
import com.github.kklisura.cdt.services.types.ChromeTab

/**
 * A set of helper methods for a E2E test in Java to activate com.github.kklisura.cdt.services.ChromeDevToolsSerivce
 * which enables the test to save the MIME HTML file of a web page rendered on a Chrome browser. It is assumed that
 * the browser has been opened by Selenium WebDriver.
 *
 * The original is written by Katalon, hosted at
 * https://github.com/katalon-studio/katalon-studio-chrome-devtools-protocol-plugin/blob/master/Include/scripts/groovy/com/katalon/cdp/CdpUtils.groovy
 * but I modified it to make it independent on the Katalon API
 *
 * @author kazurayam
 */
public class ChromeDevToolsProtocolSupport {

	public static String[] getServiceEndpoint(WebDriver driver) {
		HasCapabilities hc = ((driver) as HasCapabilities)
		Map<?, ?> m = hc.getCapabilities().asMap()
		String cap = 'goog:chromeOptions'
		m = m.get(cap)
		if (m != null) {
			String da = m.get('debuggerAddress')
			String[] daElements = da.split(':')
			return daElements
		} else {
			throw new IllegalArgumentException("Capability \"${cap}\" is not found in the current browser")
		}
	}

	public static ChromeDevToolsService getService(WebDriver driver) {
		String[] daElements = getServiceEndpoint(driver)
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
