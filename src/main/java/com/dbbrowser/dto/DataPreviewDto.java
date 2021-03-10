package com.dbbrowser.dto;

import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Data
@Relation(itemRelation = "preview")
public class DataPreviewDto {

    List<Object[]> tablePreview;
}
