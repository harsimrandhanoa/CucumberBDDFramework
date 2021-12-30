package zoho.managers;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class WebDriverManager {
	
	WebDriver driver;
	ExtentTest test;
	Properties prop;
	SoftAssert softAssert;
	
	
	public WebDriverManager(){
		String path = System.getProperty("user.dir") + "//src//test//resources//project.properties";
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		softAssert = new SoftAssert();

	}

public void openBrowser(String browser){
	
	if (getProperty("grid_run").equals("Y")) {
		driver = openBrowserOnGrid(browser);
	}
	
	else {
		
		log("Opening browser "+browser);

		if (browser.equals("Chrome")) {
		   System.setProperty("webdriver.chrome.driver","C:\\drivers\\latest\\chromedriver.exe");
		   driver = new ChromeDriver();

		} else if (browser.equals("Mozilla")) {
			driver = new FirefoxDriver();

		} else if (browser.equals("Edge")) {
			driver = new EdgeDriver();
		}
	}
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
  }

  public WebDriver openBrowserOnGrid(String browser) {
	log("Tests to be run on selenium grid");
	ChromeOptions chromeOptions = new ChromeOptions();
	FirefoxOptions firefoxOptions = new FirefoxOptions();

	DesiredCapabilities cap = new DesiredCapabilities();
	if (browser.equals("Mozilla")) {

		// driver = new RemoteWebDriver(new
		// URL("http://192.168.56.1:4444/"), firefoxOptions);

	} else if (browser.equals("Chrome")) {

		chromeOptions.addArguments("start-maximized");
		chromeOptions.addArguments("disable-infobards");
	}
	try {
		// hit the hub
		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/"), chromeOptions);
	} catch (Exception e) {
		e.printStackTrace();
	}

	return driver;
}
	
	public void navigate(String urlKey){
		log("Navigating to url "+getProperty(urlKey));
        driver.get(getProperty(urlKey));
	}
	
	public void click(String locatorKey){
		log("clicking on locator "+getProperty(locatorKey));
        findElement(locatorKey).click();
	}
	
	public void type(String locatorKey,String data){
		log("Typing the text  "+getProperty(data) + " in the locator "+getProperty(locatorKey));
		 findElement(locatorKey).sendKeys(getProperty(data));
	}
	
	public void typeLeadData(String locatorKey,String data){
		log("Typing the text  "+data + " in the locator "+getProperty(locatorKey));
		 findElement(locatorKey).sendKeys(data);
	}
	
	public WebElement findElement(String locator){
		By by = getLocator(locator);
		WebElement e = null;
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
            e = driver.findElement(by);
		}
		catch(Exception exp){
			reportFailure("Object not found "+locator,true);
		}
		return e;
	}
	
	
	public By getLocator(String locator){
		log("The locator element is "+locator);
		if(locator.endsWith("_id")){
			return By.id(getProperty(locator));
		}
		else if(locator.endsWith("_xpath")){
           return By.xpath(getProperty(locator));
		}
		else if(locator.endsWith("_name")){
           return By.name(getProperty(locator));
		}
		
		else 
			return By.cssSelector(getProperty(locator));
	}
	
	/*************************** Validation functions **********************************/
	
	  public boolean verifyTitle(String expectedTitleKey){
		  String expectedTitle = getProperty(expectedTitleKey);
		  String actualTitle = driver.getTitle();
		  if(expectedTitle.trim().equals(actualTitle.trim()))
			  return true;
		  else
			  return false;
	  }
	  
	  public boolean isElementPresent(String locator){
		  By by = getLocator(locator);
			WebElement e = null;
			try {
				
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
	            wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
			}
			catch(Exception exp){
				return false;
			}
			return true;
	  }

	
   /****************************Utility functions ***************************************/
	
	public String getProperty(String key){
		return prop.getProperty(key);
	}
	
	
	public void init(ExtentTest test){
		this.test = test;
	}
	
	  public void log(String message){
	    	test.log(Status.INFO,message);
			System.out.println(message);
	  }

	  public void reportFailure(String failureMsg,boolean stopOnFailure) {
		    System.out.println("Failure ---> "+failureMsg);
			test.log(Status.FAIL, failureMsg);// failure in extent reports
		//	takeScreenShot();// put the screenshot in reports
			softAssert.fail(failureMsg);// failure in TestNG reports

     		if (stopOnFailure) {
				quit();
			}
		}
	  
	  public int getLeadRowNumberWithCellData(String leadName) {
		    wait(1);
			List<WebElement> names = driver.findElements(getLocator("leadnames_css"));
            for(int i=0;i<names.size();i++) {
				if(leadName.equalsIgnoreCase(names.get(i).getText()))
					return (i+1);
			}
			
			return -1;// not found
		}

		public void selectLeadCheckBox(int rowNum) {
			driver.findElement(By.cssSelector("lyte-exptable-tr:nth-child("+rowNum+") > lyte-exptable-td:nth-child(2) label")).click();
			
		}
		

       public void wait(int time){
    	   try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
	  
	  public void quit(){
	    if(driver!=null){
           driver.quit();}
        if(softAssert!=null){
			  softAssert.assertAll();
		  }
	  }
}
