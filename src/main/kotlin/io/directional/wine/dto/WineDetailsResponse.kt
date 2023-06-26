package io.directional.wine.dto

import io.directional.wine.entity.Region as Region

data class WineDetailsResponse (

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
    val wineRegionKoreanName: HashMap<String,List<String>>,
    val wineRegionEnglishName: HashMap<String,List<String>>,
) {
    companion object {
        fun fromWineDetailsResponse(
            wineDetailsDto: WineDetailsDto
            , regionTopList: List<Region>?
        ): WineDetailsResponse {

            val regionKoreanNameMap = hashMapOf<String, List<String>>()
            val regionEnglishNameMap = hashMapOf<String, List<String>>()

            val regionKoreanNameList = regionTopList
                ?.subList(1, regionTopList.size)
                ?.map { it.nameKorean }
                ?: emptyList()

            val regionEnglishNameList = regionTopList
                ?.subList(1, regionTopList.size)
                ?.map { it.nameEnglish }
                ?: emptyList()

            regionEnglishNameMap[wineDetailsDto.wineRegionEnglishName] = regionEnglishNameList
            regionKoreanNameMap[wineDetailsDto.wineRegionKoreanName] = regionKoreanNameList


            return WineDetailsResponse(
                wineType = wineDetailsDto.wineType,
                wineKoreanName = wineDetailsDto.wineKoreanName,
                wineEnglishName = wineDetailsDto.wineEnglishName,
                wineAlcohol = wineDetailsDto.wineAlcohol,
                wineAcidity = wineDetailsDto.wineAcidity,
                wineBody = wineDetailsDto.wineBody,
                wineSweetness = wineDetailsDto.wineSweetness,
                wineTannin = wineDetailsDto.wineTannin,
                wineScore = wineDetailsDto.wineScore,
                winePrice = wineDetailsDto.winePrice,
                wineStyle = wineDetailsDto.wineStyle,
                wineGrade = wineDetailsDto.wineGrade,
                wineImporter = wineDetailsDto.wineImporter,
                aroma = wineDetailsDto.aroma,
                pairing = wineDetailsDto.pairing,
                wineGrapeKoreanName = wineDetailsDto.wineGrapeKoreanName,
                wineGrapeEnglishName = wineDetailsDto.wineGrapeEnglishName,
                wineRegionKoreanName = regionKoreanNameMap,
                wineRegionEnglishName = regionEnglishNameMap,
            )
        }
    }
}