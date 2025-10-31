package io.bmeurant.java.functional;

import java.util.function.IntUnaryOperator;

import static java.lang.IO.println;

/**
 * Demonstrates Functional Composition using IntUnaryOperator's andThen and compose methods.
 */
public class FunctionalCompositionDemo {

    void main() {
        // Function 1: Multiplies the input by 2
        IntUnaryOperator multiplyByTwo = x -> x * 2;

        // Function 2: Subtracts 1 from the input
        IntUnaryOperator subtractOne = x -> x - 1;

        // 1. Composition using andThen()
        // Applies 'multiplyByTwo' first, THEN 'subtractOne'
        // f(g(x)) where f is subtractOne and g is multiplyByTwo
        IntUnaryOperator multiplyThenSubtract =
                multiplyByTwo.andThen(subtractOne);

        int resultAndThen = multiplyThenSubtract.applyAsInt(5); // (5 * 2) - 1 = 9
        println("Result with andThen (x*2 then -1): " + resultAndThen);

        // 2. Composition using compose()
        // Applies 'subtractOne' first, THEN 'multiplyByTwo'
        // g(f(x)) where g is multiplyByTwo and f is subtractOne
        IntUnaryOperator subtractThenMultiply =
                multiplyByTwo.compose(subtractOne);

        int resultCompose = subtractThenMultiply.applyAsInt(5); // (5 - 1) * 2 = 8
        println("Result with compose (-1 then x*2): " + resultCompose);
    }
}
