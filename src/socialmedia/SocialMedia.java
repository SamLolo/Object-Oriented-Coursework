package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	// 2 Private Attributes
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Post> posts = new ArrayList<>();

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// Check handle is valid (not null; no whitespace; <=30 characters)
		if (handle == "") {
			throw new InvalidHandleException("Handle cannot be empty!");
		} else if (handle.matches(".*\\s+.*")) {
			throw new InvalidHandleException("Handle '"+handle+"' contains invalid characters (whitespace)!");
		} else if (handle.length() > 30) {
			throw new InvalidHandleException("Handle '"+handle+"' is too long (>30 characters)!");
		}

		// Check if handle is unique (doesn't already exist)
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw new InvalidHandleException("Handle '"+handle+"' already exists!");
			}
		}

		// Create new account object using given handle
		Account newAccount = new Account(handle);
		System.out.println("New "+newAccount);
		accounts.add(newAccount);
		return newAccount.getIdentifier();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		// Check handle is valid (not null; no whitespace; <=30 characters)
		if (handle == "") {
			throw new InvalidHandleException("Handle cannot be empty!");
		} else if (handle.matches(".*\\s+.*")) {
			throw new InvalidHandleException("Handle '"+handle+"' contains invalid characters (whitespace)!");
		} else if (handle.length() > 30) {
			throw new InvalidHandleException("Handle '"+handle+"' is too long (>30 characters)!");
		}

		// Check if handle is unique (doesn't already exist)
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				throw new InvalidHandleException("Handle '"+handle+"' already exists!");
			}
		}

		// Create new account object using given handle and description
		Account newAccount = new Account(handle, description);
		System.out.println("New "+newAccount);
		accounts.add(newAccount);
		return newAccount.getIdentifier();
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// Get account with given identifier
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getIdentifier() == id) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw AccountIDNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new AccountIDNotRecognisedException("Un-able to find an account with id: "+id+"!");
        }

		ArrayList<Post> accountPosts = account.getPosts();
		for (int i=0; i < accountPosts.size(); i++) {
			Post post = accountPosts.get(i);
			post.delete();

			int postID = post.getIdentifier();
			for (int j=0; j < posts.size(); j++) {
				if (posts.get(j).getIdentifier() == postID) {
					posts.remove(j);
					break;
				}
			}
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// Get account with given handle
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
        }

		ArrayList<Post> posts = account.getPosts();
		for (int i=0; i < posts.size(); i++) {
			Post post = posts.get(i);
			post.delete();
			
			int postID = post.getIdentifier();
			for (int j=0; j < posts.size(); j++) {
				if (posts.get(j).getIdentifier() == postID) {
					posts.remove(j);
					break;
				}
			}
		}

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// Get account with given handle
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == oldHandle) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new HandleNotRecognisedException("Un-able to find account with handle: "+oldHandle+"!");
        }

		// Check handle is valid (not null; no whitespace; <=30 characters)
		if (newHandle == "") {
			throw new InvalidHandleException("Handle cannot be empty!");
		} else if (newHandle.matches(".*\\s+.*")) {
			throw new InvalidHandleException("Handle '"+newHandle+"' contains invalid characters (whitespace)!");
		} else if (newHandle.length() > 30) {
			throw new InvalidHandleException("Handle '"+newHandle+"' is too long (>30 characters)!");
		}

		// Check if handle is unique (doesn't already exist)
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == newHandle) {
				throw new InvalidHandleException("Handle '"+newHandle+"' already exists!");
			}
		}

		// Change account handle
		account.setHandle(newHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// Get account with given handle
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				account = accounts.get(i);
				break;
			}
		}

		// Change account description
		account.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// Search through the list of post objects.
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				Account account = accounts.get(i);
				StringBuilder accountInfo = account.getInfo();
				return accountInfo.toString();
			}
		}

		// Throw PostIDNotRecognisedException if no post found matching given id
		throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// Checks to see if the account exists
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				account = accounts.get(i);
				break;
			}
		}
		
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		}
		// Checks to see if the post is valid.
		if (message.length() > 100) {
			throw new InvalidPostException("The post message is too long! (>100 characters)");
		} else if (message.length() < 1) {
			throw new InvalidPostException("The post message is too short! (Cannot be empty!)");
		}

		Post newPost = account.createPost(message, account);
		posts.add(newPost);
		return newPost.getIdentifier();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

		// Checks to see if the account exists
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				account = accounts.get(i);
				break;
			}
		}
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		}
		else {
			// Checks to see if the post exists
			for (int i=0; i < posts.size(); i++) {
				if (posts.get(i).getIdentifier() == id) {
					// Creating New comment and adding it to the list of comments linked to the post
					Endorsement newEndorsement = account.createEndorsement(posts.get(i), account);
					posts.add(newEndorsement);
					return newEndorsement.getIdentifier();
				}
			}
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
			}
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

		// Checks to see if the account exists
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				account = accounts.get(i);
				break;
			}
		}
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		} else {
			// Checks to see if the post exists
			for (int i=0; i < posts.size(); i++) {
				if (posts.get(i).getIdentifier() == id) {

					// Creating New comment by using the function in Account
					Comment newComment = account.createComment(posts.get(i), message, account);
					posts.add(newComment);
					// Returning the ID of the new comment
					return newComment.getIdentifier();
				}
			}
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
		}
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// Check variable as if post ID is not Found it will remain False.
		Post post = null;

		// Search through the list of post objects.
		for (int i=0; i < posts.size(); i++)
			if (posts.get(i).getIdentifier() == id) {
				post = posts.get(i);
				break;
			}

		if (post == null) {
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
		}
		
		ArrayList<Endorsement> endorsements = post.getEndorsements();
		for (int i=0; i < endorsements.size(); i++) {
			int endorsementID = endorsements.get(i).getIdentifier();
			for (int j=0; j < posts.size(); j++) {
				if (posts.get(j).getIdentifier() == endorsementID) {
					posts.remove(j);
					break;
				}
			}
		}

		// The post is then deleted loosing the location of the original post and all of its endorsements
		post.delete();
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {
				posts.remove(i);
				break;
			}
		}
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// Search through the list of post objects.
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {
				Post post = posts.get(i);
				StringBuilder postInfo = post.getInfo();
				return postInfo.toString();
			}
		}

		// Throw PostIDNotRecognisedException if no post found matching given id
		throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		int count = 0;
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i) instanceof Post) {
				count += 1;
			}
		}
		return count;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int count = 0;
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i) instanceof Endorsement) {
				count += 1;
			}
		}
		return count;
	}

	@Override
	public int getTotalCommentPosts() {
		int count = 0;
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i) instanceof Comment) {
				count += 1;
			}
		}
		return count;
	}

	@Override
	public int getMostEndorsedPost() {
		Post topPost = null;
		int topCount = 0;
		
		for (int i=0; i < posts.size(); i++) {
			ArrayList<Endorsement> endorsements = posts.get(i).getEndorsements();
			if (endorsements.size() >= topCount) {
				topPost = posts.get(i);
				topCount = endorsements.size();
			}
		}

		if (topPost == null) {
			return 0;
		} else {
			return topPost.getIdentifier();
		}
	}

	@Override
	public int getMostEndorsedAccount() {
		Account topAccount = null;
		int topCount = 0;
		
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getTotalEndorsments() >= topCount) {
				topAccount = accounts.get(i);
				topCount = topAccount.getTotalEndorsments();
			}
		}

		if (topAccount == null) {
			return 0;
		} else {
			return topAccount.getIdentifier();
		}
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
