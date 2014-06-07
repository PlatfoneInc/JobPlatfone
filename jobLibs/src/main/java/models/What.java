package models;

public abstract class What {

	public static final String TYPE = "what";

	protected String title;
	// protected String description;
	// protected String schedule;
	protected String content;
	protected String url;

	public What() {
	}

	public What(String title, String content, String url) {
		this.title = title;
		this.content = content;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
