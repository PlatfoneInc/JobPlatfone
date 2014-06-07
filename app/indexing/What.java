package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Indexable;

public class What extends models.What implements Indexable {

	public What() {
	}

	public What(String title, String content, String url) {
		super(title, content, url);
	}

	@Override
	public Map<String, Object> toIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("url", url);
		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {
		if (map == null) {
			return this;
		}

		title = (String) map.get("title");
		content = (String) map.get("content");
		url = (String) map.get("url");
		return this;
	}

}
