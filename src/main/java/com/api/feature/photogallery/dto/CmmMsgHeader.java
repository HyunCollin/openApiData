package com.api.feature.photogallery.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmmMsgHeader {

    @JacksonXmlProperty(localName = "errMsg")
    private String errMsg;

    @JacksonXmlProperty(localName = "returnAuthMsg")
    private String returnAuthMsg;

    @JacksonXmlProperty(localName = "returnReasonCode")
    private int returnReasonCode;

}