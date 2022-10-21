# selenium_4_5_0_unable_to_parse

With Selenium 4.5.0 I get `org.openqa.selenium.WebDriverException: Unable to parse`

This repo contains a test to reproduce this issue.

```xml
<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-java</artifactId>
  <version>4.5.0</version>
</dependency>
		
<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-http-jdk-client</artifactId>
  <version>4.5.0</version>
</dependency>
```

Selenium Docker : 4.5.0-20221017

## Start Selenium Docker

`docker-compose -f composer/docker-compose.yaml up --scale chrome=1 --scale firefox=1`

## Run the test

`mvn test`

```Java
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
```

## WebDriverException: Unable to parse

During this test I get :

```
ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 206.787 s <<< FAILURE! - in TestSuite
[ERROR] waitTest(demoWait.TestWait)  Time elapsed: 189.212 s  <<< FAILURE!
org.openqa.selenium.WebDriverException: 
Unable to parse: 
Build info: version: '4.5.0', revision: 'fe167b119a'
System info: os.name: 'Linux', os.arch: 'amd64', os.version: '5.4.0-131-generic', java.version: '11.0.16'
Driver info: org.openqa.selenium.remote.RemoteWebDriver
Command: [f64a79a7-4b3c-42d5-ab58-b915914d20ce, findElement {using=id, value=secondes}]
Capabilities {acceptInsecureCerts: true, browserName: firefox, browserVersion: 105.0.3, moz:accessibilityChecks: false, moz:buildID: 20221007134813, moz:debuggerAddress: 127.0.0.1:49151, moz:firefoxOptions: {}, moz:geckodriverVersion: 0.32.0, moz:headless: false, moz:platformVersion: 5.4.0-131-generic, moz:processID: 171, moz:profile: /tmp/rust_mozprofileuusXVb, moz:shutdownTimeout: 60000, moz:useNonSpecCompliantPointerOrigin: false, moz:webdriverClick: true, moz:windowless: false, pageLoadStrategy: normal, platformName: LINUX, proxy: Proxy(), se:cdp: ws://172.18.0.4:4444/sessio..., se:cdpVersion: 85.0, se:noVncPort: 7900, se:vnc: ws://172.18.0.4:4444/sessio..., se:vncEnabled: true, se:vncLocalAddress: ws://172.18.0.4:7900, setWindowRect: true, strictFileInteractability: false, timeouts: {implicit: 0, pageLoad: 300000, script: 30000}, unhandledPromptBehavior: dismiss and notify}
Session ID: f64a79a7-4b3c-42d5-ab58-b915914d20ce
	at demoWait.TestWait.waitTest(TestWait.java:58)
```
