package victor.training.crypto;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class Hashing {

  @Test
  public void hashingDemo() throws NoSuchAlgorithmException {
    System.out.println("one way only: cannot infer the original message from the hash");
    hashText("The quick brown fox jumped over the lazy dog.");

    System.out.println("deterministic: same input => same hash");
    // TODO hash the same input and see the same output
    hashText("The quick brown fox jumped over the lazy dog.");

    System.out.println("psuedorandom");
    // TODO insert one typo in the same input, and observe a wildly different hash
    hashText("The quick brown fox jumped ower the lazy dog.");

    System.out.println("fixed length, no matter how large the input");
    // TODO hash an input twice as large, and observe hash has the same size.
    hashText("The quick brown fox jumped ower the lazy dog and a lot more stuff happened after that.");
  }

  private String hashText(String data) throws NoSuchAlgorithmException {
    System.out.println("Input: " + data);

    // TODO MessageDigest.getInstance SHA-256
    byte[] digest = null;

    Utils.printByteArray("Digest", digest);
    return new String(Base64.getEncoder().encode(digest));
  }

  @Test
  void checkFilesAreDifferentTestingTheirHash() throws IOException, NoSuchAlgorithmException {
    String file1HashHex = hashFile("/file1.xml");
    String file2HashHex = hashFile("/file2.xml");
    assertThat(file1HashHex).isNotEqualTo(file2HashHex);
  }

  private static String hashFile(String fileName) throws IOException, NoSuchAlgorithmException {
    try (InputStream inputStream = Hashing.class.getResourceAsStream(fileName)) {
      byte[] contentBytes = IOUtils.toByteArray(inputStream);

      byte[] hashBytes = null; // TODO
      String hashHex = Hex.encodeHexString(hashBytes);
      System.out.println("hash(" + fileName + ") = " + hashHex);
      return hashHex;
    }
  }


}
