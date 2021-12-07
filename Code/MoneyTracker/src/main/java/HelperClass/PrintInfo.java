package HelperClass;

import Model.Person;
import Model.Ticket;

public class PrintInfo {
    public static void printPersonInfo(Person p) {
        System.out.println(p.getFirstNameValue() + " " +  p.getLastNameValue() + " is born on " + p.getBirthDateValue().getDayOfMonth() + "/" + p.getBirthDateValue().getMonthValue() + "/" + p.getBirthDateValue().getYear() + " and is currently " + (Date.getAge(p.getBirthDateValue()) < 0 ? "living in the future. " : (Date.getAge(p.getBirthDateValue()) + " years old. ")) + p.getFirstNameValue() + "s additional info is: ");
        System.out.println("Gender: " + p.getGenderValue().toString() + ", phone number: " + p.getPhoneNumberValue() + ". " + (p.getGenderValue() == Gender.FEMALE ? "She has " : p.getGenderValue() == Gender.MALE ? "He has " : "They have ") + (p.getIconValue().length() == 0 ? "no custom icon" : "a custom icon") + ".");
        System.out.println();
    }

    public static void printTicketInfo(Ticket t) {
        System.out.println("Ticket (Type - " + t.getSplitTypeValue().toString() + "): created by " + t.getPayedByValue().getFirstNameValue() + " on " + t.getCreationDateValue().getDayOfMonth() + "/" + t.getCreationDateValue().getMonthValue());
        System.out.println("This ticket is for the " + t.getEventTypeValue().getEventName() + " with a total sum of " + t.getTotalSum() + " which was payed by " + t.getPayedByValue().getFirstNameValue());
        System.out.print("In detail: ");
        for (Person p:t.getPersonArrayList()) System.out.print(
                p.getFirstNameValue() +
                        (t.getAmountForPerson(p) > 0 ? " needs to get back " : " owes " + t.getPayedByValue().getFirstNameValue() + " ") +
                        t.getAmountForPerson(p) + ", ");
        System.out.println("\n");
    }
}
