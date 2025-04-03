import java.util.Scanner;

public class ces01 {
    public static void main(String[] args) {
        System.out.println("Hello java");
        int z =10;
        System.out.println(z);
        Scanner scanner =new Scanner(System.in);
        calculet(scanner);

    }
    private static void calculet(Scanner scanner){
        System.out.print("enter the first number (x):  ");
        int x =scanner.nextInt();
        System.out.print("enter the first number (x):  ");
        int y =scanner.nextInt();

        int result1= x + y;
        System.out.println(result1);
        int result2 = x -y;
        System.out.println(result2);
        int result3= x * y;
        System.out.println(result3);
        double result4= x / y;
        System.out.println(result4);


    }
    private static void newLIST(Scanner scanner){
        int sum[]={0};
        for (int i=0; i<=5; i++){
            int r =scanner.nextInt();


        }
    }

    }

