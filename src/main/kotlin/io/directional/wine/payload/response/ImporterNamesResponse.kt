package io.directional.wine.payload.response

import io.directional.wine.entity.Importer


data class ImporterNamesResponse(
    val importerName: String
) {
    companion object {
        fun fromImporterNameDto(importerList: List<Importer>): List<ImporterNamesResponse> {
            return importerList.stream()
                .map { importer ->
                    ImporterNamesResponse(
                        importerName = importer.name
                    )
                }
                .toList()
        }
    }
}