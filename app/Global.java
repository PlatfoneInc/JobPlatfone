import play.Application;
import play.GlobalSettings;
import play.Logger;
import controllers.LinkedInProcess;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");
		LinkedInProcess.run();
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

}