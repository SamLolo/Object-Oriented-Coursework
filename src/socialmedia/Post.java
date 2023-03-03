package socialmedia;

import java.util.ArrayList;

public class Post {
    // Private Attribute
    private static int count = 0;
    private int identifier;
    private String message;
    private String authorHandle;
    private ArrayList<Integer> commentsEndorsements = new ArrayList<>();
    // ?? array of comments and endorsements

    // Overloaded Constructors (To be continued)
    public Post(String handle, String message) {
        this.identifier = (++count);
        this.message = message;
        this.authorHandle = handle;

    }

    public int getIdentifier() {
        return identifier;
    }
}

