package com.api.feature.photogallery.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JacksonXmlRootElement(localName = "OpenAPI_ServiceResponse")
public class OpenApiServiceResponse {

    private CmmMsgHeader cmmMsgHeader;

}



