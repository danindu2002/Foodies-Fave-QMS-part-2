public class FoodQueue
{
    private Customer[] customerQueue;
    public FoodQueue(int size)
    {
        customerQueue = new Customer[size];
    }
    public Customer[] getCustomerQueue()
    {
        return customerQueue;
    }
}
