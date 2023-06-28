package io.directional.wine.repository.querydsl

import io.directional.wine.payload.response.WineryWithRegionResponse
import io.directional.wine.payload.response.WineryWithRegionWithWineNameResponse

interface WineryRepositoryCustom {

    fun findWineryWithRegion(wineryName: String, wineryRegion: String): WineryWithRegionWithWineNameResponse?

    fun findWineryWithRegionAll(wineryName: String, wineryRegion: String): List<WineryWithRegionResponse>
}