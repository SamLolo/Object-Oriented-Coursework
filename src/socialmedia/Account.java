package socialmedia;

public class Account {
    // Private Attributes
    private int identifier;
    private String handle;
    private String description;

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

    // 2 Overloadeded Constructors
    public int Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        return identifier;
    }

    public int Account(String handle) {
        this.handle = handle;
        return identifier;
    }
}
