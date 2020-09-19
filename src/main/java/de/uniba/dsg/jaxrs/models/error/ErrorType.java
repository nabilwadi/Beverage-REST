package de.uniba.dsg.jaxrs.models.error;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum()
public enum ErrorType {
    INVALID_PARAMETER,
    FORBIDDEN
}
