package ArielBotos_EytanCabalero2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the college name: ");
        String collegeName = scanner.nextLine();

        CollegeManager manager = new CollegeManager(collegeName);
        Menu menu = new Menu(manager, scanner);
        menu.start();
    }
}
