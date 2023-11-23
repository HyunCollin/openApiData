package com.api.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Arrange {
    GAL_CREATED_TIME("A","촬영일"),
    SUBJECT("B","제목"),
    GAL_MODIFIED_TIME("C","수정일");
    private String code;
    private String name;
}
