package models;

public abstract class Who {
	
	protected String company;
	protected String description;
	protected String url;

	public Who() {
	}

	public Who(String company, String description, String url) {
		this.company = company;
		this.description = description;
		this.url = url;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
