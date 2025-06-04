package io.bmeurant.java18.features;

public class FinalizersDeprecated {
    static class ResourceHolder {
        private String name;

        public ResourceHolder(String name) {
            this.name = name;
            System.out.println("ResourceHolder '" + name + "' created.");
        }

        // This finalize method is now deprecated for removal in Java 18+
        // You will see a compile-time warning (e.g., in IntelliJ or command line)
        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizing ResourceHolder '" + name + "'...");
            // In a real scenario, this would clean up native resources, close files, etc.
            // But it's unreliable and should be avoided.
            super.finalize();
        }

        public void doSomething() {
            System.out.println("ResourceHolder '" + name + "' is doing something.");
        }
    }

    public static void main(String[] args) {
        createAndDiscardResource();

        // Suggesting garbage collection - not guaranteed to run finalize immediately
        System.out.println("\nSuggesting garbage collection...");
        System.gc(); // Request GC
        // Give a moment for GC to potentially run finalizers (still not guaranteed)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Main method finished. Observe compilation warning about finalize().");
        System.out.println("Modern alternatives like try-with-resources or Cleaner API should be used.");
    }

    private static void createAndDiscardResource() {
        ResourceHolder r1 = new ResourceHolder("R1");
        r1.doSomething();
        // r1 is now out of scope and eligible for garbage collection
        System.out.println("ResourceHolder 'R1' is now out of scope.");
    }
}
