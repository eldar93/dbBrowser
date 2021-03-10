package com.dbbrowser.repository.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrowserDAO {

    List<SchemaDto> getAllSchemas();
    List<ColumnDto> getAllColumns(String schemaName, String tableName);
    List<TableDto> getAllTables(String schemaName);
    Page<Object[]> getDataPreview(String schemaName, String tableName, Pageable page);
}
