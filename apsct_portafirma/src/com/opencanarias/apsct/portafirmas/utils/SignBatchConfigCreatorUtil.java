package com.opencanarias.apsct.portafirmas.utils;

import com.opencanarias.apsct.plae.dto.SignBatchConfig;
import com.opencanarias.apsct.plae.dto.Signer;
import com.opencanarias.apsct.portafirmas.bean.UsuarioBean;

public class SignBatchConfigCreatorUtil {
	
	public static SignBatchConfig getConfig(){
		SignBatchConfig signBatchConfig = new SignBatchConfig();
		signBatchConfig.setFontSize(ConfigUtils.getParametroInteger("sign.pades.fontSize"));
		signBatchConfig.setFontStyle(ConfigUtils.getParametroInteger("sign.pades.fontStyle"));
		signBatchConfig.setMarginMin(ConfigUtils.getParametroInteger("sign.pades.marginMin"));
		signBatchConfig.setMarginOne(ConfigUtils.getParametroInteger("sign.pades.marginOne"));
		signBatchConfig.setMarginTwo(ConfigUtils.getParametroInteger("sign.pades.marginTwo"));
		signBatchConfig.setMarginTree(ConfigUtils.getParametroInteger("sign.pades.marginTree"));
		signBatchConfig.setPort(FacesUtils.getServerPort());
		signBatchConfig.setProtocol(FacesUtils.getSchemeProtocol());
		signBatchConfig.setServerName(FacesUtils.getServerName());
		signBatchConfig.setSignaturePage(ConfigUtils.getParametroInteger("sign.pades.signaturePage"));
//		signBatchConfig.setTmpDocExternal(ConfigUtils.getParametro("sign.pades.portafirmastmpDoc"));
//		signBatchConfig.setTmpDocInternal(ConfigUtils.getParametro("sign.pades.tmpDoc"));
		signBatchConfig.setTmpDocExternal(ConfigUtils.getParametro("sign.directory.portafirmastmpDoc"));
		signBatchConfig.setTmpDocInternal(ConfigUtils.getParametro("sign.directory.tmpDoc"));
		return signBatchConfig;
	}
	
	public static Signer getSigner(){
		Signer signer = new Signer();
		UsuarioBean usuarioBean = (UsuarioBean) FacesUtils.getSessionBean(Constantes.USUARIO_BEAN);
		signer.setFirstname(usuarioBean.getNombre());
		signer.setIdentificationNumber(usuarioBean.getNumIdentificacion());
		signer.setLastname(usuarioBean.getApellido1() + usuarioBean.getApellido2());
		return signer;
		
	}
	
}
