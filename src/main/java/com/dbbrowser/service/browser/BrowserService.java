package com.dbbrowser.service.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrowserService {

    List<SchemaDto> getAllSchemas(String connId);

    List<ColumnDto> getAllColumns(String connId, String schemaName, String tableName);

    List<TableDto> getAllTables(String connId, String schemaName);

    Page<Object[]> dataPreview(String connId, String schemaId, String tableName, Integer page);
}
