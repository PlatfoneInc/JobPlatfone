package models;

public abstract class Who {

	public static final String TYPE = "who";

	protected String company;
	// protected String description;

	public Who() {
	}

	public Who(String company) {
		this.company = company;
		// this.description = description;
//		this.url = url;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
