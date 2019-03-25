package com.opencanarias.consola.informes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;

import com.opencanarias.consola.utilidades.DataBaseUtils;

public class ReportDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReportDAO.class);


	public List<OldExpBean> list() {
		List<OldExpBean> datos = null;
//		DateUtils f = new DateUtils();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
//		Timestamp startDate = f.parseRangeDate("Start", fechasTMP2);
//		Timestamp endDate = f.parseRangeDate("End", fechasTMP2);
		try {
			con  = DataBaseUtils.getConnection("OC3F");
			sql = "select ROL.DESCRIPTION_, AF.CREATION_DATE_, AF.ID_, AF.ASUNTO_, AF.INTERESADO_, ART.NAME_ ,ART.ASSIGNEE_ " + 
					"from OC3F.ADMINISTRATIVE_FILE_ AF " + 
					"join EAACTIVITI.ACT_RU_TASK ART on AF.WF_INSTANCE_ = ART.PROC_INST_ID_ " + 
					"join EAACTIVITI.ACT_RU_IDENTITYLINK ARI on ART.ID_ = ARI.TASK_ID_ " + 
					"join ROLE_ ROL on ROL.ID_ = ARI.GROUP_ID_ " + 
					"where AF.CREATION_DATE_ < '01/07/18 00:00:07,874000000' and AF.END_DATE_ is null and ARI.GROUP_ID_ is not null " + 
					"order by ROL.DESCRIPTION_ asc, AF.ID_ asc";
			ps = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			ps.setString(1, "%" + numExpTMP2.trim() + "%");
//			ps.setTimestamp(2, startDate);
//			ps.setTimestamp(3,  endDate);
			rs = ps.executeQuery();
			rs.last();
			int numRows = rs.getRow();
			if (numRows <= 0) { // Si NO se encuentran resultados //
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se han encontrado resultados.", ""));
			} else { // Si se encuentran, carga los datos en una lista para devolverlos.
				@SuppressWarnings("unused")
				int index = 0;
				rs.beforeFirst(); // vuelvo al primer registro
				if (numRows < 100000) { // Sí los resultados son menos de 100
					datos = new ArrayList<OldExpBean>();
					OldExpBean o_Exp;
					while (rs.next()) { // Creo la lista de expedientes
						o_Exp = new OldExpBean(rs.getString("ROL.DESCRIPTION_"), rs.getTimestamp("AF.CREATION_DATE_"), rs.getString("AF.ID_"), rs.getString("AF.ASUNTO_"),
								rs.getString("AF.INTERESADO_"), rs.getString("ART.NAME_"), rs.getString("ART.ASSIGNEE_"),"");

						// Añadimos el objeto "Expediente" al ArrayList
						datos.add(o_Exp);
					}
				} else {// Si los resultados pasan del límite.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La consulta devuelve demasiados resultados.", "Especifique un criterio más concreto."));
				}
			}
			// Cerrar la conexión con la BBDD
			rs.close();
			ps.close();
		} catch (SQLException e) {
			logger.error(e);
		}finally {
			DataBaseUtils.close(con);
		}
		return datos;
	}
}
