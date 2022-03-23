package zoho.teststeps;

import java.util.List;
import java.util.Map;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import zoho.context.TestContext;
import zoho.pages.CreateLeadPage;
import zoho.pages.LeadDescriptionPage;
import zoho.pages.LeadsDetailPage;
import zoho.teststeps.data.LeadData;

public class Leads {
	
	TestContext testContext;
	public LeadsDetailPage leadDetailPage;
	public CreateLeadPage createLeadPage;
	public LeadDescriptionPage leadDescriptionPage;
	 
	public Leads(TestContext testContext){
		this.testContext = testContext;
		this.leadDetailPage= testContext.getPageObjectManager().getleadsDetailPage();
		this.createLeadPage= testContext.getPageObjectManager().getCreateLeadPage();
		this.leadDescriptionPage= testContext.getPageObjectManager().getLeadDescriptionPage();

    }
	
	
	@Before
	public void before(Scenario scenario){
		testContext.createScenario(scenario.getName());
		testContext.log("Starting of scenario " +scenario.getName());
	}
	
	@After
	public void after(Scenario scenario){
		testContext.endScenario(scenario.getName());
		testContext.getPageObjectManager().getWebDriverManager().quit();
	}
	
	 @When("I go to create lead page")
	 public void goToCreateLead() {
		 leadDetailPage.goToCreateLeadPage();

	 }
	 
	@And("enter and submit lead details")
	  public void submitDetails(List<LeadData> leadData) {
         createLeadPage.submitLeadDetails(leadData);
	 }
		
	@DataTableType
	    public LeadData entry(Map<String, String> entry) {
		//	testContext.log(entry.toString());
            return new LeadData(entry.get("FirstName"),entry.get("LastName"),entry.get("Email"),entry.get("Company"));
	    }
		
	@Then("Lead Description Page should load")
		public void verifyLeadDetailPage() {
			leadDescriptionPage.hasLoaded();
		}

		
   @And("I verify lead details")
		public void verifyDetails(){
		testContext.log("I verify leads details");
	}
	
   @Then("Lead {string} should {string} inside the grid")
	public void verifyLeadCreation(String leadName, String condition) {
	   if (condition.equals("be present")) {
           	 leadDetailPage.validateLeadPresent(leadName);
       }else {
         leadDetailPage.validateLeadNotPresent(leadName);
       }

	}

    @When("I select the lead {string}")
    public void selectLead(String leadName) {
    	testContext.log("Selecting the lead "+ leadName);
        leadDetailPage.selectLead(leadName);
    }
    
    @And("I click the delete button")
    public void deleteLead() {
    	leadDetailPage.deleteLead();
    	
    }
}
