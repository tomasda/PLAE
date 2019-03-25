/**
 * 
 */
package com.opencanarias.consola.gestorExpedientes.documentos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.commons.Constantes;
import com.opencanarias.consola.utilidades.DataBaseUtils;

/**
 * @author Tomás Delgado.
 *
 */
public class DocumentsDAO {
	private static Logger logger = Logger.getLogger(DocumentsDAO.class);
	
	
	public  List<DocumentBean> getListOfDocumentsWihtoutCSV() {
		List<DocumentBean> list = null;
		Connection con  = null;
		PreparedStatement ps  = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		try {
			con = DataBaseUtils.getConnection("OC3F");
			sql.append("select * from DOCUMENT_  DC  join PF_DOCUMENTOS PFD on DC.REPOSITORY_URI_ = PFD.URI" + 
					"  where DC.STATE_ = 'Pendiente Portafirmas' AND PFD.ID_FLUJO IS NULL AND DC.TYPE_ != 'canceled' ORDER BY DC.ADMINISTRATIVE_FILE_ID_");
			ps = con.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				list = new ArrayList<DocumentBean>();
				rs.beforeFirst(); // vuelvo al primer registro
				while (rs.next()) { // Creo un objeto de la clase "Expediente"
					DocumentBean doc = new DocumentBean();
					doc.setADMINISTRATIVE_FILE_ID_(rs.getString("ADMINISTRATIVE_FILE_ID_"));//	VARCHAR2(64 BYTE)
					doc.setID_(rs.getString("ID_"));//	VARCHAR2(160 BYTE)
					doc.setACTIVITY_(rs.getString("ACTIVITY_"));//	VARCHAR2(200 BYTE)
					doc.setCONTENT_TYPE_(rs.getString("CONTENT_TYPE_"));//	VARCHAR2(96 BYTE)
					doc.setHAS_DOCUMENT_(rs.getInt("HAS_DOCUMENT_"));//	NUMBER(1,0)
					doc.setCREATION_DATE_(rs.getTimestamp("CREATION_DATE_"));//	TIMESTAMP(6)
					doc.setDESCRIPTION_(rs.getString("DESCRIPTION_"));//	VARCHAR2(200 BYTE)
					doc.setSIGN_REFERENCE_(rs.getString("SIGN_REFERENCE_"));//	VARCHAR2(40 BYTE)
					doc.setSIGN_ACTIVITY_(rs.getString("SIGN_ACTIVITY_"));//	VARCHAR2(30 BYTE)
					doc.setRECORD_NUMBER_(rs.getString("RECORD_NUMBER_"));//	VARCHAR2(20 BYTE)
					doc.setRECORD_DATE_(rs.getTimestamp("RECORD_DATE_"));//	TIMESTAMP(6)
					doc.setRECORD_TYPE_(rs.getString("RECORD_TYPE_"));//	VARCHAR2(10 BYTE)
					doc.setTYPE_(rs.getString("TYPE_"));//	VARCHAR2(15 BYTE)
					doc.setCURRENT_(rs.getInt("CURRENT_"));//	NUMBER(1,0)
					doc.setREPOSITORY_URI_(rs.getString("REPOSITORY_URI_"));//	VARCHAR2(160 CHAR)
					doc.setSTATE_(rs.getString("STATE_"));//	VARCHAR2(96 CHAR)
					doc.setDOCUMENT_TYPE(rs.getString("DOCUMENT_TYPE"));//	VARCHAR2(4 CHAR)
					doc.setELABORATION_DATE_(rs.getTimestamp("ELABORATION_DATE_"));//	TIMESTAMP(6)
					doc.setSCALE_(rs.getInt("SCALE_"));//	NUMBER(3,2)
					doc.setINVERT_WATERMARK_(rs.getInt("INVERT_WATERMARK_"));//	NUMBER(1,0)
					doc.setACTUACION_ID_(rs.getString("ACTUACION_ID_"));//	VARCHAR2(64 BYTE)
					doc.setCANCEL_DATE(rs.getTimestamp("CANCEL_DATE"));//	TIMESTAMP(6)
					doc.setCANCEL_USER_ID(rs.getString("CANCEL_USER_ID"));//	VARCHAR2(15 BYTE)
					doc.setDOC_ORIGINAL_URI(rs.getString("DOC_ORIGINAL_URI"));//	VARCHAR2(160 BYTE)
					doc.setROTATION_(rs.getInt("ROTATION_"));//	NUMBER(10,0)
					list.add(doc);
				}
			}
			rs.close(); // Cerrar la conexión con la BBDD
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return list;
	}


