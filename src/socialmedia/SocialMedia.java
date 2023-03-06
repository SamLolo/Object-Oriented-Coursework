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
		// Checks to see if the account exists
		boolean check = true;
		for (int i=0; i<accounts.size(); i++) {
			if (accounts.get(i).getHandle() == handle) {
				check = false;
				break;
			}
		}
		if (check) {
			throw new HandleNotRecognisedException("The Account doesn't exist.");
		}
		// Checks to see if the post is valid.
		if (message.length() > 100) {
			throw new InvalidPostException("The Post message is too long.");
		} else if (message.length() < 1) {
			throw new InvalidPostException("The Post message is too short.");
		}

		Post newPost;
		newPost = new Post(handle,message);
		posts.add(newPost);
		return newPost.getIdentifier();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// Checks to see if the account exists
		boolean check = true;
		for (int i=0; i<accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				check = false;
				break;
			}
		}
		if (check) {
			throw new HandleNotRecognisedException("The Account doesn't exist.");
		}
		else {

			// Checks to see if the post exists
			boolean check1 = true;
			for (int i=0; i<posts.size(); i++) {
				if (posts.get(i).getIdentifier() == id) {
					// Creating New comment and adding it to the list of comments linked to the post
					Endorsement newEndorsement;
					newEndorsement = new Endorsement(handle, id, posts.get(i).getMessage());
					posts.get(i).addEndorsement(newEndorsement);

					check1 = false;
					break;
				}
			}

			if (check1) {
				throw new PostIDNotRecognisedException("The Post doesn't exist.");
			}}

		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// Checks to see if the account exists
		boolean check = true;
		for (int i=0; i<accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				check = false;
				break;
			}
		}
		if (check) {
			throw new HandleNotRecognisedException("The Account doesn't exist.");
		}
		else {

		// Checks to see if the post exists
		boolean check1 = true;
		for (int i=0; i<posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {
				// Creating New comment and adding it to the list of comments linked to the post
				Comment newComment;
				newComment = new Comment(handle, id, message);
				posts.get(i).addComment(newComment);

				check1 = false;
				break;
			}
		}

		if (check1) {
			throw new PostIDNotRecognisedException("The Post doesn't exist.");
		}}


		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// Check variable as if post ID is not Found it will remain False.
		boolean check = false;

		// Search through the list of post objects.
		for (int i = 0; i < this.posts.size(); i++)
			if (posts.get(i).getIdentifier() == id) {
				posts.get(i).createOrphans();
				posts.remove(posts.get(i).getIdentifier() - 1);
				check = true;
				break;
			}

		if (check) {
			throw new PostIDNotRecognisedException("The Post ID was not found.");
		}

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		StringBuilder postInfo = new StringBuilder();
		boolean check = true;

		// Search through the list of post objects.
		for (int i = 0; i < this.posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {
				postInfo.append("Id: ").append(posts.get(i).getIdentifier());
				postInfo.append("\n");
				postInfo.append("Account: ").append(posts.get(i).getAuthorHandle());
				postInfo.append("\n");
				postInfo.append("NO. endorsements: ").append(posts.get(i).getNOEndorsements());
				postInfo.append("  |  ");
				postInfo.append("NO. Comments: ").append(posts.get(i).getNOComments());
				postInfo.append("\n");
				postInfo.append(posts.get(i).getMessage());
				postInfo.append("\n");
				check = false;
				break;
			}
		}

		if (check) {
			throw new PostIDNotRecognisedException("The Post ID was not found.");
		}
		return postInfo.toString();
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
