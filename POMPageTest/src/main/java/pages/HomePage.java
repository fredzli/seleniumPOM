package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;;


public class HomePage extends TestBase{
	
	@FindBy(xpath = "//*[@id=\"hplogo\"]")
	@CacheLookup
	WebElement googleLogo;
		
	@FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[1]/input")
	WebElement searchBox;
	
	@FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]")
	WebElement btnGoogleSearch;
	//*[@id="tsf"]/div[2]/div[1]/div[3]/center/input[1]
	
	@FindBy(xpath = "//*[@id=\"gb192\"]/span[1]")
	WebElement accountLink;
	
	@FindBy(xpath = "//*[@id=\"gb_70\"]")
	WebElement btnSignin;
	
	@FindBy(xpath = "//*[@id=\"hptl\"]/a[1]")
	WebElement aboutLink;

		
	// Initializing the Page Objects:
	public HomePage() {
		//super();
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle(){
		return driver.getTitle();
	}
	
	
	public boolean verifyLogo(){
		return googleLogo.isDisplayed();
	}
	

	public AboutPage clickOnAboutLink(){
		aboutLink.click();
		return new AboutPage();
	}
	
	public ResultPage enterTextClickSearch(String keysToSend){
		searchBox.sendKeys(keysToSend);
		btnGoogleSearch.click();
		return new ResultPage(keysToSend);
	}
	
	/*
	public TasksPage clickOnTasksLink(){
		tasksLink.click();
		return new TasksPage();
	}
	
	public void clickOnNewContactLink(){
		Actions action = new Actions(driver);
		action.moveToElement(contactsLink).build().perform();
		newContactLink.click();
		
	}
	*/

}
