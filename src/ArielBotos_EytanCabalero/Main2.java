package ArielBotos_EytanCabalero;

import java.util.Scanner;

public class Main2 {
    private final int MULT = 2;
    private String collegeName;
    private String[] lecturers = new String[2];
    private int lecturerCount = 0;
    private String[] committees = new String[2];
    private int committeeCount = 0;
    private String[] departments = new String[2];
    private int departmentCount = 0;


    public void run() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Enter the college name: ");
        collegeName = userInput.nextLine();

        int choice;
        do {
            displayMenu();
            choice = getValidChoice(userInput);
            handleMenuChoice(userInput, choice);
        } while (choice != 0);

        userInput.close();
    }

    private void displayMenu() {
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

    private int getValidChoice(Scanner userInput) {
        while (true) {
            String inputLine = userInput.nextLine().trim();
            if (inputLine.length() == 1 && inputLine.charAt(0) >= '0' && inputLine.charAt(0) <= '8') {
                return inputLine.charAt(0) - '0';
            }
            System.out.print("Invalid choice. Please enter a number between 0 and 8: ");
        }
    }

    private void handleMenuChoice(Scanner userInput, int choice) {
        switch (choice) {
            case 1 -> addItem(userInput, "lecturer");
            case 2 -> addItem(userInput, "committee");
            case 3 -> addItem(userInput, "department");
            case 4 -> assignLecturerToCommittee(userInput);
            case 5, 6 -> System.out.println("This feature is not available.");
            case 7 -> displayItems("lecturers", lecturers, lecturerCount);
            case 8 -> displayItems("committees", committees, committeeCount);
            case 0 -> System.out.println("Exiting the program...");
        }
    }

    private void addItem(Scanner userInput, String type) {
        System.out.print("Enter " + type + " name (or type 'exit' to cancel): ");
        String name = userInput.nextLine().trim();

        if (name.equalsIgnoreCase("exit")) {
            System.out.println("Operation cancelled.");
            return;
        }

        String[] array = getArrayByType(type);
        int count = getCountByType(type);

        if (nameExistsInArray(name, array, count)) {
            System.out.println(capitalize(type) + " already exists. Choose another name.");
            return;
        }

        if (count == array.length) {
            array = expandArray(array);
            updateArrayByType(type, array);
        }

        array[count] = name.toLowerCase();
        incrementCountByType(type);
        System.out.println(capitalize(type) + " added successfully.");
    }

    private void assignLecturerToCommittee(Scanner userInput) {
        System.out.print("Enter lecturer name: ");
        String lecturer = userInput.nextLine();

        System.out.print("Enter committee name: ");
        String committee = userInput.nextLine();

        if (!nameExistsInArray(lecturer, lecturers, lecturerCount)) {
            System.out.println("This lecturer does not exist in the system.");
        } else if (!nameExistsInArray(committee, committees, committeeCount)) {
            System.out.println("This committee does not exist in the system.");
        } else {
            System.out.println("Lecturer assigned to committee recorded.");
        }
    }

    private void displayItems(String title, String[] array, int count) {
        System.out.println("List of " + title + ":");
        for (int i = 0; i < count; i++) {
            System.out.println("- " + capitalize(array[i]));
        }
    }

    private boolean nameExistsInArray(String name, String[] array, int count) {
        for (int i = 0; i < count; i++) {
            if (array[i] != null && array[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private String[] expandArray(String[] array) {
        String[] newArray = new String[array.length * MULT];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private String[] getArrayByType(String type) {
        return switch (type) {
            case "lecturer" -> lecturers;
            case "committee" -> committees;
            case "department" -> departments;
            default -> new String[0];
        };
    }

    private int getCountByType(String type) {
        return switch (type) {
            case "lecturer" -> lecturerCount;
            case "committee" -> committeeCount;
            case "department" -> departmentCount;
            default -> 0;
        };
    }

    private void updateArrayByType(String type, String[] newArray) {
        switch (type) {
            case "lecturer" -> lecturers = newArray;
            case "committee" -> committees = newArray;
            case "department" -> departments = newArray;
        }
    }

    private void incrementCountByType(String type) {
        switch (type) {
            case "lecturer" -> lecturerCount++;
            case "committee" -> committeeCount++;
            case "department" -> departmentCount++;
        }
    }

    private String capitalize(String text) {
        return text == null || text.isEmpty() ? text : text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    public static void main(String[] args) {
        new Main2().run();
    }
}





