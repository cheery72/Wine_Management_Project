package io.directional.wine.service

import io.directional.wine.dto.CreateWineryRequest
import io.directional.wine.dto.UpdateWineryRequest
import io.directional.wine.entity.Region
import io.directional.wine.entity.Winery
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
import io.directional.wine.repository.RegionRepository
import io.directional.wine.repository.WineryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WineryService(
    private val wineryRepository: WineryRepository,
    private val regionRepository: RegionRepository,
) {

    @Transactional
    fun createWinery(regionId: Long, createWineryRequest: CreateWineryRequest) {
        val region: Region = findRegion(regionId)

        val winery: Winery = Winery.toEntity(createWineryRequest, region)

        wineryRepository.save(winery)
    }

    @Transactional
    fun updateWinery(wineryId: Long, updateWineryRequest: UpdateWineryRequest){
        val winery: Winery = findWinery(wineryId)
        val region: Region = findRegion(updateWineryRequest.regionId)

        winery.setWineryInfo(updateWineryRequest, region)
    }

    @Transactional
    fun deleteWinery(wineryId: Long) {
        val winery: Winery = findWinery(wineryId)

        winery.setDeleted()
    }

    private fun findRegion(regionId: Long): Region {
        return regionRepository.findByIdAndDeletedFalse(regionId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_REGION) }
    }

    private fun findWinery(wineryId: Long): Winery {
        return wineryRepository.findByIdAndDeletedFalse(wineryId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_WINERY)}
    }
}