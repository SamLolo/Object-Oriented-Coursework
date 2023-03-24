package socialmedia;

import java.util.ArrayList;


/**
 * Account class representing an account on the SocialMediaPlatform. 
 * Has two constructors, one for supplying both a description and a handle
 * and the other for just a handle. The identifier is linearly generated as to be
 * unique.
 * Also provides functionality for managing account's posts within the platform.
 * 
 * @version 1.0
 *
 */
public class Account {
    // Static count to keep track of highest identifier
    private static int count = 0;
    // 4 private attributes
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
	 * The method sets the account class's counter to the given n value, meaning future
     * identifiers will be set as n+1, and so on
     * <p>
     * Using n=0 is the same as reseting the counter as if no accounts exist
     * Therefore you must be careful not to duplicate identifiers when using this method!
	 * 
	 * @param n the number to start setting new identifers from
	 */
    public static void setCount(int n) {
        count = n;
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

    /**
	 * The method removes the account's post that matches the given ID
	 * 
	 * @param id the int identifier for the post that needs to be removed
	 */
    public void deletePost(int id) {
        // Search through posts until a post with matching id is found, then delete the post
        for (int i=0; i < posts.size(); i++) {
            if (posts.get(i).getIdentifier() == id) {
                posts.remove(i);
                break;
            }
        }
    }

    /**
	 * The method creates a new post associated with this account, given a message
     * <p>
     * Assumes the given message is a valid String, that has already been checked
     * against the requirements for a post
	 * 
	 * @param message the String message to be used on the post
     * 
     * @return the Post object that has just been created
	 */
    public Post createPost(String message) {
        Post newPost = new Post(this, message);
        posts.add(newPost);
        return newPost;
    }

    /**
	 * The method creates a new comment associated with this account, given a message and original post to comment on
     * <p>
     * Assumes the given message is a valid String, that has already been checked
     * against the requirements for a post and that the given post is one which allows comments
	 * 
     * @param post a Post object representing the post that is being commented on
	 * @param message the String message to be used on the comment
     * 
     * @return the Comment object that has just been created
	 */
    public Comment createComment(Post post, String message) {
        Comment newComment = new Comment(post.getIdentifier(), message, this);
        post.addComment(newComment);
        return newComment;
    }

    /**
	 * The method creates a new endorsement associated with this account, given a post to endorse
     * <p>
     * Assumes that the given post is valid and one which allows endorsements
	 * 
     * @param post a Post object representing the post that is being endorsed
     * 
     * @return the Endorsement object that has just been created
	 */
    public Endorsement createEndorsement(Post post) {
        Endorsement newEndorsement = new Endorsement(this, post.getIdentifier(), post.getMessage());
        post.addEndorsement(newEndorsement);
        return newEndorsement;
    }

    /**
	 * The method counts the total number of endorsements accociated with the account
     * 
     * @return the int count of endorsements
	 */
    public int getTotalEndorsments() {
        // For each post in account, get number of endorsements, and add to total count
        int total = 0;
        for (int i=0; i<posts.size(); i++) {
            ArrayList<Endorsement> endorsements = posts.get(i).getEndorsements();
            total += endorsements.size();
        }
        return total;
    }

    /**
	 * The method returns the account information in a format ready to be displayed to the user
     * 
     * @return the StringBuilder object containing the formatted information
	 */
    public StringBuilder getInfo() {
        int endorsementCount = getTotalEndorsments();
        // Format account information into format provided by specification
        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append("\nHandle: ").append(handle);
        info.append("\nDescription: ").append(description);
        info.append("\nPost count: ").append(posts.size());
        info.append("\nEndorse count: ").append(endorsementCount);
        return info;
    }

    /**
	 * Constructs a new account instance with a pre-defined handle and account description
     * <p>
     * Assumes the given handle has already been verified as unique by SocialMedia
     * Assumes the description provided has already been validated
     * 
     * @param handle the handle to be used for the account
     * @param description the String to set as the account's description
	 */
    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.identifier = ++count;
    }

    /**
	 * Constructs a new account instance with a pre-defined handle only
     * <p>
     * Assumes the given handle has already been verified as unique by SocialMedia
     * Account created will have a blank description string
     * 
     * @param handle the handle to be used for the account
	 */
    public Account(String handle) {
        this.handle = handle;
        this.identifier = ++count;
    }
}
