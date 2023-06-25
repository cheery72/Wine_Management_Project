package io.directional.wine.repository.querydsl

import io.directional.wine.dto.WineDetailsDto


interface WineRepositoryCustom {

    fun findWineDetails(
        wineType: String, alcoholMin: Double, alcoholMax: Double, priceMin: Int, priceMax: Int,
        wineStyle: String?, wineGrade: String?, wineRegion: String): WineDetailsDto?

}