package com.opencanarias.consola.commons;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.commons.Log;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Downloads {
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat font;
	private WritableCellFormat cf;
	private String inputFile;
	
	
	public void exportToXML(String option) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		OutputStream out = response.getOutputStream();
		response.reset();
		response.setContentType("text/xml");
		if (option.equals("ALFRESCO"))
			response.addHeader("Content-Disposition", "attachment; filename=APSCT_ALF_GROUPS.xml");
		if (option.equals("USERS"))
			response.addHeader("Content-Disposition", "attachment; filename=APSCT_USERS.xml");
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			//trans.transform(exportToExcelGenerateXML(option), new StreamResult(out));
		} catch (Exception e) {

		}
		out.flush();
		try {
			if (out != null) {
				out.close();
			}
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

		}
	}
	
	public void exportToXLS(String option) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		OutputStream out = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		if (option.equals("ALFRESCO"))
			response.addHeader("Content-Disposition", "attachment; filename=APSCT_List_of_records.xls");
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.transform(exportToExcelGenerateXML(option), new StreamResult(out));
		} catch (Exception e) {

		}
		out.flush();
		try {
			if (out != null) {
				out.close();
			}
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

		}
	}
	
	/*
	 * Crea el fichero excel y establece los estilos y parámetros de la primera pestaña.
	 */
	public void createXLS(String valor) {
		try {
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("es", "ES"));
			CellView t_objView = new CellView();
			t_objView.setAutosize(true);
			CellView t_objView2 = new CellView();
			t_objView2.setAutosize(true);
			WritableWorkbook workBook = Workbook.createWorkbook(new File(inputFile));
			workBook.createSheet("Tabla Reportes", 0);
			WritableSheet excelSheet = workBook.getSheet(0); // Pestaña 0
			excelSheet.setColumnView(1, t_objView);
			createLabel(excelSheet);
			createContent(excelSheet, valor);
			workBook.write();
			workBook.close();
		} catch (IOException | WriteException e) {
			Log.log.info("Error Creando el fichero XLS" + e);
			e.printStackTrace();
		}
	}

}
