import java.util.InputMismatchException;

public class FoodQueue
{
    public static void addCustomersToQueue()
    {
        boolean nameLoop = true;
        boolean queueLoop = true;
        while(nameLoop)
        {
            Customer customer = new Customer();
            System.out.print("Enter first name: ");
            customer.setFirstName(Main.input.next());
            System.out.print("Enter second name: ");
            customer.setSecondName(Main.input.next());
            System.out.print("Enter burger amount: ");
            customer.setBurgerAmount(Main.input.nextInt());

            System.out.println(customer.getFullName());
            System.out.println(customer.getBurgerCount());

            // checking if the customer can be served with the remaining stock or not
            if (Main.burgerStock >= 5)
            {
                System.out.print("Enter Name: ");
                String name = Main.input.next();

                // validating if the customer name is only alphabetical or not
                if (name.matches("^[a-zA-Z]*$"))
                {
                    nameLoop = false;
                    while(queueLoop)
                    {
                        // validating user input only as an integer
                        try
                        {
                            Main.getQueue();

                            // checking the queue number is one of a cashier
                            if(Main.queueNumber >= 1 && Main.queueNumber <=3)
                            {
                                Main.actualQueueNumber = Main.queueNumber - 1;
                                int occupiedQueuePositions = 0;

                                for (int k = 0; k < Main.cashiers[Main.actualQueueNumber].length; k++)
                                {
                                    // adding customer name to an empty queue position only if it is empty
                                    if (Main.cashiers[Main.actualQueueNumber][k] == null)
                                    {
                                        Main.cashiers[Main.actualQueueNumber][k] = name;
                                        System.out.println(name + " added to the queue " + Main.queueNumber + " successfully");

                                        // reserving 5 burgers for the customer and updating empty queues
                                        Main.burgerStock -= 5;
                                        Main.reservedBurgers +=5;
                                        Main.emptySlots--;

                                        // showing the low stocks alert if the burger count is less than 10
                                        Main.stockAlert();

                                        queueLoop = false;
                                        Main.loopController();
                                        break;
                                    }
                                    else
                                    {
                                        occupiedQueuePositions++;

                                        // giving a message if the particular queue is full
                                        if (occupiedQueuePositions == Main.cashiers[Main.actualQueueNumber].length)
                                        {
                                            System.out.println("Queue is full, Please try again");
                                        }
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
                else
                {
                    System.out.println("Please enter alphabetical letters only");
                }
            }
            else
            {
                System.out.println("Burger stock is insufficient, Please Refill!");
            }
        }
    }
}
