
package project2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.*;

public class Users {
    
    private static  HashMap<String, String> pwMap = new HashMap<>();
    private static HashMap<String, String> saltMap = new HashMap<>();

    // Method to add hexadecimal salt and salted pw hash to hashmaps
    public static void createAccount(String userName, String pw) throws NoSuchAlgorithmException {

        byte[] newSalt = getSalt();
        byte[] newHash = getSaltedHash(pw, newSalt);
        pwMap.put(userName, convertToHex(newHash));
        saltMap.put(userName, convertToHex(newSalt));
    }

    public static boolean checkUser(String username) {
        return pwMap.containsKey(username);
    }

    // User sign in - if stored salted pw hash matches the hash of pw attempt and stored salt then success
    public static boolean signIn(String userName, String pw) throws NoSuchAlgorithmException {

        String salt = saltMap.get(userName);
        byte[] byteSalt = convertFromHex(salt);
        String saltedPwHash = convertToHex(getSaltedHash(pw, byteSalt));

        if (saltedPwHash.equals(pwMap.get(userName))) {
            return true;          
        } else {
            return false;
        }
    }

    // Generate byte array of random salt number
    public static byte[] getSalt() {

        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return salt;
    }

    // Use SHA-256 algorithm to create a hash of password string and salt byte array
    public static byte[] getSaltedHash(String password, byte[] salt) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashBytes = md.digest(password.getBytes());
        md.reset();

        return hashBytes;
    }

    // Conversion of byte array to hexadecimal number
    public static String convertToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    // Conversion of hexadecimal number to byte array
    public static byte[] convertFromHex(String hex) {

        byte[] hexBytes = new byte[hex.length() / 2];

        for (int i = 0; i < hexBytes.length; i++) {
            hexBytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return hexBytes;
    }
}
