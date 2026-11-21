package firstProd.dev.console;

import firstProd.dev.exception.ConsoleException;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);


    public String readText() {
        try {
            return scanner.nextLine().trim();
        }catch (NoSuchElementException e) {
            throw new ConsoleException("EOF reached");
        }
    }

    public int readInt() {
        int number = 0;

        while (true) {
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                break;


            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Please enter a number");

            } catch (NoSuchElementException e1) {
                throw new ConsoleException("EOF reached");
            }
        }
        return number;
    }
}



