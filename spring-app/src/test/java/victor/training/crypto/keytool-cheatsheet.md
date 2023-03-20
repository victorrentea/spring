# Generate a private-public key pair and store it in a .jks protected with the password storepass
keytool -genkeypair -keysize 2048 -keyalg RSA -alias a -keystore a.jks -storepass storepass -dname cn=A

# See the contents of a JKS
keytool -list -keystore a.jks -storepass storepass
 
# Export the public key from a KeyPair as a certificate file (.cer)
keytool -exportcert -keystore a.jks -storepass storepass -alias a -file a.cer -rfc
 
# Import a certificate (.cer) in the truststore of a JKS: from now on. if a java program uses b.jks
keytool -import -alias a -file a.cer -keystore b.jks -storepass storepass
 

# (Optional) Export a certificate in a format that can be imported in Browser (in Intermediate CA)
keytool -importkeystore -srckeystore gateway.jks -destkeystore gateway.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass parola -deststorepass parola -srcalias gateway -destalias gateway -srckeypass parola -destkeypass parola -noprompt
