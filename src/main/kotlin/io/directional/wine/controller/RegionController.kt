package io.directional.wine.controller

import io.directional.wine.dto.CreateRegionRequest
import io.directional.wine.service.RegionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


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

}