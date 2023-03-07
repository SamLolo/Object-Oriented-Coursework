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

    public void deletePost(int id) {
        for (int i=0; i < posts.size(); i++) {
            if (posts.get(i).getIdentifier() == id) {
                posts.remove(i);
                break;
            }
        }
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

    // Method: Create Comment
    public Comment createComment(Post post, String message, Account account) {
        Comment newComment = new Comment(post.getIdentifier(), message, account);
        post.addComment(newComment);
        return newComment;
    }

    // Method: Create Endorsement
    public Endorsement createEndorsement(Post post, Account account) {
        Endorsement newEndorsement = new Endorsement(account, post.getIdentifier(), post.getMessage());
        post.addEndorsement(newEndorsement);
        return newEndorsement;
    }

    // Method: Sum Endorsements For All Posts
    public int getTotalEndorsments() {
        int total = 0;
        for (int i=0; i<posts.size(); i++) {
            ArrayList<Endorsement> endorsements = posts.get(i).getEndorsements();
            total += endorsements.size();
        }
        return total;
    }

    // Method: Get Account Info In Formatted StringBuilder
    public StringBuilder getInfo() {
        // Get total endorsements recieved by account
        int endorsementCount = getTotalEndorsments();

        // Create StringBuilder using account information
        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append("\nHandle: ").append(handle);
        info.append("\nDescription: ").append(description);
        info.append("\nPost count: ").append(posts.size());
        info.append("\nEndorse count: ").append(endorsementCount);
        return info;
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
