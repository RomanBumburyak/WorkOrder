
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Creator {
    public void createWorkOrders() {            // read input, create work orders and write as json files


        while (true) { //always true
            try {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter Work Order Description: ");
                String descriptionEntry = scanner.nextLine();

                System.out.println("Enter Work Order Sender's Name: ");
                String senderNameEntry = scanner.nextLine();

                WorkOrder newWorkOrder = new WorkOrder(descriptionEntry, senderNameEntry, Status.INITIAL);

                System.out.println("File Name is: " + newWorkOrder.getId() + ".json");
                File fileForJson = new File(newWorkOrder.getId() + ".json");
                FileWriter fileWriter = new FileWriter(fileForJson);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(newWorkOrder);

                fileWriter.write(json);
                fileWriter.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
