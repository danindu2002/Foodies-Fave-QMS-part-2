public class Customer
{
    private String firstName;
    private String secondName;
    private int burgerAmount;

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }
    public void setBurgerAmount(int burgerAmount)
    {
        this.burgerAmount = burgerAmount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFullName()
    {
        return firstName + " " + secondName;
    }
    public int getBurgerCount()
    {
        return burgerAmount;
    }
}
