package de.mehrzweckmandala.tommel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TOMLBuilder {

    private final StringBuilder output;

    public TOMLBuilder() {
        this.output = new StringBuilder();
    }

    private final Set<String> keysInCurrentTable = new HashSet<>();

    public void addKeyValue(String key, String value, String type) {
        if (keysInCurrentTable.contains(key)) {
            throw new IllegalArgumentException("Duplicate key: " + key + " already exists in the current table.");
        }
        keysInCurrentTable.add(key);
        String formattedValue = validateAndFormatValue(value, type);
        output.append(key)
                .append(TOML_KEYS.EQUAL.asString())
                .append(formattedValue)
                .append(TOML_KEYS.NEWLINE.asString());
    }

    public void addTable(String tableName) {
        keysInCurrentTable.clear();
        output.append(TOML_KEYS.wrapInBrackets(tableName))
                .append(TOML_KEYS.NEWLINE.asString());
    }


    public void addArrayOfTables(String arrayName) {
        output.append("[[").append(arrayName).append("]]")
                .append(TOML_KEYS.NEWLINE.asString());
    }

    public void addArray(String key, String[] elements) {
        String array = "[" + String.join(", ", wrapElements(elements)) + "]";
        output.append(key)
                .append(TOML_KEYS.EQUAL.asString())
                .append(array)
                .append(TOML_KEYS.NEWLINE.asString());
    }

    private String[] wrapElements(String[] elements) {
        return Arrays.stream(elements)
                .map(e -> e.matches("\\d+") ? e : TOML_KEYS.wrapInQuotes(e))
                .toArray(String[]::new);
    }


    public void addMultilineString(String content) {
        output.append("\"\"\"")
                .append(content)
                .append("\"\"\"")
                .append(TOML_KEYS.NEWLINE.asString());
    }

    public void addComment(String comment) {
        output.append(TOML_KEYS.COMMENT.asString())
                .append(" ")
                .append(comment)
                .append(TOML_KEYS.NEWLINE.asString());
    }

    public String getConfiguration() {
        return output.toString();
    }

    public void reset() {
        output.setLength(0);
    }

    private String validateAndFormatValue(String value, String type) {
        return switch (type.toLowerCase()) {
            case "string" -> TOML_KEYS.wrapInQuotes(value);
            case "integer" -> {
                if (!value.matches("\\d+")) throw new IllegalArgumentException("Invalid integer.");
                yield value;
            }
            case "float" -> {
                if (!value.matches("\\d+\\.\\d+")) throw new IllegalArgumentException("Invalid float.");
                yield value;
            }
            case "boolean" -> {
                if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false"))
                    throw new IllegalArgumentException("Invalid boolean.");
                yield value.toLowerCase();
            }
            case "datetime" -> {
                if (!value.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z"))
                    throw new IllegalArgumentException("Invalid datetime format.");
                yield value;
            }
            default -> throw new IllegalArgumentException("Unknown type.");
        };
    }
}
