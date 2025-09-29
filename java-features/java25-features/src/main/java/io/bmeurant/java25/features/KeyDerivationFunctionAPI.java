package io.bmeurant.java25.features;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class KeyDerivationFunctionAPI {

    public static void main(String[] args) {
        char[] password = "myVerySecurePassword".toCharArray();
        byte[] salt = "SomeRandomSaltBytes".getBytes();
        int iterations = 65536; // Recommended iteration count
        int keyLength = 256;    // Key length in bits

        try {
            // 1. Specify the KDF algorithm name. This is what JEP 510 standardizes.
            String algorithmName = "PBKDF2WithHmacSHA256";

            // 2. Obtain an instance of the SecretKeyFactory for the KDF
            // This is the correct, existing class used for KDFs in the Java Security API.
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithmName);

            // 3. Define the key/password specification
            KeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);

            // 4. Derive the key (the Hashed output)
            SecretKey derivedKey = factory.generateSecret(spec);

            // Output
            System.out.println("KDF Algorithm Used: " + derivedKey.getAlgorithm());
            System.out.println("Derived Key Length (bytes): " + derivedKey.getEncoded().length);
            System.out.println("Success: Key derived successfully.");

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algorithm not found. " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("Error: Invalid key spec. " + e.getMessage());
        } finally {
            // CRITICAL: Clear the password from memory
            Arrays.fill(password, ' ');
        }
    }
}
