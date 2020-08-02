package testPackage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class TestClass {

	private static WebDriver driver;

	// Scenario#1
	@Test(description = "Evaluate the broken images out of all images and assert the result.")

	public void scenario_01() {

		// set driver property for Chrome driver
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

		// launch chrome browser
		driver = new ChromeDriver();

		// maximize the browser
		driver.manage().window().maximize();

		// Navigate to the given url
		driver.get("https://the-internet.herokuapp.com/broken_images");

		// get all the image elements of the page in the list
		List<WebElement> imagesList = driver.findElements(By.tagName("img"));

		int brokenImageCnt = 0;// initialize the broken images count
		int totalImageCnt = imagesList.size(); // get the count of total number images in the page

		// Now iterate through each image and check if it's broken
		for (WebElement imgElement : imagesList) {
			try {
				if (imgElement != null) {
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(imgElement.getAttribute("src"));
					HttpResponse response = client.execute(request);
					/*
					 * verifying response code he HttpStatus should be 200 if not, increment as
					 * broken images count
					 */
					if (response.getStatusLine().getStatusCode() != 200)
						brokenImageCnt++;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Total no. of images: " + totalImageCnt);
		System.out.println("Total no. of broken images: " + brokenImageCnt);

		// closing the driver instance
		driver.quit();

	}

	// Scenario#2
	@Test(description = "Basic Auth")

	public void scenario_02() throws AWTException, InterruptedException {

		// set driver property for Chrome driver
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

		// launch chrome browser
		driver = new ChromeDriver();

		// maximize the browser
		driver.manage().window().maximize();

		// Navigate to the given url
		driver.get("https://the-internet.herokuapp.com/basic_auth");

		String Str = "admin"; // user and pass

		// copy the string to clip board
		StringSelection stringSelection = new StringSelection(Str);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		System.out.println("user name and password is:" + Str);
		// wait for page to load for 10 sec
		Thread.sleep(5000);

		Robot robot = new Robot();

		// paste user name in the field
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		System.out.println("Entered username");

		Thread.sleep(1000);
		// move to password field
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);

		Thread.sleep(1000);
		// paste password in the field
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		System.out.println("Entered password");

		// press enter button
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(5000);

		System.out.println("Logged into the application");

		// closing the driver instance
		driver.quit();
	}

	// Scenario#3
	@Test(description = "Move slider to Max and Min")

	public void scenario_03() throws InterruptedException {

		// set driver property for Chrome driver
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

		// launch chrome browser
		driver = new ChromeDriver();

		// maximize the browser
		driver.manage().window().maximize();

		// Navigate to the given url
		driver.get("https://the-internet.herokuapp.com/horizontal_slider");

		Thread.sleep(5000);

		// get the slider element
		WebElement slider = driver.findElement(By.tagName("input"));

		// get the width of the slider
		int slidersizewidth = slider.getSize().getWidth();

		// Move slider to max value
		Actions action = new Actions(driver);
		action.clickAndHold(slider);
		action.moveByOffset(slidersizewidth, 0).click().build().perform();

		// get the maximum value of the slider
		int maxValue = Integer.parseInt(driver.findElement(By.xpath("//*[@id='range']")).getText());

		System.out.println("The maximum number on the slider: " + maxValue);

		Thread.sleep(1000);

		// Move slider to min value
		action.clickAndHold(slider);
		action.moveByOffset(-slidersizewidth, 0).click().build().perform();

		// get the value of the slider when moved to minimum number
		int minValue = Integer.parseInt(driver.findElement(By.xpath("//*[@id='range']")).getText());

		System.out.println("The slider value when moved to minimum number: " + minValue);

		driver.quit();
	}

	// Scenario#4
	@Test(description = "Hover on the pictures and assert the details like User")

	public void scenario_04() throws InterruptedException {

		// set driver property for Chrome driver
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

		// launch chrome browser
		driver = new ChromeDriver();

		// maximize the browser
		driver.manage().window().maximize();

		// Navigate to the given url
		driver.get("https://the-internet.herokuapp.com/hovers");

		Thread.sleep(5000);

		// get the image elements
		List<WebElement> imagesList = driver.findElements(By.xpath("//div/img"));

		int i = 0;

		// Hover on each image and get the user name
		for (WebElement imgElement : imagesList) {
			i++;
			Actions action = new Actions(driver);
			action.moveToElement(imgElement).build().perform();
			Thread.sleep(1000);
			String userName = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[" + i + "]/div/h5"))
					.getAttribute("innerText");
			System.out.println("Image " + i + "user " + userName);

		}
		
		driver.quit();

	}
}