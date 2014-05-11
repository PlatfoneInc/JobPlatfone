package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Index;
import com.github.cleverage.elasticsearch.IndexUtils;
import com.github.cleverage.elasticsearch.Indexable;
import com.github.cleverage.elasticsearch.annotations.IndexType;

@IndexType(name = "job_info")
public class Job extends Index {

	public static final String TYPE = "job_info";
	public static final String TIMESTAMP = "timestamp";
	public static final String KEY_JOB = "jobs";
	// Find method static for request
	public static Finder<Job> find = new Finder<Job>(Job.class);

	public Jobs jobs;
	public Who who;
	public What what;
	public Where where;

	public Job() {
		jobs = new Jobs();
	}

	@Override
	public Map toIndex() {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("who", getWho().toIndex());
		map.put("what", getWhat().toIndex());
		map.put("where", getWhere().toIndex());
		map.put("timestamp", jobs.getTimestamp());

		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {

		if (map == null) {
			return this;
		}

		jobs.setId(Long.valueOf((String) map.get("id")));
		jobs.setTimestamp((String) map.get("timestamp"));

		// UnSerialize to a Indexable Object
		jobs.setWho(IndexUtils.getIndexable(map, "who", Who.class));
		jobs.setWhat(IndexUtils.getIndexable(map, "what", What.class));
		jobs.setWhere(IndexUtils.getIndexable(map, "where", Where.class));

		return this;
	}

	public String getId() {
		return jobs.getId();
	}

	public void setId(long id) {
		jobs.setId(id);
	}

	public Who getWho() {

		if (jobs.getWho() == null) {
			who = new Who();
			jobs.setWho(who);
			return who;
		}
		return who;
	}

	public void setWho(Who who) {
		jobs.setWho(who);
	}

	public What getWhat() {
		if (jobs.getWhat() == null) {
			what = new What();
			jobs.setWhat(what);
			return what;
		}
		return what;
	}

	public void setWhat(What what) {
		jobs.setWhat(what);
	}

	public Where getWhere() {
		if (jobs.getWhere() == null) {
			where = new Where();
			jobs.setWhere(where);
			return where;
		}
		return where;
	}

	public void setWhere(Where where) {
		jobs.setWhere(where);
	}

	public class Jobs extends models.Job {
	}
}
