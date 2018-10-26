package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	private static int defaultTimeout;
	
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources"
					+ "\\config.properties");
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
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("useAutomationExtension", false);
			driver = new ChromeDriver(options); 
		}
		else if(browserName.equals("edge")){
			WebDriverManager.edgedriver().setup();
			//EdgeOptions edgeCap = new EdgeOptions();
			driver = new EdgeDriver(); 
		}
		
	
		defaultTimeout = Integer.parseInt(prop.getProperty("defaultTimeout"));
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(defaultTimeout, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("address"));
		
	}
	
}
