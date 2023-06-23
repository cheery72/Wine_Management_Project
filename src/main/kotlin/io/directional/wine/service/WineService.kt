package io.directional.wine.service

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.entity.Region
import io.directional.wine.entity.Wine
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
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
    private val regionRepository: RegionRepository,
) {

    @Transactional
    fun createWine(wineryId: Long,regionId: Long, createWineRequest: CreateWineRequest) {
        val winery = findWinery(wineryId)
        val region = findRegion(regionId)
        val wine = Wine.toEntity(createWineRequest, winery, region)

        wineRepository.save(wine)
    }

    fun findWinery(wineryId: Long): Winery {
        return wineryRepository.findById(wineryId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_WINERY)}
    }

    fun findRegion(regionId: Long): Region {
        return regionRepository.findById(regionId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_REGION)}
    }

}