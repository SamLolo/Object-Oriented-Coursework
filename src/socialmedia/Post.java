package socialmedia;

import java.util.ArrayList;

/**
 * Post class representing a post within the system. Each post is associated with
 * an account that owns the post. Posts have incremental, unique identifiers, starting
 * from 1, and keep track of their comments and endorsements.
 * 
 * @version 1.0
 *
 */
public class Post {
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

    public int getIdentifier() {
        return identifier;
    }

    public Account getAuthor() {
        return author;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getMessage() {
        return message;
    }

    public void delete() {
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).removeOriginalPost();
        }
        endorsements.clear();
        author.deletePost(identifier);
        author = null;
        message = "The original content was removed from the system and is no longer available.";
    }

    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public StringBuilder getInfo(int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "    ";
        }

        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append(indentation).append("\nAccount: ").append(author.getHandle());
        info.append(indentation).append("\nNo. endorsements: ").append(endorsements.size());
        info.append(" | No. comments: ").append(comments.size());
        info.append(indentation).append("\n").append(message);
        return info;
    }

    public StringBuilder getChildInfo(StringBuilder postInfo, int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "    ";
        }

        if (comments.size() > 0) {
            for (int j=0; j < comments.size(); j++) {
                ++indent;
                postInfo.append(indentation+"\n|"+indentation+"\n| > " + comments.get(j).getInfo(indent));
                comments.get(j).getChildInfo(postInfo, indent);
            }
        }
        return postInfo;
    }
}
