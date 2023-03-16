package socialmedia;

import java.util.ArrayList;

public class Post {
    // Private Attribute
    private static int count = 0;
    protected int identifier;
    protected String message;
    protected Account author;
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Endorsement> endorsements = new ArrayList<>();

    @Override
    public String toString() {
        return "Post[id="+identifier+", Message="+message+", Author="+author.getHandle()+"]";
    }

    public static void setCount(int n) {
        count = n;
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

    public Account getAuthor() {
        return author;
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

    public void delete() {
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).removeOriginalPost();
        }
        endorsements.clear();
        author.deletePost(identifier);
        author = null;
        message = "The original content was removed from the system and is no longer available.";
    }

    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public StringBuilder getInfo(int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "    ";
        }

        StringBuilder info = new StringBuilder();
        info.append("ID: ").append(identifier);
        info.append(indentation).append("\nAccount: ").append(author.getHandle());
        info.append(indentation).append("\nNo. endorsements: ").append(endorsements.size());
        info.append(" | No. comments: ").append(comments.size());
        info.append(indentation).append("\n").append(message);
        return info;
    }

    public StringBuilder getChildInfo(StringBuilder postInfo, int indent) {
        // Create Indentation String
        String indentation = "";
        for (int i=0; i<indent; i++) {
            indentation += "    ";
        }

        if (comments.size() > 0) {
            for (int j=0; j < comments.size(); j++) {
                ++indent;
                postInfo.append(indentation+"\n|"+indentation+"\n| > " + comments.get(j).getInfo(indent));
                comments.get(j).getChildInfo(postInfo, indent);
            }
        }
        return postInfo;
    }
}
