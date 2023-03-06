package socialmedia;

public class Comment extends Post{

    private int originalPostID;

    public String toString() {
        return "Comment[id="+getIdentifier()+", Message="+getMessage()+", Author Handle="+getAuthorHandle()+", Original Post ID="+originalPostID+"]";
    }

    public Comment(int id, String message, Account account) {
        super(account, message);
        this.originalPostID = id;
    }

    public void setOriginalID(int newId) {
        originalPostID = newId;
    }
}
