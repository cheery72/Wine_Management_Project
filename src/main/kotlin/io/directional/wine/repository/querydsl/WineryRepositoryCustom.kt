package io.directional.wine.repository.querydsl

import io.directional.wine.dto.WineryWithRegionDto

interface WineryRepositoryCustom {

    fun findWineryWithRegion(wineryName: String, wineryRegion: String): WineryWithRegionDto?
}