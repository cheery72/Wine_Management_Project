package io.directional.wine.service

import io.directional.wine.dto.*
import io.directional.wine.entity.Region
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.repository.RegionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RegionService(
    private val regionRepository: RegionRepository
) {
    @Transactional
    fun createRegion(createRegionRequest: CreateRegionRequest) {
        val parentRegion: Region? = findParentRegion(createRegionRequest.regionParentId)

        val region = Region.toEntity(createRegionRequest, parentRegion)

        regionRepository.save(region)

    }

    @Transactional
    fun updateRegion(regionId: Long, createRegionRequest: CreateRegionRequest) {
        val region: Region = findRegion(regionId)

        val parentRegion: Region? = findParentRegion(createRegionRequest.regionParentId)

        region.setRegionInfo(createRegionRequest, parentRegion)
    }

    @Transactional
    fun deleteRegion(regionId: Long) {
        val region: Region = findRegion(regionId)

        region.setDeleted()
    }

    fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsResponse? {
        val regionDetails: RegionDetailsDto? = regionRepository.findRegionDetails(regionName, parentRegion)

        val regions: List<Region>? = regionDetails?.let { regionRepository.findByRegionTopList(it.regionId) }

        return RegionDetailsResponse.fromRegionDetailResponse(regionDetails, regions)
    }

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesDto> {
        return regionRepository.findRegionsName(regionName, parentRegion)
    }

    private fun findRegion(regionId: Long): Region {
        return regionRepository.findByIdAndDeletedFalse(regionId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_REGION) }
    }

    private fun findParentRegion(parentId: Long?): Region? {
        return parentId?.let { regionRepository.findById(it).orElse(null) }
    }
}