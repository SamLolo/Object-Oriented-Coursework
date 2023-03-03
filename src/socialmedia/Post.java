package socialmedia;

public class Post {
    // Private Attributes
    private int identifier;
    // Sequential Number
    private String message;
    // Needs to be up to 100 Chars
    private int authorID;
    private final List commentsEndorsements;
    // ?? array of comments and endorsements

    // Method: Get Identifier
    public int getIdentifier() {
        return identifier;
    }

    // Method: Get Message
    public String getMessage() {
        return message;
    }

    // Method: Get Author ID
    public int getAuthorID() {
        return authorID;
    }

    // Method: Get List of Comments and Endorsements
    public List getCommentsEndorsements() {
        return commentsEndorsements;
    }

    // Method set ID
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    // Method set Message
    public void setMessage(String message) {
        this.message = message;
    }

    // Method set Author ID
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    // Method Add to list of comments and endorsements
    public void addCommentEndorsment(int ID) {
        this.commentsEndorsements.add(ID);
    }

    // Overloaded Constructors (To be continued)
    public Post(int identifier, String message, int authorID, List commentsEndorsements) {
        this.identifier = identifier;
        this.message = message;
        this.authorID = authorID;
        this.commentsEndorsements = commentsEndorsements;
    }
}

