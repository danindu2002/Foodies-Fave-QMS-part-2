import java.util.InputMismatchException;

public class FoodQueue
{
    private Customer[] customerQueue;
    public static int queueNumber;
    public static int actualQueueNumber;
    public static int lastElement;
    public static int burgerPrice = 650;
    public static int emptySlots = 10;
    public static int soldBurgers = 0;
    public static int servedCustomerCount = 0;
    public static int reservedBurgers = 50 + Main.newBurgersCount - soldBurgers - Main.burgerStock;
    public static int[] queueIncome = new int[3];

    // declaring an array to sort names
    public static String[] names = new String[10];

    // declaring a 2D array to store ASCII values of the names up to 2 characters
    public static int[][] asciiValues = new int[][]
            {
                    new int[10], new int[10]
            };
    public FoodQueue(int size)
    {
        customerQueue = new Customer[size];
    }
    public Customer[] getCustomerQueue()
    {
        return customerQueue;
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
            for(int j = 0; j < Main.cashier.length; j++)
            {
                if(i >= Main.cashier[j].getCustomerQueue().length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(Main.cashier[j].getCustomerQueue()[i] == null)
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
        int totalSlots = Main.cashier[0].getCustomerQueue().length + Main.cashier[1].getCustomerQueue().length + Main.cashier[2].getCustomerQueue().length;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < Main.cashier.length; j++)
            {
                if(i >= Main.cashier[j].getCustomerQueue().length)
                {
                    System.out.print("      ");
                    continue;
                }
                if(Main.cashier[j].getCustomerQueue()[i] == null)
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
        for(int i = 0; i < Main.cashier.length; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                if(j >= Main.cashier[i].getCustomerQueue().length) continue;
                if(Main.cashier[i].getCustomerQueue()[j] == null)
                {
                    System.out.println("Cashier " + (i+1) + " has empty slots");
                    break;
                }
            }
        }
        if(emptySlots == 1) System.out.println(emptySlots + " slot is Empty out of " + totalSlots + " slots");
        else System.out.println(emptySlots + " slots are Empty out of " + totalSlots + " slots");
    }

//    public static void addCustomersToQueue()
//    {
//        boolean nameLoop = true;
//        boolean queueLoop = true;
//        while(nameLoop)
//        {
//            Customer customer = new Customer();
//
//            // checking that at least one burger is available or not
//            if (Main.burgerStock > 0)
//            {
//                System.out.print("Enter First Name: ");
//                customer.setFirstName(Main.input.next());
//                System.out.print("Enter Second Name: ");
//                customer.setSecondName(Main.input.next());
//
//                // validating the customer name as only alphabetical
//                if (customer.getFirstName().matches("^[a-zA-Z]*$") && customer.getSecondName().matches("^[a-zA-Z]*$"))
//                {
//                    nameLoop = false;
//                    while(queueLoop)
//                    {
//                        // validating user input only as an integer
//                        try
//                        {
//                            System.out.print("Enter Burger Amount: ");
//                            customer.setBurgerAmount(Main.input.nextInt());
//
//                            // validating burger amount
//                            if(customer.getBurgerCount() > 0)
//                            {
//                                // adding additional customers to waiting area if all queues are full
//                                if(emptySlots != 0)
//                                {
//                                    if(customer.getBurgerCount() <= Main.burgerStock)
//                                    {
//                                        // placing in the shortest queue
//                                        for(int i = 0; i < 5; i++)
//                                        {
//                                            for(int j = 0; j < Main.cashier.length; j++)
//                                            {
//                                                if(i >= Main.cashier[j].getCustomerQueue().length) continue;
//                                                if(Main.cashier[j].getCustomerQueue()[i] == null)
//                                                {
//                                                    Main.cashier[j].getCustomerQueue()[i] = customer;
//                                                    // updating the income of each queue
//                                                    queueIncome[j] += Main.cashier[j].getCustomerQueue()[i].getBurgerCount() * burgerPrice;
//
//                                                    i = 5;
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                        // reserving 5 burgers for the customer and updating empty slots
//                                        Main.burgerStock -= customer.getBurgerCount();
//                                        reservedBurgers += customer.getBurgerCount();
//                                        emptySlots--;
//                                    }
//                                    else
//                                    {
//                                        System.out.println("Unable to supply that amount of burgers");
//                                    }
//                                }
//                                else
//                                {
//                                    // adding to waiting queue
//                                    WaitingQueue.insert(customer);
//                                }
//                                // showing the low stocks alert if the burger count is less than 10
//                                Main.stockAlert();
//                                queueLoop = false;
//                                Main.loopController();
//                            }
//                            else
//                            {
//                                System.out.println("Positive integer required");
//                            }
//                        }
//                        catch (InputMismatchException e)
//                        {
//                            System.out.println("Integer required");
//                            Main.input.next();
//                        }
//                    }
//                }
//                else
//                {
//                    System.out.println("Please enter alphabetical letters only");
//                }
//            }
//            else
//            {
//                System.out.println("Burger stock is insufficient, Please Refill!");
//                Main.loopController();
//            }
//        }
//    }

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
                    int lastElement = Main.cashier[actualQueueNumber].getCustomerQueue().length - 1;

                    if(Main.cashier[actualQueueNumber].getCustomerQueue()[0] != null)
                    {
                        // updating sold burger count, served customer count, empty slots, reserved burgers variables
                        soldBurgers += Main.cashier[actualQueueNumber].getCustomerQueue()[0].getBurgerCount();
                        servedCustomerCount +=1;
                        emptySlots++;
                        reservedBurgers -= Main.cashier[actualQueueNumber].getCustomerQueue()[0].getBurgerCount();

                        // removing first one and updating others
                        for(int k = 0; k < lastElement; k++)
                        {
                            Main.cashier[actualQueueNumber].getCustomerQueue()[k] = Main.cashier[actualQueueNumber].getCustomerQueue()[k + 1];
                        }

                        // adding customers from waiting area to queue if they exist
                        if(WaitingQueue.nItems == 0)
                        {
                            Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement] = null;   /* updating the last one in the queue as empty */
                        }
                        else
                        {
                            Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement] = WaitingQueue.remove();
                            // updating income and empty slots
                            queueIncome[actualQueueNumber] += Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement].getBurgerCount() * burgerPrice;
                            emptySlots--;
                        }
                        System.out.println("Served customer was removed successfully from queue " + queueNumber);
                        loop = false;
                        Main.loopController();
                    }
                    else
                    {
                        System.out.println("The selected queue is already empty");
                        Main.loopController();
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
                Main.input.next();
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
                            int position = Main.input.nextInt();

                            // checking the validity of the position
                            if(position >= 1 && position <= Main.cashier[actualQueueNumber].getCustomerQueue().length)
                            {
                                int actualPosition = position - 1;
                                lastElement = Main.cashier[actualQueueNumber].getCustomerQueue().length - 1;
                                Customer removedCustomer = Main.cashier[actualQueueNumber].getCustomerQueue()[actualPosition];

                                // updating other positions
                                if(removedCustomer != null)
                                {
                                    // increasing the burger count as the customer had not been served
                                    Main.burgerStock += removedCustomer.getBurgerCount();
                                    reservedBurgers -= removedCustomer.getBurgerCount();
                                    emptySlots++;

                                    // decreasing the income of the removed customer
                                    queueIncome[actualQueueNumber] -= removedCustomer.getBurgerCount() * burgerPrice;

                                    for(int k = actualPosition; k < lastElement; k++)
                                    {
                                        Main.cashier[actualQueueNumber].getCustomerQueue()[k] = Main.cashier[actualQueueNumber].getCustomerQueue()[k + 1];
                                    }

                                    // adding customers from waiting area to queue if they exist
                                    if(WaitingQueue.nItems == 0)
                                    {
                                        Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement] = null;   /* updating the last one in the queue as empty */
                                    }
                                    else
                                    {
                                        Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement] = WaitingQueue.remove();
                                        // updating the income of the added customer
                                        queueIncome[actualQueueNumber] += Main.cashier[actualQueueNumber].getCustomerQueue()[lastElement].getBurgerCount() * burgerPrice;
                                        emptySlots--;
                                    }

                                    System.out.println("Customer was removed successfully from queue " + queueNumber);
                                    loop4 = false;
                                }
                                else
                                {
                                    System.out.println("The selected position is already empty");
                                }
                                Main.loopController();
                            }
                            else
                            {
                                System.out.println("Invalid position");
                            }
                        }
                        catch (InputMismatchException e)
                        {
                            System.out.println("Integer required");
                            Main.input.next();
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
                Main.input.next();
            }
        }
    }

    // printing income from the current customers in each queue
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
            for(int j = 0; j < Main.cashier.length; j++)
            {
                // adding ascii values of first 2 characters to asciiValues array
                if(i >= Main.cashier[j].getCustomerQueue().length) continue;
                if(Main.cashier[j].getCustomerQueue()[i] != null)
                {
                    names[l] = Main.cashier[j].getCustomerQueue()[i].getFullName().toLowerCase();
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
                String[] nameParts = Main.nameCapitalization(name.split(" "));
                System.out.println(rank + ". " + nameParts[0] + " " + nameParts[1]);
                rank++;
            }
        }
    }

    // printing or writing cashier data along with their income to the file
    public static void writeCashierData(boolean printToFile)
    {
        for (int i = 0; i < 3; i++)
        {
            if (printToFile) {
                Main.fileInput.write(String.format("Cashier %s   >   Rs. %05d      : ", (i + 1), queueIncome[i]));
            } else {
                System.out.printf("Cashier %s   >   Rs. %05d      : ", (i + 1), queueIncome[i]);
            }

            for (int j = 0; j < 5; j++)
            {
                if (j >= Main.cashier[i].getCustomerQueue().length) continue;
                if (Main.cashier[i].getCustomerQueue()[j] != null)
                {
                    String[] nameParts = Main.nameCapitalization(Main.cashier[i].getCustomerQueue()[j].getFullName().split(" "));
                    String customerInfo = nameParts[0] + " " + nameParts[1] + " - " + Main.cashier[i].getCustomerQueue()[j].getBurgerCount();

                    if (printToFile)  Main.fileInput.print(customerInfo);
                    else  System.out.print(customerInfo);

                    // printing a "," if it is not the last one
                    if (j + 1 != Main.cashier[i].getCustomerQueue().length && Main.cashier[i].getCustomerQueue()[j + 1] != null)
                    {
                        if (printToFile)  Main.fileInput.print(", ");
                        else  System.out.print(", ");
                    }
                }
            }
            if (printToFile)  Main.fileInput.println(" ");
            else  System.out.println(" ");
        }
    }

    // getting input for the queue number
    public static void getQueue()
    {
        System.out.print("Enter queue: ");
        queueNumber = Main.input.nextInt();
    }
}

