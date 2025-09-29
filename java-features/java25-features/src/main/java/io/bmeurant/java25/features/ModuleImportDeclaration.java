package io.bmeurant.java25.features;

import module java.base;

public class ModuleImportDeclaration {
    public static void main(String[] args) {
        // No need to import List or Arrays that are imported with java.base
        List<String> names = Arrays.asList("bob", "alice");
        System.out.println(names);
    }
}
