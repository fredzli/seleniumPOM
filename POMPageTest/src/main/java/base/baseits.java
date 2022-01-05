package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import util.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;

	public static final Logger log = LogManager.getLogger("AutoTestLog");

	public TestBase(){

		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void initialization(){
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			String driverVersion = prop.getProperty("driver_version", "96");
			log.debug("ChromeDriver version = " + driverVersion);
			
			if(!driverVersion.equals("")) {
	    		WebDriverManager.chromedriver().version(driverVersion).setup();
	    	} else {
	    		WebDriverManager.chromedriver().setup();
	    	}
			
			//System.setProperty("webdriver.chrome.driver","/var/lib/jenkins/.m2/repository/webdriver/chromedriver/linux64/96/chromedriver");
	        ChromeOptions options = new ChromeOptions();
	        //options.setExperimentalOption("useAutomationExtension", false);

	        //run script with headless mode not require a physical or virtual display.
	        //options.setHeadless(true);
	        //https://stackoverflow.com/questions/47776774/element-is-not-clickable-at-point-in-headless-mode-but-when-we-remove-headless
	        //options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");

	        driver = new ChromeDriver(options);
		}
		else if(browserName.equals("firefox")){
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);

			driver = new FirefoxDriver(options);
		}
		else if(browserName.equals("IE")){
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
		}

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("D7_url"));
		//driver.get(System.getProperty("url"));
	}

}
