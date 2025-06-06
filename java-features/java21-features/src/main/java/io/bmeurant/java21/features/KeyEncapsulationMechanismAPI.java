package io.bmeurant.java21.features;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.NamedParameterSpec;

public class KeyEncapsulationMechanismAPI {
    public static void main(String[] args) {
        System.out.println("--- Java 21: Key Encapsulation Mechanism API (JEP 435) - GA ---");

        try {
            // 1. Instantiate a KEM based on a key agreement algorithm
            //    Here, we use "DHKEMwithAES256GCMandHKDFSHA256" as an example.
            //    Availability depends on your JCE providers.
            //    Note: Not all KEM algorithms are available by default. You might need
            //    to add a security provider (e.g., Bouncy Castle) for specific post-quantum KEMs.
            //    For this example, we'll use "XDH" as it's common for key agreement.
            String kemAlgorithm = "DHKEMwithAES256GCMandHKDFSHA256"; // Conceptual algorithm name
            KEM kem = null;
            try {
                kem = KEM.getInstance(kemAlgorithm);
                System.out.println("\nSuccessfully instantiated KEM: " + kem.getAlgorithm());
            } catch (NoSuchAlgorithmException e) {
                System.out.println("\nKEM algorithm '" + kemAlgorithm + "' not found. Falling back to a simpler example.");
                // Fallback to a common key agreement algorithm for demonstration if KEM is not available
                // This demonstrates the *concept* of key agreement, not necessarily a KEM algorithm directly.
                // For a true KEM demo, ensure your JDK or providers support a KEM algorithm.
                System.out.println("Try ensuring 'jdk.crypto.cryptoki' module is present or a provider like BouncyCastle is added.");
                System.out.println("This example proceeds conceptually without direct KEM instantiation.");
                return; // Exit if KEM cannot be instantiated
            }


            // 2. Generate a KeyPair for the recipient (e.g., using X25519)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("XDH"); // XDH for X25519/X448
            kpg.initialize(new NamedParameterSpec("X25519")); // Initialize for X25519 curve
            KeyPair recipientKeyPair = kpg.generateKeyPair();
            System.out.println("Recipient public key algorithm: " + recipientKeyPair.getPublic().getAlgorithm());
            System.out.println("Recipient public key format: " + recipientKeyPair.getPublic().getFormat());

            // 3. Create an encap/decap pair
            // These methods are available on the KEM instance.
            KEM.Encapsulator encapsulator = kem.newEncapsulator(recipientKeyPair.getPublic());
            KEM.Decapsulator decapsulator = kem.newDecapsulator(recipientKeyPair.getPrivate());

            // 4. Encapsulate the key (sender side)
            KEM.Encapsulated encapsulated = encapsulator.encapsulate();
            byte[] ciphertext = encapsulated.encapsulation(); // The encapsulated key (ciphertext)
            SecretKey senderSecret = encapsulated.key(); // The derived secret key for the sender

            System.out.println("\nKey Encapsulation performed:");
            System.out.println("Encapsulated key length: " + ciphertext.length + " bytes");
            System.out.println("Sender's derived secret key algorithm: " + senderSecret.getAlgorithm());

            // 5. Decapsulate the key (recipient side)
            SecretKey recipientSecret = decapsulator.decapsulate(ciphertext);

            System.out.println("\nKey Decapsulation performed:");
            System.out.println("Recipient's derived secret key algorithm: " + recipientSecret.getAlgorithm());

            // 6. Verify that both parties derived the same secret key
            if (senderSecret.equals(recipientSecret)) {
                System.out.println("\nSuccess: Sender and recipient derived the same secret key!");
            } else {
                System.err.println("\nError: Secret keys do NOT match!");
            }

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure your JCE provider supports the KEM algorithm and key pair algorithm.");
            System.err.println("Example KEM algorithm might not be available by default: DHKEMwithAES256GCMandHKDFSHA256, BC-PQC-DHKEM-X25519-AES256-GCM-HKDF-SHA256, etc.");
            System.err.println("Consider adding a JCE provider like BouncyCastle to your project/JVM.");
        } catch (DecapsulateException e) {
            throw new RuntimeException(e);
        }
    }
}
