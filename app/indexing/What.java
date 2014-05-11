package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Indexable;

public class What extends models.What implements Indexable {

	public What() {
	}

	public What(String title, String description, String schedule,
			String content) {
		super(title, description, schedule, content);
	}

	@Override
	public Map<String, Object> toIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("description", description);
		map.put("schedule", schedule);
		map.put("content", content);
		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {
		if (map == null) {
			return this;
		}

		title = (String) map.get("title");
		description = (String) map.get("description");
		schedule = (String) map.get("schedule");
		content = (String) map.get("content");
		return this;
	}

}
