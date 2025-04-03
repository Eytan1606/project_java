package ArielBotos_EytanCabalero;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    // ArielBotos_EytanCabalero

    public final static int MULT = 2;

    private static String collegeName;

    private static String[] lecturers = new String[2];
    private static int lecturerCount = 0;

    private static String[] committees = new String[2];
    private static int committeeCount = 0;

    private static String[] departments = new String[2];
    private static int departmentCount = 0;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Enter the college name: ");
        collegeName = userInput.nextLine();

        int choice;

        do {
            displayMenu();
            choice = getValidChoice(userInput);
            userInput.nextLine();
            handleMenuChoice(userInput, choice);
        } while (choice != 0);

        userInput.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("0 - Exit");
        System.out.println("1 - Add Lecturer");
        System.out.println("2 - Add Committee");
        System.out.println("3 - Add Department");
        System.out.println("4 - Assign Lecturer to Committee");
        System.out.println("5 - Display average salaries of all lecturers");
        System.out.println("6 - Display average salaries by department");
        System.out.println("7 - Display all lecturers");
        System.out.println("8 - Display all committees");
        System.out.print("Choose an option: ");
    }

    private static int getValidChoice(Scanner userInput) {
        while (!userInput.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number:");
            userInput.nextLine();
        }
        return userInput.nextInt();
    }

    private static void handleMenuChoice(Scanner userInput, int choice) {
        switch (choice) {
            case 1 -> addItem(userInput, "lecturer");
            case 2 -> addItem(userInput, "committee");
            case 3 -> addItem(userInput, "department");
            case 4 -> assignLecturerToCommittee(userInput);
            case 5, 6 -> System.out.println("This feature is not available.");
            case 7 -> displayItems("Lecturers", lecturers, lecturerCount);
            case 8 -> displayItems("Committees", committees, committeeCount);
            case 0 -> System.out.println("Exiting the program...");
            default -> System.out.println("Invalid choice, try again.");
        }
    }

    private static void addItem(Scanner userInput, String type) {
        String name;
        do {
            System.out.print("Enter " + type + " name (or type 'exit' to cancel): ");
            name = userInput.nextLine().trim();

            if (name.equalsIgnoreCase("exit")) {
                System.out.println("Operation cancelled.");
                return;
            }

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Try again.");
                continue;
            }

            String[] array = getArrayByType(type);
            int count = getCountByType(type);

            if (nameExistsInArray(name, array, count)) {
                System.out.println(capitalize(type) + " already exists. Choose another name.");
            } else {
                array = addToArray(array, count, name);
                updateArrayByType(type, array);
                incrementCountByType(type);
                System.out.println(capitalize(type) + " added successfully.");
                break;
            }
        } while (true);
    }

    private static void assignLecturerToCommittee(Scanner userInput) {
        System.out.print("Enter lecturer name: ");
        String lecturer = userInput.nextLine();

        System.out.print("Enter committee name: ");
        String committee = userInput.nextLine();

        boolean lecturerExists = nameExistsInArray(lecturer, lecturers, lecturerCount);
        boolean committeeExists = nameExistsInArray(committee, committees, committeeCount);

        if (!lecturerExists) {
            System.out.println("This lecturer or committee does not exist in the system.");
        } else if (!committeeExists) {
            System.out.println("This committee does not exist in the system.");
        } else {
            System.out.println("Lecturer assigned to committee recorded.");
        }
    }

    private static void displayItems(String title, String[] array, int count) {
        System.out.println("List of " + title.toLowerCase() + ":");
        for (int i = 0; i < count; i++) {
            System.out.println("- " + array[i]);
        }
    }

    private static boolean nameExistsInArray(String name, String[] array, int count) {
        for (int i = 0; i < count; i++) {
            if (array[i] != null && array[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static String[] addToArray(String[] array, int count, String name) {
        array = expandArray(array, count);
        array[count] = name;
        return array;
    }

    private static String[] expandArray(String[] array, int count) {
        if (count == array.length) {
            array = Arrays.copyOf(array, array.length * MULT);
        }
        return array;
    }

    private static String[] getArrayByType(String type) {
        return switch (type) {
            case "lecturer" -> lecturers;
            case "committee" -> committees;
            case "department" -> departments;
            default -> new String[0];
        };
    }

    private static int getCountByType(String type) {
        return switch (type) {
            case "lecturer" -> lecturerCount;
            case "committee" -> committeeCount;
            case "department" -> departmentCount;
            default -> 0;
        };
    }

    private static void updateArrayByType(String type, String[] newArray) {
        switch (type) {
            case "lecturer" -> lecturers = newArray;
            case "committee" -> committees = newArray;
            case "department" -> departments = newArray;
        }
    }

    private static void incrementCountByType(String type) {
        switch (type) {
            case "lecturer" -> lecturerCount++;
            case "committee" -> committeeCount++;
            case "department" -> departmentCount++;
        }
    }

    private static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}