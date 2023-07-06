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
        if(firstName != null && secondName != null) return firstName + " " + secondName;
        else return null;
    }
    public int getBurgerCount()
    {
        return burgerAmount;
    }
}
