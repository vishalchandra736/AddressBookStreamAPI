package com.blz.addressbookstreamapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMethods {
    static Scanner sc = new Scanner(System.in);
    static Map<String, String> cityDictionary = new HashMap<>();
    static Map<String, String> stateDictionary = new HashMap<>();


    public void manageAddressBook(String addressBook, ArrayList<Contact> contactList) {
        int  choice = 0;

        do {
            do {
                System.out.println("\nWhich of the following operations would you like to perform?");
                System.out.println("1. Add a New contact");
                System.out.println("2. Edit an Existing contact");
                System.out.println("3. Delete an Existing contact");
                System.out.println("4. Display the Address Book");
                System.out.println("5. Exit");
                System.out.print("\nEnter your choice : ");
                choice = sc.nextInt();

                if (!(choice >=1 && choice <= 5))
                    System.out.println("\nInvalid choice!\nPlease try again.\n");
            }while (!(choice >=1 && choice <= 5));

            switch (choice)
            {
                case 1 :
                    addContact(contactList);
                    break;

                case 2 :
                    editContact(contactList);
                    break;

                case 3 :
                    deleteContact(contactList);
                    break;

                case 4 :
                    displayAddressBook(addressBook, contactList);
                    break;

                case 5 :
                    System.out.println("\nExiting Address Book '" + addressBook + "'");
                    break;
            }
        }while(choice != 5);
    }

    public void registerInCityDictionary(Contact contact) {
        cityDictionary.put(contact.getFirstName(), contact.getAddress().getCity());
    }

    public void registerInStateDictionary(Contact contact) {
        stateDictionary.put(contact.getFirstName(), contact.getAddress().getState());
    }

    public Contact getContactToModify(String name, ArrayList<Contact> contactList) {
        Contact contact = null;

        for(int i = 0; i < contactList.size(); i++) {
            Contact temp = contactList.get(i);
            if(name.equalsIgnoreCase(temp.firstName)) {
                contact = temp;
            }
        }
        return contact;
    }

    public void addContact(ArrayList<Contact> contactList) {
        System.out.println("\nCreating a new contact!");
        System.out.print("Enter First Name :	");
        String name = sc.next();

        boolean contactExists = contactList.stream().anyMatch(entry -> entry.firstName.equals(name));

        if (contactExists) {
            System.out.println("\nThis conatct already exists!\nPlease try adding a new conatct.");
        }
        else {
            System.out.print("Enter Last Name :	");
            String lastname = sc.next();
            System.out.print("Enter City :	");
            String city = sc.next();
            System.out.print("Enter State :	");
            String state = sc.next();
            System.out.print("Enter zip :	");
            String zip = sc.next();
            System.out.print("Enter Phone Number :	");
            String number = sc.next();
            System.out.print("Enter Email Address :	");
            String email = sc.next();

            Contact newcontact = new Contact();
            Address address = new Address();
            newcontact.setFirstName(name);
            newcontact.setLastName(lastname);
            address.setCity(city);
            address.setState(state);
            address.setZip(zip);
            newcontact.setAddress(address);
            newcontact.setPhoneNumber(number);
            newcontact.setEmailID(email);

            contactList.add(newcontact);
            registerInCityDictionary(newcontact);
            registerInStateDictionary(newcontact);
        }
    }

    public String toString(ArrayList<Contact> contactList) {
        return "\nAddressBook [\nContact List" + contactList + "\n]";
    }

    public void displayContact(Contact contact) {
        System.out.println(contact);
    }

    public void displayAddressBook(String addressbook, ArrayList<Contact> contactList) {
        System.out.println("\n\n------- " + addressbook + " Address Book -------");
        for (int i = 0; i < contactList.size(); i++)
            System.out.println("\n"+contactList.get(i));
        System.out.println();
    }

    public void editContact(ArrayList<Contact> contactList) {
        Contact contact = null;
        String name = null;

        System.out.print("\nEnter the First Name of the contact you want to edit : ");
        name = sc.next();
        while (contact == null) {
            contact = getContactToModify(name, contactList);
            if (contact == null) {
                System.out.print("\nNo such entry exists!\nPlease enter a valid First Name : ");
                name = sc.next();
                contact = getContactToModify(name, contactList);
            }
        }
        makeEdits(contact);
    }

    public void makeEdits(Contact contact) {
        int choice = 0;

        while (choice < 1 || choice > 4) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Phone Number");
            System.out.println("3. Email Id");
            System.out.println("4. Address");
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();

            if (!(choice >=1 && choice <= 4))
                System.out.println("\nInvalid choice!\nPlease try again.\n");
        }

        switch (choice) {
            case 1 :
                System.out.print("Enter the updated First Name :	");
                String firstname = sc.next();
                System.out.print("Enter the updated Last Name :	");
                String lastname = sc.next();
                contact.setFirstName(firstname);
                contact.setLastName(lastname);
                break;

            case 2 :
                System.out.print("Enter the updated Phone Number :	");
                String number = sc.next();
                contact.setPhoneNumber(number);
                break;

            case 3 :
                System.out.print("Enter the updated Email Address :	");
                String email = sc.next();
                contact.setEmailID(email);
                break;

            case 4 :
                System.out.print("Enter the updated City :	");
                String city = sc.next();
                System.out.print("Enter the updated State :	");
                String state = sc.next();
                System.out.print("Enter the updated zip :	");
                String zip = sc.next();
                contact.address.setCity(city);
                contact.address.setState(state);
                contact.address.setZip(zip);
                break;
        }

        System.out.println("\nIs there anything else you'd like to update?");
        System.out.print("Enter 'Yes' or 'No' : ");
        char continueEdit = sc.next().charAt(0);
        if (continueEdit == 'Y' || continueEdit == 'y') {
            makeEdits(contact);
        }
        else if (continueEdit == 'N' || continueEdit == 'n') {
            System.out.println("\n\nHere is the updated contact.");
            displayContact(contact);
        }
        else {
            System.out.println("\nInvalid Input.\nPlease try again!");
        }
    }

    public void deleteContact(ArrayList<Contact> contactList) {
        Contact contact = null;
        String name = null;

        System.out.print("\nEnter the First Name of the contact you want to delete : ");
        name = sc.next();
        while (contact == null) {
            contact = getContactToModify(name, contactList);
            if (contact == null) {
                System.out.println("\nNo such entry exists!\nPlease enter a valid First Name.");
                name = sc.next();
                contact = getContactToModify(name, contactList);
            }
        }
        contactList.remove(contact);
    }
}
