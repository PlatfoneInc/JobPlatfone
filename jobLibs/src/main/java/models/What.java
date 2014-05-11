package models;

public abstract class What {

	protected String title;
	protected String description;
	protected String schedule;
	protected String content;

	public What() {
	}

	public What(String title, String description, String schedule,
			String content) {
		this.title = title;
		this.description = description;
		this.schedule = schedule;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
