package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultPage {
	private String searchString;
	
	public ResultPage(String text) {
		searchString = text;
	}
	
	@FindBy (xpath = "//*[@id=\"tsf\"]/div[2]/div/div[2]/div/div[1]/input")
	WebElement searchBox;
	
	public String searchBoxValue() {
		return searchBox.getAttribute("value");
	}
	
	public String getSearchString() {
		return searchString;
	}
}
