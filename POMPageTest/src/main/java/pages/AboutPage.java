package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import base.TestBase;

public class AboutPage extends TestBase {
	
	@FindBy(xpath = "/html/body/header/div/h1/a/svg/path[2]")
	@CacheLookup
	WebElement googleLogo;

}
