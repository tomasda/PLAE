package es.apt.ae.facade.dto;

import java.util.Date;

import es.opencanarias.managers.workflow.common.ActivityProperties;
import es.opencanarias.managers.workflow.common.ProcessProperties;

public final class GestionExpedienteInfo extends TerceroInfo {

	/** serialVersion UID */
    private static final long serialVersionUID = 1L;
    
    public static final String ACTION_END_ACTIVITY = "action.activity.end";
    
	private String userName = "";
	private String ocApplicantId = "";	
	private String adminFileId = "";
	private String workflowInstance = "";
	private String workflowVersion = "";
	private String workflowId = "";	
    private boolean flujoBloqueado;	
	private String pendingWorkflowId = "";
	private String pendingWorkflowDesc = "";
	private String notificationType = "";
	private String activity = "";	
	private String asunto = "";
	private boolean mandatoryNotificationType;
	private boolean startedByCitizen;
	private String recordNumber;
	private Date recordDate;
	private String expedRelacionado = "";
	private String typeOfRelation = "";
	private String selectedAction = "";
	
	private ProcessProperties projectProperties = new ProcessProperties();
	private ActivityProperties activityProperties = new ActivityProperties();
	
	
    public GestionExpedienteInfo() {
		this.flujoBloqueado = false;
	}


	public void reset() {
    	super.reset();
    }


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getOcApplicantId() {
		return ocApplicantId;
	}


	public void setOcApplicantId(String ocApplicantId) {
		this.ocApplicantId = ocApplicantId;
	}


	public String getAdminFileId() {
		return adminFileId;
	}


	public void setAdminFileId(String adminFileId) {
		this.adminFileId = adminFileId;
	}


	public String getWorkflowInstance() {
		return workflowInstance;
	}


	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}


	public String getWorkflowVersion() {
		return workflowVersion;
	}


	public void setWorkflowVersion(String workflowVersion) {
		this.workflowVersion = workflowVersion;
	}


	public String getWorkflowId() {
		return workflowId;
	}


	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}


	public boolean isFlujoBloqueado() {
		return flujoBloqueado;
	}


	public void setFlujoBloqueado(boolean flujoBloqueado) {
		this.flujoBloqueado = flujoBloqueado;
	}


	public String getPendingWorkflowId() {
		return pendingWorkflowId;
	}


	public void setPendingWorkflowId(String pendingWorkflowId) {
		this.pendingWorkflowId = pendingWorkflowId;
	}


	public String getPendingWorkflowDesc() {
		return pendingWorkflowDesc;
	}


	public void setPendingWorkflowDesc(String pendingWorkflowDesc) {
		this.pendingWorkflowDesc = pendingWorkflowDesc;
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}


	public String getActivity() {
		return activity;
	}


	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public boolean isMandatoryNotificationType() {
		return mandatoryNotificationType;
	}


	public void setMandatoryNotificationType(boolean mandatoryNotificationType) {
		this.mandatoryNotificationType = mandatoryNotificationType;
	}


	public boolean isStartedByCitizen() {
		return startedByCitizen;
	}


	public void setStartedByCitizen(boolean startedByCitizen) {
		this.startedByCitizen = startedByCitizen;
	}


	public String getRecordNumber() {
		return recordNumber;
	}


	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}


	public Date getRecordDate() {
		return recordDate;
	}


	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	public String getExpedRelacionado() {
		return expedRelacionado;
	}


	public void setExpedRelacionado(String expedRelacionado) {
		this.expedRelacionado = expedRelacionado;
	}


	public String getTypeOfRelation() {
		return typeOfRelation;
	}


	public void setTypeOfRelation(String typeOfRelation) {
		this.typeOfRelation = typeOfRelation;
	}


	public String getSelectedAction() {
		return selectedAction;
	}


	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}


	public ProcessProperties getProjectProperties() {
		return projectProperties;
	}


	public void setProjectProperties(ProcessProperties projectProperties) {
		this.projectProperties = projectProperties;
	}


	public ActivityProperties getActivityProperties() {
		return activityProperties;
	}


	public void setActivityProperties(ActivityProperties activityProperties) {
		this.activityProperties = activityProperties;
	}

}