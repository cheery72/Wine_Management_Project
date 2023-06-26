package io.directional.wine.dto

data class RegionDetailsResponse(

    val regionNameEnglish: String,
    val regionNameKorean: String,
    val regionTopNameEnglishList: List<String>,
    val regionTopNameKoreanList: List<String>,
    val regionGrapeNameEnglish: String,
    val regionGrapeNameKorean: String,
    val regionWineryNameEnglish: String,
    val regionWineryNameKorean: String,
    val regionWineNameEnglish: String,
    val regionWineNameKorean: String,
) {

    companion object {
        fun fromRegionDetailResponse(regionDetails:RegionDetailsDto,recursiveRegions: List<RecursiveRegionDto>): RegionDetailsResponse{
            val regionEnglishList: MutableList<String> = mutableListOf()
            val regionKoreanList: MutableList<String> = mutableListOf()

            recursiveRegions.forEach { r ->
                regionEnglishList.add(r.nameEnglish)
                regionKoreanList.add(r.nameKorean)
            }

            return RegionDetailsResponse(
                regionNameEnglish = regionDetails.regionNameEnglish,
                regionNameKorean = regionDetails.regionNameKorean,
                regionGrapeNameEnglish = regionDetails.regionGrapeNameEnglish,
                regionGrapeNameKorean = regionDetails.regionGrapeNameKorean,
                regionWineryNameEnglish = regionDetails.regionWineryNameEnglish,
                regionWineryNameKorean = regionDetails.regionWineNameKorean,
                regionWineNameEnglish = regionDetails.regionWineNameEnglish,
                regionWineNameKorean = regionDetails.regionWineryNameKorean,
                regionTopNameEnglishList = regionEnglishList,
                regionTopNameKoreanList = regionKoreanList,
            )
        }
    }
}