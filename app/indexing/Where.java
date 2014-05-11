package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Indexable;

public class Where extends models.Where implements Indexable {

	public Where() {
	}

	public Where(String city, String state, String country) {
		this.city = city;
		this.state = state;
		this.country = country;
	}

	@Override
	public Map<String, Object> toIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		map.put("state", state);
		map.put("country", country);
		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {
		if (map == null) {
			return this;
		}

		city = (String) map.get("city");
		state = (String) map.get("state");
		country = (String) map.get("country");
		return this;
	}

}
