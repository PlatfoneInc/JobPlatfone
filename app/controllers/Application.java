package controllers;

import indexing.Job;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import models.UserProfile;

import org.apache.http.ParseException;
import org.json.JSONException;

import play.Logger;
import play.cache.Cache;
import play.libs.Json;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class Application extends Controller {

	public static Result index() {
		return index(null);
	}

	public static Result index(List<Job> results) {
		if (results != null && !results.isEmpty()) {
			// Json is not null and is not empty
			// don't need to query again
		} else {
			results = JobSearcher.getJobResults();
		}
		return ok(views.html.index.render(results));
	}

	public static Result search(String who, String what, String where,
			String when) {

		List<Job> results = JobSearcher.getJobResults(who, what, where, when);
		return index(results);
	}

	public static Result login() {
		return login(null);
	}

	public static Result login(String message) {
		return ok(views.html.login.render(message));
	}

	public static Result userProfile() {
		if (session().containsKey("user_id")) {
			String userId = session().get("user_id");
			Logger.debug("user id: " + userId);
			UserProfile userProfile = (UserProfile) Cache.get("linkedin.login."
					+ userId);

			// TODO get UserProfile in DB
			return ok(Json.toJson(userProfile));
		} else {
			return redirect(routes.Application.index());
		}
	}

	public static Result linkedinProfile(String profileId) {
		UserProfile userProfile = (UserProfile) Cache.get("linkedin.login."
				+ profileId);

		// TODO parsing json has problem of null key
		Logger.debug("profile id " + profileId);

		String userId = userProfile.getId();
		String userFirstName = userProfile.getFirstName();
		String userPicture = userProfile.getPictureUrl();

		session().clear();
		session().put("user_id", userId);
		session().put("user_first_name", userFirstName);
		session().put("user_picture", userPicture);
		return redirect(routes.Application.index());
	}

	public static Result linkedinLogin() {

		String error = request().getQueryString("error");
		String errorDescription = request().getQueryString("error_description");
		String state = request().getQueryString("state");
		String authorizationCode = request().getQueryString("code");

		if (error != null && !error.isEmpty()) {
			Logger.debug("error: " + errorDescription);
			return redirect(routes.Application.login());
		} else if (authorizationCode != null && !authorizationCode.isEmpty()) {
			Logger.debug("state: " + state + ", auth code: "
					+ authorizationCode);

			// Comment linkedin try hardcode, OK
			String accessUrl = LinkedInProcess
					.getAccessTokenUrl(authorizationCode);
			JsonNode profile = null;
			try {
				Logger.debug("access url: " + accessUrl);
				Response response = LinkedInProcess
						.getAccessTokenResponse(accessUrl);
				JsonNode accessToken = response.asJson();
				profile = LinkedInProcess.getUserProfileJson(accessToken.get(
						"access_token").asText());

				UserProfile userProfile = new UserProfile();
				userProfile.setAccessToken(accessToken.get("access_token")
						.asText());
				userProfile.setExpiredAt((System.currentTimeMillis() / 1000)
						+ accessToken.get("expires_in").asLong());
				userProfile.setId(profile.get("id").asText());
				userProfile.setFirstName(profile.get("firstName").asText());
				userProfile.setLastName(profile.get("lastName").asText());
				userProfile.setEmailAddress(profile.get("emailAddress")
						.asText());
				userProfile.setHeadline(profile.get("headline").asText());
				userProfile.setPictureUrl(profile.get("pictureUrl").asText());
				userProfile.setPublicProfileUrl(profile.get("publicProfileUrl")
						.asText());
				userProfile.setIndustry(profile.get("industry").asText());

				Cache.set("linkedin.login." + profile.get("id").asText(),
						userProfile);
				// TODO store UserProfile to DB

			} catch (ParseException e) {
				Logger.error(e.getMessage());
			} catch (JSONException e) {
				Logger.error(e.getMessage());
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
			return redirect(routes.Application.linkedinProfile(profile
					.get("id").asText()));
		} else {
			// TODO empty check
		}

		return ok("How r u?");
	}

	public static Result authorization() {

		Logger.info("Enter authorization");
		// Form<Login> loginForm = play.data.Form.form(Login.class)
		// .bindFromRequest();
		//
		// if (loginForm.hasErrors()) {
		// return badRequest(views.html.login.render(loginForm));
		// } else {
		// session().clear();
		// session("email", loginForm.get().email);
		// return redirect(routes.Application.index());
		// }

		final Map<String, String[]> values = request().body()
				.asFormUrlEncoded();
		if (values != null && !values.isEmpty()) {
			String requestType = values.get("request")[0];
			Logger.debug("request type: " + requestType);
			if (requestType.equals("linkedin")) {
				Logger.debug("auth url: "
						+ LinkedInProcess.getAuthrizationUrl());
				return redirect(LinkedInProcess.getAuthrizationUrl());
			} else if (requestType.equals("login")) {
			}
			return redirect(routes.Application.index());
		} else {
			Logger.debug("map null");
			return redirect(routes.Application.login());
		}
	}

	public static Result authorizationDialog() {
		Logger.info("Enter authorization");
		Logger.debug("auth url: " + LinkedInProcess.getAuthrizationUrl());
		return ok(LinkedInProcess.getAuthrizationUrl());
	}

	public static Result linkedinProfileDialog(String profileId) {
		UserProfile userProfile = (UserProfile) Cache.get("linkedin.login."
				+ profileId);

		// TODO parsing json has problem of null key
		Logger.debug("profile id " + profileId);

		String userId = userProfile.getId();
		String userFirstName = userProfile.getFirstName();
		String userPicture = userProfile.getPictureUrl();

		session().clear();
		session().put("user_id", userId);
		session().put("user_first_name", userFirstName);
		session().put("user_picture", userPicture);
		return redirect(routes.Application.index());
	}

	public static Result linkedinLoginDialog() {

		String error = request().getQueryString("error");
		String errorDescription = request().getQueryString("error_description");
		String state = request().getQueryString("state");
		String authorizationCode = request().getQueryString("code");

		if (error != null && !error.isEmpty()) {
			Logger.debug("error: " + errorDescription);
			return redirect(routes.Application.login());
		} else if (authorizationCode != null && !authorizationCode.isEmpty()) {
			Logger.debug("state: " + state + ", auth code: "
					+ authorizationCode);

			// Comment linkedin try hardcode, OK
			String accessUrl = LinkedInProcess
					.getAccessTokenUrl(authorizationCode);
			JsonNode profile = null;
			try {
				Logger.debug("access url: " + accessUrl);
				Response response = LinkedInProcess
						.getAccessTokenResponse(accessUrl);
				JsonNode accessToken = response.asJson();
				profile = LinkedInProcess.getUserProfileJson(accessToken.get(
						"access_token").asText());

				UserProfile userProfile = new UserProfile();
				userProfile.setAccessToken(accessToken.get("access_token")
						.asText());
				userProfile.setExpiredAt((System.currentTimeMillis() / 1000)
						+ accessToken.get("expires_in").asLong());
				userProfile.setId(profile.get("id").asText());
				userProfile.setFirstName(profile.get("firstName").asText());
				userProfile.setLastName(profile.get("lastName").asText());
				userProfile.setEmailAddress(profile.get("emailAddress")
						.asText());
				userProfile.setHeadline(profile.get("headline").asText());
				userProfile.setPictureUrl(profile.get("pictureUrl").asText());
				userProfile.setPublicProfileUrl(profile.get("publicProfileUrl")
						.asText());
				userProfile.setIndustry(profile.get("industry").asText());

				Cache.set("linkedin.login." + profile.get("id").asText(),
						userProfile);
				// TODO store UserProfile to DB

			} catch (ParseException e) {
				Logger.error(e.getMessage());
			} catch (JSONException e) {
				Logger.error(e.getMessage());
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
			return redirect(routes.Application.linkedinProfile(profile
					.get("id").asText()));
		} else {
			// TODO empty check
		}

		return ok("How r u?");
	}

}
