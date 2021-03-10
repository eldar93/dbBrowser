package com.dbbrowser.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.server.core.Relation;

import java.sql.Date;

@Data
@Accessors(chain = true)
@Relation(itemRelation = "table", collectionRelation = "tables")
public class TableDto {

    private String name;
    private String schemaName;
    private String tableType;
    private String engine;
    private Long version;
    private String rowFormat;
    private Long tableRows;
    private Long avgRowLength;
    private Long dataLength;
    private Long maxDataLength;
    private Long indexLength;
    private Long autoIncrement;
    private Date createTime;
    private String collation;
    private String tableComment;
}
