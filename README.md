Saving Web Page as MHTML in Katalon Studio
=====

This is a [Katalon Studio](https://www.katalon.com/) project for demonstration purpose. You can download the zip file from the [Releases](https://github.com/kazurayam/SaveWebPageAsMHT/releases) page, unzip and open it with your local Katalon Studio.

This project was developed using Katalon Studio version 7.2.2 on Mac Catalina.

## Problem to solve

I want to automate the following tasks by scripts in Katalon Studio:

1. to take snapshot of current DOM of Web Page in browser, save it into a local file in the [MHTML](https://en.wikipedia.org/wiki/MHTML) format.

2. to take full screenshots of a web page in multiple viewport sizes (with*height) just as I can do in Chrome DevTools using [Device Mode](https://www.deconetwork.com/blog/how-to-take-full-webpage-screenshots-instantly/) like Galaxy S5, Pixel 2, iPhone 6/7/8, iPad. I want screenshots in the size of PC display of XGA (1024px,768px) and SXGA (1280px,1024px) as well. I want to save the screenshots as *.png files.

## Demo

### Prerequisite

1. Into your Katalon Studio, you need to install the Katalon Studio Plugin: [Chrome DevTools Protocal Integration](https://store.katalon.com/product/144/Chrome-DevTools-Protocol-Integration)
2. Download the zip file of this project from the zip file from the [Releases](https://github.com/kazurayam/SaveWebPageAsMHT/releases) page, unzip and open it with your local Katalon Studio.
3. I tested this project with KS ver 7.2.2, however the older versions will be ok as well.
4. You should choose `Google Chrome` as browser to run the demo scripts

### Description of the demos

#### Save web page as MHTML

1. Run [Test Cases/Save web page as MHTML](Scripts/Save web page as MHTML/Script1605707377828.groovy)
2. It will open a web page 'http://demoaut.katalon.com/' and save it as a single MHTML file
3. It will write a file [tmp/snapshot.mht](tmp/snapshot.mht)

#### Capture full page in multiple viewport sizes

1. Run [TestCases/Capture full page](Scripts/Capture full page/Script1605616830298.groovy)
2. It will open a web page `http://demoaut-mimic.kazurayam.com/`
3. It will take full page screenshots while resizing the viewport to the size of XGA/SXGA/iPhone6/iPad
4. It will write 4 files: 
- [tmp/screenshot.png](tmp/screenshot.png)
- [tmp/screenshot_SXGA.png](tmp/screenshot_SXGA.png)
- [tmp/screenshot_iPhone6.png](tmp/screenshot_iPhone6.png)
- [tmp/screenshot_iPad.png](tmp/screenshot_iPad.png)

## Background

### Motivations

In October 2020 in a [post](https://forum.katalon.com/t/hey-katalon-7-8-beta-is-out-with-new-cool-features-its-cooler-to-have-your-feedback/48751) in the Katalon Forum, Katalon 7.8 beta was announced where included a new Feater called *Time Capsule*. 

>Time Capsule captures the state of your application when the test fails. No not a picture of your application when the test fails but the actual HTML, CSS of the application. Yes, that also includes the visual aspect of your application as well.

ThanTo, Katalon Developer, added a description about this feature.

>Time Capsule captures the MHTML of the page when a test fails. 

[MHTML](https://en.wikipedia.org/wiki/MHTML)? What is it? 

Quoting from [Wikipedia](https://en.wikipedia.org/wiki/MHTML):

>MHTML, an initialism of MIME encapsulation of aggregate HTML documents, is a web page archive format used to combine, in a single computer file, the HTML code and its companion resources (such as images, Flash animations, Java applets, and audio and video files) that are represented by external hyperlinks in the web page's HTML code. The content of an MHTML file is encoded using the same techniques that were first developed for HTML email messages, using the MIME content type multipart/related.[1] MHTML files use a .mhtml or .mht filename extension.

OK. Now I understand what MHTML is. 

Inspired by the Time Capsule, I got a new requirement to myself. I want **a Custom Keyword which enables me to save snapshot of web page opened in browser into a single file in MHTML format**. I want to use the keyword as often as I like during WebUI testing in Katalon Studio. I am not sure but Time Capsule in KS ver7.8 is not going to offer such casual keyword.

For what? --- For my [VisualTestingInKatalonStudio](https://forum.katalon.com/t/visual-testing-in-katalon-studio/13361) project. The current version of this project takes web page screenshots in raster images of PNG format. I thought that MHTML might be better than PNG for recording the web page. Especially when I want to do [Visual Testing for multiple viewPortSizes](https://forum.katalon.com/t/hey-katalon-7-8-beta-is-out-with-new-cool-features-its-cooler-to-have-your-feedback/48751/8), the MHTML would be useful.

I had another motivation. In [another disucusion](https://forum.katalon.com/t/update-with-katalon-studio-7-7-early-release-of-katalon-testops-visual-testing-image-comparison/45557/8) in the Katalon Forum I was informed of a commercial product [Percy, Visual Testing as a service](https://docs.percy.io/v1/docs/faq). Percy does visual page comparison, but they wrote "it is not designed to accept screenshots, but instead captures DOM snapshots and page assets (CSS, images, etc.)". --- How they do that? In October 2020 I had no idea. But now in November I have got a guess --- Percy possibly utilizes MHTML.

So I have studied how to save pages in MHTML within Katalon Studio. I have learned some points that might be interest Katalon users. I have developed a demo project. Here I will share my findings.



### Researches

#### Chrome DevTools Protocol supports taking snapshot of current DOM

I happend to find the following thread.

- https://github.com/puppeteer/puppeteer/issues/3575

This suggests that Chrome DevTools Protocol (also known as **CDP**) supports [`Page.captureSnapshot`](https://chromedevtools.github.io/devtools-protocol/tot/Page/#method-captureScreenshot) method, which turns the current DOM of web page into a String as MHTML. 

This looked exactly what I want to do! I realised that my question was how to make use of `Page.captureSnapshot` method of CDP in Katalon Studio.

#### Chrome DevTools Protocol (CDP)

Quoting from :[Chrome DevTools Protocol](https://chromedevtools.github.io/devtools-protocol/) 

>The Chrome DevTools Protocol allows for tools to instrument, inspect, debug and profile Chromium, Chrome and other Blink-based browsers. Many existing projects currently use the protocol. The Chrome DevTools uses this protocol and the team maintains its API.

>Instrumentation is divided into a number of domains (DOM, Debugger, Network etc.). Each domain defines a number of commands it supports and events it generates. Both commands and events are serialized JSON objects of a fixed structure.




#### Katalon Plugin: Chrome DevTools Protocol Integration

In Nov 2019, Katalon LLC released a Katalon Plugin [Chrome DevTools Protocol Integration](https://store.katalon.com/product/144/Chrome-DevTools-Protocol-Integration). In its [README](https://github.com/katalon-studio/katalon-studio-chrome-devtools-protocol-plugin), it writes:

>Integrate Chrome Devtools Protocol with Katalon Studio using https://github.com/kklisura/chrome-devtools-java-client.

This plugin bundles the jar of [Chrome DevTools Java Client](https://github.com/kklisura/chrome-devtools-java-client) and make it available to Katalon projects.

And this plugin provides a class [`com.katalon.cdp.CdpUtils`](Include/scripts/groovy/com/katalon/cdp/CdpUtils.groovy) which implements a factory method that creates an instance of `com.github.kklisure.cdt.services.ChromeDevToolsSerivce` from `com.kms.katalon.core.webui.dirver.DriverFactory`. Provided with this utility, we can use CDP Java Client API together with Katalon's WebUI API.

#### Chrome DevTools Java Client by Kenan Clisura

Quoting from its [README](https://github.com/kklisura/chrome-devtools-java-client):

>Chrome DevTools Java Client is a DevTools client - in Java. (: It can be used for instrumenting, inspecting, debuging and profiling Chromium, Chrome and other Blink-based browsers. 

The list of [examples](https://github.com/kklisura/chrome-devtools-java-client/tree/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples) would best describe what we can do using Chrome DevTools Protocol in Katalon Studio.

- [BlockUrlGivenPatternExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/BlockUrlGivenPatternExample.java) --- opening `http://github.com` while blocking some links from fetching; specifed by Ant Fileset pattern such as `**/*.css`, `**/*.png`, `**/*.svg`
- [BlockUrlExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/BlockUrlsExample.java) --- opening `http://github.com` while blocking some links from fetching; specifed by filename extentions such as `.css`, `.png`
- [CssCoverageExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/CssCoverageExample.java) --- call up [CSS Coverage](https://developers.google.com/web/tools/chrome-devtools/coverage) inspection to find unused CSS Code
- [DumpHtmlFromPageExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/DumpHtmlFromPageExample.java) --- print HTML source (`document.documentElement.outerHTML`) of the page
- [FullPageScreenshotExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/FullPageScreenshotExample.java) --- taks a full page screenshot of `http://github.com` and save it into a local file in PNG format
- [HighlightElementExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/HighlightElementExample.java) --- Highlits an element in the same way as the Chrome inspector does
- [IncreasedIncomingBufferInTyrusExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/IncreasedIncomingBufferInTyrusExample.java) --- quoted from the source: *This example demostrates how to increase incoming buffer size in Tyrus client. Use the following example to fix the issues with Tyrus Buffer overflow. This issue occurs when the incoming size is larger than the incoming buffer in Tyrus client at which the client disconnects.*
- [InterceptAndBlockUrlsExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/InterceptAndBlockUrlsExample.java) --- Intercept and block per URL.
- [LogRequestExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/LogRequestsExample.java) --- Log HTTP requests to linked URLs from a page, print the detail such as Method and URL.
- [PassingCustomeHeadersToRequests.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/PassingCustomHeadersToRequests.java) --- adding HTTP headers to the intercepted requests. E.g, you can insert `Authorization` header for Basic AUTH.
- [PerformacneMetricsExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/PerformanceMetricsExample.java) --- show [Performance metrics measured by Chrome DevTools](https://developers.google.com/web/tools/chrome-devtools/evaluate-performance)
- [PrintingPageToPdf.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/PrintingPageToPdf.java) --- print web page as a PDF to save into a local file.
- [SimpleNavigateToUrlExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/SimpleNavigateToUrlExample.java) --- do the same as `WebUI.navigateToUrl(...)`
- [TakeScreenshotExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/TakeScreenshotExample.java) --- take the screenshot of the current viewport
- [TracingExample.java](https://github.com/kklisura/chrome-devtools-java-client/blob/master/cdt-examples/src/main/java/com/github/kklisura/cdt/examples/TracingExample.java) --- show what Chrome's [Trace Event Profile Tools (about:tracing)](https://www.chromium.org/developers/how-tos/trace-event-profiling-tool) records for diagnosing performance problems.



## Conclusion

Katalon Studio has been solely dependent on Selenium WebDriver for Web UI testing.
What we could do in Katalon Studio stays within the scope of WebDriver. 
Now I see, Chrome DevTools Protocol enables Web UI testers to do something exceeding the capability of WebDriver. For example, we can save web page into MHTML easily. CDP is interesting.
I would look at CDP more deeply looking forward to its better use in Katalon Studio.
