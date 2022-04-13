package crypto;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A small message digest demo.
 */
public class HashingDemo {

	@Test
	public void hashingDemo() throws NoSuchAlgorithmException {

		System.out.println("Aa".hashCode());
		System.out.println("BB".hashCode());

		Map<String, Integer> map = new HashMap<>();
		map.put("Aa", 1);
		map.put("BB", 1);






		// get a message digest
		System.out.println("one way only!");
		hashText("The quick brown fox jumped over the lazy dog.");

		//hash it again, deterministic
		System.out.println("deterministic");
		hashText("The quick brown fox jumped over the lazy dog.");

		// psuedorandom, new digest looks nothing like old digest
		System.out.println("psuedorandom");
		hashText("The quick brown fox jumped ower the lazy dog.");

		// hash is always fixed length.
		System.out.println("fixedlength");
		hashText("The quick brown fox jumped ower the lazy dog and a lot more stuff happened after that.");
	}

	private void hashText(String data) throws NoSuchAlgorithmException {
		System.out.println("Input: " + data);
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] digest = messageDigest.digest(data.getBytes());
		Utils.printByteArray("Digest", digest);
	}


}
