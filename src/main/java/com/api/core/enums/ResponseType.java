package com.api.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
    JSON("json", "JSON"),
    XML("xml", "XML");
    private String code;
    private String name;
}
