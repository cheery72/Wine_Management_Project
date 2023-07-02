package io.directional.wine.repository.querydsl

import io.directional.wine.payload.dto.WineryWithRegionDto
import io.directional.wine.payload.dto.WineryWithRegionWithWineNameDto

interface WineryRepositoryCustom {

    fun findWineryWithRegion(wineryName: String, wineryRegion: String): WineryWithRegionWithWineNameDto?

    fun findWineryWithRegionAll(wineryName: String, wineryRegion: String): List<WineryWithRegionDto>
}