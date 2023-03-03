package socialmedia;

public class Account {
    // Private Attributes
    private static int Count = 0;
    private int Identifier;
    private String handle;
    private String description = "";

    // Method: To String
    public String toString() {
        return "Account[id="+Identifier+", handle="+handle+", description="+description+"]";
    }

    // Method: Get Identifier
    public int getIdentifier() {
        return Identifier;
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
    public Account(String handle, String description) {
        this.handle = handle;
        this.description = description;
        this.Identifier = ++Count;
    }

    public Account(String handle) {
        this.handle = handle;
        this.Identifier = ++Count;
    }
}
