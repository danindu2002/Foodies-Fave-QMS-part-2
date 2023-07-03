import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
    public static int burgerStock = 50;
    public static int emptySlots = 10;
    public static int newBurgersCount;
    public static int soldBurgers = 0;
    public static int servedCustomerCount = 0;
    public static String answer;
    public static int queueNumber;
    public static int actualQueueNumber;
    public static int lastElement;
    public static Date saveTime = new Date();
    public static int reservedBurgers = 50 + newBurgersCount - soldBurgers - burgerStock;
    public static Scanner input = new Scanner(System.in);

    // declaring a 2D array for storing queue data
    public static String[][] cashiers = new String[][]
            {
                    new String[2], new String[3], new String[5]
            };

    // declaring an array to sort names
    public static String[] names = new String[10];

    // declaring a 2D array to store ASCII values of the names up to 2 characters
    public static int[][] asciiValues = new int[][]
            {
                    new int[10], new int[10]
            };

    public static void main(String[] args)
    {
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
                    FoodQueue.addCustomersToQueue();
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

    static void cashierHeader()
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
    static void cashierFullQueue()
    {
        // Printing the full cashier queue
        for(int i = 0; i < cashiers[2].length; i++)
        {
            for(int j = 0; j < cashiers.length; j++)
            {
                if(i >= cashiers[j].length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(cashiers[j][i] == null)
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
        int totalSlots = cashiers[0].length + cashiers[1].length + cashiers[2].length;
        for(int i = 0; i < cashiers[2].length; i++)
        {
            for(int j = 0; j < cashiers.length; j++)
            {
                if(i >= cashiers[j].length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(cashiers[j][i] == null)
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
        for(int i = 0; i < cashiers.length; i++)
        {
            for(int j = 0; j < cashiers[2].length; j++)
            {
                if(j >= cashiers[i].length) continue;
                if(cashiers[i][j] == null)
                {
                    System.out.println("Cashier " + (i+1) + " has empty slots");
                    break;
                }
            }
        }
        System.out.println(emptySlots + " slots are Empty out of " + totalSlots);
    }

    static void addBurgersToStock()
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

    static void removeServedCustomer()
    {
        boolean loop = true;
        while(loop)
        {
            try
            {
                getQueue();

                // checking the queue number is one of a cashier
                if(queueNumber >= 1 && queueNumber <=3)
                {
                    actualQueueNumber = queueNumber - 1;
                    int lastElement = cashiers[actualQueueNumber].length - 1;

                    // removing first one and updating others
                    if(cashiers[actualQueueNumber][0] != null)
                    {
                        for(int k = 0; k < lastElement; k++)
                        {
                            cashiers[actualQueueNumber][k] = cashiers[actualQueueNumber][k + 1];
                        }
                        cashiers[actualQueueNumber][lastElement] = null;   /* updating the last one in the queue as empty*/
                        System.out.println("Served customer was removed successfully from queue " + queueNumber);

                        // updating sold burger count and served customer count
                        soldBurgers += 5;
                        servedCustomerCount +=1;
                        emptySlots++;
                        reservedBurgers -= 5;

                        loop = false;
                        loopController();
                    }
                    else
                    {
                        System.out.println("The selected queue is empty");
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
                            if(position >= 1 && position <= cashiers[actualQueueNumber].length)
                            {
                                int actualPosition = position - 1;
                                lastElement = cashiers[actualQueueNumber].length - 1;

                                // updating other positions
                                if(cashiers[actualQueueNumber][actualPosition] != null)
                                {
                                    for(int k = actualPosition; k < lastElement; k++)
                                    {
                                        cashiers[actualQueueNumber][k] = cashiers[actualQueueNumber][k + 1];
                                    }
                                    cashiers[actualQueueNumber][lastElement] = null;   /* updating the last one in the queue as empty */

                                    System.out.println("Customer was removed successfully from queue " + queueNumber);

                                    // increasing the burger count as the customer had not been served
                                    burgerStock += 5;
                                    reservedBurgers -= 5;
                                    emptySlots++;
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

    static void sortNames()
    {
        // adding customer names to names array
        int l = 0;
        for(int i = 0; i < cashiers[2].length; i++)
        {
            for(int j = 0; j < cashiers.length; j++)
            {
                // adding ascii values of first 2 characters to asciiValues array
                if(i >= cashiers[j].length) continue;
                if(cashiers[j][i] != null)
                {
                    names[l] = cashiers[j][i].toLowerCase();
                    asciiValues[0][l] = names[l].charAt(0);
                    if(names[l].length() > 1)
                    {
                        asciiValues[1][l] = names[l].charAt(1);
                    }
                    l++;
                }
            }
        }

        // bubble sorting first two characters of each name in ascending order
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
                System.out.println(rank +". " + name);
                rank++;
            }
        }
    }

    static void saveFile()
    {
        try {
            // creating and writing into the file
            PrintWriter fileInput = new PrintWriter("FFQMS-Data.txt");

            // writing first half
            String firstHalf = String.format("""
                            -------------------------------------->  Foodies Fave Queue Management System Data  <-------------------------------------- \n \n
                            Last Saved             ; %s \n
                            Sold Burger Count      : %s
                            Reserved Burger Count  : %s
                            Remaining Burger Count : %s \n
                            ----------------------------------------------------- Cashier Queues ------------------------------------------------------ \n
                            """, saveTime, soldBurgers, reservedBurgers, burgerStock);

            fileInput.write(firstHalf);

            // writing cashier data
            for (int i = 0; i < 3; i++) {
                fileInput.write("Cashier " + (i + 1) + "              : ");
                for (int j = 0; j < 5; j++) {
                    if (j >= cashiers[i].length) continue;
                    fileInput.print(cashiers[i][j] + "  ");
                }
                fileInput.println(" ");
            }

            // writing second half
            String secondHalf = String.format("""
                            No. of Empty slots     : %s \n
                            --------------------------------------------------------------------------------------------------------------------------- \n
                            Served Customers Count : %s \n \n \n
                                                                                   * * * * * *
                            """, emptySlots, servedCustomerCount);
            fileInput.write(secondHalf);

            // closing file connection
            fileInput.close();

            System.out.println("Saved successfully to the file");
            loopController();
        } catch (IOException e) {
            System.out.println("An error occurred");
        }
    }

    static void loadFile()
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
                } else if (line.startsWith("Sold Burger Count")) {
                    soldBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Reserved Burger Count")) {
                    reservedBurgers = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Remaining Burger Count")) {
                    burgerStock = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("No. of Empty slots")) {
                    emptySlots = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Served Customers Count")) {
                    servedCustomerCount = Integer.parseInt(line.split(":")[1].trim());
                }
                // reading and adding values to cashier array
                else if (line.startsWith("Cashier")) {
                    String[] queueTokens = line.split(":")[1].trim().split("\\s+");
                    for (int i = 0; i < queueTokens.length; i++) {
                        if (!queueTokens[i].equals("null")) {
                            cashiers[cashierIndex][i] = queueTokens[i];
                        }
                    }
                    cashierIndex++;
                }
            }
            fileReader.close();

            // printing saved data into the console
            String fileOutput = String.format("""
                                    -------------------- Loaded Data -------------------- \n
                                    Last Saved             : %s \n
                                    Sold Burger Count      : %s
                                    Reserved Burger Count  : %s
                                    Remaining Burger Count : %s \n
                                    No. of Empty Queues    : %s
                                    Served Customers Count : %s \n
                                    Cashier 1              : %s
                                    Cashier 2              : %s
                                    Cashier 3              : %s
                                    """, saveTime, soldBurgers, reservedBurgers, burgerStock, emptySlots, servedCustomerCount,
                    Arrays.toString(cashiers[0]), Arrays.toString(cashiers[1]), Arrays.toString(cashiers[2]));
            System.out.println(fileOutput);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
    }
    static void stockAlert()
    {
        // printing a low stocks alert
        if(burgerStock <= 10)
        {
            System.out.println("""
                            
                                              *** Alert ***
                            You have less than 10 burgers left, please refill !""");
        }
    }
    static void getQueue()
    {
        // getting queue number
        System.out.print("Enter Queue: ");
        queueNumber = input.nextInt();
    }
}