	public void createSignatureRegs(List<DocumentBean> list) {
		/*
		EXFABBM-2018-08-09-00086	Resolucion_Estimatoria_EXFABBM-2018-08-09-00086.pdf_signature	resolucion	text/xml	0	03/09/18 13:16:45,000000000	Resolucion_Estimatoria_EXFABBM-2018-08-09-00086	d6f21de5bde89f06c4f77752025e7526d9f3bda1						0				03/09/18 13:16:45,000000000	-1	0					0
		EXFABBM-2018-08-09-00086	Resolucion_Estimatoria_EXFABBM-2018-08-09-00086.pdf	resolucion	application/pdf	0	17/08/18 09:46:32,019000000	Resolucion_Estimatoria_EXFABBM-2018-08-09-00086	d6f21de5bde89f06c4f77752025e7526d9f3bda1		S-GEN-2018/003170	06/09/18 14:10:56,461000000	exit	attachment	1	01f62652-086f-431e-9fd3-c851b8b9e88d	Firmado Portafirmas	TD01	17/08/18 00:00:00,000000000	0,95	0					0
		
		insert into DOCUMENT_ values ('DPDPSAU-2018-09-04-00200','informe-propuesta_autorizacion_20180904104442.pdf_signature','instruccion','text/xml',0,'04/09/18 10:39:11,765000000','PROPUESTA RELATIVA AL OTORGAMIENTO DE AUTORIZACIÓN ADMINISTRATIVA A LA ENTIDAD "CONTENERFRUT, S.A." PARA LA OCUPACIÓN DE UNA PARCELA DE 7.000 METROS CUADRADOS DEL PUERTO DE GRANADILLA.','','','','','','',0,'','','','04/09/18 00:00:00,000000000',-1,0,'','','','',0);
		update DOCUMENT_ set STATE_ = 'Firmado Portafirmas' where REPOSITORY_URI_='991a3843-807f-4ea3-9688-cd4d2823dba4';
		update GESTION_PORTAFIRMAS_DOC set ESTADO_FIRMA='Firmado Portafirmas' where URI_DOC_REPOSITORY = '991a3843-807f-4ea3-9688-cd4d2823dba4'
		
		*/
		List<DocumentBean> list2 = new ArrayList<>();
		for (DocumentBean documentBean : list) {
			documentBean.setSIGN_REFERENCE_(getCSVFromFile(documentBean.getREPOSITORY_URI_()));
			list2.add(documentBean);
		}
		
		Connection con = null;
		PreparedStatement ps = null;
		//ResultSet rs =  null;
//		List<PF_GruposBean> groups = flujo.getListOfGroups();
		try {
			con = DataBaseUtils.getConnection("OC3F");
			String sql = null;
			
			for (DocumentBean documentBean : list2) {
				/*insert into DOCUMENT_ values ('DPDPSAU-2018-09-04-00200','informe-propuesta_autorizacion_20180904104442.pdf_signature','instruccion','text/xml',0,'04/09/18 10:39:11,765000000','PROPUESTA RELATIVA AL OTORGAMIENTO DE AUTORIZACIÓN ADMINISTRATIVA A LA ENTIDAD "CONTENERFRUT, S.A." PARA LA OCUPACIÓN DE UNA PARCELA DE 7.000 METROS CUADRADOS DEL PUERTO DE GRANADILLA.','','','','','','',0,'','','','04/09/18 00:00:00,000000000',-1,0,'','','','',0);*/
				sql = "insert into DOCUMENT_ values (?,?,?,'text/xml',0,?,?,?,'','','','','',0,'','','',?,1,0,'','','','',0)";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, documentBean.getADMINISTRATIVE_FILE_ID_());
				ps.setString(2, documentBean.getID_()+"_signature");
				ps.setString(3, documentBean.getACTIVITY_());
				ps.setTimestamp(4, documentBean.getCREATION_DATE_());
				ps.setString(5, documentBean.getDESCRIPTION_());
				ps.setString(6, documentBean.getSIGN_REFERENCE_());
				ps.setTimestamp(7, documentBean.getELABORATION_DATE_());
				ps.execute();
				
				/* update DOCUMENT_ set STATE_ = 'Firmado Portafirmas' where REPOSITORY_URI_='991a3843-807f-4ea3-9688-cd4d2823dba4'; */
				sql = "update DOCUMENT_ set STATE_ = 'Firmado Portafirmas',SIGN_REFERENCE_ = ? where REPOSITORY_URI_= ?";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, documentBean.getSIGN_REFERENCE_());
				ps.setString(2, documentBean.getREPOSITORY_URI_());
				ps.execute();
				
				/* update GESTION_PORTAFIRMAS_DOC set ESTADO_FIRMA='Firmado Portafirmas' where URI_DOC_REPOSITORY = '991a3843-807f-4ea3-9688-cd4d2823dba4' */
				sql = "update GESTION_PORTAFIRMAS_DOC set ESTADO_FIRMA='Firmado Portafirmas' where URI_DOC_REPOSITORY = ?";
				ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, documentBean.getREPOSITORY_URI_());
				ps.execute();
				
			}
			
			
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al modificar los documentos.", ""));
		}finally {
			DataBaseUtils.close(con);
		}
		
		
//		StringBuffer output = new StringBuffer();
//		System.out.println("\n\n···········································");
//		System.out.println("Documentos encontrados = "+list2.size());
//		for (DocumentBean documentBean : list2) {
//			output = new StringBuffer();
//			output.append(documentBean.getADMINISTRATIVE_FILE_ID_()).append("\t").append(documentBean.getREPOSITORY_URI_()).append("\t").append(documentBean.getSIGN_REFERENCE_());
//			System.out.println(output);
//		}
	}
	
	private String getCSVFromFile(String uri) {
		String csv = null;
		//String data=null;
		String propertyFile = System.getProperty(Constantes.SYSTEM_PROPRETIES_FOLDER);
		File file = new File(propertyFile+"/consola/csv.properties");
		Properties configuration = new Properties();
        try {
			configuration.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_1);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(Constantes.LOGIN_PROPERTIES_ERROR_2 + e);
		}
		csv = configuration.getProperty(uri);
		//System.out.println(data);
		return csv;
	}
	

}
