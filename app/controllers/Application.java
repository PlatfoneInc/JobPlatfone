package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	
	public static Result index(String any) {
		Logger.debug(any);
		return ok(views.html.main.render("JobPlatfone"));
	}

//	public static Result index(List<Job> results) {
//		if (results != null && !results.isEmpty()) {
//			// Json is not null and is not empty
//			// don't need to query again
//		} else {
//			results = JobSearcher.getJobResults();
//		}
//		return ok(views.html.index.render(results));
//	}

//	public static Result search(String who, String what, String where,
//			String when) {
//
//		List<Job> results = JobSearcher.getJobResults(who, what, where, when);
//		return index(results);
//	}

//	public static Result login() {
//		return login(null);
//	}
//
//	public static Result login(String message) {
//		return ok(views.html.login.render(message));
//	}

//	public static Result userProfile() {
//		if (session().containsKey("user_id")) {
//			String userId = session().get("user_id");
//			Logger.debug("user id: " + userId);
//			UserProfile userProfile = (UserProfile) Cache.get("linkedin.login."
//					+ userId);
//
//			// TODO get UserProfile in DB
//			return ok(Json.toJson(userProfile));
//		} else {
//			return redirect(routes.Application.index());
//		}
//	}

	public static Result loadHTML(String any) {
		switch (any) {
		case "index":
			return ok(views.html.index.render());
		case "1":
			break;
		}
		return ok();
	}

}
