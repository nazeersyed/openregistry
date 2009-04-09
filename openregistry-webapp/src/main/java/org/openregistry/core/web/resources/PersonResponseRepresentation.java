package org.openregistry.core.web.resources;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.List;

/**
 * Simple Java Bean encapsulating the representation of persons in the registry
 * for the purpose of exposing it via RESTful resources.
 * This is class is marshalled into an XML representation using JAXB.
 * <p/>
 * This class is immutable
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
@XmlRootElement(name = "open-registry-person")
public class PersonResponseRepresentation implements Serializable {

    @XmlAttribute(name = "activation-generator-uri")
    String activationGeneratorUri;

    @XmlAttribute(name = "activation-processor-uri")
    String activationProcessorUri;

    @XmlAttribute(name = "activation-token")
    String activationToken;

    @XmlElement(name = "id")        
    List<PersonIdentifierRepresentation> identifiers;


    /**
     * Required by JAXB
     */
    PersonResponseRepresentation() {
    }

    PersonResponseRepresentation(String activationGeneratorUri,
                                        String activationProcessorUri,
                                        String activationToken,
                                        List<PersonIdentifierRepresentation> ids) {

        this.activationGeneratorUri = activationGeneratorUri;
        this.activationProcessorUri = activationProcessorUri;
        this.activationToken = activationToken;
        this.identifiers = ids;
    }
}
