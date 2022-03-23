package zoho.pages;

import zoho.managers.WebDriverManager;

public class LoginPage {
	
	WebDriverManager app;

	public LoginPage(WebDriverManager app) {
		this.app = app;
		// TODO Auto-generated constructor stub
	}
	
	public void doLogin(){
		app.log("Trying to login to zoho");
		app.type("username_id","user");
		app.click("nextBtn_id");
		app.type("password_id","password");
		app.click("nextBtn_id");
		app.wait(5);
        app.click("crm_xpath");
		app.log("I am logged in zoho.com");
 }

}
