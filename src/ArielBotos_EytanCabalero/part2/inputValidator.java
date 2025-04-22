package ArielBotos_EytanCabalero.part2;

import java.util.Scanner;

class inputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readNonEmptyLine(String prompt) { // בדיקה האם השורה ריקה
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String readValidDegree(String prompt) { //בדיקה האם הקלט לא תקין(איזה מחרוזת) וגם אם בדיקה של המספר
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
             input =input.toUpperCase();
            if (!input.equals("BA") && !input.equals("MA") && !input.equals("DR") && !input.equals("PROF")) {
                System.out.println("Invalid degree title. Allowed: BA, MA, Dr, Prof. Please try again.");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    public static int readPositiveInt(String prompt) {
        int value = -1;
        while (value < 0) {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. " + prompt);
                scanner.next();
            }
            value = scanner.nextInt();
            scanner.nextLine();
            if (value < 0) {
                System.out.println("Value must be non-negative. Please try again.");
            }
        }
        return value;
    }

    public static double readPositiveDouble(String prompt) {
        double value = -1;
        while (value < 0) {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.print("Invalid input. " + prompt);
                scanner.next();
            }
            value = scanner.nextDouble();
            scanner.nextLine();
            if (value < 0) {
                System.out.println("Value must be non-negative. Please try again.");
            }
        }
        return value;
    }

}
