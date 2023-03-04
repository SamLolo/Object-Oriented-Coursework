package socialmedia;

public class Comment extends Post{

    private int originalPostID;

    public Comment(String handle, int id, String message) {
        super(handle, message);
        this.originalPostID = id;
    }

    public void setOriginalPostIDtoNull() {
        originalPostID = Integer.parseInt(null);
    }
}
