package io.directional.wine.dto


data class RecursiveRegionDto (
    val regionId: Long,
    val nameEnglish: String,
    val nameKorean: String,
    val parentId: Long?,
) {
    companion object {
        fun fromRecursiveRegionDTO(recursiveRegion: List<Any?>): List<RecursiveRegionDto> {
            val resultList = mutableListOf<RecursiveRegionDto>()

            for (row in recursiveRegion) {
                if (row is Array<*>) {
                    val regionId = row.getOrNull(0) as? Long
                    val nameEnglish = row.getOrNull(4) as? String
                    val nameKorean = row.getOrNull(5) as? String
                    val parentId = row.getOrNull(6) as? Long

                    if (regionId != null && nameEnglish != null && nameKorean != null) {
                        val recursiveRegionDTO = RecursiveRegionDto(regionId, nameEnglish, nameKorean, parentId)
                        resultList.add(recursiveRegionDTO)
                    }
                }
            }

            return resultList
        }
    }
}