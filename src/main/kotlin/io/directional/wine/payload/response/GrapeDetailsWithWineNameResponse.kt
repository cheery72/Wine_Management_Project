package io.directional.wine.payload.response

import io.directional.wine.payload.dto.GrapeDetailsWithWineNameDto

data class GrapeDetailsWithWineNameResponse(

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
) {
    companion object {
        fun GrapeDetailsWithWineNameDto.of() = GrapeDetailsWithWineNameResponse(
            this.grapeNameKorean,
            this.grapeNameEnglish,
            this.grapeAcidity,
            this.grapeBody,
            this.grapeSweetness,
            this.grapeTannin,
            this.grapeRegionNameKorean,
            this.grapeRegionNameEnglish,
            this.grapeWineNameKorean,
            this.grapeWineNameEnglish,
        )
    }
}