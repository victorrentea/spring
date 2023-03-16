package victor.training.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SymmetricEncryption {

    @Test
    public void testSymmetricEncryption() throws GeneralSecurityException {
        // generate a symmetric encryption key
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(192); // allowed for AES
        Key key = generator.generateKey();
        Utils.printByteArray("key", key.getEncoded());

        // get a random Initialization Vector (IV) for the block symmetric encryption
        byte[] iv = generateRandomBytes(16);
        Utils.printByteArray("ivSpec", iv);

        //input
        byte[] input = "SORSIX ROCKS!".getBytes();
        Utils.printText("input", input);

        // encryption
        Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encrypt.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedOutput = encrypt.doFinal(input);
        Utils.printByteArray("encrypted output", encryptedOutput);

        // decryption
        // TODO
        Cipher decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decrypt.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decryptedOutput = decrypt.doFinal(encryptedOutput);
        Utils.printText("decrypted input", decryptedOutput);
    }

    private static byte[] generateRandomBytes(int size) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[size];
        secureRandom.nextBytes(random);
        return random;
    }
}
