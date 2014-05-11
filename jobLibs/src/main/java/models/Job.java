package models;

public abstract class Job {
	public static final String TYPE = "job_info";
	public static final String TIMESTAMP = "timestamp";

	private long id;
	private Who who;
	private What what;
	private Where where;
	private String timestamp;
	
	public Job() {
	}

	public Job(long id, Who who, What what, Where where, String timestamp) {
		this.id = id;
		this.who = who;
		this.what = what;
		this.where = where;
		this.timestamp = timestamp;
	}

	public String getId() {
		return String.valueOf(id);
	}

	public void setId(long id) {
		this.id = id;
	}

	public Who getWho() {
		return who;
	}

	public void setWho(Who who) {
		this.who = who;
	}

	public What getWhat() {
		return what;
	}

	public void setWhat(What what) {
		this.what = what;
	}

	public Where getWhere() {
		return where;
	}

	public void setWhere(Where where) {
		this.where = where;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
