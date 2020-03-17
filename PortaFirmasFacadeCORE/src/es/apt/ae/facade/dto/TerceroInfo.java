package es.apt.ae.facade.dto;

import java.io.Serializable;


public class TerceroInfo implements Serializable {

	/** serialVersion UID */
    private static final long serialVersionUID = 1L;
	
	
	/********************************
	 * Constantes
	 ********************************/
	public static final String APPLICANT				= "appTag.applicant";
	public static final String REPRESENTATIVE			= "appTag.representative";
	
	public static final String NATURAL_PERSON 			= "appTag.type.natural.person";
	public static final String ARTIFICIAL_PERSON 		= "appTag.type.artificial.person";
	public static final String PUBLIC_ADMINISTRATION 	= "appTag.type.public.administration";
	
	
	/********************************
	 * Propiedades
	 ********************************/
	private String appTagOnBehalf = "";
	private String appTagApplicType = "";
	private String appTagApplicName = "";
	private String appTagApplicSurnames = "";
	private String appTagApplicSurname1 = "";
	private String appTagApplicSurname2 = "";
	private String appTagApplicNif = "";
	private String appTagApplicDocTypeId = "";
	private String appTagApplicDocTypeDesc = "";
	private String appTagApplicBirthDate = "";
	private String appTagApplicBusinessName = "";
	private String appTagApplicCif = "";
	private String appTagApplicPublicAdmin = "";
	private String appTagApplicPublicAdminCode = "";

	// appTagApplicAddress. Añadido Para la integración con los terceros del nuevo 
	// módulo de Registro de E/S.
	private String appTagApplicAddress = "";
	private String appTagApplicStreet = "";
	private String appTagApplicNumber = "";
	private String appTagApplicBlock = "";
	private String appTagApplicKm = "";
	private String appTagApplicFloor = "";
	private String appTagApplicBuildingNumber = "";
	private String appTagApplicStair = "";
	private String appTagApplicDoor = "";
	
	private String appTagApplicLocality = "";
	private String appTagApplicLocalityId = "";
	private String appTagApplicProvinceId = "";
	// appTagApplicCountry. Añadido Para la integración con los terceros del nuevo 
	// módulo de Registro de E/S.	
	private String appTagApplicCountry = "";
	private String appTagApplicProvince = "";
	private String appTagApplicZip = "";
	private String appTagApplicTelephone = "";
	private String appTagApplicFax = "";
	private String appTagApplicEmail = "";
	private String appTagRepresType = "";

	private String appTagRepresName = "";
	private String appTagRepresSurnames = "";
	private String appTagRepresSurname1 = "";
	private String appTagRepresSurname2 = "";
	private String appTagRepresNif = "";
	private String appTagRepresDocTypeId = "";
	private String appTagRepresDocTypeDesc = "";	
	private String appTagRepresBusinessName = "";
	private String appTagRepresCif = "";
	private String appTagRepresPublicAdmin = "";
	private String appTagRepresPublicAdminCode = " ";

	// appTagRepresAddress. Añadido Para la integración con los terceros del nuevo 
	// módulo de Registro de E/S.
	private String appTagRepresAddress = "";
	private String appTagRepresStreet = "";
	private String appTagRepresNumber = "";
	private String appTagRepresBlock = "";
	private String appTagRepresKm = "";
	private String appTagRepresFloor = "";
	private String appTagRepresBuildingNumber = "";
	private String appTagRepresStair = "";
	private String appTagRepresDoor = "";
	private String appTagRepresLocality = "";
	private String appTagRepresLocalityId = "";
	private String appTagRepresProvince = "";
	private String appTagRepresProvinceId = "";
	private String appTagRepresZip = "";
	private String appTagRepresTelephone = "";	
	private String appTagRepresFax = "";
	private String appTagRepresEmail = "";
	// appTagRepresCountry. Añadido Para la integración con los terceros del nuevo 
	// módulo de Registro de E/S.
	private String appTagRepresCountry = "";	
	

	/****************************************
	 * Getters Y Setters
	 ****************************************/
	public String getAppTagOnBehalf() {
		return appTagOnBehalf;
	}

	public void setAppTagOnBehalf(String appTagOnBehalf) {
		this.appTagOnBehalf = appTagOnBehalf;
	}

	public String getAppTagApplicSurnames() {
		return appTagApplicSurnames;
	}

	public void setAppTagApplicSurnames(String appTagApplicSurnames) {
		this.appTagApplicSurnames = appTagApplicSurnames;
	}

