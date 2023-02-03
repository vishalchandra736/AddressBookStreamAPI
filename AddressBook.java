package com.blz.addressbookstreamapi;

import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {
    static Scanner sc = new Scanner(System.in);
    HashMap<String, ArrayList<Contact>> addressBookMap = new HashMap<>();
    AddressBookMethods operations = new AddressBookMethods();

    public void manageAddressBookList() {
        int  choice = 0;

        do {
            do {
                System.out.println("\nWhich of the following operations would you like to perform?");
                System.out.println("1. Add a New Address Book");
                System.out.println("2. Edit an Existing Address Book");
                System.out.println("3. Delete an Existing Address Book");
                System.out.println("4. Display Address Book List");
                System.out.println("5. List all Address Books");
                System.out.println("6. Search for");
                System.out.println("0. Exit");
                System.out.print("\nEnter your choice : ");
                choice = sc.nextInt();

                if (!(choice >=0 && choice <= 6))
                    System.out.println("\nInvalid choice!\nPlease try again.\n");
            }while (!(choice >=0 && choice <= 6));

            switch (choice)
            {
                case 1 :
                    addAddressBook();
                    break;

                case 2 :
                    editAddressBook();
                    break;

                case 3 :
                    deleteAddressBook();
                    break;

                case 4 :
                    displayAddressBookList();
                    break;

                case  5 :
                    displayAllAddressBooks();
                    break;

                case  6 :
                    searchOperation();
                    break;

                case 0 :
                    System.out.println("\nEXITED PROGRAM");
                    break;
            }
        }while(choice != 0);
    }

    public void searchOperation() {
        int  choice = 0;
        do {
            System.out.println("\nSearch for Persons ");
            System.out.println("1. By City");
            System.out.println("2. By State");
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();

            if (!(choice ==1 || choice == 2))
                System.out.println("\nInvalid choice!\nPlease try again.\n");
        }while (!(choice ==1 || choice == 2));

        switch (choice)
        {
            case 1 :
                searchByCity();
                break;

            case 2 :
                searchByState();
                break;

            default :
                break;
        }
    }

    public void searchByCity() {
        System.out.println("Enter the City : ");
        String searchCity = sc.next();
        System.out.println();

        List<String> cityEntries = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Contact>> book : addressBookMap.entrySet()) {
            List<String> matchingCity = book.getValue().stream().filter(entry -> entry.address.city.equalsIgnoreCase(searchCity)).map(entry -> entry.getFirstName()).collect(Collectors.toList());
            cityEntries.addAll(matchingCity);
        }

        if (cityEntries.size() > 0)
            System.out.println("\nPersons in '" + searchCity + "' city : " + cityEntries);
        else
            System.out.println("\nNo Persons information found in '" + searchCity + "' city.");
    }

    public void searchByState() {
        System.out.println("Enter the State : ");
        String searchState = sc.next();
        System.out.println();

        List<String> stateEntries = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Contact>> book : addressBookMap.entrySet()) {
            Collectors Collectors = null;
            List<String> matchingState = book.getValue().stream().filter(entry -> entry.address.state.equalsIgnoreCase(searchState)).map(entry -> entry.getFirstName()).collect(Collectors.toList());
            stateEntries.addAll(matchingState);
        }

        if (stateEntries.size() > 0)
            System.out.println("\nPersons in '" + searchState + "' city : " + stateEntries);
        else
            System.out.println("\nNo Persons information found in '" + searchState + "' city.");
    }

    public void addAddressBook() {
        String addressbookName = "";

        System.out.println("\nCreating a new Address Book...");
        addressbookName = getAddressBookName();

        ArrayList<Contact> contactList = new ArrayList<>();
        addressBookMap.put(addressbookName, contactList);
        operations.manageAddressBook(addressbookName, contactList);
    }

    public String getAddressBookName() {
        String name = "";
        boolean keyExists = false;

        System.out.print("\nEnter the name of the Address Book : ");
        name = sc.next();
        keyExists = addressBookMap.containsKey(name);

        if (keyExists == true) {
            System.out.println("\nThis Address Book already exists.\nTry selecting a different name to proceed further.");
            name = getAddressBookName();
        }

        return name;
    }

    public void editAddressBook() {
        String name = "";
        ArrayList<Contact> editList = getAddressBookToModify();

        for (Map.Entry<String, ArrayList<Contact>> entry : addressBookMap.entrySet()) {
            if (entry.getValue().equals(editList))
                name = entry.getKey();
        }
        operations.manageAddressBook(name, editList);
    }

    public boolean doesAddressBookExist(String name) {
        boolean listExists;

        if (addressBookMap.containsKey(name))
            listExists = true;
        else
            listExists = false;

        return listExists;
    }

    public ArrayList<Contact> getAddressBookToModify() {
        ArrayList<Contact> listToEdit = null;
        String name = null;

        System.out.print("\nEnter the name of the address book you want to edit : ");
        name = sc.next();
        while(true) {
            if (doesAddressBookExist(name)) {
                listToEdit = addressBookMap.get(name);
                break;
            } else {
                System.out.print("\nNo such Address Book exists!\nPlease enter a valid name : ");
                name = sc.next();
            }
        }
        return listToEdit;
    }

    public String getAddressBookToDelete() {
        String name = null;

        System.out.print("\nEnter the name of the address book you want to delete : ");
        name = sc.next();
        while (true) {
            if (doesAddressBookExist(name)) {
                break;
            } else {
                System.out.print("\nNo such Address Book exists!\nPlease enter a valid name : ");
                name = sc.next();
            }
        }
        return name;
    }

    public void deleteAddressBook() {
        String name = getAddressBookToDelete();

        addressBookMap.remove(name);
        System.out.println("\nSuccessfully removed "+name+" Address Book!\n");
    }

    public void displayAddressBookList() {
        System.out.println("\nExisting Address Books -\n");
        addressBookMap.forEach((key, value) -> System.out.println(key + " \n"));
    }

    public void displayAllAddressBooks() {
        System.out.println("");
        for (Object obj : addressBookMap.entrySet()) {
            Map.Entry<String, ArrayList<Contact>> entry = (Map.Entry) obj;
            System.out.print("Address Book : " + entry.getKey());
            operations.displayAddressBook(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "Address Book List [\nAddress Book : " + addressBookMap + "]";
    }
}
