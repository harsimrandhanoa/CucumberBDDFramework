package zoho.pages;

import zoho.managers.WebDriverManager;

public class LeadDescriptionPage {
	
    WebDriverManager app;
	
	public LeadDescriptionPage(WebDriverManager app) {
		this.app=app;
	}

	public void hasLoaded() {
		if(!app.isElementPresent("last_name_id")) {
			app.reportFailure("Lead Description Page has not loaded", true);
		}
	}


}
