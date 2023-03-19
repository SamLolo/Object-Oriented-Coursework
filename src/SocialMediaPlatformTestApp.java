import org.junit.Test;
import socialmedia.*;

import javax.swing.border.CompoundBorder;

import static org.junit.jupiter.api.Assertions.*;

public class SocialMediaPlatformTestApp {

	// Tests For create Account:
	@Test
	public void InvalidHandleExceptionFromEmptyHandle() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("");
		});
	}

	@Test
	public void InvalidHandleExceptionFromWhiteSpaces() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("Handle Test");
		});
	}

	@Test
	public void InvalidHandleExceptionFromTooLong() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(InvalidHandleException.class, () -> {
			platform.createAccount("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		});
	}

	@Test
	public void IllegalHandleExceptionFromHandleAlreadyExisting() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(IllegalHandleException.class, () -> {
			platform.createAccount("Account1");
			platform.createAccount("Account1");
		});
	}

	@Test
	public void CheckIncrementalId() throws IllegalHandleException, InvalidHandleException {
		SocialMediaPlatform platform = new SocialMedia();
		assertEquals (1, platform.createAccount("Account1"));
		assertEquals (2, platform.createAccount("Account2"));
		assertEquals (3, platform.createAccount("Account3"));
		assertEquals (4, platform.createAccount("Account4"));
	}


	// Tests for remove account:
	@Test
	public void AccountIDNotRecognisedException() {
		SocialMediaPlatform platform = new SocialMedia();
		assertThrows(AccountIDNotRecognisedException.class, () -> {
			platform.createAccount("Account1");
			platform.removeAccount(-1);
		});
	}

	@Test
	public void testIfAnAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
		SocialMediaPlatform platform = new SocialMedia();
		int id = platform.createAccount("Account1");
		assertEquals(1, platform.getNumberOfAccounts());
		platform.removeAccount(id);
		assertEquals(0, platform.getNumberOfAccounts());
	}

	@Test
	public void testIfTheCorrectAccountIsRemoved() throws IllegalHandleException, InvalidHandleException, AccountIDNotRecognisedException {
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
	}


}

