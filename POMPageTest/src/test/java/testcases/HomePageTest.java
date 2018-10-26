package testcases;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import base.TestBase;
import pages.AboutPage;
import pages.HomePage;
import pages.ResultPage;

public class HomePageTest extends TestBase {
	
	HomePage homePage;
	AboutPage aboutPage;
	ResultPage resultPage;
	private String keysToSend;
	
	public HomePageTest() {
		super();
	}
    
	@Before
	public void setup() {		
		initialization();
		homePage = new HomePage();
		aboutPage = new AboutPage();
		
		keysToSend = "saic";
		//resultPage = new ResultPage(keysToSend);
	}
	
	@After
	public void clean() {
		driver.quit();
	}
	
	
	@Test
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		System.out.println("Page title: " + homePageTitle);
		Assert.assertEquals(homePageTitle, "Google");
		//Framework.sleep(5000);
	}
	
	@Test
	public void verifyLogoTest(){
		System.out.println("Test website Logo. ");
		Assert.assertTrue(homePage.verifyLogo());
	}
	
	@Test 
	public void verifyAboutLinkTest(){
		System.out.println("Test about link. ");
		aboutPage = homePage.clickOnAboutLink();
	}
	
	@Test 
	public void verifySearchResultTest(){
		System.out.println("Test enter text and click search. ");
		resultPage = homePage.enterTextClickSearch(keysToSend );
		System.out.println("Search text: " + resultPage.getSearchString());
		//Assert.assertEquals(keysToSend, resultPage.getSearchString());
	}
}
