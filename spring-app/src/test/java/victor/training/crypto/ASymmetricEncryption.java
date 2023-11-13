package victor.training.crypto;

import org.junit.jupiter.api.Test;

import jakarta.crypto.BadPaddingException;
import jakarta.crypto.Cipher;
import jakarta.crypto.IllegalBlockSizeException;
import jakarta.crypto.NoSuchPaddingException;
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
        // TODO .init(encrypt,private), .doFinal(
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] encryptedText = cipher.doFinal(text);

        Utils.printByteArray("ciphertext", encryptedText);


        //decrypt
        // TODO .init(dencrypt,public), .doFinal(
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
        byte[] plainText = cipher.doFinal(encryptedText);

        Utils.printText("decoded text", plainText);
    }
}
