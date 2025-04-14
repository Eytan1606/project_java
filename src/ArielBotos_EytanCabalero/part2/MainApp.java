package ArielBotos_EytanCabalero.part2;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter college name: ");
        String collegeName = sc.nextLine();

        College college = new College(collegeName);
        MenuManager menu = new MenuManager(college);
        menu.displayMenu();
    }
}