	public String getAppTagApplicSurname1() {
		return appTagApplicSurname1;
	}

	public void setAppTagApplicSurname1(String appTagApplicSurname1) {
		this.appTagApplicSurname1 = appTagApplicSurname1;
	}

	public String getAppTagApplicSurname2() {
		return appTagApplicSurname2;
	}

	public void setAppTagApplicSurname2(String appTagApplicSurname2) {
		this.appTagApplicSurname2 = appTagApplicSurname2;
	}

	public String getAppTagApplicBirthDate() {
		return appTagApplicBirthDate;
	}

	public void setAppTagApplicBirthDate(String appTagApplicBirthDate) {
		this.appTagApplicBirthDate = appTagApplicBirthDate;
	}

	public String getAppTagApplicBusinessName() {
		return appTagApplicBusinessName;
	}

	public void setAppTagApplicBusinessName(String appTagApplicBusinessName) {
		this.appTagApplicBusinessName = appTagApplicBusinessName;
	}

	public String getAppTagApplicPublicAdmin() {
		return appTagApplicPublicAdmin;
	}

	public void setAppTagApplicPublicAdmin(String appTagApplicPublicAdmin) {
		this.appTagApplicPublicAdmin = appTagApplicPublicAdmin;
	}

	public String getAppTagApplicPublicAdminCode() {
		return appTagApplicPublicAdminCode;
	}

	public void setAppTagApplicPublicAdminCode(String appTagApplicPublicAdminCode) {
		this.appTagApplicPublicAdminCode = appTagApplicPublicAdminCode;
	}

	public String getAppTagApplicStreet() {
		return appTagApplicStreet;
	}

	public void setAppTagApplicStreet(String appTagApplicStreet) {
		this.appTagApplicStreet = appTagApplicStreet;
	}

	public String getAppTagApplicNumber() {
		return appTagApplicNumber;
	}

	public void setAppTagApplicNumber(String appTagApplicNumber) {
		this.appTagApplicNumber = appTagApplicNumber;
	}

	public String getAppTagApplicBlock() {
		return appTagApplicBlock;
	}

	public void setAppTagApplicBlock(String appTagApplicBlock) {
		this.appTagApplicBlock = appTagApplicBlock;
	}

	public String getAppTagApplicFloor() {
		return appTagApplicFloor;
	}

	public void setAppTagApplicFloor(String appTagApplicFloor) {
		this.appTagApplicFloor = appTagApplicFloor;
	}

	public String getAppTagApplicBuildingNumber() {
		return appTagApplicBuildingNumber;
	}

	public void setAppTagApplicBuildingNumber(String appTagApplicBuildingNumber) {
		this.appTagApplicBuildingNumber = appTagApplicBuildingNumber;
	}

	public String getAppTagApplicStair() {
		return appTagApplicStair;
	}

	public void setAppTagApplicStair(String appTagApplicStair) {
		this.appTagApplicStair = appTagApplicStair;
	}

	public String getAppTagApplicDoor() {
		return appTagApplicDoor;
	}

	public void setAppTagApplicDoor(String appTagApplicDoor) {
		this.appTagApplicDoor = appTagApplicDoor;
	}

	public String getAppTagApplicLocality() {
		return appTagApplicLocality;
	}

	public void setAppTagApplicLocality(String appTagApplicLocality) {
		this.appTagApplicLocality = appTagApplicLocality;
	}

	public String getAppTagApplicLocalityId() {
		return appTagApplicLocalityId;
	}

	public void setAppTagApplicLocalityId(String appTagApplicLocalityId) {
		this.appTagApplicLocalityId = appTagApplicLocalityId;
	}

	public String getAppTagApplicProvinceId() {
		return appTagApplicProvinceId;
	}

	public void setAppTagApplicProvinceId(String appTagApplicProvinceId) {
		this.appTagApplicProvinceId = appTagApplicProvinceId;
	}

	public String getAppTagApplicProvince() {
		return appTagApplicProvince;
	}

	public void setAppTagApplicProvince(String appTagApplicProvince) {
		this.appTagApplicProvince = appTagApplicProvince;
	}

	public String getAppTagApplicZip() {
		return appTagApplicZip;
	}

	public void setAppTagApplicZip(String appTagApplicZip) {
		this.appTagApplicZip = appTagApplicZip;
	}

	public String getAppTagApplicTelephone() {
		return appTagApplicTelephone;
	}

	public void setAppTagApplicTelephone(String appTagApplicTelephone) {
		this.appTagApplicTelephone = appTagApplicTelephone;
	}

