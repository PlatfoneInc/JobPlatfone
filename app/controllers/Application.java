package controllers;

import indexing.Job;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import com.github.cleverage.elasticsearch.IndexQuery;
import com.github.cleverage.elasticsearch.IndexQueryPath;
import com.github.cleverage.elasticsearch.IndexResults;
import com.github.cleverage.elasticsearch.IndexService;

public class Application extends Controller {

	public static Result index() {
		return index(null);
	}

	public static Result index(List<Job> results) {
		if (results != null && !results.isEmpty()) {
			// Json is not null and is not empty
			// don't need to query again
		} else {
			results = getJobResults();
			// JsonNode jn = Json.toJson(results);
			// json = jn.toString();
			// Gson gson = new Gson();
			// json = gson.toJson(results);
			// try {
			// json = getSource(json);
			// } catch (JSONException e) {
			// e.printStackTrace();
			// }
			// Logger.debug(json);
		}
		// "[{\"id\":\"2\",\"what\":\"hh\"},{\"id\":\"0\",\"what\":\"yy\"}]"
		return ok(views.html.index.render(results));
	}

	public static Result search(String who, String what, String where,
			String when) {

		// POST way to get parameters
		// final Map<String, String[]> values = request().body()
		// .asFormUrlEncoded();
		// String who = values.get("who")[0];
		// String what = values.get("what")[0];
		// String where = values.get("where")[0];
		// String when = values.get("when")[0];

		List<Job> results = getJobResults(who, what, where, when);
		// JsonNode jn = Json.toJson(results);
		return index(results);
	}

	private static List<Job> getJobResults() {
		return getJobResults("", "", "", "");
	}

	private static List<Job> getJobResults(String who, String what,
			String where, String when) {
		List<Job> results = new ArrayList<Job>();

		int pageNb = 0;
		int pageCurrent = 0;
		int from = 0;
		int size = 10;

		do {
			// Query all this Model
			IndexQuery<Job> indexQuery = new IndexQuery<Job>(Job.class).from(
					from).size(size);
			indexQuery.setBuilder(getQueryBuilder(who, what, where, when));
			IndexQueryPath iqp = new IndexQueryPath(Job.TYPE);

			// Query "string" for model IndexTest
			IndexResults<Job> res = IndexService.search(iqp, indexQuery);
			size = (int) res.pageSize;
			pageNb = (int) res.pageNb;
			pageCurrent = (int) res.pageCurrent;
			results.addAll(res.results);

			from += size;

			// Logger.debug(Json.toJson(res).toString());
			Logger.debug("size: " + size);
			Logger.debug("pageNb: " + pageNb);
			Logger.debug("pageCurrent: " + pageCurrent);
		} while (pageCurrent < pageNb);

		return results;
	}

	// private static JSONArray getSource(String results) throws JSONException {
	// // Gson gson = new Gson();
	// JSONArray sourceJsonArray = new JSONArray();
	// JSONArray ja = new JSONArray(results);
	// int len = ja.length();
	// for (int i = 0; i < len; i++) {
	// JSONObject jo = ja.getJSONObject(i);
	// sourceJsonArray.put(jo.get(Job.KEY_JOB));
	// }
	// return sourceJsonArray;
	// }

	private static QueryBuilder getQueryBuilder(String who, String what,
			String where, String when) {

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		boolean hasWho = false;
		boolean hasWhat = false;
		boolean hasWhere = false;
		boolean hasWhen = false;

		if (who != null && !who.isEmpty()) {
			QueryBuilder queryWho = QueryBuilders.fieldQuery("who.*", who);
			boolQuery.must(queryWho);
			hasWho = true;
		}

		if (what != null && !what.isEmpty()) {
			QueryBuilder queryWhat = QueryBuilders.fieldQuery("what.*", what);
			boolQuery.must(queryWhat);
			hasWhat = true;
		}

		if (where != null && !where.isEmpty()) {
			QueryBuilder queryWhere = QueryBuilders
					.fieldQuery("where.*", where);
			boolQuery.must(queryWhere);
			hasWhere = true;
		}

		if (when != null && !when.isEmpty()) {
			QueryBuilder queryWhen = QueryBuilders.rangeQuery(Job.TIMESTAMP)
					.from(when);
			boolQuery.must(queryWhen);
			hasWhen = true;
		}

		if (!(hasWho || hasWhat || hasWhere || hasWhen)) {
			return QueryBuilders.matchAllQuery();
		}

		return boolQuery;
	}

}
