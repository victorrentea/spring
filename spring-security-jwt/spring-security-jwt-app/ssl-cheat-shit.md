#Prerequisite Knowledge
JKS=Java Key Store (KeyPair + certificate ale entitatilor in care ai incredere)
 = un fel de hashMap pt material cripto
By default un JVM incarca automat la startup un JKS special aflat in jre/lib/security/cacerts
by default parola este "changeit"
JKS Alias=numele intrarii in aceasta mapa. Sub un alias poate fi o cheie publica sau un KeyPair (privata)
JKS password(s)= globala pe tot JKS si inca cate o parola aditionala pentru fiecare KeyPair (chei private)
JKS truststore("trustedCertEntry") vs "KeyPair"=
KeyPair=private + publica(semnaturi pe ea)
Certificate=

** GOAL: Create 2 .jks files that both trust eachother using asymetric keys.
In other words: JKS1{privateKeyA, publicKeyB} and JKS2{privateKeyB, publicKeyA}
Goal: Using JKS1 on Server1 and JKS2 on Server2 to allow 2-way SSL (client-cert authentication)

#Generates a private/public key pair and store it in a .jks
keytool -genkeypair -keysize 2048 -keyalg RSA -alias backend -keystore dest.keystore

#See the contents of a JKS
keytool -list -keystore dest.keystore

#Export the public key from a KeyPair as a certificate file (.cer)
keytool -exportcert -keystore dest.keystore -alias backend -storepass parola -file out.cer -rfc

#Import the public key from a certificate file (.cer) in the truststore of a JKS
keytool -import -alias gateway -file gateway.cer -keystore backend.jks -storepass parola

Create 2 such jks each trusting eachother si mi le dati pe mail victorrentea@gmail.com > 
Primeste de la mine un cupon pt 1 curs gratuit de pe victorrentea.teachable.com

#[Optional] Export a certificate in a format that can be imported in Browser (in Intermediate CA)
keytool -importkeystore -srckeystore gateway.jks -destkeystore gateway.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass parola -deststorepass parola -srcalias gateway -destalias gateway -srckeypass parola -destkeypass parola -noprompt