package au.edu.unsw.soacourse.adrvalidator;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-05-30T21:10:30.308+10:00
 * Generated source version: 3.0.4
 * 
 */
@WebService(targetNamespace = "http://soacourse.unsw.edu.au/adrvalidator", name = "ADRValidationPT")
@XmlSeeAlso({au.edu.unsw.soacourse.adrdefinitions.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ADRValidationPT {

    @WebResult(name = "ValidationMessage", targetNamespace = "http://soacourse.unsw.edu.au/adrdefinitions", partName = "adrresp")
    @WebMethod(action = "http://soacourse.unsw.edu.au/adrvalidator/validate")
    public au.edu.unsw.soacourse.adrdefinitions.ValidationType validate(
        @WebParam(partName = "adrreq", name = "ADRInfoMessage", targetNamespace = "http://soacourse.unsw.edu.au/adrdefinitions")
        au.edu.unsw.soacourse.adrdefinitions.ADRInputType adrreq
    );
}
