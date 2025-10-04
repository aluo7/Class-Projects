import java.util.Scanner;

public class assignment1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfInst = scanner.nextInt();
        scanner.nextLine();

        String inputs[] = new String[numOfInst];
        for(int i = 0; i < numOfInst; i++) {
            inputs[i] = scanner.nextLine();
        }

        for(int i = 0; i < inputs.length; i++) {
            System.out.println("Hello, " + inputs[i] + "!");
        }
        scanner.close();
    }
}