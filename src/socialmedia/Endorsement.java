package socialmedia;

public class Endorsement extends Post{
    private int originalPostID;

    public Endorsement(String handle, int id, String message) {
        super(handle, message);
        this.originalPostID = id;
    }

    public void setOriginalPostIDtoNull() {
        originalPostID = Integer.parseInt(null);
    }
}
