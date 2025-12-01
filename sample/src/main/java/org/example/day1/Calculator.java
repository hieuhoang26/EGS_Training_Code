package org.example.day1;

import java.util.Scanner;

public class Calculator {
    public static void main(StringEx[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;

        while (continueCalculating) {
            try {
                System.out.print("\nEnter first number: ");
                double firstNumber = scanner.nextDouble();

                System.out.print("Enter second number: ");
                double secondNumber = scanner.nextDouble();

                // Display menu
                System.out.println("\nChoose operation:");
                System.out.println("+ : Addition");
                System.out.println("- : Subtraction");
                System.out.println("* : Multiplication");
                System.out.println("/ : Division");
                System.out.println("% : Modulus");
                System.out.print("Enter your choice: ");

                scanner.nextLine();
                String operation = scanner.nextLine();

                performCalculation(firstNumber, secondNumber, operation);

                System.out.print("\nDo you want to continue? (y/n): ");
                String choice = scanner.nextLine();

                if (!choice.equalsIgnoreCase("y")) {
                    continueCalculating = false;
                    System.out.println("Thank you for using the calculator!");
                }

            } catch (Exception e) {
                System.out.println("Error: Please enter valid numbers!");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    public static void performCalculation(double a, double b, String operation) {
        double result;
        boolean valid = true;

        switch (operation) {
            case "+":
                result = a + b;
                System.out.println("\nResult: " + a + " + " + b + " = " + result);
                break;

            case "-":
                result = a - b;
                System.out.println("\nResult: " + a + " - " + b + " = " + result);
                break;

            case "*":
                result = a * b;
                System.out.println("\nResult: " + a + " * " + b + " = " + result);
                break;

            case "/":
                if (b != 0) {
                    result = a / b;
                    System.out.println("\nResult: " + a + " / " + b + " = " + result);
                } else {
                    System.out.println("\nError: Cannot divide by zero!");
                    valid = false;
                }
                break;

            case "%":
                if (b != 0) {
                    result = a % b;
                    System.out.println("\nResult: " + a + " % " + b + " = " + result);
                } else {
                    System.out.println("\nError: Cannot divide by zero!");
                    valid = false;
                }
                break;

            default:
                System.out.println("\nError: Invalid operation '" + operation + "'!");
                System.out.println("Valid operations: +, -, *, /, %");
                valid = false;
                break;
        }
    }
}
