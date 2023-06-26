package io.directional.wine.controller

import io.directional.wine.payload.request.CreateRegionRequest
import io.directional.wine.payload.response.RegionDetailsResponse
import io.directional.wine.payload.response.RegionNamesResponse
import io.directional.wine.service.RegionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1")
class RegionController(
    private val regionService: RegionService
) {

    @PostMapping("/regions")
    fun createRegion(
        @RequestBody createRegionRequest: CreateRegionRequest
    ): ResponseEntity<Unit> {

        regionService.createRegion(createRegionRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{regionId}/regions")
    fun updateRegion(
        @PathVariable regionId: Long,
        @RequestBody createRegionRequest: CreateRegionRequest
    ): ResponseEntity<Unit> {

        regionService.updateRegion(regionId, createRegionRequest)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @DeleteMapping("/{regionId}/regions")
    fun deleteRegion(
        @PathVariable regionId: Long
    ): ResponseEntity<Unit> {

        regionService.deleteRegion(regionId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @GetMapping("/regions")
    fun findRegionDetails(
        @RequestParam regionName: String,
        @RequestParam parentRegion: String,
    ): ResponseEntity<RegionDetailsResponse> {

        return ResponseEntity
            .ok(regionService.findRegionDetails(regionName, parentRegion))
    }

    @GetMapping("/regions/all")
    fun findRegionsName(
        @RequestParam regionName: String,
        @RequestParam parentRegion: String,
    ): ResponseEntity<List<RegionNamesResponse>> {

        return ResponseEntity
            .ok(regionService.findRegionsName(regionName, parentRegion))
    }
}