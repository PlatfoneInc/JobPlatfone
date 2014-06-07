package controllers;

import indexing.Job;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import play.Logger;

import com.github.cleverage.elasticsearch.IndexQuery;
import com.github.cleverage.elasticsearch.IndexQueryPath;
import com.github.cleverage.elasticsearch.IndexResults;
import com.github.cleverage.elasticsearch.IndexService;

class JobSearcher {
	JobSearcher() {
	}

	static List<Job> getJobResults() {
		return getJobResults("", "", "", "");
	}

	static List<Job> getJobResults(String who, String what, String where,
			String when) {
		List<Job> results = new ArrayList<Job>();

		int pageNb = 0;
		int pageCurrent = 0;
		int from = 0;
		int size = 10;

		// do {
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
		// } while (pageCurrent < pageNb);

		return results;
	}

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
