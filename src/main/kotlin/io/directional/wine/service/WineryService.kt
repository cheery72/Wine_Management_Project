package io.directional.wine.service

import io.directional.wine.dto.CreateWineryRequest
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

    fun findRegion(regionId: Long): Region {
        return regionRepository.findByIdAndDeletedFalse(regionId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_REGION) }
    }
}