package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class GrapeNamesWithRegionsDto @QueryProjection constructor(
    val grapeNameEnglish: String,
    val grapeNameKorean: String,
    val grapeRegionNameEnglish: String,
    val grapeRegionNameKorean: String
)