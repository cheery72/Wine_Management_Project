package io.directional.wine.controller

import io.directional.wine.dto.CreateGrapeRequest
import io.directional.wine.service.GrapeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class GrapeController(
    private val grapeService: GrapeService
) {

    @PostMapping("/grapes")
    fun createGrape(
        @RequestBody createGrapeRequest: CreateGrapeRequest
    ): ResponseEntity<Unit> {

        grapeService.createGrape(createGrapeRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}