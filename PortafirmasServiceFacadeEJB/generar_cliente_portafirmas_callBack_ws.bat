set PROJECT=C:\Users\jcasablanca\workspace-APSCT\CallBacksServiceFacadeEJB
set URL_WSDL=C:\Users\jcasablanca\workspace-APSCT\CallBacksServiceFacadeEJB\wsdlNotificacionFirma\NotificacionFirma.wsdl

del /Q /S %PROJECT%\ejbModule\opencanarias\com\services\facade\portafirmas\callBack\v01_00\*.java

set WSDL2JAVA=C:\apache-cxf-2.4.6\bin\wsdl2java
%WSDL2JAVA% -verbose -d %PROJECT%\ejbModule %URL_WSDL% 
