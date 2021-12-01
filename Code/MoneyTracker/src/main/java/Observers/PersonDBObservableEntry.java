package Observers;

import Model.Person;

public class PersonDBObservableEntry {
    private final Person person;
    private final boolean added;

    public PersonDBObservableEntry(Person person, boolean added){
        this.person = person;
        this.added = added;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isAdded() {
        return added;
    }
}
