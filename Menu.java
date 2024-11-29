package de.mehrzweckmandala.tommel;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public String showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1: Create a new table");
        System.out.println("2: Add key-value pairs");
        System.out.println("3: Add an array of tables");
        System.out.println("4: Add an array");
        System.out.println("5: Add a multiline string");
        System.out.println("6: Add a comment");
        System.out.println("7: Review current configuration");
        System.out.println("8: Export configuration to TOML");
        System.out.println("9: Reset configuration");
        System.out.println("10: Exit");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public String promptInput(String message) {
        System.out.println(message);
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
