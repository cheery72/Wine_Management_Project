package io.directional.wine.repository.querydsl

import io.directional.wine.dto.WineDetailsDto
import io.directional.wine.dto.WineWithTopRegionDto


interface WineRepositoryCustom {

    fun findWineDetails(
        wineName: String, wineType: String, alcoholMin: Double, alcoholMax: Double, priceMin: Int, priceMax: Int,
        wineStyle: String?, wineGrade: String?, wineRegion: String
    ): WineDetailsDto?

    fun findWineWithTopRegion(
        wineName: String, wineType: String, alcoholMin: Double, alcoholMax: Double,
        priceMin: Int, priceMax: Int, wineStyle: String?, wineGrade: String?, wineRegion: String
    ): List<WineWithTopRegionDto>
}