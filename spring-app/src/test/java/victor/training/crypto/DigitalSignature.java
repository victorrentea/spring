package victor.training.crypto;

import org.junit.jupiter.api.Test;

import java.security.*;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalSignature {

    @Test
    public void testAsymmetricSigningWithSignatureClasses() throws GeneralSecurityException {
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(1024);
        KeyPair keyPair = kpGen.generateKeyPair();
        Utils.printByteArray("private key", keyPair.getPrivate().getEncoded());
        Utils.printByteArray("public key", keyPair.getPublic().getEncoded());

        String data = "My Company is the best!!!";

        Signature signatureAlgorithm = Signature.getInstance("SHA256WithRSA");
        // TODO initSign(privateK), update, sign
        signatureAlgorithm.initSign(keyPair.getPrivate());
        signatureAlgorithm.update(data.getBytes());
        byte[] signature = signatureAlgorithm.sign();

        Utils.printByteArray("signature", signature);


        // verification on the other end of the channel ----
        String receivedData = "My Company is the best!!!";
//        String receivedData = "My Company is the worst!!!";

        Signature verificationAlgorithm = Signature.getInstance("SHA256WithRSA");
        // TODO initVerify(publicK), update, verify
        verificationAlgorithm.initVerify(keyPair.getPublic());
        verificationAlgorithm.update(receivedData.getBytes());

        boolean matches = verificationAlgorithm.verify(signature);

        assertThat(matches).as("Signature Matches").isTrue();
    }

}
