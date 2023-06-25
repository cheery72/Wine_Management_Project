package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class WineWithTopRegionDto @QueryProjection constructor(
    val wineType: String,
    val wineKoreanName: String,
    val wineEnglishName: String,
    val regionId: Long,
)