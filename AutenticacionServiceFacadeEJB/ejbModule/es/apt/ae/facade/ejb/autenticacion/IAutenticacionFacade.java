package es.apt.ae.facade.ejb.autenticacion;

import java.security.cert.X509Certificate;

//import javax.servlet.http.HttpServletRequest;

import es.apt.ae.facade.ejb.autenticacion.exceptions.AutenticacionFacadeException;
import es.apt.ae.facade.ws.params.autenticacion.in.Login;
import es.apt.ae.facade.ws.params.autenticacion.out.Resultado;

public interface IAutenticacionFacade {

	//public Resultado identificarPorCertificadoTercero(HttpServletRequest request) throws AutenticacionFacadeException;
	public Resultado identificarPorCertificadoTercero(X509Certificate[] certs, String headerCert) throws AutenticacionFacadeException;
	public Resultado identificarPorCertificadoTercero (byte[] certificado) throws AutenticacionFacadeException;
	public Resultado seleccionarAccesoTercero(String personaId, String entidadId) throws AutenticacionFacadeException;
	public Resultado identificarPorCertificadoInterno (byte[] certificado) throws AutenticacionFacadeException;
	public boolean validarToken (String token);
	public Resultado getTerceroByToken(String token) throws AutenticacionFacadeException;
	public Resultado loginByToken(String token) throws AutenticacionFacadeException;
	public Resultado login(Login login) throws AutenticacionFacadeException;
}
