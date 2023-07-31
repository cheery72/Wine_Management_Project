package io.directional.wine.service

import io.directional.wine.payload.request.CreateRegionRequest
import io.directional.wine.payload.response.RegionDetailsResponse
import io.directional.wine.payload.response.RegionNamesResponse
import io.directional.wine.entity.Region
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.payload.dto.RegionDetailsDto
import io.directional.wine.payload.dto.RegionParentDto
import io.directional.wine.payload.response.RegionNamesResponse.Companion.of
import io.directional.wine.repository.RegionRepository
import org.springframework.cache.annotation.Cacheable
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

        val regions: List<RegionParentDto>? = regionDetails?.let { findRegionList(it.regionId) }

        return RegionDetailsResponse.fromRegionDetailResponse(regionDetails, regions)
    }
    private fun findRegionList(regionId: Long): List<RegionParentDto> {

        val regions = mutableListOf<RegionParentDto>()
        val region = findParentRegion(regionId)

        if (region != null) {
            addParentRegions(region, regions)
            regions.reverse()
        }

        return regions
    }

    private fun addParentRegions(region: RegionParentDto, regions: MutableList<RegionParentDto>) {
        regions.add(region)

        val parentId = region.parentId

        if (parentId != null) {
            val parentRegion = findParentRegion(parentId)
            if (parentRegion != null) {
                addParentRegions(parentRegion, regions)
            }
        }
    }
    private fun findParentRegion(parentId: Long): RegionParentDto?  {
        return regionRepository.findByRegionTopList(parentId)
    }

    fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesResponse> {
        return regionRepository.findRegionsName(regionName, parentRegion).of()
    }

    private fun findRegion(regionId: Long): Region {
        return regionRepository.findByIdAndDeletedFalse(regionId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_REGION) }
    }

    private fun findParentRegion(parentId: Long?): Region? {
        return parentId?.let { regionRepository.findById(it).orElse(null) }
    }
}