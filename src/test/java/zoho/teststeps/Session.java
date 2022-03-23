package zoho.teststeps;

import io.cucumber.java.en.Given;
import zoho.context.TestContext;
import zoho.pages.HomePage;
import zoho.pages.LoginPage;

public class Session {
	
  public TestContext testContext;
  public HomePage homePage;
  public LoginPage loginPage;
	 
  public Session(TestContext testContext){
	  this.testContext = testContext;
	  this.homePage = this.testContext.getPageObjectManager().getHomePage();
	  this.loginPage =  this.testContext.getPageObjectManager().getLoginPage();
	  
  }
	
  @Given("I am logged in zoho.com")
	public void zohoLogin(){
      homePage.load("Chrome");
	  homePage.goToLoginPage();
	  loginPage.doLogin();
    }
  
  
  
  
	
	

}
