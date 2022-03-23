package zoho.pages;


import zoho.managers.WebDriverManager;

public class HomePage {
	
	WebDriverManager app;
	
	public HomePage(WebDriverManager app){
		this.app = app;
		
	}
	
	public void load(String browser){
		app.openBrowser(browser);
		app.navigate("url");
		if(!app.verifyTitle("homepageTitle")){
		//	app.reportFailure("Title did not match",false);
	    }
	}
	
	public void goToLoginPage()	{
		app.log("On the goToLoginPage");
		app.click("signin_link_css");
		if(!app.isElementPresent("username_id"))
	     app.reportFailure("Login page did not load",false);     
	}	
	

}
