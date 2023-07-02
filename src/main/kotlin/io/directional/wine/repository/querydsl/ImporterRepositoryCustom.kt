package io.directional.wine.repository.querydsl

import io.directional.wine.payload.dto.ImporterWithWineDto

interface ImporterRepositoryCustom {
    fun findImporterNameWithWine(importerName: String): ImporterWithWineDto?
}