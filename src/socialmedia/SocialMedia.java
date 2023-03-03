package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

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
		for (int i=0; i<accounts.size(); i++) {
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
		for (int i=0; i<accounts.size(); i++) {
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
		for (int i=0; i<accounts.size(); i++) {
			if (accounts.get(i).getIdentifier() == id) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw AccountIDNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new AccountIDNotRecognisedException("Failed to find an account with id: '"+id+"'");
        }

		// TODO: Add functionality to delete all posts
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// Get account with given handle
		Account account = null;
		for (int i=0; i<accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new HandleNotRecognisedException("'"+handle+"' is not an existing account handle!");
        }

		// TODO: Add functionality to delete all posts

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// Get account with given handle
		Account account = null;
		for (int i=0; i<accounts.size(); i++) {
			if (accounts.get(i).getHandle() == oldHandle) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new HandleNotRecognisedException("'"+oldHandle+"' is not an existing account handle!");
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
		for (int i=0; i<accounts.size(); i++) {
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
		for (int i=0; i<accounts.size(); i++) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		Post newPost;
		newPost = new Post(handle,message);
		this.posts.add(newPost);
		return newPost.getIdentifier();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		for (int i = 0; i < this.posts.size(); i++) {
			Post post = posts.get(i);
			int postID = post.getIdentifier();
			if (postID == id) {
				posts.remove(postID - 1);
				break;
			}
		}

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}
// Fix
	@Override
	public int getTotalOriginalPosts() {
		return posts.size();
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
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
