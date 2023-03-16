package socialmedia;

import java.util.ArrayList;


/**
 * Account class representing an account on the SocialMediaPlatform. 
 * Has two constructors, one for supplying both a description and a handle
 * and the other for just a handle. The identifier is linearly generated as to be
 * unique.
 * Also provides functionality for managing account posts within the platform.
 * 
 * @version 1.0
 *
 */
public class Account {
    // Private Attributes
    private static int count = 0;
    private int identifier;
    private String handle;
    private String description = "";
    private ArrayList<Post> posts = new ArrayList<Post>();

    /**
	 * The method creates a string showing clearly the state of the attributes of the account class.
	 * 
	 * @return a String detailing account attributes.
	 */
    @Override
    public String toString() {
        return "Account[id="+identifier+", handle="+handle+", description="+description+"]";
    }

    /**
	 * The method gets the value of the private int identifier for the class.
	 * 
	 * @return a int identifier for the account
	 */
    public int getIdentifier() {
        return identifier;
    }

    /**
	 * The method gets the accounts handle.
	 * 
	 * @return a String handle used to identify the account
	 */
    public String getHandle() {
        return handle;
    }

    /**
	 * The method gets the accounts description. Will return "" if no description
     * has been set.
	 * 
	 * @return a String description for the account
	 */
    public String getDescription() {
        return description;
    }

    /**
	 * The method gets the value of the private int identifier for the class.
	 * 
	 * @return a int identifier for the account
	 */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
	 * The method sets the account handle to the new String specified.
     * <p>
     * Assumes the given handle is already unique, and has been checked 
     * beforehand by the SocialMedia class.
	 * 
	 * @param h the new handle for the account
	 */
    public void setHandle(String h) {
        handle = h;
    }

    /**
	 * The method sets the account description to the new String specified.
	 * 
	 * @param d the new description for the account
	 */
    public void setDescription(String d) {
        description = d;
    }

    // Method: delete Post
    public void deletePost(int id) {
        for (int i=0; i < posts.size(); i++) {
            if (posts.get(i).getIdentifier() == id) {
                posts.remove(i);
                break;
            }
        }
    }

    // Method: Create Post
    public Post createPost(String message, Account account) {
        Post newPost = new Post(account, message);
        posts.add(newPost);
        return newPost;
    }

    // Method: Create Comment
    public Comment createComment(Post post, String message, Account account) {
        Comment newComment = new Comment(post.getIdentifier(), message, account);
        post.addComment(newComment);
        return newComment;
    }

    // Method: Create Endorsement
    public Endorsement createEndorsement(Post post, Account account) {
        Endorsement newEndorsement = new Endorsement(account, post.getIdentifier(), post.getMessage());
        post.addEndorsement(newEndorsement);
        return newEndorsement;
    }

    // Method: Sum Endorsements For All Posts
    public int getTotalEndorsments() {
        int total = 0;
        for (int i=0; i<posts.size(); i++) {
            ArrayList<Endorsement> endorsements = posts.get(i).getEndorsements();
            total += endorsements.size();
        }
        return total;
    }

    // Method: Get Account Info In Formatted StringBuilder
    public StringBuilder getInfo() {
        // Get total endorsements recieved by account
        int endorsementCount = getTotalEndorsments();

        // Create StringBuilder using account information
        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append("\nHandle: ").append(handle);
        info.append("\nDescription: ").append(description);
        info.append("\nPost count: ").append(posts.size());
        info.append("\nEndorse count: ").append(endorsementCount);
        return info;
    }

    // 2 Overloaded Constructors
    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.identifier = ++count;
    }

    public Account(String handle) {
        this.handle = handle;
        this.identifier = ++count;
    }
}
