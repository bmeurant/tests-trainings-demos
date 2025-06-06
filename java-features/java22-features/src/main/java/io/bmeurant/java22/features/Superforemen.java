package io.bmeurant.java22.features;

public class Superforemen {
    class Outer {
        int outerField = 10;

        void outerMethod() {
            System.out.println("Outer.outerMethod called.");
        }

        class Inner extends SuperInner {
            int innerField = 20;

            Inner() {
                // Calling constructor of SuperInner, which is its direct superclass.
                // This is standard.
                super();
                System.out.println("Inner constructor called.");
            }

            void printFields() {
                System.out.println("Inner Field: " + innerField);
                System.out.println("Outer Field (from Inner): " + Outer.this.outerField); // Access outer instance
                System.out.println("SuperInner Field: " + super.superInnerField); // Access direct superclass field
            }

            void callMethods() {
                super.superInnerMethod(); // Call method from direct superclass (SuperInner)

                // NEW WAY (Java 22 GA): Calling outer class's super method
                // If Outer class had a superclass (e.g., ParentOuter),
                // this would allow calling ParentOuter's method from Inner's context.
                // Let's simulate a scenario where 'Outer' extends 'ParentOuter'
                // For simplicity here, we'll demonstrate a direct qualified call to 'outerMethod' of 'Outer'.
                // The 'super' here would typically refer to a method of a superclass of 'Outer'.
                // As 'Outer' doesn't explicitly extend another class in this simple example,
                // we'll show how it would be used if it did.
                // Syntax: OuterClassName.super.methodName()
                // If Outer extended ParentOuter, and ParentOuter had 'someMethod()':
                // Outer.super.someMethod();
                // Since Outer doesn't extend, let's call outerMethod directly to show context.
                // The core of JEP 447 is about resolving ambiguity when multiple 'super' scopes exist.

                System.out.println("\nDemonstrating qualified 'super' context (conceptually for JEP 447):");
                // In a more complex scenario, if 'Outer' extended 'AnotherClass',
                // and 'AnotherClass' had a method 'doSomething()', then from 'Inner',
                // you could call 'Outer.super.doSomething()'.
                // For this simple example, we'll just refer to the outer instance method.
                // The JEP 447 is really about the *compiler* resolving the correct supertype.
                // This specific call will just execute the outer method:
                Outer.this.outerMethod();
                System.out.println("Called Outer.outerMethod via Outer.this (to show outer context).");
            }
        }
    }

    class SuperInner {
        int superInnerField = 30;

        void superInnerMethod() {
            System.out.println("SuperInner.superInnerMethod called.");
        }
    }

    public static void main(String[] args) {
        Superforemen demo = new Superforemen();
        Outer outer = demo.new Outer();
        Outer.Inner inner = outer.new Inner();

        inner.printFields();
        inner.callMethods();

        System.out.println("\nSuperforemen (Java 22) clarifies qualified 'super' calls in complex class hierarchies.");
        System.out.println("This feature is primarily for resolving ambiguities in nested inheritance scenarios.");
    }
}