	public String getAppTagRepresType() {
		return appTagRepresType;
	}

	public void setAppTagRepresType(String appTagRepresType) {
		this.appTagRepresType = appTagRepresType;
	}

	public String getAppTagRepresName() {
		return appTagRepresName;
	}

	public void setAppTagRepresName(String appTagRepresName) {
		this.appTagRepresName = appTagRepresName;
	}

	public String getAppTagRepresSurnames() {
		return appTagRepresSurnames;
	}

	public void setAppTagRepresSurnames(String appTagRepresSurnames) {
		this.appTagRepresSurnames = appTagRepresSurnames;
	}

	public String getAppTagRepresSurname1() {
		return appTagRepresSurname1;
	}

	public void setAppTagRepresSurname1(String appTagRepresSurname1) {
		this.appTagRepresSurname1 = appTagRepresSurname1;
	}

	public String getAppTagRepresSurname2() {
		return appTagRepresSurname2;
	}

	public void setAppTagRepresSurname2(String appTagRepresSurname2) {
		this.appTagRepresSurname2 = appTagRepresSurname2;
	}

	public String getAppTagRepresBusinessName() {
		return appTagRepresBusinessName;
	}

	public void setAppTagRepresBusinessName(String appTagRepresBusinessName) {
		this.appTagRepresBusinessName = appTagRepresBusinessName;
	}

	public String getAppTagRepresPublicAdmin() {
		return appTagRepresPublicAdmin;
	}

	public void setAppTagRepresPublicAdmin(String appTagRepresPublicAdmin) {
		this.appTagRepresPublicAdmin = appTagRepresPublicAdmin;
	}

	public String getAppTagRepresPublicAdminCode() {
		return appTagRepresPublicAdminCode;
	}

	public void setAppTagRepresPublicAdminCode(String appTagRepresPublicAdminCode) {
		this.appTagRepresPublicAdminCode = appTagRepresPublicAdminCode;
	}

	public String getAppTagRepresStreet() {
		return appTagRepresStreet;
	}

	public void setAppTagRepresStreet(String appTagRepresStreet) {
		this.appTagRepresStreet = appTagRepresStreet;
	}

	public String getAppTagRepresNumber() {
		return appTagRepresNumber;
	}

	public void setAppTagRepresNumber(String appTagRepresNumber) {
		this.appTagRepresNumber = appTagRepresNumber;
	}

	public String getAppTagRepresBlock() {
		return appTagRepresBlock;
	}

	public void setAppTagRepresBlock(String appTagRepresBlock) {
		this.appTagRepresBlock = appTagRepresBlock;
	}

	public String getAppTagRepresFloor() {
		return appTagRepresFloor;
	}

	public void setAppTagRepresFloor(String appTagRepresFloor) {
		this.appTagRepresFloor = appTagRepresFloor;
	}

	public String getAppTagRepresBuildingNumber() {
		return appTagRepresBuildingNumber;
	}

	public void setAppTagRepresBuildingNumber(String appTagRepresBuildingNumber) {
		this.appTagRepresBuildingNumber = appTagRepresBuildingNumber;
	}

	public String getAppTagRepresStair() {
		return appTagRepresStair;
	}

	public void setAppTagRepresStair(String appTagRepresStair) {
		this.appTagRepresStair = appTagRepresStair;
	}

	public String getAppTagRepresDoor() {
		return appTagRepresDoor;
	}

	public void setAppTagRepresDoor(String appTagRepresDoor) {
		this.appTagRepresDoor = appTagRepresDoor;
	}

	public String getAppTagRepresLocality() {
		return appTagRepresLocality;
	}

	public void setAppTagRepresLocality(String appTagRepresLocality) {
		this.appTagRepresLocality = appTagRepresLocality;
	}

	public String getAppTagRepresLocalityId() {
		return appTagRepresLocalityId;
	}

	public void setAppTagRepresLocalityId(String appTagRepresLocalityId) {
		this.appTagRepresLocalityId = appTagRepresLocalityId;
	}

	public String getAppTagRepresProvince() {
		return appTagRepresProvince;
	}

	public void setAppTagRepresProvince(String appTagRepresProvince) {
		this.appTagRepresProvince = appTagRepresProvince;
	}

	public String getAppTagRepresProvinceId() {
		return appTagRepresProvinceId;
	}

	public void setAppTagRepresProvinceId(String appTagRepresProvinceId) {
		this.appTagRepresProvinceId = appTagRepresProvinceId;
	}

