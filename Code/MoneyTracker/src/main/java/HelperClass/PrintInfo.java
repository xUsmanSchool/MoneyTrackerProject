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
        for (Person p:t.getPersonArrayList()) if (t.getAmountForPerson(p) == 0) System.out.print(p.getFirstNameValue() + " payed nothing. ");
        System.out.println();
        Double sum = 0.00;
        for (Person p:t.getPersonArrayList()) if (t.getAmountForPerson(p) < 0) {
            System.out.print(p.getFirstNameValue() + " owes " + t.getPayedByValue().getFirstNameValue() + " " + String.format("%.2f", t.getAmountForPerson(p)) + ", ");
            sum += t.getAmountForPerson(p);
        }
        sum = sum + t.getTotalSum();
        System.out.println();
        for (Person p:t.getPersonArrayList()) if (t.getAmountForPerson(p) > 0) System.out.print("Because " + p.getFirstNameValue() + " payed " + t.getTotalSum() + " in total and " + String.format("%.2f", sum) + " was for his own expenses, he only needs to get back $ " + String.format("%.2f", t.getAmountForPerson(p)) + "");
        System.out.println("\n");
    }
}
