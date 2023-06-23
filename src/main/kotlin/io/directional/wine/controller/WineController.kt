package io.directional.wine.controller

import io.directional.wine.dto.CreateWineRequest
import io.directional.wine.service.WineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class WineController(
    private val wineService: WineService,
) {

    @PostMapping("/{wineryId}/{regionId}/wines")
    fun createWine(
        @PathVariable wineryId: Long,
        @PathVariable regionId: Long,
        @RequestBody createWineRequest: CreateWineRequest
    ): ResponseEntity<Unit> {
        wineService.createWine(wineryId,regionId,createWineRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}