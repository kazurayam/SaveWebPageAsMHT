import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import java.awt.Robot
import java.awt.event.KeyEvent
import org.apache.commons.lang3.RandomStringUtils


String url = "http://demoaut-mimic.kazurayam.com/"

WebUI.openBrowser('')
WebUI.navigateToUrl(url)
WebDriver driver = DriverFactory.getWebDriver()

// add the following line to ensure the page is fully loaded
String pageSource = driver.getPageSource()

Robot robot = new Robot()

// Press META+S
robot.keyPress(KeyEvent.VK_META)
robot.keyPress(KeyEvent.VK_S)
robot.keyRelease(KeyEvent.VK_META)
robot.keyRelease(KeyEvent.VK_S)

Thread.sleep(5000)

// Generate a 2 digit random number and split it into two separate chars
String random = RandomStringUtils.randomNumeric(2)
println(random)
char charOne = random.charAt(0)
char charTwo = random.charAt(1)

// Save As window has opened and the focus is on the file name field.
// Click right arrow key to go to the last of the already present name
robot.keyPress(KeyEvent.VK_RIGHT)
robot.keyRelease(KeyEvent.VK_RIGHT)

// Append the generated random number to the name
robot.keyPress(getKeyEvent(charOne))
robot.keyRelease(getKeyEvent(charOne))
robot.keyPress(getKeyEvent(charTwo))
robot.keyRelease(getKeyEvent(charTwo))

Thread.sleep(5000)

// Press ENTER
robot.keyPress(KeyEvent.VK_ENTER)
robot.keyRelease(KeyEvent.VK_ENTER)

WebUI.delay(3)
WebUI.closeBrowser()



int getKeyEvent(char key) {
	switch (key) {
		case '1':
			return KeyEvent.VK_1
		case '2':
			return KeyEvent.VK_2
		case '3':
			return KeyEvent.VK_3
		case '4':
			return KeyEvent.VK_4
		case '5':
			return KeyEvent.VK_5
		case '6':
			return KeyEvent.VK_6
		case '7':
			return KeyEvent.VK_7
		case '8':
			return KeyEvent.VK_8
		case '9':
			return KeyEvent.VK_9
		default:
			return KeyEvent.VK_0
	}
}
