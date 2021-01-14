How to listen to console.log from Chrome to Katalon Studio via Chrome DevTools Protocol
======

## Problem to solve

Calling `console.log(message)` API in JavaScript in web pages is ubiquitous. You can see the messages in the Console tabs of Browser Developer Tools. Russ Thomas gave us [a nice tutorial about the Developer Tools](https://forum.katalon.com/t/how-to-use-the-browser-developer-tools-f12-devtools/34329). It is natural that WebUI testers want to transfer the messages that apprears in the Broswer console into automated test scripts like Katalon Studio's Test Cases. I would show you an example of `console.log`:

![404NotFound](images/404NotFound.png)

In the Console tab you find a line

>`2021/1/14 10:47:43`

This is the output of `console.log()` performed by a javascript in the web page.


[Once upon a time](https://forum.katalon.com/t/console-logging-selenium-and-katalon/12100/7), there was an API called "Selenium WebDriver API" which supported "Logging API". Using its Logging API, automated-test scripts could read messages writen by JavaScript 'console.log(msssage)' calls in browser. However nowadays (I am writing this at Jan 2021) "Selenium WebDrvier API" is already obsolete. W3C has defined at 2018 the standard [WebDriver](https://www.w3.org/TR/webdriver1/) specification. The standard spec did not include "Logging API". So all browsers have ceased support for "Logging API".

However, we UI testers still want to read the `console.log()` in the automated UI test scripts. Is there any feasible approach?

## How to solve it

Chrome DevTools Protocol (a.k.a. CDP) supports [ConsoleAPICalled](https://chromedevtools.github.io/devtools-protocol/tot/Runtime/#event-consoleAPICalled) interface. The Chrome browser supports CDP fully. If we can utilize CDP in Katalon Studio Test Case, it would be possible to transfer the `console.log()` messages from Chrome browser to the test script. 

Also FireFox browser provides [Remote Debugging Protocol](https://firefox-source-docs.mozilla.org/devtools/backend/protocol.html#remote-debugging-protocol) which supports the subset of CDP.

To me it seems that major browser vendors (Google, Mozilla) are willing to support remote interface like CDP apart from the W3C WebDrvier standard. I think that CDP is worth studying.

## Solution description

### code 

You can see a working sample Test case:

- [Test Cases/Listen to Console log](Scripts/Listen%20to%20Console%20log/Script1610443410700.groovy)

Let me copy the partial source and paste here for easier reference:

```
WebUI.openBrowser('')
WebUI.setViewPortSize(1024,768)

/** Get DevTools Service to a tab in Chrome browser opened by Katalon Studio */
ChromeDevToolsService devToolsService = CdpUtils.getService()

/** Get indivisual CDP commands */
Page page = devToolsService.getPage()
Runtime runtime = devToolsService.getRuntime()
page.enable()
runtime.enable()

/** Listen to the events that JavaScript console.log(msg) was called on the web page */
runtime.onConsoleAPICalled({ ConsoleAPICalled event ->
	List<RemoteObject> args = event.getArgs()
	for (rm in args) {
		KeywordUtil.logInfo(">>>${type.toString()} ${rm.getValue()}")
	}
})

/** Wait for on load event */
page.onLoadEventFired({ event ->
	// Evaluate javascript
	devToolsService.close()
})

/** Navigate to the Application Under Test */
WebUI.navigateToUrl('http://demoaut-mimic.kazurayam.com/')

devToolsService.waitUntilClosed()

WebUI.delay(3)

WebUI.closeBrowser()
```

When I ran this test case, I saw following output in the Katalon Log View:

```
...
2021-01-14 16:46:05.220 DEBUG testcase.Listen to Console log           - 11: navigateToUrl("http://demoaut-mimic.kazurayam.com/")
2021-01-14 16:46:05.796 INFO  com.kms.katalon.core.util.KeywordUtil    - >>>LOG content script: Katalon Waiter v.2 is up and running !
2021-01-14 16:46:06.845 INFO  com.kms.katalon.core.util.KeywordUtil    - >>>LOG 2021/1/14 7:46:6
...
```

This web page called 'console.log()' twice, and emitted 2 messages:
- `>>>LOG content script: Katalon Waiter v.2 is up and running !`
- `>>>LOG 2021/1/14 7:46:6`

Printing the messages are too simplee. You can process the messages more sophisticated way. You would be able to make assertions over the messages. What should you do? It is totally up to you.

### Prerequisites

You need to install the "Chrome DevTools Protocol Integration" plugin into your local Katalon Studio.
It is avaliable fre at
https://store.katalon.com/product/144/Chrome-DevTools-Protocol-Integration

I tested this project using Katalon Studio version 7.6.6.

