package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class WineDetailsDto @QueryProjection constructor(
    val wineType: String,
    val wineKoreanName: String,
    val wineEnglishName: String,
    val wineAlcohol: Double,
    val wineAcidity: Int,
    val wineBody: Int,
    val wineSweetness: Int,
    val wineTannin: Int,
    val wineScore: Double,
    val winePrice: Int,
    val wineStyle: String?,
    val wineGrade: String?,
    val wineImporter: String,
    val aroma: String,
    val pairing: String,
    val wineGrapeKoreanName: String,
    val wineGrapeEnglishName: String,
    val wineRegionKoreanName: String,
    val wineRegionEnglishName: String,
    val wineryId: Long,
    val regionId: Long,
    )