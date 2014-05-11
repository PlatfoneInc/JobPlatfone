import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {

		Logger.info("Application has started");

		// Clean the index
//		IndexService.cleanIndex();
//		IndexService.refresh();
		
//		Who who = new Who(
//				"GE Capital",
//				"GE Capital Americas: At GE Capital Americas, we’re redefining ...",
//				"http://jobs.gecareers.com/job/Chicago-Java-Architect-Job-IL-60290/33443000/");
//
//		List<String> responsibilities = new ArrayList<String>();
//		responsibilities
//				.add("Analyze business needs and information requirements...");
//		responsibilities.add("Develops detailed analysis...");
//		responsibilities.add("Conducts business analysis...");
//
//		List<String> qualifications = new ArrayList<String>();
//		qualifications.add("Bachelor’s Degree with 2+ years of IT experience.");
//		qualifications.add("Must submit your application...");
//		qualifications.add("Legal authorization to work in...");
//
//		List<String> additionalQualifications = new ArrayList<String>();
//		additionalQualifications.add("GE will only employ ...");
//
//		List<String> desiredCharacteristics = new ArrayList<String>();
//		desiredCharacteristics
//				.add("liIn depth hands on experience with Java/J2EE architecture & design");
//		desiredCharacteristics.add("3+ years hands on experience...");
//		desiredCharacteristics.add("2+ years experience in data warehousing");
//
//		What what = new What("Java Senior Developer Job", "GE is an ...",
//				"full-time", responsibilities, qualifications,
//				additionalQualifications, desiredCharacteristics);
//
//		Where where = new Where("Chicago", "IL", "United States");
//
//		Job job1 = new Job(0, who, what, where, "2014-03-14");
//		Job job2 = new Job(1, who, what, where, "2014-04-14");
//		
//		job1.index();
//		job2.index();
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

}