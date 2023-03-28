package socialmedia;

/**
 * Comment class inherts attributes from the Post class, as comments and posts share similar
 * properties. However, a comment also keeps track of the identifier of the original post
 * associated with the comment.
 * 
 * @author Sam Townley and Charles Symonds
 * @version 1.0
 */
public class Comment extends Post {
    /**
     * The identifier of the parent post that this comment belongs to
     */
    private int originalPostID;

    /**
	 * Constructs a new comment on a post within the system.
     * <p>
     * Assumes the id points to an existing post that allows comments
     * Assumes the message is a valid string
     * 
     * @param id the int identifier of the post being commented on
     * @param message the message to be used on the comment
     * @param account the author of the comment
	 */
    public Comment(int id, String message, Account account) {
        super(account, message);
        this.originalPostID = id;
    }

    /**
	 * The method creates a string showing clearly the state of the attributes of the comment class.
	 * 
	 * @return a String detailing comment's attributes.
	 */
    @Override
    public String toString() {
        return "Comment[id="+identifier+", Message="+message+", Author="+author.getHandle()+", Original_Post_ID="+originalPostID+"]";
    }

    /**
	 * The method makes the comment an orphan, by removing its link with the parent post.
     * <p>
     * Used when the post being commented on has been removed from the system.
	 */
    public void removeOriginalPost() {
        // -1 indicates that the comment is an orphan
        originalPostID = -1;
    }
}
