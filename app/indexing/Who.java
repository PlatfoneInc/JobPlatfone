package indexing;

import java.util.HashMap;
import java.util.Map;

import com.github.cleverage.elasticsearch.Indexable;

public class Who extends models.Who implements Indexable {

	public Who() {
	}

	public Who(String company) {
		this.company = company;
	}
 
	@Override
	public Map<String, Object> toIndex() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("company", company);
		return map;
	}

	@Override
	public Indexable fromIndex(Map map) {
		if (map == null) {
			return this;
		}

		company = (String) map.get("company");
		return this;
	}

}
