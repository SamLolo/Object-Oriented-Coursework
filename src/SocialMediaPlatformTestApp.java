import socialmedia.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) throws InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {
		postTesting();

		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		}
		catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		}
		catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}
		catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}
	}

	public static void postTesting() throws InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		int id = platform.createPost("handle", "message");
		System.out.println(id);

		int id1 = platform.createPost("handle", "message");
		System.out.println(id1);

		int temp1 = platform.getTotalOriginalPosts();
		System.out.println(temp1);

		platform.deletePost(2);

		int temp2 = platform.getTotalOriginalPosts();
		System.out.println(temp2);

	}
}
