
package opencanarias.com.services.facade.gestor.documental.v01_00;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the opencanarias.com.services.facade.gestor.documental.v01_00 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetVersionResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "getVersionResponse");
    private final static QName _EliminarNodoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "eliminarNodoResponse");
    private final static QName _EliminarNodo_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "eliminarNodo");
    private final static QName _ConsultarNodo_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "consultarNodo");
    private final static QName _MoverNodo_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "moverNodo");
    private final static QName _MoverNodoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "moverNodoResponse");
    private final static QName _GestorDocumentalFacadeException_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "GestorDocumentalFacadeException");
    private final static QName _CrearNodo_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "crearNodo");
    private final static QName _ConsultarNodoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "consultarNodoResponse");
    private final static QName _ObtenerVisualizacionDocumento_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "obtenerVisualizacionDocumento");
    private final static QName _ActualizarNodoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "actualizarNodoResponse");
    private final static QName _CrearNodoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "crearNodoResponse");
    private final static QName _ObtenerVisualizacionDocumentoResponse_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "obtenerVisualizacionDocumentoResponse");
    private final static QName _GetVersion_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "getVersion");
    private final static QName _ActualizarNodo_QNAME = new QName("http://com.opencanarias/services/facade/gestor/documental/v01_00", "actualizarNodo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: opencanarias.com.services.facade.gestor.documental.v01_00
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GestorDocumentalFacadeException }
     * 
     */
    public GestorDocumentalFacadeException createGestorDocumentalFacadeException() {
        return new GestorDocumentalFacadeException();
    }

    /**
     * Create an instance of {@link MoverNodoResponse }
     * 
     */
    public MoverNodoResponse createMoverNodoResponse() {
        return new MoverNodoResponse();
    }

    /**
     * Create an instance of {@link ConsultarNodo }
     * 
     */
    public ConsultarNodo createConsultarNodo() {
        return new ConsultarNodo();
    }

    /**
     * Create an instance of {@link MoverNodo }
     * 
     */
    public MoverNodo createMoverNodo() {
        return new MoverNodo();
    }

    /**
     * Create an instance of {@link EliminarNodoResponse }
     * 
     */
    public EliminarNodoResponse createEliminarNodoResponse() {
        return new EliminarNodoResponse();
    }

    /**
     * Create an instance of {@link EliminarNodo }
     * 
     */
    public EliminarNodo createEliminarNodo() {
        return new EliminarNodo();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link ActualizarNodo }
     * 
     */
    public ActualizarNodo createActualizarNodo() {
        return new ActualizarNodo();
    }

    /**
     * Create an instance of {@link ObtenerVisualizacionDocumentoResponse }
     * 
     */
    public ObtenerVisualizacionDocumentoResponse createObtenerVisualizacionDocumentoResponse() {
        return new ObtenerVisualizacionDocumentoResponse();
    }

    /**
     * Create an instance of {@link GetVersion }
     * 
     */
    public GetVersion createGetVersion() {
        return new GetVersion();
    }

    /**
     * Create an instance of {@link CrearNodoResponse }
     * 
     */
    public CrearNodoResponse createCrearNodoResponse() {
        return new CrearNodoResponse();
    }

    /**
     * Create an instance of {@link ObtenerVisualizacionDocumento }
     * 
     */
    public ObtenerVisualizacionDocumento createObtenerVisualizacionDocumento() {
        return new ObtenerVisualizacionDocumento();
    }

    /**
     * Create an instance of {@link ActualizarNodoResponse }
     * 
     */
    public ActualizarNodoResponse createActualizarNodoResponse() {
        return new ActualizarNodoResponse();
    }

    /**
     * Create an instance of {@link ConsultarNodoResponse }
     * 
     */
    public ConsultarNodoResponse createConsultarNodoResponse() {
        return new ConsultarNodoResponse();
    }

    /**
     * Create an instance of {@link CrearNodo }
     * 
     */
    public CrearNodo createCrearNodo() {
        return new CrearNodo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "getVersionResponse")
    public JAXBElement<GetVersionResponse> createGetVersionResponse(GetVersionResponse value) {
        return new JAXBElement<GetVersionResponse>(_GetVersionResponse_QNAME, GetVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarNodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "eliminarNodoResponse")
    public JAXBElement<EliminarNodoResponse> createEliminarNodoResponse(EliminarNodoResponse value) {
        return new JAXBElement<EliminarNodoResponse>(_EliminarNodoResponse_QNAME, EliminarNodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EliminarNodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "eliminarNodo")
    public JAXBElement<EliminarNodo> createEliminarNodo(EliminarNodo value) {
        return new JAXBElement<EliminarNodo>(_EliminarNodo_QNAME, EliminarNodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarNodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "consultarNodo")
    public JAXBElement<ConsultarNodo> createConsultarNodo(ConsultarNodo value) {
        return new JAXBElement<ConsultarNodo>(_ConsultarNodo_QNAME, ConsultarNodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoverNodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "moverNodo")
    public JAXBElement<MoverNodo> createMoverNodo(MoverNodo value) {
        return new JAXBElement<MoverNodo>(_MoverNodo_QNAME, MoverNodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoverNodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "moverNodoResponse")
    public JAXBElement<MoverNodoResponse> createMoverNodoResponse(MoverNodoResponse value) {
        return new JAXBElement<MoverNodoResponse>(_MoverNodoResponse_QNAME, MoverNodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GestorDocumentalFacadeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "GestorDocumentalFacadeException")
    public JAXBElement<GestorDocumentalFacadeException> createGestorDocumentalFacadeException(GestorDocumentalFacadeException value) {
        return new JAXBElement<GestorDocumentalFacadeException>(_GestorDocumentalFacadeException_QNAME, GestorDocumentalFacadeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearNodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "crearNodo")
    public JAXBElement<CrearNodo> createCrearNodo(CrearNodo value) {
        return new JAXBElement<CrearNodo>(_CrearNodo_QNAME, CrearNodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarNodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "consultarNodoResponse")
    public JAXBElement<ConsultarNodoResponse> createConsultarNodoResponse(ConsultarNodoResponse value) {
        return new JAXBElement<ConsultarNodoResponse>(_ConsultarNodoResponse_QNAME, ConsultarNodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerVisualizacionDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "obtenerVisualizacionDocumento")
    public JAXBElement<ObtenerVisualizacionDocumento> createObtenerVisualizacionDocumento(ObtenerVisualizacionDocumento value) {
        return new JAXBElement<ObtenerVisualizacionDocumento>(_ObtenerVisualizacionDocumento_QNAME, ObtenerVisualizacionDocumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarNodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "actualizarNodoResponse")
    public JAXBElement<ActualizarNodoResponse> createActualizarNodoResponse(ActualizarNodoResponse value) {
        return new JAXBElement<ActualizarNodoResponse>(_ActualizarNodoResponse_QNAME, ActualizarNodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrearNodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "crearNodoResponse")
    public JAXBElement<CrearNodoResponse> createCrearNodoResponse(CrearNodoResponse value) {
        return new JAXBElement<CrearNodoResponse>(_CrearNodoResponse_QNAME, CrearNodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerVisualizacionDocumentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "obtenerVisualizacionDocumentoResponse")
    public JAXBElement<ObtenerVisualizacionDocumentoResponse> createObtenerVisualizacionDocumentoResponse(ObtenerVisualizacionDocumentoResponse value) {
        return new JAXBElement<ObtenerVisualizacionDocumentoResponse>(_ObtenerVisualizacionDocumentoResponse_QNAME, ObtenerVisualizacionDocumentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "getVersion")
    public JAXBElement<GetVersion> createGetVersion(GetVersion value) {
        return new JAXBElement<GetVersion>(_GetVersion_QNAME, GetVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizarNodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.opencanarias/services/facade/gestor/documental/v01_00", name = "actualizarNodo")
    public JAXBElement<ActualizarNodo> createActualizarNodo(ActualizarNodo value) {
        return new JAXBElement<ActualizarNodo>(_ActualizarNodo_QNAME, ActualizarNodo.class, null, value);
    }

}
