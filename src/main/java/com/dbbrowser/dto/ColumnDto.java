package com.dbbrowser.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.server.core.Relation;

@Data
@Accessors(chain = true)
@Relation(itemRelation = "column", collectionRelation = "columns")
public class ColumnDto {

    private String name;
    private String datatype;
    private String isNullable;
    private String columnKey;
    private String additionalInfo;
}
