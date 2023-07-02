package io.directional.wine.payload.dto

import com.querydsl.core.annotations.QueryProjection

data class GrapeDetailsWithWineNameDto @QueryProjection constructor(

    val grapeNameKorean: String,
    val grapeNameEnglish: String,
    val grapeAcidity: Int,
    val grapeBody: Int,
    val grapeSweetness: Int,
    val grapeTannin: Int,
    val grapeRegionNameKorean: String,
    val grapeRegionNameEnglish: String,
    val grapeWineNameKorean: String,
    val grapeWineNameEnglish: String

)