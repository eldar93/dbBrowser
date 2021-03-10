package com.dbbrowser.repository.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional("dynamicDSTransactionManager")
public class MySQLBrowserDAOImpl implements BrowserDAO {

    private static final String SELECT_ALL_SCHEMAS =
            "select * from INFORMATION_SCHEMA.SCHEMATA";
    private static final String SELECT_TABLES_WITH_PROPERTIES =
            "select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = ?";
    private static final String SELECT_ALL_COLUMNS =
            "select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = ? and TABLE_NAME = ?";
    //public static final String CATALOG_NAME_LABEL = "TABLE_CAT";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SchemaDto> getAllSchemas() {
        List<SchemaDto> schemas = jdbcTemplate.query(
                SELECT_ALL_SCHEMAS, //
                (rs, rowNum) -> {
                    SchemaDto schemaDto = new SchemaDto();
                    schemaDto.setName(rs.getString("SCHEMA_NAME"))
                            .setDefaultCharset(rs.getString("DEFAULT_CHARACTER_SET_NAME"))
                            .setDefaultCollation(rs.getString("DEFAULT_COLLATION_NAME"));
                    return schemaDto;
                });

        return schemas;
    }

    @Override
    public List<TableDto> getAllTables(String schemaName) {
        List<TableDto> tables = jdbcTemplate.query(
                SELECT_TABLES_WITH_PROPERTIES, //
                (rs, rowNum) -> {
                    TableDto tableDto = new TableDto();
                    tableDto.setName(rs.getString("TABLE_NAME"))
                            .setSchemaName(rs.getString("TABLE_SCHEMA"))
                            .setTableType(rs.getString("TABLE_TYPE"))
                            .setEngine(rs.getString("ENGINE"))
                            .setVersion(rs.getLong("VERSION"))
                            .setRowFormat(rs.getString("ROW_FORMAT"))
                            .setTableRows(rs.getLong("TABLE_ROWS"))
                            .setAvgRowLength(rs.getLong("AVG_ROW_LENGTH"))
                            .setDataLength(rs.getLong("DATA_LENGTH"))
                            .setMaxDataLength(rs.getLong("MAX_DATA_LENGTH"))
                            .setIndexLength(rs.getLong("INDEX_LENGTH"))
                            .setAutoIncrement(rs.getLong("AUTO_INCREMENT"))
                            .setCreateTime(rs.getDate("CREATE_TIME"))
                            .setCollation(rs.getString("TABLE_COLLATION"))
                            .setTableComment(rs.getString("TABLE_COMMENT"));
                    return tableDto;
                }, //
                schemaName);

        return tables;
    }

    @Override
    public List<ColumnDto> getAllColumns(String schemaName, String tableName) {
        List<ColumnDto> columns = jdbcTemplate.query(
                SELECT_ALL_COLUMNS, //
                (rs, rowNum) -> {
                    ColumnDto columnDto = new ColumnDto();
                    columnDto.setName(rs.getString("COLUMN_NAME"))
                    .setDatatype(rs.getString("COLUMN_TYPE"))
                    .setColumnKey(rs.getString("COLUMN_KEY"))
                    .setIsNullable(rs.getString("IS_NULLABLE"))
                    .setAdditionalInfo(rs.getString("EXTRA"));
                    return columnDto;
                }, //
                schemaName, tableName);

        return columns;
    }

    @Override
    public Page<Object[]> getDataPreview(String schemaName, String tableName, Pageable page) {
        String fullSelectName = schemaName + "." + tableName;
        List<Object[]> tablePreview = jdbcTemplate.queryForList(
                "SELECT * FROM " + fullSelectName
                        + " LIMIT " + page.getPageSize()
                        + " OFFSET " + page.getOffset())
                .stream().map(row -> row.values().toArray())
                .collect(Collectors.toList());
        return new PageImpl<Object[]>(tablePreview, page, getRowCount(fullSelectName));
    }

    private Integer getRowCount(String tableName) {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM " + tableName, Integer.class);
    }

    private Connection getConnection() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

    /*
    public List<SchemaDto> getSchemasNames() {
        List<SchemaDto> schemas = new ArrayList<>();
        try (Connection conn = this.getConnection();
             ResultSet resultSet = conn.getMetaData().getCatalogs()) {
            while (resultSet.next()) {
                String schemaName = resultSet.getString(CATALOG_NAME_LABEL);
                schemas.add(new SchemaDto().setName(schemaName));
            }
        } catch (SQLException e) {
            // handle it
            throw new RuntimeException(e);
        }

        return schemas;
    }
    */
}
