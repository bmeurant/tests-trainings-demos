package io.bmeurant.java16.features;

// A simple Record declaration
record Person(String name, int age) {
    // Optional: Compact constructor for validation or normalization
    // No parameters, it implicitly gets them from the record header
    public Person {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        // No explicit assignment needed, the record components are implicitly assigned
    }

    // Optional: Custom instance method
    public String greeting() {
        return "Hello, my name is " + name + " and I am " + age + " years old.";
    }

    // Optional: Static method
    public static Person unknownPerson() {
        return new Person("Unknown", 0);
    }
}

// A more complex Record
record Book(String title, String author, int publicationYear, double price) {
    // Custom canonical constructor (full explicit version)
    // This is rarely needed with records as the compact constructor is more common.
    // It's mainly for showing the full form if you have complex logic before super().
    public Book(String title, String author, int publicationYear, double price) {
        if (publicationYear < 0) {
            throw new IllegalArgumentException("Publication year cannot be negative.");
        }
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    // Additional custom method
    public String getFullDetails() {
        return String.format("%s by %s, published in %d, priced at %.2f", title, author, publicationYear, price);
    }
}

public class Records {

    public static void main(String[] args) {

        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        Person person3 = new Person("Alice", 30); // Same data as person1

        System.out.println("\n+++ Example 1: Accessing components using generated accessor methods +++\n");
        System.out.println("Person 1 Name: " + person1.name());
        System.out.println("Person 1 Age: " + person1.age());

        System.out.println("\n+++ Example 2: Generated toString(), equals(), and hashCode() methods +++\n");
        System.out.println("Person 1 toString(): " + person1); // Output: Person[name=Alice, age=30]
        System.out.println("person1.equals(person2): " + person1.equals(person2)); // Expected: false
        System.out.println("person1.equals(person3): " + person1.equals(person3)); // Expected: true
        System.out.println("person1.hashCode() == person3.hashCode(): " + (person1.hashCode() == person3.hashCode())); // Expected: true

        System.out.println("\n+++ Example 3: Using custom methods in Record +++\n");
        System.out.println(person1.greeting());
        System.out.println("Unknown person: " + Person.unknownPerson());

        System.out.println("\n+++ Example 4: Demonstrate validation in compact constructor +++\n");
        try {
            new Person("Invalid", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error for negative age: " + e.getMessage());
        }

        System.out.println("\n+++ Example 5: Book record example +++\n");
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, 25.99);
        System.out.println("Book 1: " + book1.getFullDetails());
        System.out.println("Book 1 title: " + book1.title());
    }
}
