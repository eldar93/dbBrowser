package com.dbbrowser.service.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import com.dbbrowser.entity.connection.ConnectionEntity;
import com.dbbrowser.exception.connection.ConnectionNotFoundException;
import com.dbbrowser.repository.browser.BrowserDAO;
import com.dbbrowser.repository.connection.ConnectionRepository;
import com.dbbrowser.routing.holder.ConnContextHolder;
import com.dbbrowser.routing.token.ConnConfig;
import com.dbbrowser.validation.ConnectionValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MySQLBrowserServiceImpl implements BrowserService {

    // mysql example -- "jdbc:mysql://hostname:port/dbname"
    private static final String CONNECTION_PATTERN = "jdbc:mysql://%s:%s/%s";
    public static int PAGE_SIZE = 10;

    @Autowired
    private final BrowserDAO browserDAO;
    @Autowired
    private final ConnectionRepository repository;
    @Autowired
    private final ConnectionValidationService validationService;

    @Override
    public List<SchemaDto> getAllSchemas(String connId) {
        ConnConfig config = buildConnectionConfig(connId);
        ConnContextHolder.set(config);
        List<SchemaDto> schemas = browserDAO.getAllSchemas();
        ConnContextHolder.clear();
        return schemas;
    }

    @Override
    public List<ColumnDto> getAllColumns(String connId, String schemaName, String tableName) {
        ConnConfig config = buildConnectionConfig(connId);
        ConnContextHolder.set(config);
        List<ColumnDto> columns = browserDAO.getAllColumns(schemaName, tableName);
        ConnContextHolder.clear();
        return columns;
    }

    @Override
    public List<TableDto> getAllTables(String connId, String schemaName) {
        ConnConfig config = buildConnectionConfig(connId);
        ConnContextHolder.set(config);
        List<TableDto> tables = browserDAO.getAllTables(schemaName);
        ConnContextHolder.clear();
        return tables;
    }

    @Override
    public Page<Object[]> dataPreview(String connId, String schemaId, String tableName, Integer page) {
        ConnConfig config = buildConnectionConfig(connId);
        ConnContextHolder.set(config);
        PageRequest pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Object[]> tableData = browserDAO.getDataPreview(schemaId, tableName, pageable);
        ConnContextHolder.clear();
        return tableData;
    }

    private ConnectionEntity getConnectionEntity(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ConnectionNotFoundException(id));
    }

    private ConnConfig buildConnectionConfig(String connId) {
        ConnectionEntity connEntity = getConnectionEntity(connId);
        validationService.validatePort(connEntity.getPort());
        ConnConfig config = new ConnConfig(connEntity.getName(), buildConnectionUrl(connEntity),
                connEntity.getUsername(),
                connEntity.getPassword());
        return config;
    }

    private String buildConnectionUrl(ConnectionEntity connEntity) {
        return String.format(CONNECTION_PATTERN,
                connEntity.getHostname(), connEntity.getPort(), connEntity.getDatabaseName());
    }

}
