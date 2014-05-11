package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Indexable;

public class Who extends models.Who implements Indexable {

	public Who() {
	}

	public Who(String company, String description, String url) {
		this.company = company;
		this.description = description;
		this.url = url;
	}
 
	@Override
	public Map<String, Object> toIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		map.put("description", description);
		map.put("url", url);
		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {
		if (map == null) {
			return this;
		}

		company = (String) map.get("company");
		description = (String) map.get("description");
		url = (String) map.get("url");
		return this;
	}

}
