
import org.codehaus.jackson.map.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Processor {

    Map<Status, Set<WorkOrder>> workOrders = new HashMap<>();
    public Processor() {
        for (Status status: Status.getAllStatus()) {
            workOrders.put(status, new HashSet<WorkOrder>());
        }
    }

    public static void main(String[] args) {
        Processor newProcessor = new Processor();
        newProcessor.processWorkOrders();
    }

    public void processWorkOrders() {
        while (true) {                     //runs true by default
            readIt();
            moveIt();
            try {
                Thread.sleep(5000l);    // loop waits for 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readIt() {               //Method read it
        // read the json files into WorkOrders and put in map

        File currentDir = new File(".");


        for (File f : currentDir.listFiles()) {
            if (f.getName().endsWith(".json")) {
                String workOrderJSON = getFileInformation(f.getName()).get(0);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder workOrderFile = mapper.readValue(workOrderJSON, WorkOrder.class);

                    Set<WorkOrder> appropriateSet = workOrders.get(workOrderFile.getStatus());
                    appropriateSet.add(workOrderFile);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static List<String> getFileInformation (String fileName) {
        File file = new File (fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            List<String> fileContents = new ArrayList<>();
            while (fileScanner.hasNext()) {
                fileContents.add(fileScanner.nextLine());
            }
            return fileContents;
        } catch (FileNotFoundException ex) {             //just a regular IO exception, catches a variety of exceptions;
            System.out.println("Could not find file *" + fileName + "*");
            ex.printStackTrace();
            return null;
        }
    }



    private void moveIt() {                    // This method moves the methods through the four statuses;


        Set<WorkOrder> inProgressWorkOrders = workOrders.get(Status.IN_PROGRESS);    //In progress
        System.out.println("Looking for Work Orders In Progress... ");
        if (inProgressWorkOrders.size() > 0) {
            WorkOrder firstInProgress = inProgressWorkOrders.iterator().next();
            inProgressWorkOrders.remove(firstInProgress);
            firstInProgress.setStatus(Status.DONE);
            workOrders.get(Status.DONE).add(firstInProgress);
            System.out.println("****** Moved " + firstInProgress + " to Done.******");
            updateWorkOrder(firstInProgress);

        }

        Set<WorkOrder> assignedWorkOrder = workOrders.get(Status.ASSIGNED);    //Assigned
        System.out.println("Looking for Assigned Work Orders... ");
        if (assignedWorkOrder.size() > 0) {
            WorkOrder firstAssigned = assignedWorkOrder.iterator().next();
            assignedWorkOrder.remove(firstAssigned);
            firstAssigned.setStatus(Status.IN_PROGRESS);
            workOrders.get(Status.IN_PROGRESS).add(firstAssigned);
            System.out.println("****** Moved " + firstAssigned + " to In Progress.******");
            updateWorkOrder(firstAssigned);
        }

        Set<WorkOrder> initialWorkOrder = workOrders.get(Status.INITIAL);   //Initial work order
        System.out.println("Looking for new Work Orders... ");
        if (initialWorkOrder.size() > 0) {
            WorkOrder firstInitial = initialWorkOrder.iterator().next();
            initialWorkOrder.remove(firstInitial);
            firstInitial.setStatus(Status.ASSIGNED);
            workOrders.get(Status.ASSIGNED).add(firstInitial);
            System.out.println("****** Moved " + firstInitial + " to Assigned.******");
            updateWorkOrder(firstInitial);
        }

    }

    private void updateWorkOrder(WorkOrder newWorkOrder) {      //updating a work order
        try{
            File fileForJson = new File(newWorkOrder.getId() + ".json");
            FileWriter fileWriter = new FileWriter(fileForJson);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(newWorkOrder);

            fileWriter.write(json);
            fileWriter.close();
        }
        catch (IOException ex) {     //just a regular IO exception, catches a variety of exceptions;
            ex.printStackTrace();
        }
    }


}