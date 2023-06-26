package io.directional.wine.repository.querydsl

import io.directional.wine.dto.ImporterWithWineDto

interface ImporterRepositoryCustom {
    fun findImporterNameWithWine(importerName: String): ImporterWithWineDto?
}