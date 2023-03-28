package socialmedia;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * SocialMedia is an implementor of the SocialMediaPlatform interface.
 * It provides functionality for handling accounts and posts on the system, as
 * well as analytics and system management methods.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	/**
	 * List of all active accounts within the system
	 */
	private ArrayList<Account> accounts = new ArrayList<>();
	/**
	 * List of all posts active within the system (including comments and endorsements)
	 */
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
				throw new IllegalHandleException("Handle '"+handle+"' already exists!");
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
				throw new IllegalHandleException("Handle '"+handle+"' already exists!");
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
				accounts.remove(i);
				break;
			}
		}

		// Throw AccountIDNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new AccountIDNotRecognisedException("Un-able to find an account with id: "+id+"!");
        }

		// Get all posts associated with account & delete each one
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
				accounts.remove(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
        if (account == null) {
            throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
        }

		// Get all posts associated with account & delete each one
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
				throw new IllegalHandleException("Handle '"+newHandle+"' already exists!");
			}
		}
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

		// Throw HandleNotRecognisedException if account cannot be found with given handle
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		}
		account.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// Search through the list of post objects, and get post info when account found
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

		// Checks to see if the post is valid.
		if (message.length() > 100) {
			throw new InvalidPostException("The post message is too long! (>100 characters)");
		} else if (message.length() < 1) {
			throw new InvalidPostException("The post message is too short! (Cannot be empty!)");
		}

		// Create new post
		Post newPost = account.createPost(message);
		posts.add(newPost);
		return newPost.getIdentifier();
	}

	@Override
	public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

		// Get account with given handle
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		}
		else {
			// Check post is actionable and exists in the system
			for (int i=0; i < posts.size(); i++) {
				if (posts.get(i).getIdentifier() == id) {
					if (posts.get(i) instanceof Endorsement) {
						throw new NotActionablePostException("Un-able to endorse a endorsement");
					}

					// Create new endorsement
					Endorsement newEndorsement = account.createEndorsement(posts.get(i));
					posts.add(newEndorsement);
					return newEndorsement.getIdentifier();
					}
				}
			}
			// Throw PostIDNotRecognisedException if no post found with matching id
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

		// Checks to see if the post is valid.
		if (message.length() > 100) {
			throw new InvalidPostException("The post message is too long! (>100 characters)");
		} else if (message.length() < 1) {
			throw new InvalidPostException("The post message is too short! (Cannot be empty!)");
		}

		// Get account with given handle
		Account account = null;
		for (int i=0; i < accounts.size(); i++) {
			if (Objects.equals(accounts.get(i).getHandle(), handle)) {
				account = accounts.get(i);
				break;
			}
		}

		// Throw HandleNotRecognisedException if account cannot be found with given handle
		if (account == null) {
			throw new HandleNotRecognisedException("Un-able to find account with handle: "+handle+"!");
		} else {
			// Check post is actionable and exists in the system
			for (int i=0; i < posts.size(); i++) {
				if (posts.get(i).getIdentifier() == id) {
					if (posts.get(i) instanceof Endorsement) {
						throw new NotActionablePostException("Un-able to endorse a endorsement");
					}
					// Create new comment
					Comment newComment = account.createComment(posts.get(i), message);
					posts.add(newComment);
					return newComment.getIdentifier();
				}
			}
			// Throw PostIDNotRecognisedException if no post found with matching id
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
		}
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// Get post with matching id
		Post post = null;
		for (int i=0; i < posts.size(); i++)
			if (posts.get(i).getIdentifier() == id) {
				post = posts.get(i);
				break;
			}

		// Throw PostIDNotRecognisedException if no post found with matching id
		if (post == null) {
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
		}
		
		// Go through posts endorsements and remove each of them from the system
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

		// Tell post to delete and remove it from the system.
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
		// Find post with matching id and get posts formatted information
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {
				Post post = posts.get(i);
				StringBuilder postInfo = post.getInfo(0);
				return postInfo.toString();
			}
		}

		// Throw PostIDNotRecognisedException if no post found matching given id
		throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
		// Get post with matching id
		Post post = null;
		for (int i=0; i < posts.size(); i++) {
			if (posts.get(i).getIdentifier() == id) {

				// Check if post is actionable, ie: not an endorsement
				if (posts.get(i) instanceof Endorsement) {
					throw new NotActionablePostException("Un-able to showPostChildrenDetails of a endorsement");
				} else {
					post = posts.get(i);
					break;
				}
			}
		}

		// Throw PostIDNotRecognisedException if no post found matching given id
		if (post == null) {
			throw new PostIDNotRecognisedException("Un-able to find post with id: "+id+"!");
		}

		// Create string builder by calling recursive child info function of root post
		StringBuilder details = new StringBuilder();
		post.getChildInfo(details, 0);
		return details;
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		// Count all posts of type Post (and not comments or endorsements) in posts array
		int count = 0;
		for (int i=0; i < posts.size(); i++) {
			if (!(posts.get(i) instanceof Comment) && !(posts.get(i) instanceof Endorsement)) {
				count += 1;
			}
		}
		return count;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// Count all posts of type Endorsement in posts array
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
		// Count all posts of type Comment in posts array
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
		// Check each post in posts array and keep track of the most endorsed post and it's endorsement count
		Post topPost = null;
		int topCount = 0;
		for (int i=0; i < posts.size(); i++) {
			ArrayList<Endorsement> endorsements = posts.get(i).getEndorsements();
			if (endorsements.size() >= topCount) {
				topPost = posts.get(i);
				topCount = endorsements.size();
			}
		}

		// Return 0 if no posts in system
		if (topPost == null) {
			return 0;
		} else {
			return topPost.getIdentifier();
		}
	}

	@Override
	public int getMostEndorsedAccount() {
		// Check each account and keep track of the most endorsed account and it's endorsement count
		Account topAccount = null;
		int topCount = 0;
		for (int i=0; i < accounts.size(); i++) {
			if (accounts.get(i).getTotalEndorsments() >= topCount) {
				topAccount = accounts.get(i);
				topCount = topAccount.getTotalEndorsments();
			}
		}

		// Return 0 if no accounts in system
		if (topAccount == null) {
			return 0;
		} else {
			return topAccount.getIdentifier();
		}
	}

	@Override
	public void erasePlatform() {
		// Reset counters for identifiers in account and post classes
		Account.setCount(0);
		Post.setCount(0);

		// Remove all stored posts and accounts from the system
		accounts.clear();
		posts.clear();
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try {
			// Create ObjectOutputStream for given filepath (creates file if one not found)
			FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream (file);
            
			// Write accounts and posts arrays into file
			out.writeObject(accounts);
			out.writeObject(posts);
            
			// Properly close output streams once finished
			out.close();
			file.close();

		// Pass through exceptions if thrown during file write process
        } catch(IOException e) {
            throw e;
        }
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try {  
			// Create ObjectInputStream for given filename
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
             
			// Load accounts and posts array objects from serialised file
            accounts = (ArrayList<Account>)in.readObject();
			posts = (ArrayList<Post>)in.readObject();

			// Set the count in Account & Post classes to largest current identifier just loaded
            Account lastAccount = accounts.get(accounts.size() - 1);
			Account.setCount(lastAccount.getIdentifier());

			Post lastPost = posts.get(posts.size() - 1);
			Post.setCount(lastPost.getIdentifier());
			
			// Properly close input streams once finished
            in.close();
            file.close();

		// Pass through exceptions if thrown during file read process
        } catch(IOException e) {
            throw e;
        } catch(ClassNotFoundException e) {
            throw e;
        }
	}

}
