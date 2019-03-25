package com.opencanarias.consola.informes;

import java.io.Serializable;
import java.util.List;

public class ReportManagedBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private ReportDAO reportDAO;
	
	

	public ReportManagedBean() {
		super();
		this.reportDAO = new ReportDAO();
	}

	public void oldExp() {
		List<OldExpBean> list = reportDAO.list();
	}
	
	
	
	public void findAction() {
		// TODO Auto-generated method stub
		
	}

	public void newAction() {
		// TODO Auto-generated method stub
		
	}

	public void editAction(String identification) {
		// TODO Auto-generated method stub
		
	}

	public void saveAction() {
		// TODO Auto-generated method stub
		
	}

	public void returnAction() {
		// TODO Auto-generated method stub
		
	}

	public void deleteAction(String identification) {
		// TODO Auto-generated method stub
		
	}

}
