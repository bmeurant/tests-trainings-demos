package io.bmeurant.java23.features;

/// --- Java 23: Markdown Documentation Comments (JEP 467) ---
///
/// This `MarkdownDocDemo` class demonstrates the use of documentation comments
/// in **Markdown** format in Java 23.
///
/// Benefits include:
/// - Better readability in the source code.
/// - Simplicity of writing compared to HTML tags.
/// - Rich and standardized rendering by Javadoc tools.
///
/// ### Examples of Markdown tags:
///
/// * **Bold** : `**text**`
/// * *Italic* : `*text*`
/// * Lists:
/// - Item 1
/// - Item 2
///
/// ```java
/// // Example Java code block
/// public void exampleMethod(){
///     System.out.println("Hello, Javadoc Markdown!");
///}
///```
///
/// @author YourName
/// @version 1.0
/// @see <a href="https://openjdk.org/jeps/467">JEP 467: Markdown Documentation Comments</a>
public class MarkdownDocumentationComments {

    /// This method displays a greeting message.
    ///
    /// It illustrates a simple documentation comment with Markdown elements:
    ///
    /// 1. Use of an ordered list.
    /// 2. Highlighting text with **bold** and *italic*.
    /// 3. Including a link [to OpenJDK](https://openjdk.org).
    ///
    /// @param name The name of the person to greet.
    /// @return A string containing the greeting.
    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    public static void main(String[] args) {
        MarkdownDocumentationComments demo = new MarkdownDocumentationComments();
        System.out.println(demo.greet("World"));
        System.out.println("Check the generated Javadoc for this class to see the Markdown impact.");
    }
}