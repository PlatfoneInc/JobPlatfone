package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.IndexUtils;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexType;

@IndexType(name = "job_info")
public class Job extends Index implements models.Job {

	// Find method static for request
	public static Finder<Job> find = new Finder<Job>(Job.class);

	private Who who;
	private What what;
	private Where where;
	private String timestamp;

	public Job() {
	}

	@Override
	public Map toIndex() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put(Who.TYPE, who.toIndex());
		map.put(What.TYPE, what.toIndex());
		map.put(Where.TYPE, where.toIndex());
		map.put(TIMESTAMP, timestamp);

		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {

		if (map == null) {
			return this;
		}

		setId(Long.valueOf((String) map.get("id")));
		setTimestamp((String) map.get(TIMESTAMP));

		// UnSerialize to a Indexable Object
		setWho(IndexUtils.getIndexable(map, Who.TYPE, Who.class));
		setWhat(IndexUtils.getIndexable(map, What.TYPE, What.class));
		setWhere(IndexUtils.getIndexable(map, Where.TYPE, Where.class));

		return this;
	}

	@Override
	public void setWho(models.Who who) {
		// TODO check instance maybe?
		this.who = (Who) who;
	}

	@Override
	public void setWhat(models.What what) {
		this.what = (What) what;
	}

	@Override
	public void setWhere(models.Where where) {
		this.where = (Where) where;
	}

	@Override
	public String getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = String.valueOf(id);
	}

	@Override
	public models.Who getWho() {
		return who;
	}

	@Override
	public models.What getWhat() {
		return what;
	}

	@Override
	public models.Where getWhere() {
		return where;
	}
}
