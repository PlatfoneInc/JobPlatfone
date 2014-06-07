package controllers;

import java.io.IOException;

import models.UserProfile;

import org.apache.http.ParseException;
import org.json.JSONException;

import play.Logger;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;

import com.fasterxml.jackson.databind.JsonNode;

public class LinkedInProcess {

	public static enum HttpMethod {
		POST, GET
	};

	public static final long TIMEOUT = 10000;

	public static String API_KEY;
	public static String API_SECRET_KEY;
	public static String REDIRECT_URI;
	public static String STATE;
	public static String SCOPE;

	public static String accessToken;
	public static String expiresIn;

	public static void run() {
		Logger.debug("loading linkedin properties");
		API_KEY = play.Play.application().configuration()
				.getString("linkedin.api_key");
		API_SECRET_KEY = play.Play.application().configuration()
				.getString("linkedin.api_secret_key");
		REDIRECT_URI = play.Play.application().configuration()
				.getString("linkedin.redirect_uri");
		STATE = play.Play.application().configuration()
				.getString("linkedin.state");
		SCOPE = play.Play.application().configuration()
				.getString("linkedin.scope");

		Logger.debug("API_KEY: " + API_KEY);
		Logger.debug("API_SECRET_KEY: " + API_SECRET_KEY);
		Logger.debug("REDIRECT_URI: " + REDIRECT_URI);
		Logger.debug("STATE: " + STATE);
		Logger.debug("SCOPE: " + SCOPE);
	}

	public static JsonNode startAuthToProfileString(String authrizationUrl)
			throws ParseException, JSONException, IOException {

		Response response = getAccessTokenResponse(authrizationUrl);
		JsonNode jsonObject = response.asJson();

		Logger.debug("access: " + jsonObject.toString());
		UserProfile userProfile = new UserProfile();
		userProfile.setAccessToken(jsonObject.get("access_token").asText());
		userProfile.setExpiredAt((System.currentTimeMillis() / 1000)
				+ jsonObject.get("expires_in").asLong());

		return getUserProfileJson(jsonObject.get("access_token").asText());
	}

	public static String getAccessTokenUrl(String authCode) {
		StringBuilder sb = new StringBuilder(
				"https://www.linkedin.com/uas/oauth2/accessToken");
		// "http://163.21.245.128/test/try.php");
		sb.append("?");
		sb.append("grant_type=authorization_code");

		sb.append("&");
		sb.append("code=");
		sb.append(authCode);

		sb.append("&");
		sb.append("redirect_uri=");
		sb.append(REDIRECT_URI);

		sb.append("&");
		sb.append("client_id=");
		sb.append(API_KEY);

		sb.append("&");
		sb.append("client_secret=");
		sb.append(API_SECRET_KEY);

		return sb.toString();
	}

	public static String getAuthrizationUrl() {
		StringBuilder sb = new StringBuilder(
				"https://www.linkedin.com/uas/oauth2/authorization");
		sb.append("?");
		sb.append("response_type=code");
		sb.append("&");
		sb.append("client_id=");
		sb.append(API_KEY);
		sb.append("&");
		sb.append("scope=");
		sb.append(SCOPE);
		sb.append("&");
		sb.append("state=");
		sb.append(STATE);
		sb.append("&");
		sb.append("redirect_uri=");
		sb.append(REDIRECT_URI);

		return sb.toString();
	}

	public static Response getAccessTokenResponse(String url) throws ParseException,
			IOException {
		return httpPost(url, "");
	}

	public static Response fetch(String accessToken, HttpMethod method,
			String resource) throws ParseException, IOException {

		StringBuilder sb = new StringBuilder("https://api.linkedin.com");
		sb.append(resource);
		sb.append("?");
		sb.append("oauth2_access_token=");
		sb.append(accessToken);
		sb.append("&");
		sb.append("format=json");
		Logger.debug("fetch: " + sb.toString());
		Response res;
		if (method.equals(HttpMethod.POST)) {
			res = httpPost(sb.toString(), "");
		} else {
			res = httpGet(sb.toString());
		}

		return res;
	}

	public static JsonNode getUserProfileJson(String accessToken)
			throws ParseException, JSONException, IOException {
		String fields = "id,first-name,last-name,headline,location:(name),"
				+ "industry,summary,specialties,picture-url,"
				+ "public-profile-url,site-standard-profile-request,"
				+ "skills,educations,connections,email-address";

		Response response = fetch(accessToken, HttpMethod.GET, 
				"/v1/people/~:(" + fields + ")");
		Logger.debug("profile: " + response.getBody());
		JsonNode profile = response.asJson();
		return profile;
	}

	private static Response httpGet(String url) throws ParseException,
			IOException {
		WS.WSRequest resquest = new WS.WSRequest("GET");
		resquest.setUrl(url);
		Promise<Response> resPromise = resquest.execute();
		Response res = resPromise.get(TIMEOUT);
		// Promise<Response> resultPromise = WS.url(url).get();
		// Response res = resultPromise.get(TIMEOUT);
		return res;
	}

	private static Response httpPost(String url, String content)
			throws ParseException, IOException {
		WS.WSRequest resquest = new WS.WSRequest("POST");
		resquest.setUrl(url);
		Promise<Response> resPromise = resquest.execute();
		Response res = resPromise.get(TIMEOUT);

		// Promise<Response> resultPromise = WS.url(url).execute("POST");
		// Response res = resultPromise.get(TIMEOUT);
		return res;
	}

}
