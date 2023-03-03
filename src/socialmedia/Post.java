package socialmedia;

public class Post {
    // Private Attributes
    private int identifier;
    // Sequential Number
    private String message;
    // Needs to be up to 100 Chars
    private int authorID;
    private List commentsEndorsments;
    // ?? array of comments and endorsements

    // Method: Get Identifier
    public int getIdentifier() {
        return identifier;
    }

    // Method: Get Message
    public string getMessage() {
        return message
    }

    // Method: Get Author ID
    public int getAuthorID() {
        return authorID
    }

    // Method: Get List of Comments and Endorsments
    public List getCommentsEndorsments() {
        return commentsEndorsments;
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
        this.commentsEndorsments.add(ID);
    }

    // Overloaded Constructors (To be continued)
    public void Post(int identifier, String message, int authorID, List commentsEndorsments) {
        this.identifier = identifier;
        this.message = message;
        this.authorID = authorID;
        this.commentsEndorsments = commentsEndorsments;
    }
}

