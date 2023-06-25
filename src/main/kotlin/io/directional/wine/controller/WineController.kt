package io.directional.wine.controller

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.dto.UpdateWineRequest
import io.directional.wine.dto.WineDetailsResponse
import io.directional.wine.dto.WineWithTopRegionResponse
import io.directional.wine.service.WineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class WineController(
    private val wineService: WineService,
) {

    @PostMapping("/{wineryId}/{importerId}/wines")
    fun createWine(
        @PathVariable wineryId: Long,
        @PathVariable importerId: Long,
        @RequestBody createWineRequest: CreateWineRequest
    ): ResponseEntity<Unit> {
        wineService.createWine(wineryId,importerId,createWineRequest)

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

    @GetMapping("/wines")
    fun findWineDetails(
        @RequestParam wineType: String,
        @RequestParam alcoholMin: Double,
        @RequestParam alcoholMax: Double,
        @RequestParam priceMin: Int,
        @RequestParam priceMax: Int,
        @RequestParam wineStyle: String?,
        @RequestParam wineGrade: String?,
        @RequestParam wineRegion: String,
    ): ResponseEntity<WineDetailsResponse> {

        return ResponseEntity
            .ok(wineService.findWineDetails(wineType,alcoholMin,alcoholMax,priceMin,priceMax,wineStyle,wineGrade,wineRegion))
        }

    @GetMapping("/wines/all")
    fun findWineWithTopRegion(
        @RequestParam wineType: String,
        @RequestParam alcoholMin: Double,
        @RequestParam alcoholMax: Double,
        @RequestParam priceMin: Int,
        @RequestParam priceMax: Int,
        @RequestParam wineStyle: String?,
        @RequestParam wineGrade: String?,
        @RequestParam wineRegion: String,
    ): ResponseEntity<List<WineWithTopRegionResponse>> {

        return ResponseEntity
            .ok(wineService.findWineWithTopRegion(wineType,alcoholMin,alcoholMax,priceMin,priceMax,wineStyle,wineGrade,wineRegion))
    }
}