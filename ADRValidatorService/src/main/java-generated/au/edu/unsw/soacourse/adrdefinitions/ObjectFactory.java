
package au.edu.unsw.soacourse.adrdefinitions;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.adrdefinitions package. 
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

    private final static QName _ADRInfoMessage_QNAME = new QName("http://soacourse.unsw.edu.au/adrdefinitions", "ADRInfoMessage");
    private final static QName _ValidationMessage_QNAME = new QName("http://soacourse.unsw.edu.au/adrdefinitions", "ValidationMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.adrdefinitions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ADRInputType }
     * 
     */
    public ADRInputType createADRInputType() {
        return new ADRInputType();
    }

    /**
     * Create an instance of {@link ValidationType }
     * 
     */
    public ValidationType createValidationType() {
        return new ValidationType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ADRInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/adrdefinitions", name = "ADRInfoMessage")
    public JAXBElement<ADRInputType> createADRInfoMessage(ADRInputType value) {
        return new JAXBElement<ADRInputType>(_ADRInfoMessage_QNAME, ADRInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/adrdefinitions", name = "ValidationMessage")
    public JAXBElement<ValidationType> createValidationMessage(ValidationType value) {
        return new JAXBElement<ValidationType>(_ValidationMessage_QNAME, ValidationType.class, null, value);
    }

}
