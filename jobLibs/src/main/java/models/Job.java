package models;

public interface Job {
	public static final String TYPE = "job_info";
	public static final String TIMESTAMP = "timestamp";

	public String getId();

	public void setId(long id);

	public Who getWho();

	public void setWho(Who who);

	public What getWhat();

	public void setWhat(What what);

	public Where getWhere();

	public void setWhere(Where where);

	public String getTimestamp();

	public void setTimestamp(String timestamp);
}
