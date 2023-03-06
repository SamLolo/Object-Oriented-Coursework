package socialmedia;

import java.util.ArrayList;

public class Post {
    // Private Attribute
    private static int count = 0;
    private int identifier;
    private String message;
    private String authorHandle;
    private ArrayList<Comment> comments = new ArrayList<>();

    private ArrayList<Endorsement> endorsements = new ArrayList<>();


    // Overloaded Constructors (To be continued)
    public Post(String handle, String message) {
        this.identifier = ++count;
        this.message = message;
        this.authorHandle = handle;

    }

    public int getIdentifier() {
        return identifier;
    }

    public String getAuthorHandle() { 
        return authorHandle; 
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Sets original post id to null as the original post has been deleted
    public void createOrphans() {
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).setOriginalPostIDtoNull();
            endorsements.get(i).setOriginalPostIDtoNull();
        }
    }
    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }
}

