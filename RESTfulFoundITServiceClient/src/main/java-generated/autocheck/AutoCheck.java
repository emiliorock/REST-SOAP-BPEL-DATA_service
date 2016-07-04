package autocheck;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.4
 * 2016-05-30T21:09:19.065+10:00
 * Generated source version: 3.0.4
 * 
 */
@WebService(targetNamespace = "http://autoCheck", name = "autoCheck")
@XmlSeeAlso({au.edu.unsw.soacourse.adrdefinitions.ObjectFactory.class, ObjectFactory.class, au.edu.unsw.soacourse.dldefinitions.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface AutoCheck {

    @WebResult(name = "autoCheckResponse", targetNamespace = "http://autoCheck", partName = "autoResponse")
    @WebMethod(action = "http://soacourse.unsw.edu.au/autoCheck/process")
    public AutoCheckResponse process(
        @WebParam(partName = "autoRequest", name = "autoCheckRequest", targetNamespace = "http://autoCheck")
        AutoCheckRequest autoRequest
    );
}
