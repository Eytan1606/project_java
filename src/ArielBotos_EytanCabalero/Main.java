// Ariel Botos 206205254
// Eytan Cabalero 214180036
package ArielBotos_EytanCabalero;
import java.util.Scanner;

import static ArielBotos_EytanCabalero.College.readNonEmpty;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String collegeName = readNonEmpty("Enter college name: ", "College name");
        College loaded = Persistence.load(collegeName);
        College college = (loaded != null)
                ? loaded
                : new College(collegeName);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Saving data...");
            Persistence.save(college);
        }));

        while (true) {
            College.mainMenu();
            int choice = College.readInt("\nChoose an option (0–15): ", 0, 15);
            System.out.println();
            try {
                switch (choice) {
                    case 0  -> { Persistence.save(college);
                                 System.out.println("Exiting. Bye!");
                                 System.exit(0); }
                    case 1  -> College.flowAddLecturer(college);
                    case 2  -> College.flowAddDepartment(college);
                    case 3  -> College.flowAddCommittee(college);
                    case 4  -> College.flowRemoveCommittee(college);
                    case 5  -> College.flowAddLecturerToDepartment(college);
                    case 6  -> College.flowAddLecturerToCommittee(college);
                    case 7  -> College.flowRemoveLecturer(college);
                    case 8  -> College.flowRemoveCommitteeMember(college);
                    case 9  -> System.out.printf("Average salary: ₪%.2f%n", college.getAverageSalaryAllLecturers());
                    case 10 -> College.flowAverageByDepartment(college);
                    case 11 -> College.flowListAll(college.getLecturers());
                    case 12 -> College.flowListAll(college.getCommittees());
                    case 13 -> College.flowCompareCommitteesNew(college);
                    case 14 -> College.flowCompareProfessorsByArticles(college);
                    case 15 -> College.flowCloneCommittee(college);
                    default -> System.out.println("⚠ Option not implemented.");
                }
            }  catch (ValidationException |
                      DuplicateEntityException |
                      EntityNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println();
        }
    }
}
