package socialmedia;

public class Comment extends Post {

    private int originalPostID;

    public Comment(int id, String message, Account account) {
        super(account, message);
        this.originalPostID = id;
    }

    @Override
    public String toString() {
        return "Comment[id="+identifier+", Message="+message+", Author="+author.getHandle()+", Original_Post_ID="+originalPostID+"]";
    }

    public void removeOriginalPost() {
        originalPostID = -1;
    }
}
