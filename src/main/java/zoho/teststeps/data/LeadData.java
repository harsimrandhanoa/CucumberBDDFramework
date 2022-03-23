package zoho.teststeps.data;

public class LeadData {
	public String firstName;
	public String lastName;
	public String email;
	public String company;

	public LeadData(String firstName, String lastName, String email, String company) {
	
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.company=company;
		
	}

	  @Override
	    public String toString() {
	        return "First Name: "+ this.firstName + ",Last Name: " + this.lastName + ",Email: "+ this.email+ ",Company: "+this.company;
	    }
}
