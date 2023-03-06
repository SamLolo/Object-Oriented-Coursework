package socialmedia;

public class Endorsement extends Post{
    private int originalPostID;

    public Endorsement(Account account, int id, String message) {
        super(account, message);
        this.originalPostID = id;
    }

    public String toString() {
        return "Endorsement[id=" + getIdentifier() + ", Message=" + getMessage() + ", Author Handle=" + getAuthorHandle()+ ", Original Post ID=" + originalPostID + "]";
    }

    public void setOriginalIDtoNull() {
        originalPostID = -1;
    }
}
