package io.bmeurant.java15.features;

public class TextBlocks {

    public static void main(String[] args) {

        System.out.println("\n+++ Example 1: Multi-line HTML string +++\n");
        String html = """
                <html>
                    <body>
                        <p>Hello, world</p>
                    </body>
                </html>
                """;
        System.out.println("HTML (Text Block):\n" + html);

        System.out.println("+++ Example 2: JSON string +++\n");
        String json = """
                {
                  "name": "Alice",
                  "age": 30
                }
                """;
        System.out.println("JSON (Text Block):\n" + json);

        System.out.println("+++ Example 3: SQL query +++\n");
        String sql = """
                SELECT id, name, email
                FROM users
                WHERE age > 25 AND city = 'Paris';
                """;
        System.out.println("SQL (Text Block):\n" + sql);

        System.out.println("+++ Example 4: Escaping characters +++\n");
        // Escaping characters (less frequent but possible)
        String escapedTextBlock = """
                Line 1\nLine 2\twith a tab.
                "Quoted text".
                """;
        System.out.println("Text Block with escapes:\n" + escapedTextBlock);

        System.out.println("+++ Example 5: New escape sequences introduced in Java 15 +++\n");
        // New escape sequences introduced in Java 15 for specific control
        // - \s:  single space
        // - \<line-terminator>:  suppress the implicit new line
        String formattedText = """
                This text is \
                formatted on a single line.
                """;
        System.out.println("Formatted text (single line using \\ at end of line):\n" + formattedText);

        String textWithTrailingSpaces = """
                Line with trailing spaces:     \s
                Next line starts here.
                """;
        System.out.println("Text with explicit trailing spaces (using \\s):\n" + textWithTrailingSpaces);
    }
}
