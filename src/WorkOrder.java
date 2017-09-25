public class WorkOrder {

    private int id;
    private String description;
    private String senderName;
    private Status status;
    private static int nextId = 0;

    public WorkOrder{
        this.id = getAndIncrementNextId();
    }

    public WorkOrder(int id, String description, String senderName, Status status) {
        this.id = getAndIncrementNextId();   //This will get your next ID:
        this.description = description;
        this.senderName = senderName;
        this.status = status;
    }

    @Override
    public int hashCode() {
        return id();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static int getAndIncrementNextId() {
        return nextId ++ ;
    }

//    public static void setNextId(int nextId) {
//        WorkOrder.nextId = nextId;
//    }
}
