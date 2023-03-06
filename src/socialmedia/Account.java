package socialmedia;

import java.util.ArrayList;

public class Account {
    // Private Attributes
    private static int count = 0;
    private int identifier;
    private String handle;
    private String description = "";
    private ArrayList<Post> posts = new ArrayList<Post>();

    // Method: To String
    public String toString() {
        return "Account[id="+identifier+", handle="+handle+", description="+description+"]";
    }

    // Method: Get Identifier
    public int getIdentifier() {
        return identifier;
    }

    // Method: Get Handle
    public String getHandle() {
        return handle;
    }

    // Method: Get Description
    public String getDescription() {
        return description;
    }

    // Method: Set Handle
    public void setHandle(String h) {
        handle = h;
    }

    // Method: Set Description
    public void setDescription(String d) {
        description = d;
    }

    // Method: Get Posts
    public ArrayList<Post> getPosts() {
        return posts;
    }

    // Method: Create Post
    public Post createPost(String message, Account account) {
        Post newPost = new Post(account, message);
        posts.add(newPost);
        return newPost;
    }

    public Comment createComment(int id, String message, Account account) {
        Comment newComment = new Comment(id, message, account);
        posts.get(id).addComment(newComment);
        return newComment;
    }

    public Endorsement createEndorsement(int id, Account account) {
        Endorsement newEndorsement = new Endorsement(account, id, posts.get(id).getMessage());
        posts.get(id).addEndorsement(newEndorsement);
        return newEndorsement;
    }

    // 2 Overloaded Constructors
    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.identifier = ++count;
    }

    public Account(String handle) {
        this.handle = handle;
        this.identifier = ++count;
    }
}
