package es.opencanarias.security.jjwt.api.restful;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.opencanarias.commons.utils.ConfigUtils;

public class JJWTService {
	private static final Logger LOGGER = Logger.getLogger(JJWTService.class);
	private static JJWTService instance = null;
	
	public static JJWTService getInstance () {
		if (instance == null) {
			instance = new JJWTService();
		}
		return instance;
	}
	
	public String generarJWT(String subject, String password) throws NoSuchAlgorithmException, IOException {
		LOGGER.info("[APSCT-SECURITY] Creando un nuevo token para autenticacion ...");
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(generarClaveBase64(getCipherPassword()));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		String algoritmoDigestPassword = getAlgorithmCipherPassword();
		// MessageDigest digest = MessageDigest.getInstance(algoritmoDigestPassword);
		// String resultDigestPassword = new String(Base64.encodeBase64(digest.digest(password.getBytes())));

		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
										   .setId(UUID.randomUUID().toString())
										   .setIssuedAt(now)
										   .setSubject(subject)
										   .setIssuer(getIssuer())
										   .claim("roles", "admin")
										   .claim("algoritmoCifradoPassword", algoritmoDigestPassword + "|base64")
										   .claim("password", password) //resultDigestPassword
										   .signWith(signatureAlgorithm, signingKey);

		long expMillis = nowMillis + getSessionTTL(); 
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		return builder.compact();
	}

	public String validarJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, IOException {		
		LOGGER.info("[APSCT-SECURITY] Validando un nuevo token de autenticacion ...");
		String clave = generarClaveBase64(getCipherPassword());
		//TODO: IMportante - FALTA LA CREACIÓN DE LA COOKIE
		Claims claims = Jwts.parser().setSigningKey((clave)).parseClaimsJws(jwt).getBody(); 
//				//Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(clave)).parseClaimsJws(jwt).getBody();
//
		DatosJWT datosJWT = new DatosJWT(); 
		datosJWT.setId(claims.getId());
		datosJWT.setUsuario(claims.getSubject());
		datosJWT.setEmisor(claims.getIssuer());
		datosJWT.setFechaExpiracion("" + claims.getExpiration().getTime());
		datosJWT.setRoles((String) claims.get("roles"));
		datosJWT.setPasswordCifrado((String) claims.get("password"));
		datosJWT.setAlgoritmoCifradoPassword((String) claims.get("algoritmoCifradoPassword"));
		
		ObjectMapper mapper = new ObjectMapper();
		LOGGER.debug(mapper.writeValueAsString(datosJWT));
		return mapper.writeValueAsString(datosJWT);
	}

	private String generarClaveBase64(String clave) {
		return DatatypeConverter.printBase64Binary(clave.getBytes());
	}
	
	private String getCipherPassword () throws IOException {
		return ConfigUtils.getParametro("es.opencanarias.security.cypher.pass.querys");
	}
	
	private String getAlgorithmCipherPassword () throws IOException {
		return ConfigUtils.getParametro("es.opencanarias.security.algorithm.cypher.pass");
	}
	
	private String getIssuer () throws IOException {
		return ConfigUtils.getParametro("es.opencanarias.security.jjwt.issuer");
	}
	
	private Long getSessionTTL () throws IOException, NumberFormatException {
		Long segundos = new Long(ConfigUtils.getParametro("es.opencanarias.security.jjwt.expiration.ttl"));
		Long milisegundos = segundos * 1000;
		return milisegundos;
	}
	
}
