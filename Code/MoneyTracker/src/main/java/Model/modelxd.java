package Model;

public class modelxd {
    private Person personFrom;
    private Person personTo;
    private double amount;

    public modelxd(Person personFrom, Person personTo, double amount) {
        this.personFrom = personFrom;
        this.personTo = personTo;
        this.amount = amount;
    }

    public Person getPersonFrom() {
        return personFrom;
    }

    public Person getPersonTo() {
        return personTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setPersonFrom(Person personFrom) {
        this.personFrom = personFrom;
    }

    public void setPersonTo(Person personTo) {
        this.personTo = personTo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void swapPersons() {
        Person temp = personTo;
        personTo = personFrom;
        personFrom = temp;
    }
}
