package io.bmeurant.java18.features;

/**
 * This class demonstrates the use of the new Javadoc {@code @snippet} tag.
 * The {@code @snippet} tag allows integrating code examples into the documentation
 * in a more reliable and verifiable way.
 *
 * <p>Here's a code example directly embedded in the Javadoc:</p>
 * {@snippet :
 * public class HelloWorld {
 * public static void main(String[] args) {
 * System.out.println("Hello, Javadoc Snippets!"); // @highlight substring="Hello"
 * }
 * }
 *}
 *
 * <p>It is also possible to include snippets from external files,
 * by specifying a file and a named region (e.g., {@code file:path/to/File.java region="myRegion"}).
 * This helps keep code examples up-to-date and compilable.</p>
 */
public class SnippetDoc {

    /**
     * This method returns a welcome message.
     *
     * <p>Call example:</p>
     * {@snippet :
     * SnippetDocDemo demo = new SnippetDocDemo();
     * String message = demo.getGreeting("Java 18"); // @highlight substring="getGreeting"
     * System.out.println(message);
     *}
     *
     * @param name The name to greet.
     * @return A greeting message.
     */
    public String getGreeting(String name) {
        return "Bonjour, " + name + " !";
    }

    public static void main(String[] args) {
        SnippetDoc demo = new SnippetDoc();
        System.out.println(demo.getGreeting("monde"));
        System.out.println("\nGo to javadoc_output/index.html to see the documentation with snippets.");
    }
}
