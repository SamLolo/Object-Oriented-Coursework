package socialmedia;

/**
 * Endorsements class enherits from the Post class as they share similar properties. Unlike a post though,
 * the comments and endorsements arrayList will always be empty. Similar to a comment, an 
 * endorsement keeps track of the identifier of the original post associated with the endorsement.
 * 
 * @version 1.0
 *
 */
public class Endorsement extends Post {
    // Identifier of post being endorsed
    private int originalPostID;

    /**
	 * Constructs a new endorsement on a post/comment within the system.
     * <p>
     * Assumes the id points to an existing post/comment that allows an endorsement
     * Assumes the message is a valid string
     * 
     * @param account the author of the comment
     * @param id the int identifier of the post being commented on
     * @param message the message to be used on the comment
	 */
    public Endorsement(Account account, int id, String message) {
        super(account, message);
        this.originalPostID = id;
    }

    /**
	 * The method creates a string showing clearly the state of the attributes of the endorsement class.
	 * 
	 * @return a String detailing endorsement's attributes.
	 */
    @Override
    public String toString() {
        return "Endorsement[id="+identifier+", Message="+message+", Author="+author.getHandle()+", Original_Post_ID="+originalPostID+"]";
    }
}