	public String getAppTagRepresZip() {
		return appTagRepresZip;
	}

	public void setAppTagRepresZip(String appTagRepresZip) {
		this.appTagRepresZip = appTagRepresZip;
	}

	public String getAppTagRepresTelephone() {
		return appTagRepresTelephone;
	}

	public void setAppTagRepresTelephone(String appTagRepresTelephone) {
		this.appTagRepresTelephone = appTagRepresTelephone;
	}

	public String getAppTagApplicCif() {
		return appTagApplicCif;
	}

	public void setAppTagApplicCif(String appTagApplicCif) {
		this.appTagApplicCif = appTagApplicCif;
	}

	public String getAppTagApplicKm() {
		return appTagApplicKm;
	}

	public void setAppTagApplicKm(String appTagApplicKm) {
		this.appTagApplicKm = appTagApplicKm;
	}

	public String getAppTagApplicFax() {
		return appTagApplicFax;
	}

	public void setAppTagApplicFax(String appTagApplicFax) {
		this.appTagApplicFax = appTagApplicFax;
	}

	public String getAppTagApplicEmail() {
		return appTagApplicEmail;
	}

	public void setAppTagApplicEmail(String appTagApplicEmail) {
		this.appTagApplicEmail = appTagApplicEmail;
	}

	public String getAppTagRepresNif() {
		return appTagRepresNif;
	}

	public void setAppTagRepresNif(String appTagRepresNif) {
		this.appTagRepresNif = appTagRepresNif;
	}

	public String getAppTagRepresCif() {
		return appTagRepresCif;
	}

	public void setAppTagRepresCif(String appTagRepresCif) {
		this.appTagRepresCif = appTagRepresCif;
	}
	
	public String getAppTagRepresDocTypeId() {
		return appTagRepresDocTypeId;
	}

	public void setAppTagRepresDocTypeId(String appTagRepresDocTypeId) {
		this.appTagRepresDocTypeId = appTagRepresDocTypeId;
	}

	public String getAppTagRepresDocTypeDesc() {
		return appTagRepresDocTypeDesc;
	}

	public void setAppTagRepresDocTypeDesc(String appTagRepresDocTypeDesc) {
		this.appTagRepresDocTypeDesc = appTagRepresDocTypeDesc;
	}

	public String getAppTagRepresKm() {
		return appTagRepresKm;
	}

	public void setAppTagRepresKm(String appTagRepresKm) {
		this.appTagRepresKm = appTagRepresKm;
	}
	
	public String getAppTagApplicType() {
		return appTagApplicType;
	}

	public void setAppTagApplicType(String appTagApplicType) {
		this.appTagApplicType = appTagApplicType;
	}

	public String getAppTagApplicName() {
		return appTagApplicName;
	}

	public void setAppTagApplicName(String appTagApplicName) {
		this.appTagApplicName = appTagApplicName;
	}

	public String getAppTagApplicNif() {
		return appTagApplicNif;
	}

	public void setAppTagApplicNif(String appTagApplicNif) {
		this.appTagApplicNif = appTagApplicNif;
	}
	
	public String getAppTagApplicDocTypeId() {
		return appTagApplicDocTypeId;
	}

	public void setAppTagApplicDocTypeId(String appTagApplicDocTypeId) {
		this.appTagApplicDocTypeId = appTagApplicDocTypeId;
	}

	public String getAppTagApplicDocTypeDesc() {
		return appTagApplicDocTypeDesc;
	}

	public void setAppTagApplicDocTypeDesc(String appTagApplicDocTypeDesc) {
		this.appTagApplicDocTypeDesc = appTagApplicDocTypeDesc;
	}

	public String getAppTagRepresFax() {
		return appTagRepresFax;
	}

	public void setAppTagRepresFax(String appTagRepresFax) {
		this.appTagRepresFax = appTagRepresFax;
	}

	public String getAppTagRepresEmail() {
		return appTagRepresEmail;
	}

	public void setAppTagRepresEmail(String appTagRepresEmail) {
		this.appTagRepresEmail = appTagRepresEmail;
	}

	public String getAppTagApplicAddress() {
		return appTagApplicAddress;
	}

	public void setAppTagApplicAddress(String appTagApplicAddress) {
		this.appTagApplicAddress = appTagApplicAddress;
	}

	public String getAppTagApplicCountry() {
		return appTagApplicCountry;
	}

	public void setAppTagApplicCountry(String appTagApplicCountry) {
		this.appTagApplicCountry = appTagApplicCountry;
	}

	public String getAppTagRepresAddress() {
		return appTagRepresAddress;
	}

