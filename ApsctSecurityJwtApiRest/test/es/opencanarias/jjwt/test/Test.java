package es.opencanarias.jjwt.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Test {
	public static String password = "opencan";

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String jwt = createJWT(UUID.randomUUID().toString(), "APSCT", "dprieto", (new Date()).getTime());
		
		System.out.println(jwt);
		
		parseJWT(jwt);
	}

	private static String createJWT(String id, String issuer, String subject, long ttlMillis) throws NoSuchAlgorithmException {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(generarClaveBase64("opencan"));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		String algoritmoDigestPassword = "SHA-1";
		MessageDigest digest = MessageDigest.getInstance(algoritmoDigestPassword);
		String resultDigestPassord = new String(Base64.getEncoder().encode(digest.digest(password.getBytes())));
		
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")				
										   .setId(id)
										   .setIssuedAt(now)
										   .setSubject(subject)
										   .setIssuer(issuer)
										   .claim("roles", "admin,jefeproyectos,analista,programador")
										   .claim("algoritmoCifradoPassword", algoritmoDigestPassword + "|base64")
										   .claim("password", resultDigestPassord)
										   .signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	private static void parseJWT(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(generarClaveBase64(password))).parseClaimsJws(jwt).getBody();
		StringBuilder result = new StringBuilder("\n");
		result.append("ID: " + claims.getId() + "\n");
		result.append("Usuario: " + claims.getSubject() + "\n");
		result.append("Emisor: " + claims.getIssuer() + "\n");
		result.append("Fecha de expiracion: " + claims.getExpiration() + "\n");
		result.append("Roles: " + claims.get("roles") + "\n");
		result.append("Password cifrado: " + claims.get("password") + "\n");
		result.append("Algoritmo Cifrado Password: " + claims.get("algoritmoCifradoPassword") + "\n");
		System.out.println(result.toString());
	}
	
	private static String generarClaveBase64(String clave) {
	    return DatatypeConverter.printBase64Binary(clave.getBytes());
	}
}
