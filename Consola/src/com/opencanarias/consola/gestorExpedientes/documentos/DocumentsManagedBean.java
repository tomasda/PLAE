/**
 * 
 */
package com.opencanarias.consola.gestorExpedientes.documentos;


import java.io.Serializable;
import java.util.List;

import com.opencanarias.consola.interfaces.ManagedBeanInterface;

/**
 * @author Tomás Delgado.
 *
 */
public class DocumentsManagedBean implements Serializable,ManagedBeanInterface {
	private static final long serialVersionUID = 1L;
	private DocumentsDAO docDAO;
	
	private List<DocumentBean> listDocumentsWihtoutCSV_;

	public void listDocumentsWihtoutCSV() {
		StringBuffer output;
		if (null==docDAO)
			docDAO = new DocumentsDAO();
		
		listDocumentsWihtoutCSV_ = docDAO.getListOfDocumentsWihtoutCSV();
		if (null!=listDocumentsWihtoutCSV_) {
			System.out.println("\n\n···········································");
			System.out.println("Documentos encontrados = "+listDocumentsWihtoutCSV_.size());
			for (DocumentBean documentBean : listDocumentsWihtoutCSV_) {
				output = new StringBuffer();
				output.append(documentBean.getADMINISTRATIVE_FILE_ID_()).append("\t").append(documentBean.getREPOSITORY_URI_()).append("\t").append(documentBean.getID_());
				System.out.println(output);
			}
		}
	}
	public void createSignatureRegs() {
		if (null==docDAO)
			docDAO = new DocumentsDAO();
		
		docDAO.createSignatureRegs(listDocumentsWihtoutCSV_);
	}
	
	
	public void findAction() {

	}

	public void newAction() {

	}

	public void editAction(Object identification) {

	}

	public void saveAction() {

	}

	public void returnAction() {

	}

	public void deleteAction(Object identification) {

	}
	public List<DocumentBean> getListDocumentsWihtoutCSV_() {
		return listDocumentsWihtoutCSV_;
	}
	public void setListDocumentsWihtoutCSV_(List<DocumentBean> listDocumentsWihtoutCSV_) {
		this.listDocumentsWihtoutCSV_ = listDocumentsWihtoutCSV_;
	}

	
}
