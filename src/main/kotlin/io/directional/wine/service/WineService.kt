package io.directional.wine.service

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.dto.RecursiveRegionDto
import io.directional.wine.dto.UpdateWineRequest
import io.directional.wine.dto.WineDetailsResponse
import io.directional.wine.entity.Importer
import io.directional.wine.entity.Wine
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.repository.ImporterRepository
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineRepository
import io.directional.wine.repository.WineryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        wineType: String, alcoholMin: Double, alcoholMax: Double,
        priceMin: Int, priceMax: Int, wineStyle: String?, wineGrade: String?, wineRegion: String)
    : WineDetailsResponse?{
        val wineDetailsDto = wineRepository.findWineDetails(wineType, alcoholMin, alcoholMax, priceMin, priceMax,
                                                            wineStyle, wineGrade, wineRegion)

        val recursiveRegionDTO = findRegionList(wineDetailsDto!!.regionId)

        return WineDetailsResponse.fromWineDetailsResponse(wineDetailsDto, recursiveRegionDTO)
    }

    private fun findRegionList(regionId: Long): List<RecursiveRegionDto> {
        return regionRepository.findByIdRecursiveRegions(regionId)
    }

    private fun findWine(wineId: Long): Wine {
        return wineRepository.findByIdAndDeletedFalse(wineId)
            .orElseThrow{ ClientException(ErrorCode.NOT_FOUND_WINE)}
    }

    private fun findImporter(importerId: Long): Importer {
        return importerRepository.findByIdAndDeletedFalse(importerId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_IMPORTER)}
    }

    private fun findWinery(wineryId: Long): Winery {
        return wineryRepository.findByIdAndDeletedFalse(wineryId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_WINERY)}
    }

}