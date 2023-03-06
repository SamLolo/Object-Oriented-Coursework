package socialmedia;

import java.util.ArrayList;

public class Post {
    // Private Attribute
    private static int count = 0;
    private int identifier;
    private String message;
    private Account author;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();

    public String toString() {
        return "Post[id="+identifier+", Message="+message+", Author="+author.getHandle()+"]";
    }

    // Overloaded Constructors (To be continued)
    public Post(Account account, String message) {
        this.identifier = ++count;
        this.message = message;
        this.author = account;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getAuthorHandle() {
        return author.getHandle();
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

    public StringBuilder getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append("\nAccount: ").append(author.getHandle());
        info.append("\nNo. endorsements: ").append(endorsements.size());
        info.append(" | No. comments: ").append(comments.size());
        info.append("\n").append(message);
        return info;
    }
}
