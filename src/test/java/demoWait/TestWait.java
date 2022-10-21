package demoWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestWait {

	private WebDriver driver;

	@BeforeTest
	public void setUp() throws MalformedURLException {

		System.setProperty("webdriver.http.factory", "jdk-http-client");
		
		//ChromeOptions browserOptions = new ChromeOptions();
		//driver = new RemoteWebDriver(new URL("http://localhost:4444"), browserOptions);
		FirefoxOptions firefoxOption = new FirefoxOptions();
		driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOption);
		driver.manage().window().maximize();
		driver.get("https://justepourtester.net/selenium/selenium_wait.html");
	}

	@AfterTest
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}

	}

	@Test
	public void waitTest() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		for (int i = 1; i <= 2000; i++) {
			
			System.out.println(i);
			
			driver.getWindowHandle();
		    driver.getCurrentUrl();
		    driver.getTitle();
			driver.manage().timeouts().getScriptTimeout();
			
			driver.findElement(By.id("secondes")).clear();
			driver.findElement(By.id("secondes")).sendKeys("0");
			driver.findElement(By.id("btAfficherLeTexte")).click();
			
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("texteAffiche"), "Le texte est affiché ! (délai : 0)"));
		}
	}
}