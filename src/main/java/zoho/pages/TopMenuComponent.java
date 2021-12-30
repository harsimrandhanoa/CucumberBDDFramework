package zoho.pages;

import zoho.managers.WebDriverManager;

public class TopMenuComponent {

	WebDriverManager app;
	
	public TopMenuComponent(WebDriverManager app) {
		this.app=app;
	}

	public void load(String menuOption) {
		if(menuOption.equals("Leads"))
			app.click("leads_xpath");
		else if(menuOption.equals("Contacts")) {
		}
	}
}
