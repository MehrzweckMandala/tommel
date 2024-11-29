package de.mehrzweckmandala.tommel;

public enum TOML_KEYS {
    BRACKET_START(0x5b), // [
    BRACKET_END(0x5d),   // ]
    NEWLINE(0x0A),       // \n
    SPACE(0x20),         //
    STRING(0x22),        // "
    COMMENT(0x23),       // #
    CURLY_START(0x7b),   // {
    CURLY_END(0x7d),     // }
    COMMA(0x2c),         // ,
    EQUAL(0x3d);         // =

    private final int value;

    TOML_KEYS(int value) {
        this.value = value;
    }

    public char asChar() {
        return (char) value;
    }

    public String asString() {
        return Character.toString((char) value);
    }

    public static String wrapInBrackets(String content) {
        return BRACKET_START.asString() + content + BRACKET_END.asString();
    }

    public static String wrapInQuotes(String content) {
        return STRING.asString() + content + STRING.asString();
    }

    public static String wrapInCurlyBraces(String content) {
        return CURLY_START.asString() + content + CURLY_END.asString();
    }

    @Override
    public String toString() {
        return name() + " (" + asChar() + ")";
    }
}
