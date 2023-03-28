import org.junit.Test;
import socialmedia.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaPlatformTestApp {
	private static SocialMediaPlatform platform = new SocialMedia();
	private static int count = 1;

	public String getNewAccountHandle() {
		int temp = count;
		count += 1;
		return "Account" + count;
	}

	// Tests For createAccount:
	@Test
	public void createAccount_InvalidHandleExceptionFromEmptyHandle() {
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("");
		});
	}

	@Test
	public void createAccount_InvalidHandleExceptionFromWhiteSpaces() {
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Handle Test");
		});
	}

	@Test
	public void createAccount_InvalidHandleExceptionFromTooLong() {
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		});
	}

	@Test
	public void createAccount_IllegalHandleExceptionFromHandleAlreadyExisting() {
		assertThrows(IllegalHandleException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createAccount(account);
		});
	}

	@Test
	public void createAccount_CheckIncrementalId() throws IllegalHandleException, InvalidHandleException {
		int id1 = platform.createAccount(getNewAccountHandle());

		assertEquals(id1 + 1, platform.createAccount(getNewAccountHandle()));
		assertEquals(id1 + 2, platform.createAccount(getNewAccountHandle()));
		assertEquals(id1 + 3, platform.createAccount(getNewAccountHandle()));
	}


	// Tests for removeAccount:
	@Test
	public void removeAccount_AccountIDNotRecognisedException() {
		assertThrows(AccountIDNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			platform.removeAccount(-1);
		});
	}

	@Test
	public void removeAccount_TestIfAnAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
		int id = platform.createAccount(getNewAccountHandle());
		int numOfAccountsBefore = platform.getNumberOfAccounts();
		platform.removeAccount(id);
		assertEquals(numOfAccountsBefore - 1, platform.getNumberOfAccounts());
	}

	@Test
	public void removeAccount_TestIfTheCorrectAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
		int id1 = platform.createAccount(getNewAccountHandle());
		String account = getNewAccountHandle();
		int id2 = platform.createAccount(account);
		int id3 = platform.createAccount(getNewAccountHandle());

		platform.removeAccount(id2);
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.showAccount(account);
		});
	}

	// Tests for changeAccountHandle
	@Test
	public void changeAccountHandle_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			platform.changeAccountHandle(getNewAccountHandle(), "New" + getNewAccountHandle());
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromEmptyHandle() {
		assertThrows(InvalidHandleException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.changeAccountHandle(account, "");
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromWhiteSpaces() {
		assertThrows(InvalidHandleException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.changeAccountHandle(account, "Handle Test");
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromTooLong() {
		assertThrows(InvalidHandleException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.changeAccountHandle(account, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		});
	}

	@Test
	public void changeAccountHandle_TestIfAccountIsChanged() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		String account = getNewAccountHandle();
		int id1 = platform.createAccount(account);
		assertEquals("ID: " + id1 + "\nHandle: " + account + "\nDescription: \nPost count: 0\nEndorse count: 0", platform.showAccount(account));

		String newAccount = "new" + getNewAccountHandle();
		platform.changeAccountHandle(account, newAccount);
		assertEquals("ID: " + id1 + "\nHandle: " + newAccount + "\nDescription: \nPost count: 0\nEndorse count: 0", platform.showAccount(newAccount));
	}

	//Tests for updateAccountDescription
	@Test
	public void updateAccountDescription_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			platform.updateAccountDescription(getNewAccountHandle(), "null");
		});
	}

	@Test
	public void updateAccountDescription_TestIfDescriptionIsChanged() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		String account = getNewAccountHandle();
		int id1 = platform.createAccount(account, "This is a fun description.");
		assertEquals("ID: " + id1 + "\nHandle: " + account + "\nDescription: This is a fun description.\nPost count: 0\nEndorse count: 0", platform.showAccount(account));

		platform.updateAccountDescription(account, "This is a bad description.");
		assertEquals("ID: " + id1 + "\nHandle: " + account + "\nDescription: This is a bad description.\nPost count: 0\nEndorse count: 0", platform.showAccount(account));
	}

	//Tests for showAccount
	@Test
	public void showAccount_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			platform.showAccount(getNewAccountHandle());
		});
	}

	@Test
	public void showAccount_TestIfAccountIsShown() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		String account = getNewAccountHandle();
		int id1 = platform.createAccount(account, "Old Description");
		assertEquals("ID: " + id1 + "\nHandle: " + account + "\nDescription: Old Description\nPost count: 0\nEndorse count: 0", platform.showAccount(account));
	}

	//Tests for createPost
	@Test
	public void createPost_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			platform.createPost(getNewAccountHandle(), "Null");
		});
	}

	@Test
	public void createPost_InvalidPostExceptionFromEmptyMessage() {
		assertThrows(InvalidPostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "");
		});
	}

	@Test
	public void createPost_InvalidPostExceptionFromMessageTooLong() {
		assertThrows(InvalidPostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "asdfghjkasdfghjkasdfghjkasdfghjkasdfghjkasdfghjkasdfghjkasdfghjkasdfghjkasdfghjkjasdfghjkl;lkjhgfdfghjk");
		});
	}

	@Test
	public void createPost_TestIfPostIsCreatedCorrectly() throws PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");
		assertEquals("ID: " + postId + "\nAccount: " + account + "\nNo. endorsements: 0 | No. comments: 0\nThis is the default message", platform.showIndividualPost(postId));
	}

	//Tests for endorsePost
	@Test
	public void endorsePost_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			int postId = platform.createPost(getNewAccountHandle(), "Null");
			platform.endorsePost(getNewAccountHandle(), postId);
		});
	}

	@Test
	public void endorsePost_PostIDNotRecognisedException() {
		assertThrows(PostIDNotRecognisedException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "This is the default message");
			platform.endorsePost(account, -1);
		});
	}

	@Test
	public void endorsePost_NotActionablePostExceptionWithAnEndorsement() {
		assertThrows(NotActionablePostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			int postId = platform.createPost(account, "This is the default message");
			int endorsementId = platform.endorsePost(account, postId);
			platform.endorsePost(account, endorsementId);
		});
	}

	@Test
	public void endorsePost_TestIfEndorsementIsCreatedCorrectly() throws PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, IllegalHandleException, InvalidHandleException, NotActionablePostException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");
		int endorsementId = platform.endorsePost(account, postId);
		assertEquals("ID: " + endorsementId + "\nAccount: " + account + "\nNo. endorsements: 0 | No. comments: 0\nThis is the default message", platform.showIndividualPost(endorsementId));
	}

	//Tests for commentPost
	@Test
	public void commentPost_HandleNotRecognisedException() {
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount(getNewAccountHandle());
			int postId = platform.createPost(getNewAccountHandle(), "This is the default message");
			platform.commentPost(getNewAccountHandle(), postId, "This is the default message for a comment");
		});
	}

	@Test
	public void commentPost_InvalidPostExceptionFromEmptyMessage() {
		assertThrows(InvalidPostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			int postId = platform.createPost(account, "This is the default message");
			platform.commentPost(account, postId, "");
		});
	}
	@Test
	public void commentPost_InvalidPostExceptionFromMessageTooLong() {
		assertThrows(InvalidPostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			int postId = platform.createPost(account, "This is the default message");
			platform.commentPost(account, postId, "asdfghjklkjhgfdsasdfghjklkjhgfdfghjkjgkdfjgaskljfgkasagdfgjhdhfgkjahgkdfahjgsjdghfkjahsgdfkjasdhgfkasjfh");
		});
	}

	@Test
	public void commentPost_PostIDNotRecognisedException() {
		assertThrows(PostIDNotRecognisedException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "This is the default message");
			platform.commentPost(account, -1, "This is the default message for a comment");
		});
	}
	@Test
	public void commentPost_NotActionablePostExceptionWithAnEndorsement() {
		assertThrows(NotActionablePostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			int postId = platform.createPost(account, "This is the default message");
			int endorsementId = platform.endorsePost(account, postId);
			platform.commentPost(account, endorsementId, "This is the default message for a comment");
		});
	}
	@Test
	public void commentPost_TestIfCommentIsCreatedCorrectly() throws PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, IllegalHandleException, InvalidHandleException, NotActionablePostException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");
		int commentId = platform.endorsePost(account, postId);
		assertEquals("ID: " + commentId + "\nAccount: " + account + "\nNo. endorsements: 0 | No. comments: 0\nThis is the default message", platform.showIndividualPost(commentId));
	}

	//Tests for deletePost
	@Test
	public void deletePost_PostIDNotRecognisedException() {
		assertThrows(PostIDNotRecognisedException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "This is the default message");
			platform.deletePost(-1);
		});
	}
	@Test
	public void deletePost_TestIfEndorsementsAreAlsoRemoved() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");
		int numOfEndorsements = platform.getTotalEndorsmentPosts();
		platform.endorsePost(account, postId);
		platform.endorsePost(account, postId);
		assertEquals(numOfEndorsements + 2, platform.getTotalEndorsmentPosts());
		platform.deletePost(postId);
		assertEquals(numOfEndorsements, platform.getTotalEndorsmentPosts());
	}

	@Test
	public void deletePost_TestIfAPostIsRemoved() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int numOfPosts = platform.getTotalOriginalPosts();
		int postId = platform.createPost(account, "This is the default message");
		assertEquals(numOfPosts + 1, platform.getTotalOriginalPosts());
		platform.deletePost(postId);
		assertEquals(numOfPosts, platform.getTotalOriginalPosts());
	}

	@Test
	public void deletePost_TestIfCommentsAreRemoved() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");

		platform.commentPost(account, postId, "This is the default message for a comment");
		platform.commentPost(account, postId, "This is the default message for a comment");
		int numOfComments = platform.getTotalCommentPosts();

		assertEquals(numOfComments, platform.getTotalCommentPosts());
		platform.deletePost(postId);
		assertEquals(numOfComments, platform.getTotalCommentPosts());
	}

	//Tests for showIndividualPost
	@Test
	public void showIndividualPosts_PostIDNotRecognisedException() {
		assertThrows(PostIDNotRecognisedException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "This is the default message");
			platform.showIndividualPost(-1);
		});
	}

	@Test
	public void showIndividualPosts_TestIfCorrectOutput() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		String account = getNewAccountHandle();
		platform.createAccount(account);
		int postId = platform.createPost(account, "This is the default message");
		assertEquals("ID: "+postId+"\nAccount: "+account+"\nNo. endorsements: 0 | No. comments: 0\nThis is the default message", platform.showIndividualPost(postId));
		platform.endorsePost(account, postId);
		platform.endorsePost(account, postId);
		platform.commentPost(account, postId, "This is the default message for a comment");
		assertEquals("ID: "+postId+"\nAccount: "+account+"\nNo. endorsements: 2 | No. comments: 1\nThis is the default message", platform.showIndividualPost(postId));
	}

	//Tests for showPostChildrenDetails
	@Test
	public void showPostChildrenDetails_PostIDNotRecognisedException() {
		assertThrows(PostIDNotRecognisedException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			platform.createPost(account, "This is the default message");
			platform.showPostChildrenDetails(-1);
		});
	}
	@Test
	public void showPostChildrenDetails_NotActionablePostExceptionWithAnEndorsement() {
		assertThrows(NotActionablePostException.class, () -> {
			String account = getNewAccountHandle();
			platform.createAccount(account);
			int postId = platform.createPost(account, "This is the default message");
			int endorsementId = platform.endorsePost(account, postId);
			platform.showPostChildrenDetails(endorsementId);
		});
	}
	@Test
	public void showPostChildrenDetails_TestIfCorrectOutput() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		String account1 = getNewAccountHandle();
		String account2 = getNewAccountHandle();
		String account3 = getNewAccountHandle();
		String account5 = getNewAccountHandle();
		platform.createAccount(account1);
		platform.createAccount(account2);
		platform.createAccount(account3);
		platform.createAccount(account5);

		int postId = platform.createPost(account1, "I like examples.");

		platform.endorsePost(account1, postId);
		platform.endorsePost(account2, postId);

		int comment1Id = platform.commentPost(account2, postId, "No more then me...");
		int comment2Id = platform.commentPost(account1, comment1Id, "I can prove!");
		platform.commentPost(account2, comment2Id, "prove it");

		int comment4Id = platform.commentPost(account3, postId, "Can't you do better then this?");
		platform.endorsePost(account1, comment4Id);
		platform.endorsePost(account2, comment4Id);
		platform.endorsePost(account3, comment4Id);
		platform.endorsePost(account5, comment4Id);

		int comment5Id = platform.commentPost(account5, postId, "where is the example?");
		platform.commentPost(account1, comment5Id, "This is the example!");


		System.out.print(platform.showPostChildrenDetails(postId));
		System.out.print("\n");
		System.out.print(platform.showPostChildrenDetails(comment5Id));
	}

	//Tests for getNumberOfAccounts
	@Test
	public void getNumberOfAccounts_Test() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
		int numOfAccounts = platform.getNumberOfAccounts();
		int accountId = platform.createAccount(getNewAccountHandle());
		assertEquals(numOfAccounts + 1, platform.getNumberOfAccounts());
		platform.removeAccount(accountId);
		assertEquals(numOfAccounts, platform.getNumberOfAccounts());
	}

	//Tests for getTotalOriginalPosts
	@Test
	public void getTotalOriginalPosts_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		int numOfPosts = platform.getTotalOriginalPosts();

		String account = getNewAccountHandle();
		platform.createAccount(account);

		int postId = platform.createPost(account, "This is the default message.");
		platform.commentPost(account, postId, "This is the default message for a comment.");
		platform.endorsePost(account, postId);

		assertEquals(numOfPosts + 1, platform.getTotalOriginalPosts());

		platform.deletePost(postId);
		assertEquals(numOfPosts, platform.getTotalOriginalPosts());
	}

	//Tests for getTotalEndorsedPosts
	@Test
	public void getTotalEndorsedPosts_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		int numOfEndorsements = platform.getTotalEndorsmentPosts();

		String account = getNewAccountHandle();
		platform.createAccount(account);

		int postId = platform.createPost(account, "This is the default message.");
		int endorsementId = platform.endorsePost(account, postId);

		assertEquals(numOfEndorsements + 1, platform.getTotalEndorsmentPosts());

		platform.deletePost(endorsementId);
		assertEquals(numOfEndorsements, platform.getTotalEndorsmentPosts());
	}

	//Tests for getTotalCommentPosts
	@Test
	public void getTotalCommentPosts_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		int numOfComments = platform.getTotalCommentPosts();

		String account = getNewAccountHandle();
		platform.createAccount(account);

		int postId = platform.createPost(account, "This is the default message.");
		int commentId = platform.commentPost(account, postId, "This is the default message for a comment.");

		assertEquals(numOfComments + 1, platform.getTotalCommentPosts());

		platform.deletePost(commentId);
		assertEquals(numOfComments, platform.getTotalCommentPosts());
	}

	//Tests for getMostEndorsedPost
	@Test
	public void getMostEndorsedPost_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		String account1 = getNewAccountHandle();
		platform.createAccount(account1);
		int post1Id = platform.createPost(account1, "This is the default message.");
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);

		String account2 = getNewAccountHandle();
		platform.createAccount(account2);
		int post2Id = platform.createPost(account2, "This is the default message.");
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);


		assertEquals(post2Id, platform.getMostEndorsedPost());
		platform.deletePost(post1Id);
		platform.deletePost(post2Id);
	}

	// Tests for getMostEndorsedAccount
	@Test
	public void getMostEndorsedAccount_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		String account1 = getNewAccountHandle();
		int account1Id = platform.createAccount(account1);
		int post1Id = platform.createPost(account1, "This is the default message.");
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);

		String account2 = getNewAccountHandle();
		int account2Id = platform.createAccount(account2);
		int post2Id = platform.createPost(account2, "This is the default message.");
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);
		platform.endorsePost(account2, post2Id);




		assertEquals(account2Id, platform.getMostEndorsedAccount());
		platform.deletePost(post1Id);
		platform.deletePost(post2Id);
	}

	// Tests for erasePlatform
	@Test
	public void erasePlatform_Test() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
		int numOfAccounts = platform.getNumberOfAccounts();
		int numOfPosts = platform.getTotalOriginalPosts();

		String account1 = getNewAccountHandle();
		int account1Id = platform.createAccount(account1);
		int post1Id = platform.createPost(account1, "This is the default message.");
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);

		assertEquals(numOfAccounts + 1 , platform.getNumberOfAccounts());
		assertEquals(numOfPosts + 1, platform.getTotalOriginalPosts());

		platform.erasePlatform();
		assertEquals(0 , platform.getNumberOfAccounts());
		assertEquals(0, platform.getTotalOriginalPosts());
	}

	//Tests for savePlatform / LoadPlatform
	@Test
	public void savePlatform_IOException(){
	}
	@Test
	public void LoadPlatform_IOException(){
	}

	@Test
	public void LoadPlatform_ClassNotFound(){
	}
	@Test
	public void savePlatform_loadPlatform_Test() throws IllegalHandleException, InvalidHandleException, NotActionablePostException, PostIDNotRecognisedException, HandleNotRecognisedException, InvalidPostException, IOException, ClassNotFoundException {
		int numOfAccounts = platform.getNumberOfAccounts();
		int numOfPosts = platform.getTotalOriginalPosts();

		String account1 = getNewAccountHandle();
		int account1Id = platform.createAccount(account1);
		int post1Id = platform.createPost(account1, "This is the default message.");
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);
		platform.endorsePost(account1, post1Id);

		assertEquals(numOfAccounts + 1 , platform.getNumberOfAccounts());
		assertEquals(numOfPosts + 1, platform.getTotalOriginalPosts());

		platform.savePlatform("Test");

		platform.erasePlatform();
		assertEquals(0 , platform.getNumberOfAccounts());
		assertEquals(0, platform.getTotalOriginalPosts());

		platform.loadPlatform("Test");

		assertEquals(numOfAccounts + 1 , platform.getNumberOfAccounts());
		assertEquals(numOfPosts + 1, platform.getTotalOriginalPosts());


	}
}

