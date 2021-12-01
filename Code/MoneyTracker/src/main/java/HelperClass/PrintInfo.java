package HelperClass;

import Model.Person;
import Model.Ticket;

public class PrintInfo {
    public static void printPersonInfo(Person p) {
        System.out.println(p.getFirstNameValue() + " " +  p.getLastNameValue() + " is born on " + p.getBirthDateValue().getDay() + "/" + p.getBirthDateValue().getMonth() + "/" + p.getBirthDateValue().getYear() + " and is currently " + (new Date().getAge(p.getBirthDateValue()) < 0 ? "living in the future. " : (new Date().getAge(p.getBirthDateValue()) + " years old. ")) + p.getFirstNameValue() + "s additional info is: ");
        System.out.println("Gender: " + p.getGenderValue().toString() + ", phone number: " + p.getPhoneNumberValue() + ". He has " + (p.getIconValue().length() == 0 ? "no custom icon" : "a custom icon") + ".");
        System.out.println();
    }

    public static void printTicketInfo(Ticket t) {
        System.out.println("Ticket (Type - " + t.getSplitTypeValue().toString() + "): created by " + t.getCreatedByValue().getFirstNameValue() + " on " + t.getCreationDateValue().getDay() + "/" + t.getCreationDateValue().getMonth());
        System.out.println("This ticket is for the " + t.getEventTypeValue().getEventName() + " with a total sum of " + t.getPayedAmount() + " which was payed by " + t.getPayedBy().getFirstNameValue());
        System.out.print("In detail: ");
        for (Person p:t.getPersonArrayList()) System.out.print(
                p.getFirstNameValue() +
                        (t.getAmountForPerson(p) > 0 ? " payed " : " owes " + t.getPayedBy().getFirstNameValue() + " ") +
                        t.getAmountForPerson(p) + ", ");
        System.out.println("\n");
    }
}
