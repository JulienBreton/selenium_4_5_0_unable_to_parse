# selenium_4_5_0_unable_to_parse

With Selenium 4.5.2 I get `org.openqa.selenium.WebDriverException: Unable to parse`

This repo contains a test to reproduce this issue.

The logs are in the repo.

```xml
<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-java</artifactId>
  <version>4.5.2</version>
</dependency>
		
<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-http-jdk-client</artifactId>
  <version>4.5.2</version>
</dependency>
```

Selenium Docker : 4.5.2-20221021

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
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 361.259 s <<< FAILURE! - in TestSuite
[ERROR] waitTest(demoWait.TestWait)  Time elapsed: 355.858 s  <<< FAILURE!
org.openqa.selenium.WebDriverException: 
Unable to parse: 
Build info: version: '4.5.2', revision: '702c64f787c'
System info: os.name: 'Linux', os.arch: 'amd64', os.version: '5.4.0-131-generic', java.version: '11.0.16'
Driver info: org.openqa.selenium.remote.RemoteWebDriver
Command: [d739c9fa-894a-47a2-8902-35ed5122dbaa, findElement {using=id, value=secondes}]
Capabilities {acceptInsecureCerts: true, browserName: firefox, browserVersion: 106.0.1, moz:accessibilityChecks: false, moz:buildID: 20221019185550, moz:debuggerAddress: 127.0.0.1:29248, moz:firefoxOptions: {}, moz:geckodriverVersion: 0.32.0, moz:headless: false, moz:platformVersion: 5.4.0-131-generic, moz:processID: 172, moz:profile: /tmp/rust_mozprofilek6OWCr, moz:shutdownTimeout: 60000, moz:useNonSpecCompliantPointerOrigin: false, moz:webdriverClick: true, moz:windowless: false, pageLoadStrategy: normal, platformName: LINUX, proxy: Proxy(), se:bidi: ws://172.18.0.4:4444/sessio..., se:cdp: ws://172.18.0.4:4444/sessio..., se:cdpVersion: 85.0, se:noVncPort: 7900, se:vnc: ws://172.18.0.4:4444/sessio..., se:vncEnabled: true, se:vncLocalAddress: ws://172.18.0.4:7900, setWindowRect: true, strictFileInteractability: false, timeouts: {implicit: 0, pageLoad: 300000, script: 30000}, unhandledPromptBehavior: dismiss and notify}
Session ID: d739c9fa-894a-47a2-8902-35ed5122dbaa
	at demoWait.TestWait.waitTest(TestWait.java:58)
```
