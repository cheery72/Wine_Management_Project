package io.directional.wine.payload.response

import com.querydsl.core.annotations.QueryProjection

data class GrapeNamesWithRegionsResponse @QueryProjection constructor(
    val grapeNameEnglish: String,
    val grapeNameKorean: String,
    val grapeRegionNameEnglish: String,
    val grapeRegionNameKorean: String
)