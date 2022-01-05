package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.*;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 60;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/TestUserData.xlsx"; 
	
	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	public void switchToFrame(String frameName) {
		driver.switchTo().frame(frameName);
	}

	public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
	 /**
     * Sets the drop down value of a given drop down to given value. Tries 10
     * times.
     *
     * @param element
     * @param value
     */
    public static void retrySelectFromDropDown(WebElement element, String value) {
      
        int attempts = 0;
        while (attempts < 10) {
            try {
                try {
                    (new Select(element)).selectByValue(value);
                    return;
                } catch ( NoSuchElementException e) {
                    log.info("Cannot locate option with value: " + value
                            + " --- Will retry to select option by visible text!");
                }
                (new Select(element)).selectByVisibleText(value);
                return;
            } catch (NoSuchElementException e) {
                if (++attempts < 10) {
                    log.info("Retrying [Select From Drop Down : value = " + value
                            + "] for WebElement : " + element.toString());
                }
            }
        }
        throw new NoSuchElementException("Could not [Select From Drop Down : value = " + value
                + "] for WebElement : " + element.toString());
    }
    
	public static List <WebElement> optionsFromList(WebElement element) {
		Select oSelect = new Select(element);
		return oSelect.getOptions();
    }
	
	public static String selectedInList(WebElement element) {
		Select oSelect = new Select(element);
		return oSelect.getFirstSelectedOption().getText();
    }
	
	public static void selectFromList(WebElement element, String option) {
		Select oSelect = new Select(element);
		oSelect.selectByVisibleText(option);
    }
	
	
	public static void jsSelectFromList(WebElement element, String option) {
		((JavascriptExecutor) driver).executeScript("arguments[0].value=" +option+";", element);
    }
	
	public static void select2OptionsFromList(WebElement element, String option1, String option2) {
		Select oSelect = new Select(element);
		oSelect.selectByVisibleText(option1);
		oSelect.selectByVisibleText(option2);
	}
	
	public static void selectByIndex(WebElement element, int n) {
		Select oSelect = new Select(element);
		oSelect.selectByIndex(n);
    }
	
	public static List <WebElement> selectOptions(WebElement element) {
		Select oSelect = new Select(element);
		return oSelect.getOptions();
    }
	
	public static boolean elementExists(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		element = wait.until(ExpectedConditions.visibilityOf(element));
	    try {
	        element.isDisplayed();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}

	//Convert an user in Multi-Squadron user list to an user in Squadron user list
	public static String processUserInfo(String userInfo) {
		String listUser = userInfo.replace(",", ", ");
		
		int temp = listUser.indexOf('(');
		listUser = listUser.substring(0, temp-1);
		log.debug("Prrocessed Test User - " + listUser );
		
		return listUser;
	}
	
	public static String processAlert(String flag) {
		Alert alert = driver.switchTo().alert();
		String alertMsg = alert.getText();
		log.debug("Alert message: " + alertMsg);
		
		if (flag.equalsIgnoreCase("ok")) alert.accept();
		else alert.dismiss();
		
		return alertMsg;
	}

	public static void scrollToBottomPage() {
	    ((JavascriptExecutor) driver).executeScript(("window.scrollTo(0, document.body.scrollHeight)"));
	}
	
	public static void scrollToTopPage() {
	    ((JavascriptExecutor) driver).executeScript(("window.scrollTo(0, -document.body.scrollHeight)"));
	}
	
	public static void scrollIntoView(WebElement element) {
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}
	
	 //Perform Click on button using JavascriptExecutor
	public static void clickOnButton(WebElement element) {
		if (elementExists(element))
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		else log.debug("Element not exist!!!");
	}
	
	//enter text to input using JavascriptExecutor
	public static void jsInputText(WebElement myElement, String iText) {
		String js = "arguments[0].setAttribute('value','"+iText+"')";
		((JavascriptExecutor) driver).executeScript(js, myElement);
	}
	
	//Remove the readonly attribute from input using JavascriptExecutor
	public static void jsRemoveReadOnlyAttribute(WebElement myElement) {
		((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly','readonly')",myElement);
	}
		
	//Set attribute type to text using JavascriptExecutor
	public static void jsSetAttributeByName(String name, WebElement element) {
		((JavascriptExecutor) driver).executeScript("document.getElementsByName(\"" + name + "\")[0].setAttribute(\"type\", \"text\");", element);
	}
	
	//Set attribute type to text using JavascriptExecutor
	public static void jsSetAttributeByID(String id) {
		((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").setAttribute(\"type\", \"text\");");
	}
		
	public static String jsGetText(WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].text;", element);
	}
	
	public static void actionSelect(WebElement myElement, String eValue) {
		Actions act = new Actions (driver);
		act.moveToElement(myElement).click().sendKeys(myElement, eValue).sendKeys(Keys.ENTER).build().perform();
	}
	
	public static void actionRightClick(WebElement myElement) {
		Actions act = new Actions (driver);
		act.contextClick(myElement).build().perform();
	}
	
	public static void actionClick(WebElement myElement) {
		Actions act = new Actions (driver);
		act.click(myElement).build().perform();
	}
	
	public static String getPageSource() {
	    return driver.getPageSource();
	}
	    
	public static String getToday() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        
	    return dateFormat.format(date);
	}
	
	public static String getNextXDay(int x, String aday) {
		LocalDate adate = LocalDate.parse(aday);
        
	    return adate.plusDays(x).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String getNextMonthDay(String aday) {
		LocalDate adate = LocalDate.parse(aday);
        
	    return adate.plusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String getNextSaturday() {
		LocalDate adate = LocalDate.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy"); 
		sleep(5000);
	    String formattedDate = adate.format(myFormatObj); 		
		String wd = formattedDate.substring(0, 3);
		
		int k=0;
		if (wd.equals("Sat")) return adate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		else if (wd.equals("Sun")) k=6;
		else if (wd.equals("Mon")) k=5;
		else if (wd.equals("Tue")) k=4;
		else if (wd.equals("Wed")) k=3;
		else if (wd.equals("Thu")) k=2;
		else if (wd.equals("Fri")) k=1;
		else ;
		
	    return adate.plusDays(k).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	//Get number of days between two days
	public static int getDaysBetween(String day1, String day2) {	
		LocalDate date1 = LocalDate.parse(day1);
		LocalDate date2 = LocalDate.parse(day2);
			
		long diffInDays = ChronoUnit.DAYS.between(date1, date2);
        
	    return (int) diffInDays;
	}
	
	//Get the list of days between two different days
	public static List<LocalDate> listDaysBetween(String day1, String day2) {	
		LocalDate startDate = LocalDate.parse(day1);
		LocalDate endDate = LocalDate.parse(day2);
		 
		long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
		          
		List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
		                                    .limit(numOfDays)
		                                    .collect(Collectors.toList());
		 
        
	    return listOfDates;
	}
	
	//day1 is in days list
	public static boolean isInDaysList(String day1, List<LocalDate> daysList) {	
		LocalDate date1 = LocalDate.parse(day1);
		
	    return daysList.contains(date1);
	}
		
	public static String getTodayWithFormat(String ft) {
		DateFormat dateFormat = new SimpleDateFormat(ft);
        Date date = new Date();
        
	    return dateFormat.format(date);
	}
	
	//Convert date string dateStr from format ft1 to ft2
	public static String dateFormatConvert(String dateStr, String ft1, String ft2)  {
		//"dd-MMM-yyyy", "yyyy-MM-dd"
		SimpleDateFormat formatter = new SimpleDateFormat(ft1, Locale.ENGLISH);

		Date date = new Date();
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(ft2);
	    String formattedDate = sdf.format(date);
        
	    return formattedDate;
	}
	
	public static String getNewDateFromToday(String today, int n) {
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String [] ymd = today.split("-");
        String y = ymd[0];
        String m = ymd[1];
        String d = ymd[2];
        
        int id = Integer.parseInt(d) + n;
        // TODO: make day works for all
        int im = Integer.parseInt(m);
        int iy = Integer.parseInt(y);
        
        if(id>=30) {
        	if (im==2) {
	        	m = Integer.toString(im+1);
	        	id = id - 28;
        	} else if(im==12){
        		m = Integer.toString(1);
        		y = Integer.toString(iy+1);
	        	id = id - 30;
        	} else {
        		m = Integer.toString(im+1);
	        	id = id - 30;
        	}
        }
        	
        if (id==0) id++;
	    return String.join("-", y, m, Integer.toString(id));
	}
	
	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		 System.out.println(sheet.getLastRowNum() + "--------" + sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				 System.out.println(data[i][k]);
			}
		}
		return data;
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

}
