package zoho.pages;

import io.cucumber.java.en.When;
import zoho.managers.WebDriverManager;

public class LeadsDetailPage {

	WebDriverManager app;
	
	public LeadsDetailPage(WebDriverManager app) {
		this.app=app;
	}
	
	public void goToCreateLeadPage() {
		app.click("createleadbutton_xpath");
		if(!app.isElementPresent("lead_first_name_id")) {
			app.reportFailure("Create Lead Page did not open", true);
		}
	}

	public void selectLead(String leadName) {
		int rowNum = app.getLeadRowNumberWithCellData(leadName);
		if(rowNum==-1)
			app.reportFailure("Lead not found in lead list", true);
		app.log(leadName +" lead Row Number is "+rowNum);
         
		// locator will be dynamic
		
		app.selectLeadCheckBox(rowNum);
	}
	
	
	public void deleteLead() {
		app.click("action_css");
		app.click("delete_xpath");
		app.wait(2);
		app.click("delete_id");
	}
	
	public void validateLeadPresent(String leadName) {
		int rowNum = app.getLeadRowNumberWithCellData(leadName);
		if(rowNum==-1)
			app.reportFailure("Lead not found inlead list", true);

	}
	
	
	public void validateLeadNotPresent(String leadName) {
		app.wait(5);
		int rowNum = app.getLeadRowNumberWithCellData(leadName);
       if(rowNum!=-1)
			app.reportFailure("Lead  found in lead list", true);

	}
   
}
