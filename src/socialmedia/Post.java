package socialmedia;

import java.util.ArrayList;

public class Post {
    // Private Attribute
    private static int count = 0;
    private int identifier;
    private String message;
    private Account authorObject;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();

    public String toString() {
        return "Post[id="+identifier+", Message="+message+", Author Handle="+authorObject.getHandle()+"]";
    }

    // Overloaded Constructors (To be continued)
    public Post(Account account, String message) {
        this.identifier = ++count;
        this.message = message;
        this.authorObject = account;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getAuthorHandle() {
        return authorObject.getHandle();
    }

    public int getNOEndorsements() {
        return endorsements.size();
    }

    public int getNOComments() {
        return comments.size();
    }

    public String getMessage() {
        return message;
    }

    // Sets original post id to null as the original post has been deleted
    public void deletePost(int newId) {
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).setOriginalID(newId);
        }
        for (int i = 0; i < endorsements.size(); i++) {
            endorsements.get(i).setOriginalIDtoNull();
        }
    }

    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public StringBuilder getPostInfo() {
        StringBuilder postInfo = new StringBuilder();
        postInfo.append("Id: ").append(identifier);
        postInfo.append("\nAccount: ").append(authorObject.getHandle());
        postInfo.append("\nNO. endorsements: ").append(endorsements.size());
        postInfo.append("  |  NO. comments: ").append(comments.size());
        postInfo.append("\n").append(message);
        return postInfo;
    }
}
