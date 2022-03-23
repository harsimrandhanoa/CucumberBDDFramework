package zoho.context;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import zoho.managers.PageObjectManager;
import zoho.reports.ExtentManager;

public class TestContext {
	
	ExtentReports report;
	ExtentTest test;
	private PageObjectManager pageObjectManager;
	
   public TestContext(){
	   report = ExtentManager.getReports();
	   this.pageObjectManager = new PageObjectManager();
   }
   
   public PageObjectManager getPageObjectManager(){
	   return pageObjectManager;
   }
   
   public void createScenario(String scenarioName){
    	test = report.createTest(scenarioName);
    	this.pageObjectManager.getWebDriverManager().init(test);
    	}
    
    public void endScenario(String scenarioName){
	  log("Ending of scenario " +scenarioName);
	  report.flush();
    }
    
    public void log(String message){
    	this.pageObjectManager.getWebDriverManager().log(message);
   }
    
   
   
   
}




