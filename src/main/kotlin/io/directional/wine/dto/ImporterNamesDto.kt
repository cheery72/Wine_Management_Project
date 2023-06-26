package io.directional.wine.dto

import io.directional.wine.entity.Importer


data class ImporterNamesDto(
    val importerName: String
) {
    companion object {
        fun fromImporterNameDto(importerList: List<Importer>): List<ImporterNamesDto>{
            return importerList.stream()
                .map { importer ->
                    ImporterNamesDto(
                        importerName = importer.name
                    )
                }
                .toList()
        }
    }
}