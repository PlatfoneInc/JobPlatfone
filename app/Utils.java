import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;

import controllers.LinkedInProcess;

public class Utils {

	public static void logDiagnostics(OAuthRequest request, Response response) {
		System.out
				.println("\n\n[********************LinkedIn API Diagnostics**************************]\n");
		System.out.println("Key: |-> " + LinkedInProcess.API_KEY + " <-|");
		System.out.println("\n|-> [******Sent*****] <-|");
		System.out.println("Headers: |-> " + request.getHeaders().toString()
				+ " <-|");
		System.out.println("URL: |-> " + request.getUrl() + " <-|");
		System.out.println("Query Params: |-> "
				+ request.getQueryStringParams().toString() + " <-|");
		System.out.println("Body Contents: |-> " + request.getBodyContents()
				+ " <-|");
		System.out.println("\n|-> [*****Received*****] <-|");
		System.out.println("Headers: |-> " + response.getHeaders().toString()
				+ " <-|");
		System.out.println("Body: |-> " + response.getBody() + " <-|");
		System.out
				.println("\n[******************End LinkedIn API Diagnostics************************]\n\n");
	}

}
