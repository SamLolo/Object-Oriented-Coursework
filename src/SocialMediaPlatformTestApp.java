import org.junit.Test;
import socialmedia.*;
import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaPlatformTestApp {

	// Tests For createAccount:
	@Test
	public void createAccount_InvalidHandleExceptionFromEmptyHandle() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("");
		});
	}

	@Test
	public void createAccount_InvalidHandleExceptionFromWhiteSpaces() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Handle Test");
		});
	}

	@Test
	public void createAccount_InvalidHandleExceptionFromTooLong() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		});
	}

	@Test
	public void createAccount_IllegalHandleExceptionFromHandleAlreadyExisting() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(IllegalHandleException.class, () -> {
			platform.createAccount("Account1");
			platform.createAccount("Account1");
		});
	}

	@Test
	public void createAccount_CheckIncrementalId() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id1 = platform.createAccount("Account1");
		assertEquals (id1 + 1, platform.createAccount("Account2"));
		assertEquals (id1 + 2, platform.createAccount("Account3"));
		assertEquals (id1 + 3, platform.createAccount("Account4"));

		platform.removeAccount("Account1");
		platform.removeAccount("Account2");
		platform.removeAccount("Account3");
		platform.removeAccount("Account4");
	}


	// Tests for removeAccount:
	@Test
	public void removeAccount_AccountIDNotRecognisedException() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(AccountIDNotRecognisedException.class, () -> {
			platform.createAccount("Account1");
			platform.removeAccount(-1);
		});
	}

	@Test
	public void removeAccount_TestIfAnAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id = platform.createAccount("Account1");
		assertEquals(1, platform.getNumberOfAccounts());
		platform.removeAccount(id);
		assertEquals(0, platform.getNumberOfAccounts());
	}

	@Test
	public void removeAccount_TestIfTheCorrectAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException, HandleNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id1 = platform.createAccount("Account1");
		int id2 = platform.createAccount("Account2");
		int id3 = platform.createAccount("Account3");
		assertEquals(3, platform.getNumberOfAccounts());
		platform.removeAccount(id2);
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.showAccount("Account2");
		});
		assertEquals(2, platform.getNumberOfAccounts());

		platform.removeAccount("Account1");
		platform.removeAccount("Account3");
	}

	// Tests for changeAccountHandle
	@Test
	public void changeAccountHandle_HandleNotRecognisedException() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount("Account1");
			platform.changeAccountHandle("Account2", "NewAccount1");
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromEmptyHandle() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Account1");
			platform.changeAccountHandle("Account1", "");
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromWhiteSpaces() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Account1");
			platform.changeAccountHandle("Account1", "Handle Test");
		});
	}

	@Test
	public void changeAccountHandle_InvalidNewHandleExceptionFromTooLong() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Account1");
			platform.changeAccountHandle("Account1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		});
	}

	@Test
	public void changeAccountHandle_TestIfAccountIsChanged() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id1 = platform.createAccount("Account1");
		assertEquals("ID: "+id1+"\nHandle: Account1\nDescription: \nPost count: 0\nEndorse count: 0", platform.showAccount("Account1"));
		platform.changeAccountHandle("Account1", "Account2");
		assertEquals("ID: "+id1+"\nHandle: Account2\nDescription: \nPost count: 0\nEndorse count: 0", platform.showAccount("Account2"));

		platform.removeAccount("Account2");
	}

	//Tests for updateAccountDescription
	@Test
	public void updateAccountDescription_HandleNotRecognisedException(){
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount("Account1");
			platform.updateAccountDescription("Account2", "NewAccount1");
		});
	}

	@Test
	public void updateAccountDescription_TestIfDescriptionIsChanged() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id1 = platform.createAccount("Account1", "Old Description");
		assertEquals("ID: "+id1+"\nHandle: Account1\nDescription: Old Description\nPost count: 0\nEndorse count: 0", platform.showAccount("Account1"));
		platform.updateAccountDescription("Account1", "New Description");
		assertEquals("ID: "+id1+"\nHandle: Account1\nDescription: New Description\nPost count: 0\nEndorse count: 0", platform.showAccount("Account1"));

		platform.removeAccount("Account1");
	}

	//Tests for showAccount
	@Test
	public void showAccount_HandleNotRecognisedException(){
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(HandleNotRecognisedException.class, () -> {
			platform.createAccount("Account1");
			platform.showAccount("Account2");
		});
	}

	@Test
	public void showAccount_TestIfAccountIsShown() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id1 = platform.createAccount("Account1", "Old Description");
		assertEquals("ID: "+id1+"\nHandle: Account1\nDescription: Old Description\nPost count: 0\nEndorse count: 0", platform.showAccount("Account1"));

		platform.removeAccount("Account1");
	}

	//Tests for createPost

}

