import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {

    Map<Status, Set<WorkOrder>> workOrders = new HashMap<>();


    public Processor(Map<Status, Set<WorkOrder>> workOrders) {
        for(Status status : Status.getAllStatus());
        workOrders.put(status , new HashSet<>());
        this.workOrders = workOrders;
    }

    public void processWorkOrders() {
        readIt();
        moveIt();

        }

        private void moveIt() {
            // move work orders in map from one state to another
            Set <WorkOrder> inProgressOrders = workOrders.get(Status.IN_PROGRESS);
            if (inProgressOrders){
                WorkOrder firstInProgress = inProgressOrders.iterator().next();
                inProgressOrders.remove(firstInProgress);
                firstInProgress.setStatus(Status.DONE);
                workOrders.get(Status.DONE).add(firstInProgress);
            }
        }

        private void readIt() {
            // read the json files into WorkOrders and put in map
//            File []
        }

        public static void main(String args[]) {
            Processor processor = new Processor();
            processor.processWorkOrders();


            System.out.println("");

        }





        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


