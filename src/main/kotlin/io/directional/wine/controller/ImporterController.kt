package io.directional.wine.controller

import io.directional.wine.dto.CreateImporterRequest
import io.directional.wine.service.ImporterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ImporterController(
    private val importerService: ImporterService
) {

    @PostMapping("/importers")
    fun createImporter(
        @RequestBody createImporterRequest: CreateImporterRequest
    ): ResponseEntity<Unit> {

        importerService.createImporter(createImporterRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}