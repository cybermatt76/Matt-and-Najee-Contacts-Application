
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactListApplication {
    private static final String FILENAME = "contacts.txt";
    private static ArrayList<String> contacts;

    public static void main(String[] args) {
        contacts = new ArrayList<>();
        readContactsFromFile();

        Scanner input = new Scanner(System.in);
        int option = 0;

        while (option != 5) {
            System.out.println("Please select an option:");
            System.out.println("1. View contacts.");
            System.out.println("2. Add a new contact.");
            System.out.println("3. Search a contact by name.");
            System.out.println("4. Delete an existing contact.");
            System.out.println("5. Exit.");
            option = input.nextInt();

            switch (option) {
                case 1:
                    viewContacts();
                    break;
                case 2:
                    addContact(input);
                    break;
                case 3:
                    searchContact(input);
                    break;
                case 4:
                    deleteContact(input);
                    break;
                case 5:
                    saveContactsToFile();
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            // Determine maximum length of name and phone number columns
            int maxNameLength = 0;
            int maxPhoneNumberLength = 0;
            for (String contact : contacts) {
                String[] contactInfo = contact.split(" ");
                int nameLength = (contactInfo[0] + " " + contactInfo[1]).length();
                maxNameLength = Math.max(maxNameLength, nameLength);
                int phoneNumberLength = contactInfo[2].length();
                maxPhoneNumberLength = Math.max(maxPhoneNumberLength, phoneNumberLength);
            }
            // Print table header
            System.out.printf("%-" + (maxNameLength + 2) + "s| %-" + (maxPhoneNumberLength + 2) + "s%n",
                    "Name", "Phone number");
            System.out.print(new String(new char[maxNameLength + maxPhoneNumberLength + 7]).replace('\0', '-') + "\n");
            // Print table rows
            for (String contact : contacts) {
                String[] contactInfo = contact.split(" ");
                System.out.printf("%-" + (maxNameLength + 2) + "s| %-" + (maxPhoneNumberLength + 2) + "s%n",
                        contactInfo[0] + " " + contactInfo[1], contactInfo[2]);
            }
        }
    }


    private static void addContact(Scanner input) {
        System.out.println("Please enter the first name:");
        String firstName = input.next();

        System.out.println("Please enter the last name:");
        String lastName = input.next();

        System.out.println("Please enter the phone number:");
        String phoneNumber = input.next();

        String newContact = firstName + " " + lastName + " " + phoneNumber;
        contacts.add(newContact);
        System.out.println("Contact added: " + newContact);

        saveContactsToFile();
    }

    private static void searchContact(Scanner input) {
        System.out.println("Please enter the name to search for:");
        String searchTerm = input.next();

        boolean foundContact = false;

        for (String contact : contacts) {
            if (contact.contains(searchTerm)) {
                System.out.println(contact);
                foundContact = true;
            }
        }

        if (!foundContact) {
            System.out.println("No contacts found.");
        }
    }

    private static void deleteContact(Scanner input) {
        System.out.println("Please enter the name of the contact to delete:");
        String contactName = input.nextLine();

        List<String> contactsToDelete = new ArrayList<>();
        boolean foundContact = false;

        for (String contact : contacts) {
            if (contact.contains(contactName)) {
                contactsToDelete.add(contact);
                System.out.println("Contact found: " + contact);
                foundContact = false;
            }
        }

        if (foundContact) {
            contacts.removeAll(contactsToDelete);
            System.out.println("Contacts deleted: " + contactsToDelete);
            saveContactsToFile();
        } else {
            System.out.println("Contact not found.");
        }
    }


    private static void readContactsFromFile() {
        try {
            File file = new File(FILENAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                contacts.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the contacts file: " + e.getMessage());
        }
    }

    private static void saveContactsToFile() {
        try {
            FileWriter writer = new FileWriter(FILENAME);
            for (String contact : contacts) {
                writer.write(contact + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the contacts file: " + e.getMessage());
        }
    }
}