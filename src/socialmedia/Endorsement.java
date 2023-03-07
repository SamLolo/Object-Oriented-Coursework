package socialmedia;

public class Endorsement extends Post {
    
    private int originalPostID;

    public Endorsement(Account account, int id, String message) {
        super(account, message);
        this.originalPostID = id;
    }

    @Override
    public String toString() {
        return "Endorsement[id="+identifier+", Message="+message+", Author="+author.getHandle()+", Original_Post_ID="+originalPostID+"]";
    }
}
