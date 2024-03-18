public class RemovedObject {
    
    private QuadNode changedNode;
    private String removedName;
    
    public RemovedObject(QuadNode changedNode, String removedName) {
        this.changedNode = changedNode;
        this.removedName = removedName;
    }
    
    public QuadNode getChangedNode() {
        return changedNode;
    }
    
    public String getRemovedName() {
        return removedName;
    }
}
