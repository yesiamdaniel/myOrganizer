package ui;

public class Main {

    private static boolean initialized = false;

    public static void main(String[]args) {
        initialize();
        confirmInitialization();
    }

    private static void initialize() {
        System.out.println("Initializing...");
        initialized = true;
    }

    private static void confirmInitialization() {
        if (initialized) {
            System.out.println("Successfully initialized!");
        }
    }
}
