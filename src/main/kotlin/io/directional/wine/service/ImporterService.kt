package io.directional.wine.service

import io.directional.wine.dto.CreateImporterRequest
import io.directional.wine.entity.Importer
import io.directional.wine.repository.ImporterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ImporterService(
    private val importerRepository: ImporterRepository
) {
    @Transactional
    fun createImporter(createImporterRequest: CreateImporterRequest) {

        val importer: Importer = Importer.toEntity(createImporterRequest)

        importerRepository.save(importer)
    }

}