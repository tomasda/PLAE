package es.opencanarias.security.jjwt.api.restful;

 import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.naming.NamingException;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.opencanarias.security.ldap.LdapUtils;

@Path("/security")
public class WebTokenApi extends Application {
	private static final Logger LOGGER = Logger.getLogger(WebTokenApi.class);
	
	@POST
	@Path("/generar")
	@Produces("text/plain")
	public Response generarToken(@FormParam("username") String username, @FormParam("password") String password) {
		ResponseBuilder response = null;

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			response = Response.status(Status.BAD_REQUEST);
		} else {
			try {
				String jjwt = JJWTService.getInstance().generarJWT(username, password);
				response = Response.ok(jjwt);
				response.cookie(new NewCookie("jwtAPSCT", jjwt, "/", null, "Cookie de autenticacion", -1, false));
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error(e);
				response = Response.serverError().tag("Algoritmo de cifrado para el password de usuario no encontrado.");
				response = Response.status(Status.INTERNAL_SERVER_ERROR);
			} catch (IOException e) {
				LOGGER.error(e);
				response = Response.serverError().tag("No se ha encontrado el fichero de configuracion del servicio.");
				response = Response.status(Status.INTERNAL_SERVER_ERROR);
			}
		}

		return response.build();
	}

	@GET
	@Path("/validar")
	@Produces("text/plain")
	public Response validarToken(@CookieParam("jwtAPSCT") String token) {
		ResponseBuilder response = null;
		String result = null;
		
		try {
			result = JJWTService.getInstance().validarJWT(token);
			response = Response.ok(result);
		} catch (ExpiredJwtException e) {
			response = Response.serverError().tag("El token ha expirado. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (UnsupportedJwtException e) {
			response = Response.serverError().tag("Token no soportado. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (MalformedJwtException e) {
			response = Response.serverError().tag("Token malformado. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (SignatureException e) {
			response = Response.serverError().tag("Error de firma en el token. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (IllegalArgumentException e) {
			response = Response.serverError().tag("Paso de argumento incorrectos. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			response = Response.serverError().tag("No se he contrado el fichero de configuracion para el servicio. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		
		return response.build();
	}

	@GET
	@Path("/validarAccesoApp")
	@Produces("application/json")
	public Response validarAccesoApp(@QueryParam("usuario") String usuario, @QueryParam("app") String app) {
		ResponseBuilder response = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			ResultadoValidarAccesoApp result = new ResultadoValidarAccesoApp();
			LdapUtils validator = LdapUtils.getInstance(); 
			result.setResultado(validator.isUserAuthorizate(usuario, app));
			response = Response.ok(mapper.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			response = Response.serverError().tag("El token ha expirado. Mensaje original [" + e.getMessage() + "]");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} catch (NamingException e) {
			response = Response.serverError().tag("Ha fallado el proceso de comprobacion de si el usuario tiene permisos de acceso a la aplicacion.");
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		
		return response.build();
	}

}
