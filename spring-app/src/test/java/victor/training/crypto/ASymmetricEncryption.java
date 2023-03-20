package victor.training.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;

public class ASymmetricEncryption {

    @Test
    public void encryptSomeShortTextWithRsa() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {

        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();
        Utils.printByteArray("private key", keyPair.getPrivate().getEncoded());
        Utils.printByteArray("public key", keyPair.getPublic().getEncoded());


        byte[] text = "The Lord of the Rings has been read by many people".getBytes();
        Utils.printText("plain text", text);

        //encrypt
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic()); // encrypt with my private
        byte[] encryptedText = cipher.doFinal(text);

        Utils.printByteArray("ciphertext", encryptedText);


        //decrypt
        Cipher recipientCipher = Cipher.getInstance("RSA");
        recipientCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate()); // decrypt with my public
        byte[] dencryptedText = recipientCipher.doFinal(encryptedText);

        Utils.printText("decoded text", dencryptedText);
    }
}
