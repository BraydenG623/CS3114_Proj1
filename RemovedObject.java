/**
 * Represents a point in a two-dimensional space with a name.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */
public class RemovedObject {

    private QuadNode changedNode;
    private String removedName;

    /**
     * 
     * @param changedNode
     * @param removedName
     */
    public RemovedObject(QuadNode changedNode, String removedName) {
        this.changedNode = changedNode;
        this.removedName = removedName;
    }


    /**
     * 
     * @return
     */
    public QuadNode getChangedNode() {
        return changedNode;
    }


    /**
     * 
     * @return
     */
    public String getRemovedName() {
        return removedName;
    }
}
