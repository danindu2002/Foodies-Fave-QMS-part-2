public class WaitingQueue
{
    private static Customer[] waitingListQueue;
    private static int front;
    private static int rear;
    public static int nItems;
    private static int size = 10;

    public WaitingQueue(int size)
    {
        this.size = size;
        waitingListQueue = new Customer[size];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    public static void insert(Customer customer)
    {
        if(nItems == size) System.out.println("Waiting Queue is full");
        else
        {
            if(rear == size - 1) rear = -1;

            waitingListQueue[++rear] = customer;
            nItems++;
        }
    }
    public static Customer remove()
    {
        if(nItems == 0)
        {
            System.out.println("Waiting Queue is empty");
            return null;
        }
        else
        {
            Customer temp = waitingListQueue[front];
            waitingListQueue[front] = null;
            front++;

            if(front == size) front = 0;
            nItems--;
            return temp;
        }
    }
    public static void getWaitingQueueCustomers(boolean printToFile) {
        if (printToFile) {
            Main.fileInput.print("Customers in Waiting Queue     : ");
        } else {
            System.out.print("Customers in Waiting Queue     : ");
        }

        for (int i = 0; i < waitingListQueue.length; i++) {
            if (waitingListQueue[i] != null) {
                String[] nameParts = Main.nameCapitalization(waitingListQueue[i].getFullName().split(" "));
                String customerInfo = nameParts[0] + " " + nameParts[1] + " - " + waitingListQueue[i].getBurgerCount();

                if (printToFile) {
                    Main.fileInput.print(customerInfo);
                } else {
                    System.out.print(customerInfo);
                }

                if (i + 1 != waitingListQueue.length) {
                    if (waitingListQueue[i + 1] != null) {
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
            Main.fileInput.println("\n");
        } else {
            System.out.println("\n");
        }
    }


}

