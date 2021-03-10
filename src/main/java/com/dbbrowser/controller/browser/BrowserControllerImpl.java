package com.dbbrowser.controller.browser;

import com.dbbrowser.dto.ColumnDto;
import com.dbbrowser.dto.DataPreviewDto;
import com.dbbrowser.dto.SchemaDto;
import com.dbbrowser.dto.TableDto;
import com.dbbrowser.service.browser.BrowserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class BrowserControllerImpl implements BrowserController {

    @Autowired
    private final BrowserService browserService;

    @Override
    public CollectionModel<EntityModel<SchemaDto>> getAllSchemas(String connId) {
        return StreamSupport.stream(browserService.getAllSchemas(connId).spliterator(), false) //
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(BrowserController.class)
                                .getAllTables(connId, dto.getName())).withRel(TABLES_REL))) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(linkTo(methodOn(BrowserController.class).getAllSchemas(connId)).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<TableDto>> getAllTables(String connId, String schemaName) {
        return StreamSupport.stream(browserService.getAllTables(connId, schemaName).spliterator(), false) //
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(BrowserController.class)
                                .getAllColumns(connId, schemaName, dto.getName())).withRel(COLUMNS_REL),
                        linkTo(methodOn(BrowserController.class)
                                //add a link to the first page
                                .dataPreview(connId, schemaName, dto.getName(), 0)).withRel(DATA_PREVIEW_REL))) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(linkTo(methodOn(BrowserController.class).getAllTables(connId, schemaName)).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<ColumnDto>> getAllColumns(String connId, String schemaName, String tableName) {
        return StreamSupport.stream(browserService.getAllColumns(connId, schemaName, tableName).spliterator(), false) //
                .map(dto -> EntityModel.of(dto)) //
                .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of))
                .add(linkTo(methodOn(BrowserController.class)
                        //add a link to the first page
                        .dataPreview(connId, schemaName, tableName, 0)).withRel(DATA_PREVIEW_REL))
                .add(linkTo(methodOn(BrowserController.class)
                        .getAllColumns(connId, schemaName, tableName)).withSelfRel());
    }

    @Override
    public EntityModel<DataPreviewDto> dataPreview(String connId, String schemaName, String tableName, Integer page) {
        Page tableDataPage = browserService.dataPreview(connId, schemaName, tableName, page);
        DataPreviewDto preview = new DataPreviewDto();
        preview.setTablePreview(tableDataPage.getContent());
        int totalPages = tableDataPage.getTotalPages();
        int lastPageIndex = totalPages > 0 ? totalPages - 1 : totalPages;
        return EntityModel.of(preview,
                linkTo(methodOn(BrowserController.class)
                        .dataPreview(connId, schemaName, tableName, lastPageIndex))
                        .withRel(DATA_PREVIEW_LAST_PAGE_REL),
                linkTo(methodOn(BrowserController.class)
                        .getAllColumns(connId, schemaName, tableName)).withRel(COLUMNS_REL),
                linkTo(methodOn(BrowserController.class)
                        .dataPreview(connId, schemaName, tableName, page)).withSelfRel());
    }
}
