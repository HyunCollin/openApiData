package com.api.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MobileOs {
    IOS("IOS","아이폰"),
    AND("AND","안드로이드"),
    WIN("WIN","윈도우폰"),
    ETC("ETC","기타");
    private String code;
    private String name;
}
