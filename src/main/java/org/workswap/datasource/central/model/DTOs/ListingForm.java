package org.workswap.datasource.central.model.DTOs;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ListingForm {
    private Map<String, ListingTranslationDTO> translations = new HashMap<>();
}
