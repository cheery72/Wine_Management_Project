package io.directional.wine.repository.querydsl

import io.directional.wine.payload.response.ImporterWithWineResponse

interface ImporterRepositoryCustom {
    fun findImporterNameWithWine(importerName: String): ImporterWithWineResponse?
}