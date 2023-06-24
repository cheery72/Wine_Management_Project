package io.directional.wine.controller

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.dto.UpdateWineRequest
import io.directional.wine.service.WineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class WineController(
    private val wineService: WineService,
) {

    @PostMapping("/{wineryId}/{regionId}/{importerId}/wines")
    fun createWine(
        @PathVariable wineryId: Long,
        @PathVariable regionId: Long,
        @PathVariable importerId: Long,
        @RequestBody createWineRequest: CreateWineRequest
    ): ResponseEntity<Unit> {
        wineService.createWine(wineryId,regionId,importerId,createWineRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{wineId}/wines")
    fun updateWine(
        @PathVariable wineId: Long,
        @RequestBody updateWineRequest: UpdateWineRequest
    ): ResponseEntity<Unit> {
        wineService.updateWine(wineId, updateWineRequest)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @DeleteMapping("/{wineId}/wines")
    fun deleteWine(
        @PathVariable wineId: Long
    ): ResponseEntity<Unit> {
        wineService.deleteWine(wineId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}