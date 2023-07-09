public class WaitingQueue
{
    private static Customer[] waitingListQueue;
    // defining pointers for the circular queue names front & rear
    private static int front;
    private static int rear;
    public static int nItems;
    // maximum size for the waiting queue is 10
    private static int size = 10;

    public WaitingQueue(int size)
    {
        this.size = size;
        waitingListQueue = new Customer[size];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    // inserting customers into waiting queue
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
    // removing customers from the waiting queue
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
    // printing customer attributes to the console or the file
    public static void getWaitingQueueCustomers(boolean printToFile)
    {
        if (printToFile)  Main.fileInput.print("Customers in Waiting Queue     : ");
        else  System.out.print("Customers in Waiting Queue     : ");

        for (int i = 0; i < nItems ; i++)
        {
            if (waitingListQueue[i] != null)
            {
                String[] nameParts = Main.nameCapitalization(waitingListQueue[i].getFullName().split(" "));
                String customerInfo = nameParts[0] + " " + nameParts[1] + " - " + waitingListQueue[i].getBurgerCount();

                if (printToFile)  Main.fileInput.print(customerInfo);
                else  System.out.print(customerInfo);

                if (i + 1 != nItems && waitingListQueue[i + 1] != null)
                {
                    if  (printToFile) Main.fileInput.print(", ");
                    else  System.out.print(", ");
                }
            }
        }
        if (printToFile)  Main.fileInput.println("\n");
        else  System.out.println("\n");
    }
}

