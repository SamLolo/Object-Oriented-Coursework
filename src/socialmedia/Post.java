package socialmedia;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Post class representing a post within the system. Each post is associated with
 * an account that owns the post. Posts have incremental, unique identifiers, starting
 * from 1, and keep track of their comments and endorsements.
 * 
 * @version 1.0
 *
 */
public class Post implements Serializable {
    // Static count to keep track of highest identifier
    private static int count = 0;
    // 3 protected attributes to be accessed by comment/endorsement classes
    protected int identifier;
    protected String message;
    protected Account author;
    // 2 private attributes that aren't accessible from comment/endorsement classes
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();

    /**
	 * Constructs a new post on the system, associated with the given account.
     * <p>
     * Assumes the message is a valid string
     * 
     * @param message the message to be used on the comment
     * @param account the author of the comment
	 */
    public Post(Account account, String message) {
        this.identifier = ++count;
        this.message = message;
        this.author = account;
    }

    /**
	 * The method creates a string showing clearly the state of the attributes of the post class.
	 * 
	 * @return a String detailing posts attributes.
	 */
    @Override
    public String toString() {
        return "Post[id="+identifier+", Message="+message+", Author="+author.getHandle()+"]";
    }

    /**
	 * The method sets the post class's counter to the given n value, meaning future
     * identifiers will be set as n+1, and so on
     * <p>
     * Using n=0 is the same as reseting the counter as if no posts exist yet
     * Therfore you must be careful not to duplicate identifiers when using this method!
	 * 
	 * @param n the number to start setting new identifers from
	 */
    public static void setCount(int n) {
        count = n;
    }

    /**
	 * The method gets the value of the private int identifier for the class.
	 * 
	 * @return a int identifier for the post
	 */
    public int getIdentifier() {
        return identifier;
    }

    /**
	 * The method gets the author of the post.
	 * 
	 * @return an account object representing the author
	 */
    public Account getAuthor() {
        return author;
    }

    /**
	 * The method gets the array of all endorsements the post has.
	 * 
	 * @return an arrayList of Endorsements
	 */
    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    /**
	 * The method gets the message content of the post.
	 * 
	 * @return the posts message string
	 */
    public String getMessage() {
        return message;
    }

    /**
	 * The method deletes the post from the system, replacing it with a placeholder indicating that
     * the content has been deleted.
	 */
    public void delete() {
        // Make all comments orphans (remove the link to this post)
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).removeOriginalPost();
        }
        
        // Reset posts attributes and get author to remove post from it's array records
        endorsements.clear();
        author.deletePost(identifier);
        author = null;
        message = "The original content was removed from the system and is no longer available.";
    }

    /**
	 * The method adds a new endorsement to the endorsements arraylist
	 * 
	 * @param endorsement the endorsement object to add
	 */
    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }

    /**
	 * The method adds a new comment to the comments arraylist
	 * 
	 * @param comment the comment object to add
	 */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
	 * The method returns the post information in a format ready to be displayed to the user.
     * 
     * @param indent the amount of indentation required infront of the information
     * 
     * @return the StringBuilder object containing the formatted information
	 */
    public StringBuilder getInfo(int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "        ";
        }

        // Format post information into format provided by specification
        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append("\n"+indentation).append("Account: ").append(author.getHandle());
        info.append("\n"+indentation).append("No. endorsements: ").append(endorsements.size());
        info.append(" | No. comments: ").append(comments.size());
        info.append("\n"+indentation).append(message);
        return info;
    }

    /**
	 * The method returns the post information for all child posts (comments) for the current post.
     * <p>
     * Designed recursively, so will keep calling itself over and over again until all comments have
     * been added to the stringBuilder
     * 
     * @param postInfo the current stringBuilder that's been constructed so far
     * @param indent the amount of indentation required infront of the information
	 */
    public void getChildInfo(StringBuilder postInfo, int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "        ";
        }

        // Add the post information for the comment, with the indentation added before hand if not the first post
        if (indent != 0) {
            postInfo.append("\n").append(indentation).append("|").append("\n").append(indentation).append("| > ").append(getInfo(indent));
        } else {
            postInfo.append(getInfo(indent));
        }
        ++indent;

        // For each comment, recursively call getChildInfo for that comment with an indent one higher than the current post
        if (comments.size() > 0) {
            for (int j=0; j < comments.size(); j++) {
                comments.get(j).getChildInfo(postInfo, indent);
            }
        }
        --indent;
    }
}
