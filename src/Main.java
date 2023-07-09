import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main
{
    public static String answer;
    public static int burgerPrice = 650;
    public static int burgerStock = 50;
    public static int emptySlots = 10;
    public static int newBurgersCount;
    public static int soldBurgers = 0;
    public static int servedCustomerCount = 0;
    public static int queueNumber;
    public static int actualQueueNumber;
    public static int lastElement;
    public static Date saveTime = new Date();
    public static int reservedBurgers = 50 + newBurgersCount - soldBurgers - burgerStock;
    public static PrintWriter fileInput;

    // declaring an array to sort names
    public static String[] names = new String[10];

    // declaring a 2D array to store ASCII values of the names up to 2 characters
    public static int[][] asciiValues = new int[][]
            {
                    new int[10], new int[10]
            };
    public static Scanner input = new Scanner(System.in);

    // declaring a FoodQueue type array to store customers in the queue
    public static FoodQueue[] cashier = new FoodQueue[3];

    // declaring a WaitingQueue type array to store maximum 10 customers in the waiting area
    public static WaitingQueue waitingQueue = new WaitingQueue(10);
    public static int[] queueIncome = new int[3];

    public static void main(String[] args)
    {
        cashier[0] = new FoodQueue(2);
        cashier[1] = new FoodQueue(3);
        cashier[2] = new FoodQueue(5);

        menuController();
    }

    static void menuController()
    {
        // Printing the menu
        System.out.println("""
                
                ---- Foodies Fave Queue Management System ----
                
                100 or VFQ: View all Queues
                101 or VEQ: View all Empty Queues
                102 or ACQ: Add customer to a Queue
                103 or RCQ: Remove a customer from a Queue
                104 or PCQ: Remove a served customer
                105 or VCS: View Customers Sorted in alphabetical order
                106 or SPD: Store Program Data into file
                107 or LPD: Load Program Data from file
                108 or STK: View Remaining burgers Stock
                109 or AFS: Add burgers to Stock
                110 or IFQ: View the income of each queue
                999 or EXT: Exit the Program
                """);

        System.out.print("Select a menu option: ");
        String option = input.next();

        // selecting each option and building their functionalities
        switch (option.toUpperCase()) {
            case "100", "VFQ" -> {
                // Printing the cashier view with ALL queues
                cashierHeader();
                cashierFullQueue();
                loopController();
            }
            case "101", "VEQ" -> {
                // Printing the cashier view with EMPTY queues
                cashierHeader();
                cashierEmptyQueue();
                loopController();
            }
            case "102", "ACQ" ->
                // adding customers to queue and validating user inputs
                    addCustomersToQueue();
            case "103", "RCQ" ->
                // validating user inputs and removing a customer without serving
                    removeCustomer();
            case "104", "PCQ" ->
                // getting and validating queue location and then removing a served customer
                    removeServedCustomer();
            case "105", "VCS" -> {
                // sorting names in alphabetical order by using bubble sort
                sortNames();
                loopController();
            }
            case "106", "SPD" -> {
                // save data into the file
                saveFile();
                loopController();
            }
            case "107", "LPD" -> {
                // load data from the file
                loadFile();
                loopController();
            }
            case "108", "STK" -> {
                // displaying remaining burger stock and low stocks alert
                System.out.println("Remaining Burger Stock: " + burgerStock);
                stockAlert();
                loopController();
            }
            case "109", "AFS" ->
                // getting and validating newBurgersCount
                addBurgersToStock();

            case "110", "IFQ" -> {
                // displaying the income of each queue separately
                viewQueueIncome();
                loopController();
            }

            case "999", "EXT" -> {
                // exiting the program with a default message
                System.out.println("Thank you for using Foodies Fave QMS!");
                System.exit(0);
            }
            default -> {
                // validating user input for options
                System.out.println("Please enter a valid option from the below menu.");
                menuController();
            }
        }
    }

    static void loopController()
    {
        // Controlling the iterative process by asking user a question
        System.out.println("");
        System.out.print("Enter 'y' to continue or Enter 'n' to exit: ");
        answer = input.next();

        if (answer.equalsIgnoreCase("y"))
        {
            menuController();
        }
        else if (answer.equalsIgnoreCase("n"))
        {
            System.out.println("Thank you for using Foodies Fave QMS!");
            System.exit(0);
        }
        else
        {
            System.out.print("Invalid answer");
            loopController();
        }
    }

    public static void cashierHeader()
    {
        // Printing the cashier header
        for(int i = 0; i < 3; i++)
        {
            if(i == 1)
            {
                System.out.println("*    Cashiers    *");
                continue;
            }
            for(int j = 0; j < 18; j++)
            {
                System.out.print("*");
            }
            System.out.println("");
        }
    }

    public static void cashierFullQueue()
    {
        // Printing the full cashier queue
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < cashier.length; j++)
            {
                if(i >= cashier[j].getCustomerQueue().length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(cashier[j].getCustomerQueue()[i] == null)
                {
                    System.out.print("  X   ");
                }
                else
                {
                    System.out.print("  O   ");
                }
            }
            System.out.println("");
        }
        System.out.println("""
                X – Not Occupied
                O – Occupied""");
    }

    static void cashierEmptyQueue()
    {
        // printing the empty cashier queue
        int totalSlots = cashier[0].getCustomerQueue().length + cashier[1].getCustomerQueue().length + cashier[2].getCustomerQueue().length;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < cashier.length; j++)
            {
                if(i >= cashier[j].getCustomerQueue().length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(cashier[j].getCustomerQueue()[i] == null)
                {
                    System.out.print("  X   ");
                }
                else
                {
                    System.out.print("      ");
                }
            }
            System.out.println("");
        }
        // printing cashiers with empty slots
        for(int i = 0; i < cashier.length; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(j >= cashier[i].getCustomerQueue().length) continue;
                if(cashier[i].getCustomerQueue()[j] == null)
                {
                    System.out.println("Cashier " + (i+1) + " has empty slots");
                    break;
                }
            }
        }
        if(emptySlots == 1) System.out.println(emptySlots + " slot is Empty out of " + totalSlots + " slots");
        else System.out.println(emptySlots + " slots are Empty out of " + totalSlots + " slots");
    }

    public static void addCustomersToQueue()
    {
        boolean nameLoop = true;
        boolean queueLoop = true;
        while(nameLoop)
        {
            Customer customer = new Customer();

            // checking that at least one burger is available or not
            if (burgerStock > 0)
            {
                System.out.print("Enter First Name: ");
                customer.setFirstName(input.next());
                System.out.print("Enter Second Name: ");
                customer.setSecondName(input.next());

                // validating the customer name as only alphabetical
                if (customer.getFirstName().matches("^[a-zA-Z]*$") && customer.getSecondName().matches("^[a-zA-Z]*$"))
                {
                    nameLoop = false;
                    while(queueLoop)
                    {
                        // validating user input only as an integer
                        try
                        {
                            System.out.print("Enter Burger Amount: ");
                            customer.setBurgerAmount(input.nextInt());

                            // validating burger amount
                            if(customer.getBurgerCount() > 0)
                            {
                                // adding additional customers to waiting area if all queues are full
                                if(emptySlots != 0)
                                {
                                    if(customer.getBurgerCount() <= burgerStock)
                                    {
                                        // placing in the shortest queue
                                        for(int i = 0; i < 5; i++)
                                        {
                                            for(int j = 0; j < cashier.length; j++)
                                            {
                                                if(i >= cashier[j].getCustomerQueue().length) continue;
                                                if(cashier[j].getCustomerQueue()[i] == null)
                                                {
                                                    cashier[j].getCustomerQueue()[i] = customer;
                                                    // updating the income of each queue
                                                    queueIncome[j] += cashier[j].getCustomerQueue()[i].getBurgerCount() * burgerPrice;

                                                    i = 5;
                                                    break;
                                                }
                                            }
                                        }
                                        // reserving 5 burgers for the customer and updating empty slots
                                        burgerStock -= customer.getBurgerCount();
                                        reservedBurgers += customer.getBurgerCount();
                                        emptySlots--;
                                    }
                                    else
                                    {
                                        System.out.println("Unable to supply that amount of burgers");
                                    }
                                }
                                else
                                {
                                    WaitingQueue.insert(customer);
                                }

                                // showing the low stocks alert if the burger count is less than 10
                                stockAlert();
                                queueLoop = false;
                                Main.loopController();
                            }
                            else
                            {
                                System.out.println("Positive integer required");
                            }
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("Integer required");
                            input.next();
                        }
                    }
                }
                else
                {
                    System.out.println("Please enter alphabetical letters only");
                }
            }
            else
            {
                System.out.println("Burger stock is insufficient, Please Refill!");
                Main.loopController();
            }
        }
    }

    public static void addBurgersToStock()
    {
        boolean loop2 = true;
        while (loop2)
        {
            // validating and getting only integer inputs
            try
            {
                System.out.print("Enter the amount of burgers to be added: ");
                newBurgersCount = input.nextInt();

                // checking if it is a positive integer or not
                if(newBurgersCount > 0)
                {
                    burgerStock += newBurgersCount;

                    // checking if the new burger stock exceeds the maximum stock
                    if(burgerStock <= 50)
                    {
                        System.out.println("Stocks updated successfully. Total Burger Stock: " + burgerStock);
                        loop2 = false;
                        loopController();
                    }
                    else
                    {
                        burgerStock -= newBurgersCount;
                        if ((50 - burgerStock) == 0)
                        {
                            System.out.println("Maximum stock count exceeded");
                            loopController();
                        }

                        // showing the maximum amount of burgers that can be added
                        System.out.println("Maximum stock count exceeded, " + (50 - burgerStock) + " burgers can be added");
                    }
                }
                else
                {
                    System.out.println("Positive integer required");
                }
            }
            catch (Exception e)
            {
                System.out.println("Integer required");
                input.next();
            }
        }
    }

    public static void removeServedCustomer()
    {
        boolean loop = true;
        while(loop)
        {
            try
            {
                // validating integer input
                getQueue();

                // checking the queue number is one of a cashier
                if(queueNumber >= 1 && queueNumber <=3)
                {
                    actualQueueNumber = queueNumber - 1;
                    int lastElement = cashier[actualQueueNumber].getCustomerQueue().length - 1;

                    if(cashier[actualQueueNumber].getCustomerQueue()[0] != null)
                    {
                        // updating sold burger count, served customer count, empty slots, reserved burgers variables
                        soldBurgers += cashier[actualQueueNumber].getCustomerQueue()[0].getBurgerCount();
                        servedCustomerCount +=1;
                        emptySlots++;
                        reservedBurgers -= cashier[actualQueueNumber].getCustomerQueue()[0].getBurgerCount();

                        // removing first one and updating others
                        for(int k = 0; k < lastElement; k++)
                        {
                            cashier[actualQueueNumber].getCustomerQueue()[k] = cashier[actualQueueNumber].getCustomerQueue()[k + 1];
                        }

                        // adding customers from waiting area to queue if they exist
                        if(WaitingQueue.nItems == 0)
                        {
                            cashier[actualQueueNumber].getCustomerQueue()[lastElement] = null;   /* updating the last one in the queue as empty */
                        }
                        else
                        {
                            cashier[actualQueueNumber].getCustomerQueue()[lastElement] = WaitingQueue.remove();
                            // updating income
                            queueIncome[actualQueueNumber] += cashier[actualQueueNumber].getCustomerQueue()[lastElement].getBurgerCount() * burgerPrice;
                            emptySlots--;
                        }

                        System.out.println("Served customer was removed successfully from queue " + queueNumber);

                        loop = false;
                        loopController();
                    }
                    else
                    {
                        System.out.println("The selected queue is already empty");
                        loopController();
                    }
                }
                else
                {
                    System.out.println("Invalid Queue number");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Integer required");
                input.next();
            }
        }
    }

    static void removeCustomer()
    {
        boolean loop3 = true;
        boolean loop4 = true;
        while(loop3)
        {
            // checking if the queue number is an integer
            try
            {
                getQueue();
                actualQueueNumber = queueNumber - 1;

                // checking the queue number is one of a cashier
                if(queueNumber >= 1 && queueNumber <=3)
                {
                    loop3 = false;
                    while(loop4)
                    {
                        // checking if the position is an integer
                        try
                        {
                            System.out.print("Enter Position in the queue: ");
                            int position = input.nextInt();

                            // checking the validity of the position
                            if(position >= 1 && position <= cashier[actualQueueNumber].getCustomerQueue().length)
                            {
                                int actualPosition = position - 1;
                                lastElement = cashier[actualQueueNumber].getCustomerQueue().length - 1;
                                Customer removedCustomer = cashier[actualQueueNumber].getCustomerQueue()[actualPosition];

                                // updating other positions
                                if(removedCustomer != null)
                                {
                                    // increasing the burger count as the customer had not been served
                                    burgerStock += removedCustomer.getBurgerCount();
                                    reservedBurgers -= removedCustomer.getBurgerCount();
                                    emptySlots++;

                                    // decreasing the income of the removed customer
                                    queueIncome[actualQueueNumber] -= removedCustomer.getBurgerCount() * burgerPrice;

                                    for(int k = actualPosition; k < lastElement; k++)
                                    {
                                        cashier[actualQueueNumber].getCustomerQueue()[k] = cashier[actualQueueNumber].getCustomerQueue()[k + 1];
                                    }

                                    // adding customers from waiting area to queue if they exist
                                    if(WaitingQueue.nItems == 0)
                                    {
                                        cashier[actualQueueNumber].getCustomerQueue()[lastElement] = null;   /* updating the last one in the queue as empty */
                                    }
                                    else
                                    {
                                        cashier[actualQueueNumber].getCustomerQueue()[lastElement] = WaitingQueue.remove();
                                        // updating the income of the added customer
                                        queueIncome[actualQueueNumber] += cashier[actualQueueNumber].getCustomerQueue()[lastElement].getBurgerCount() * burgerPrice;
                                        emptySlots--;
                                    }

                                    System.out.println("Customer was removed successfully from queue " + queueNumber);
                                    loop4 = false;
                                }
                                else
                                {
                                    System.out.println("The selected position is already empty");
                                }
                                loopController();
                            }
                            else
                            {
                                System.out.println("Invalid position");
                            }
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("Integer required");
                            input.next();
                        }
                    }
                }
                else
                {
                    System.out.println("Invalid Queue number");
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Integer required");
                input.next();
            }
        }
    }
    public static void viewQueueIncome()
    {
        System.out.println("---- Estimated income from the customers in each queue ----\n");
        for(int i = 0; i < queueIncome.length; i++)
        {
            System.out.println(String.format("Queue %s : Rs. %s",(i+1) ,queueIncome[i]));
        }
    }
    public static void sortNames()
    {
        // adding customer names to names array
        int l = 0;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < cashier.length; j++)
            {
                // adding ascii values of first 2 characters to asciiValues array
                if(i >= cashier[j].getCustomerQueue().length) continue;
                if(cashier[j].getCustomerQueue()[i] != null)
                {
                    names[l] = cashier[j].getCustomerQueue()[i].getFullName().toLowerCase();
                    asciiValues[0][l] = names[l].charAt(0);
                    if(names[l].length() > 1)
                    {
                        asciiValues[1][l] = names[l].charAt(1);
                    }
                    l++;
                }
            }
        }

        // bubble sorting up to two characters of each name in ascending order
        for(int m = 0; m < l; m++)
        {
            if(names[m] == null || asciiValues[0][m] == 0) continue;
            for(int n = 0; n < l; n++)
            {
                if(asciiValues[0][m] < asciiValues[0][n] || (asciiValues[0][m] == asciiValues[0][n] && asciiValues[1][m] < asciiValues[1][n]))
                {
                    // Swapping names
                    String temp = names[n];
                    names[n] = names[m];
                    names[m] = temp;

                    // Swapping first character values
                    int temp1 = asciiValues[0][n];
                    asciiValues[0][n] = asciiValues[0][m];
                    asciiValues[0][m] = temp1;

                    // Swapping second character values
                    int temp2 = asciiValues[1][n];
                    asciiValues[1][n] = asciiValues[1][m];
                    asciiValues[1][m] = temp2;
                }
            }
        }

        // printing sorted names
        int rank = 1;
        for(String name: names)
        {
            if(name != null)
            {
                String[] nameParts = nameCapitalization(name.split(" "));
                System.out.println(rank + ". " + nameParts[0] + " " + nameParts[1]);
                rank++;
            }
        }
    }

    public static void saveFile()
    {
        try {
            // creating and writing into the file
            fileInput = new PrintWriter("FFQMS-Data.txt");

            // writing first half
            String firstHalf = String.format("""
                            -------------------------------------->  Foodies Fave Queue Management System Data  <-------------------------------------- \n \n
                            Last Saved                     ; %s \n
                            Remaining Burger Count         : %s
                            Reserved Burger Count          : %s
                            Sold Burger Count              : %s
                            Served Customers Count         : %s \n
                            ----------------------------------------------------- Cashier Queues ------------------------------------------------------ \n
                            """, saveTime, burgerStock, reservedBurgers, soldBurgers, servedCustomerCount);

            fileInput.write(firstHalf);

            // writing cashier data
            writeCashierData(true);

            // writing second half
            String secondHalf = String.format("""
                            \nNo.of Empty Slots              : %s \n
                            ------------------------------------------------------ Waiting Queue ------------------------------------------------------ \n
                            No.of Waiting Queue Customers  : %s \n
                            """, emptySlots,WaitingQueue.nItems);
            fileInput.write(secondHalf);

            // writing customers in waiting queue
            WaitingQueue.getWaitingQueueCustomers(true);
            fileInput.print("                                                        * * * * * *");

            // closing file connection
            fileInput.close();

            System.out.println("Saved successfully to the file");
            loopController();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    public static void loadFile()
    {
        try {
            // opening and reading from the file
            File file = new File("FFQMS-Data.txt");
            Scanner fileReader = new Scanner(file);

            // initializing variables
            String line;
            String saveTime = "";
            int cashierIndex = 0;

            // reading and adding values to all variables
            while (fileReader.hasNextLine()) {
                line = fileReader.nextLine();

                if (line.startsWith("Last Saved")) {
                    saveTime = line.split(";")[1].trim();
                } else if (line.startsWith("Remaining Burger Count")) {
                    burgerStock = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Reserved Burger Count")) {
                    reservedBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Sold Burger Count")) {
                    soldBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Served Customers Count")) {
                    servedCustomerCount = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No.of Empty Slots")) {
                    emptySlots = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No.of Waiting Queue Customers")) {
                    WaitingQueue.nItems = Integer.parseInt(line.split(":")[1].trim());
                }
                // reading and adding values to cashier array
                else if (line.startsWith("Cashier")) {
                    String[] queueTokens = line.split(":")[1].trim().split(", ");
                    queueIncome[cashierIndex] = Integer.parseInt(line.split(">")[1].trim().split("\\s+")[1].trim());

                    for (int j = 0; j < queueTokens.length; j++)
                    {
                        if(queueTokens[j] == "") break;
                        String[] customerAttributes = queueTokens[j].split("\\s+");
                        cashier[cashierIndex].getCustomerQueue()[j] = setLoadedCustomer(customerAttributes);
                    }
                    cashierIndex++;
                }

                else if(line.startsWith("Customers in Waiting Queue")){
                    String[] waitingQueueTokens = line.split(":")[1].trim().split(", ");
                    for(int i = 0; i < waitingQueueTokens.length; i++)
                    {
                        if(waitingQueueTokens[i] == "") break;
                        String[] waitingCustomerAttributes = waitingQueueTokens[i].split("\\s+");
                        WaitingQueue.insert(setLoadedCustomer(waitingCustomerAttributes));
                        WaitingQueue.nItems--;
                    }
                    break;
                }
            }
            fileReader.close();

            // printing saved data into the console
            String fileOutput = String.format("""
                    ----------------------------------->  Foodies Fave Queue Management Loaded System Data  <---------------------------------- \n
                    Last Saved                     : %s \n
                    Remaining Burger Count         : %s
                    Reserved Burger Count          : %s
                    Sold Burger Count              : %s
                    Served Customers Count         : %s \n
                    ----------------------------------------------------- Cashier Queues ------------------------------------------------------ \n
                    """, saveTime, burgerStock, reservedBurgers, soldBurgers, servedCustomerCount);
            System.out.println(fileOutput);

            writeCashierData(false);

            System.out.println(String.format("""
                    \nNo.of Empty Slots              : %s \n
                    ------------------------------------------------------ Waiting Queue ------------------------------------------------------ \n
                    No.of Waiting Queue Customers  : %s 
                    """,emptySlots, WaitingQueue.nItems));

            WaitingQueue.getWaitingQueueCustomers(false);


        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
    }

    public static void getQueue()
    {
        System.out.print("Enter queue: ");
        queueNumber = input.nextInt();
    }

    public static void stockAlert()
    {
        // printing a low stocks alert
        if(burgerStock <= 10)
        {
            System.out.println("""

                                              *** Alert ***
                            You have less than 10 burgers left, please refill !""");
        }
    }
    public static String[] nameCapitalization(String[] nameParts)
    {
        String fName = nameParts[0].substring(0,1).toUpperCase() + nameParts[0].substring(1);
        String sName = nameParts[1].substring(0,1).toUpperCase() + nameParts[1].substring(1);
        String[] capitalizedNames = {fName, sName};
        return capitalizedNames;
    }
    public static void writeCashierData(boolean printToFile) {
        for (int i = 0; i < 3; i++) {
            if (printToFile) {
                Main.fileInput.write(String.format("Cashier %s   >   Rs. %05d      : ", (i + 1), queueIncome[i]));
            } else {
                System.out.printf("Cashier %s   >   Rs. %05d      : ", (i + 1), queueIncome[i]);
            }

            for (int j = 0; j < 5; j++) {
                if (j >= cashier[i].getCustomerQueue().length) continue;
                if (cashier[i].getCustomerQueue()[j] != null) {
                    String[] nameParts = nameCapitalization(cashier[i].getCustomerQueue()[j].getFullName().split(" "));
                    String customerInfo = nameParts[0] + " " + nameParts[1] + " - " + cashier[i].getCustomerQueue()[j].getBurgerCount();

                    if (printToFile) {
                        Main.fileInput.print(customerInfo);
                    } else {
                        System.out.print(customerInfo);
                    }

                    if (j + 1 != cashier[i].getCustomerQueue().length) {
                        if (cashier[i].getCustomerQueue()[j + 1] != null) {
                            if (printToFile) {
                                Main.fileInput.print(", ");
                            } else {
                                System.out.print(", ");
                            }
                        }
                    }
                }
            }

            if (printToFile) {
                Main.fileInput.println(" ");
            } else {
                System.out.println(" ");
            }
        }
    }

    public static Customer setLoadedCustomer(String[] customerAttributes)
    {
        Customer customer = new Customer();
        customer.setFirstName(customerAttributes[0].toLowerCase());
        customer.setSecondName(customerAttributes[1].toLowerCase());
        customer.setBurgerAmount(Integer.parseInt(customerAttributes[3]));

        return customer;
    }
}
