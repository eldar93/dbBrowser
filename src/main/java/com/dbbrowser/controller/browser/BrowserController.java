package com.dbbrowser.controller.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.DataPreviewDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/browser/{connId}")
public interface BrowserController {

    String ALL_SCHEMAS_PATH = "/allSchemas";
    String ALL_TABLES_PATH = "/{schemaName}/allTables";
    String ALL_COLUMNS_PATH = "/{schemaName}/{tableName}/allColumns";
    String DATA_PREVIEW_PATH = "/{schemaName}/{tableName}/dataPreview/{page}";

    String DATA_PREVIEW_REL = "data_preview";
    String TABLES_REL = "tables";
    String COLUMNS_REL = "columns";
    String DATA_PREVIEW_LAST_PAGE_REL = "last_page";

    @GetMapping(path = ALL_SCHEMAS_PATH)
    CollectionModel<EntityModel<SchemaDto>> getAllSchemas(@PathVariable String connId);

    @GetMapping(path = ALL_TABLES_PATH)
    CollectionModel<EntityModel<TableDto>> getAllTables(@PathVariable String connId, @PathVariable String schemaName);

    @GetMapping(path = ALL_COLUMNS_PATH)
    CollectionModel<EntityModel<ColumnDto>> getAllColumns(
            @PathVariable String connId, @PathVariable String schemaName, @PathVariable String tableName);

    @GetMapping(path = DATA_PREVIEW_PATH)
    EntityModel<DataPreviewDto> dataPreview(
            @PathVariable String connId, @PathVariable String schemaName,
            @PathVariable String tableName, @PathVariable Integer page);
}
