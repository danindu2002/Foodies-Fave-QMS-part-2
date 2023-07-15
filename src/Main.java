import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main
{
    public static String answer;
    public static int burgerStock = 50;
    public static int newBurgersCount;
    public static Date saveTime = new Date();
    public static PrintWriter fileInput;
    public static Scanner input = new Scanner(System.in);

    // declaring a FoodQueue type array to store customers in the queue
    public static FoodQueue[] cashier = new FoodQueue[3];

    // declaring a WaitingQueue type array to store maximum 10 customers in the waiting area
    public static WaitingQueue waitingQueue = new WaitingQueue();
    public static void main(String[] args)
    {
        // initializing 3 FoodQueue objects to store each queue
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
            // Printing the cashier view with ALL queues
            case "100", "VFQ" ->
            {
                FoodQueue.cashierHeader();
                FoodQueue.cashierFullQueue();
                loopController();
            }
            // Printing the cashier view with EMPTY queues
            case "101", "VEQ" ->
            {
                FoodQueue.cashierHeader();
                FoodQueue.cashierEmptyQueue();
                loopController();
            }
            // adding customers to queue and validating user inputs
            case "102", "ACQ" -> FoodQueue.addCustomersToQueue();

            // validating user inputs and removing a customer without serving
            case "103", "RCQ" -> FoodQueue.removeCustomer();

            // getting and validating queue location and then removing a served customer
            case "104", "PCQ" -> FoodQueue.removeServedCustomer();

            // sorting names in alphabetical order by using bubble sort
            case "105", "VCS" ->
            {
                FoodQueue.sortNames();
                loopController();
            }
            // save data into the file
            case "106", "SPD" ->
            {
                saveFile();
                loopController();
            }
            // load data from the file
            case "107", "LPD" ->
            {
                loadFile();
                loopController();
            }
            // displaying remaining burger stock and low stocks alert
            case "108", "STK" ->
            {
                System.out.println("Remaining Burger Stock: " + burgerStock);
                stockAlert();
                loopController();
            }
            // getting and validating newBurgersCount
            case "109", "AFS" -> addBurgersToStock();

            // displaying the income of each queue separately
            case "110", "IFQ" ->
            {
                FoodQueue.viewQueueIncome();
                loopController();
            }
            // exiting the program with a default message
            case "999", "EXT" ->
            {
                System.out.println("Thank you for using Foodies Fave QMS!");
                System.exit(0);
            }
            // validating user input for options
            default ->
            {
                System.out.println("Please enter a valid option from the below menu.");
                menuController();
            }
        }
    }
    static void loopController()
    {
        // Controlling the iterative process by asking user a question
        System.out.print("\nEnter 'y' to continue or Enter 'n' to exit: ");
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

    public static void saveFile()
    {
        try
        {
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
                            """, saveTime, burgerStock, FoodQueue.reservedBurgers, FoodQueue.soldBurgers, FoodQueue.servedCustomerCount);

            fileInput.write(firstHalf);

            // writing cashier data
            FoodQueue.writeCashierData(true);

            // writing second half
            String secondHalf = String.format("""
                            \nNo.of Empty Slots              : %s \n
                            ------------------------------------------------------ Waiting Queue ------------------------------------------------------ \n
                            No.of Waiting Queue Customers  : %s \n
                            """, FoodQueue.emptySlots,WaitingQueue.nItems);
            fileInput.write(secondHalf);

            // writing customers in waiting queue
            WaitingQueue.getWaitingQueueCustomers(true);
            fileInput.print("                                                        * * * * * *");

            // closing file connection
            fileInput.close();

            System.out.println("Saved successfully to the file");
            loopController();
        }
        catch (IOException e)
        {
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
            WaitingQueue.front = 0;
            WaitingQueue.rear = -1;

            // reading and adding values to all variables
            while (fileReader.hasNextLine())
            {
                line = fileReader.nextLine();
                if (line.startsWith("Last Saved"))
                {
                    saveTime = line.split(";")[1].trim();
                } else if (line.startsWith("Remaining Burger Count"))
                {
                    burgerStock = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Reserved Burger Count"))
                {
                    FoodQueue.reservedBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Sold Burger Count"))
                {
                    FoodQueue.soldBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Served Customers Count"))
                {
                    FoodQueue.servedCustomerCount = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No.of Empty Slots"))
                {
                    FoodQueue.emptySlots = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No.of Waiting Queue Customers"))
                {
                    WaitingQueue.nItems = Integer.parseInt(line.split(":")[1].trim());
                }
                // reading and adding values to cashier array
                else if (line.startsWith("Cashier"))
                {
                    String[] queueTokens = line.split(":")[1].trim().split(", ");
                    FoodQueue.queueIncome[cashierIndex] = Integer.parseInt(line.split(">")[1].trim().split("\\s+")[1].trim());

                    for (int j = 0; j < queueTokens.length; j++)
                    {
                        if(queueTokens[j].equals("")) break;
                        String[] customerAttributes = queueTokens[j].split("\\s+");
                        cashier[cashierIndex].getCustomerQueue()[j] = Customer.setLoadedCustomer(customerAttributes);
                    }
                    cashierIndex++;
                }
                // reading and adding values to customer queue
                else if(line.startsWith("Customers in Waiting Queue"))
                {
                    String[] waitingQueueTokens = line.split(":")[1].trim().split(", ");
                    for(int i = 0; i < waitingQueueTokens.length; i++)
                    {
                        if(waitingQueueTokens[i].equals("")) break;
                        String[] waitingCustomerAttributes = waitingQueueTokens[i].split("\\s+");
                        // inserting customer objects to the waitingQueue
                        WaitingQueue.insert(Customer.setLoadedCustomer(waitingCustomerAttributes));
                        WaitingQueue.nItems--;
                    }
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
                    """, saveTime, burgerStock, FoodQueue.reservedBurgers, FoodQueue.soldBurgers, FoodQueue.servedCustomerCount);
            System.out.println(fileOutput);

            // printing cashier data
            FoodQueue.writeCashierData(false);

            System.out.printf("""              
                    \nNo.of Empty Slots              : %s
                                        
                    ------------------------------------------------------ Waiting Queue ------------------------------------------------------
                                        
                    No.of Waiting Queue Customers  : %s
                    %n""", FoodQueue.emptySlots, WaitingQueue.nItems);

            // printing waiting queue data
            WaitingQueue.getWaitingQueueCustomers(false);
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while reading the file");
        }
    }

    // printing a low stocks alert
    public static void stockAlert()
    {
        if(burgerStock <= 10)
        {
            System.out.println("""
                                             \n *** Alert ***
                            You have less than 10 burgers left, please refill !""");
        }
    }

    // capitalizing first letters as names and returning them
    public static String[] nameCapitalization(String[] nameParts)
    {
        String fName = nameParts[0].substring(0,1).toUpperCase() + nameParts[0].substring(1);
        String sName = nameParts[1].substring(0,1).toUpperCase() + nameParts[1].substring(1);
        String[] capitalizedNames = {fName, sName};
        return capitalizedNames;
    }
}