	public void setAppTagRepresAddress(String appTagRepresAddress) {
		this.appTagRepresAddress = appTagRepresAddress;
	}

	public String getAppTagRepresCountry() {
		return appTagRepresCountry;
	}

	public void setAppTagRepresCountry(String appTagRepresCountry) {
		this.appTagRepresCountry = appTagRepresCountry;
	}

	/****************************************
	 * Metodos
	 ****************************************/	
	public void reset() {

		this.appTagOnBehalf = "";

		this.appTagApplicType = "";

		this.appTagApplicName = "";
		this.appTagApplicSurnames = "";
		this.appTagApplicSurname1 = "";
		this.appTagApplicSurname2 = "";
		this.appTagApplicNif = "";
		this.appTagApplicDocTypeId = "";
		this.appTagApplicDocTypeDesc = "";
		this.appTagApplicBirthDate = "";

		this.appTagApplicBusinessName = "";
		this.appTagApplicCif = "";
		this.appTagApplicPublicAdmin = "";
		this.appTagApplicPublicAdminCode = "";

		this.appTagApplicAddress = "";
		this.appTagApplicStreet = "";
		this.appTagApplicNumber = "";
		this.appTagApplicBlock = "";
		this.appTagApplicKm = "";
		this.appTagApplicFloor = "";
		this.appTagApplicBuildingNumber = "";
		this.appTagApplicStair = "";
		this.appTagApplicDoor = "";
		this.appTagApplicLocality = "";
		this.appTagApplicLocalityId = "";
		this.appTagApplicProvinceId = "";
		this.appTagApplicProvince = "";
		this.appTagApplicZip = "";
		this.appTagApplicTelephone = "";
		this.appTagApplicFax = "";
		this.appTagApplicEmail = "";
		this.appTagApplicCountry = "";		

		this.appTagRepresType = "";

		this.appTagRepresName = "";
		this.appTagRepresSurnames = "";
		this.appTagRepresSurname1 = "";
		this.appTagRepresSurname2 = "";
		this.appTagRepresNif = "";
		this.appTagRepresDocTypeId = "";
		this.appTagRepresDocTypeDesc = "";		
		this.appTagRepresBusinessName = "";
		this.appTagRepresCif = "";
		this.appTagRepresPublicAdmin = "";
		this.appTagRepresPublicAdminCode = " ";

		this.appTagRepresAddress = "";		
		this.appTagRepresStreet = "";
		this.appTagRepresNumber = "";
		this.appTagRepresBlock = "";
		this.appTagRepresKm = "";
		this.appTagRepresFloor = "";
		this.appTagRepresBuildingNumber = "";
		this.appTagRepresStair = "";
		this.appTagRepresDoor = "";
		this.appTagRepresLocality = "";
		this.appTagRepresLocalityId = "";
		this.appTagRepresProvince = "";
		this.appTagRepresProvinceId = "";
		this.appTagRepresZip = "";
		this.appTagRepresTelephone = "";
		this.appTagRepresFax = "";
		this.appTagRepresEmail = "";
		this.appTagRepresCountry = "";		
	}
	
	public void resetApplicant() {

		this.appTagOnBehalf = "";

		this.appTagApplicType = "";

		this.appTagApplicName = "";
		this.appTagApplicSurnames = "";
		this.appTagApplicSurname1 = "";
		this.appTagApplicSurname2 = "";
		this.appTagApplicNif = "";
		this.appTagApplicDocTypeId = "";
		this.appTagApplicDocTypeDesc = "";
		this.appTagApplicBirthDate = "";

		this.appTagApplicBusinessName = "";
		this.appTagApplicCif = "";
		this.appTagApplicPublicAdmin = "";
		this.appTagApplicPublicAdminCode = "";

		this.appTagApplicAddress = "";
		this.appTagApplicStreet = "";
		this.appTagApplicNumber = "";
		this.appTagApplicBlock = "";
		this.appTagApplicKm = "";
		this.appTagApplicFloor = "";
		this.appTagApplicBuildingNumber = "";
		this.appTagApplicStair = "";
		this.appTagApplicDoor = "";
		this.appTagApplicLocality = "";
		this.appTagApplicLocalityId = "";
		this.appTagApplicProvinceId = "";
		this.appTagApplicProvince = "";
		this.appTagApplicZip = "";
		this.appTagApplicTelephone = "";
		this.appTagApplicFax = "";
		this.appTagApplicEmail = "";
		this.appTagApplicCountry = "";		
	}	

}