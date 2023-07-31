package io.directional.wine.service

import io.directional.wine.payload.request.CreateWineRequest
import io.directional.wine.payload.request.UpdateWineRequest
import io.directional.wine.payload.response.WineDetailsResponse
import io.directional.wine.payload.response.WineWithTopRegionResponse
import io.directional.wine.entity.Importer
import io.directional.wine.entity.Region
import io.directional.wine.entity.Wine
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.payload.dto.RegionParentDto
import io.directional.wine.payload.dto.WineDetailsDto
import io.directional.wine.payload.dto.WineWithTopRegionDto
import io.directional.wine.repository.ImporterRepository
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineRepository
import io.directional.wine.repository.WineryRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

@Service
@Transactional(readOnly = true)
class WineService(
    private val wineRepository: WineRepository,
    private val wineryRepository: WineryRepository,
    private val importerRepository: ImporterRepository,
    private val regionRepository: RegionRepository,
) {

    @Transactional
    fun createWine(wineryId: Long, importerId: Long, createWineRequest: CreateWineRequest) {
        val winery = findWinery(wineryId)
        val importer = findImporter(importerId)
        val wine = Wine.toEntity(createWineRequest, winery, importer)

        wineRepository.save(wine)
    }

    @Transactional
    fun updateWine(wineId: Long, updateWineRequest: UpdateWineRequest) {
        val wine = findWine(wineId)
        val winery = findWinery(updateWineRequest.wineryId)
        val importer = findImporter(updateWineRequest.importerId)

        wine.setWineInfo(updateWineRequest, winery, importer)

    }

    @Transactional
    fun deleteWine(wineId: Long) {
        val wine = findWine(wineId)

        wine.setDeleted()
    }

    fun findWineDetails(
        wineName: String, wineType: String, alcoholMin: Double, alcoholMax: Double,
        priceMin: Int, priceMax: Int, wineStyle: String?, wineGrade: String?, wineRegion: String
    )
            : WineDetailsResponse? {
        val wineDetailsDto: WineDetailsDto? = wineRepository.findWineDetails(
            wineName, wineType, alcoholMin, alcoholMax, priceMin,
            priceMax, wineStyle, wineGrade, wineRegion
        )

        val regionTopList = wineDetailsDto?.let { findRegionList(it.regionId) }

        return wineDetailsDto?.let { regionTopList?.let { it1 -> WineDetailsResponse.fromWineDetailsResponse(it, it1) } }
    }

    fun findWineWithTopRegion(
        wineName: String, wineType: String, alcoholMin: Double, alcoholMax: Double,
        priceMin: Int, priceMax: Int, wineStyle: String?, wineGrade: String?, wineRegion: String
    )
            : List<WineWithTopRegionResponse> {

        val wineWithTopRegionDtoList: List<WineWithTopRegionDto> = wineRepository.findWineWithTopRegion(
            wineName, wineType, alcoholMin, alcoholMax, priceMin, priceMax,
            wineStyle, wineGrade, wineRegion
        )

        val topRegions: ConcurrentHashMap<Long, RegionParentDto> = findTopRegions(wineWithTopRegionDtoList)

        return WineWithTopRegionResponse.fromWineWithTopRegionResponse(wineWithTopRegionDtoList, topRegions)
    }

    private fun findTopRegions(wineWithTopRegionDtoList: List<WineWithTopRegionDto>): ConcurrentHashMap<Long, RegionParentDto> {
        val regionTopMap: ConcurrentHashMap<Long, RegionParentDto> = ConcurrentHashMap()

        wineWithTopRegionDtoList.forEach { wineWithTopRegionDto ->
            if (regionIdContainsKey(regionTopMap,wineWithTopRegionDto.regionId)) {
                val findRegionList = findRegionList(wineWithTopRegionDto.regionId)

                findRegionList.forEach { region ->
                    if (regionIdContainsKey(regionTopMap,region.regionId)) {
                        val topRegionName = RegionParentDto.fromRegionParentDto(
                                                    region.regionId,region.nameKorean,
                                                    region.nameEnglish,region.parentId)
                        regionTopMap.putIfAbsent(region.regionId, topRegionName)
                    }
                }
            }
        }

        return regionTopMap
    }

    private fun regionIdContainsKey(regionTopMap: ConcurrentHashMap<Long,RegionParentDto>, regionId: Long?): Boolean{
        return !regionTopMap.containsKey(regionId)
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

    fun findParentRegion(parentId: Long): RegionParentDto?  {
        return regionRepository.findByRegionTopList(parentId)
    }

    private fun findWine(wineId: Long): Wine {
        return wineRepository.findByIdAndDeletedFalse(wineId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_WINE) }
    }

    private fun findImporter(importerId: Long): Importer {
        return importerRepository.findByIdAndDeletedFalse(importerId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_IMPORTER) }
    }

    private fun findWinery(wineryId: Long): Winery {
        return wineryRepository.findByIdAndDeletedFalse(wineryId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_WINERY) }
    }

}