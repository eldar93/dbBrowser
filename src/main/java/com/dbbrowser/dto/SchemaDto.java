package com.dbbrowser.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.server.core.Relation;

@Data
@Accessors(chain = true)
@Relation(itemRelation = "schema", collectionRelation = "schemas")
public class SchemaDto {

    private String name;
    private String defaultCharset;
    private String defaultCollation;

}
