package de.mehrzweckmandala.tommel;

public class EventHandler {

    private final Menu menu;
    private final TOMLBuilder tomlBuilder;
    private final FileExporter fileExporter;

    public EventHandler() {
        this.menu = new Menu();
        this.tomlBuilder = new TOMLBuilder();
        this.fileExporter = new FileExporter();
    }

    public void start() {
        while (true) {
            String option = menu.showMenu();
            switch (option) {
                case "1" -> tomlBuilder.addTable(menu.promptInput("Enter table name (e.g., parent.child):"));
                case "2" -> handleKeyValue();
                case "3" -> tomlBuilder.addArrayOfTables(menu.promptInput("Enter array of tables name:"));
                case "4" -> handleArray();
                case "5" -> tomlBuilder.addMultilineString(menu.promptInput("Enter multiline string content:"));
                case "6" -> tomlBuilder.addComment(menu.promptInput("Enter comment:"));
                case "7" -> reviewConfiguration();
                case "8" -> exportConfiguration();
                case "9" -> resetConfiguration();
                case "10" -> exit();
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleKeyValue() {
        String key = menu.promptInput("Enter key:");
        String value = menu.promptInput("Enter value:");
        String type = menu.promptInput("Enter value type (string, integer, float, boolean, datetime):");
        try {
            tomlBuilder.addKeyValue(key, value, type);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleArray() {
        String key = menu.promptInput("Enter array name:");
        String elements = menu.promptInput("Enter array elements separated by commas:");
        tomlBuilder.addArray(key, elements.split(","));
    }

    private void reviewConfiguration() {
        String config = tomlBuilder.getConfiguration();
        if (config.isEmpty()) {
            System.out.println("Configuration is empty.");
        } else {
            System.out.println("Current Configuration:\n" + config);
        }
    }

    private void exportConfiguration() {
        String config = tomlBuilder.getConfiguration();
        if (config.isEmpty()) {
            System.out.println("Nothing to export. Configuration is empty.");
        } else {
            fileExporter.exportToFile(config, "config.toml");
        }
    }

    private void resetConfiguration() {
        tomlBuilder.reset();
        System.out.println("Configuration has been reset.");
    }

    private void exit() {
        menu.close();
        System.out.println("Exiting...");
        System.exit(0);
    }
}
