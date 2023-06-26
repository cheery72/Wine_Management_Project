package io.directional.wine.dto

import com.querydsl.core.annotations.QueryProjection

data class GrapeNamesWithRegions @QueryProjection constructor(
    val grapeNameEnglish: String,
    val grapeNameKorean: String,
    val grapeRegionNameEnglish: String,
    val grapeRegionNameKorean: String
)