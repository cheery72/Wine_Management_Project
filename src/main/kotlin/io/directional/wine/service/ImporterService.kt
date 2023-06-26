package io.directional.wine.service

import io.directional.wine.dto.CreateImporterRequest
import io.directional.wine.dto.ImporterNamesDto
import io.directional.wine.dto.ImporterWithWineDto
import io.directional.wine.entity.Importer
import io.directional.wine.exception.ClientException
import io.directional.wine.exception.ErrorCode
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

    @Transactional
    fun updateImporter(importerId: Long, createImporterRequest: CreateImporterRequest) {
        val importer: Importer = findImporter(importerId)

        importer.setImporterInfo(createImporterRequest)
    }

    @Transactional
    fun deleteImporter(importerId: Long) {
        val importer: Importer = findImporter(importerId)

        importer.setDeleted()
    }

    fun findImporterNameWithWine(importerName: String): ImporterWithWineDto? {
        return importerRepository.findImporterNameWithWine(importerName)
    }

    fun findImporterNames(importerName: String): List<ImporterNamesDto> {
        val importerList: List<Importer> = importerRepository
            .findAllByNameLikeAndDeletedFalseOrderByNameAsc("%$importerName%")

        return ImporterNamesDto.fromImporterNameDto(importerList)
    }

    private fun findImporter(importerId: Long): Importer {
        return importerRepository.findByIdAndDeletedFalse(importerId)
            .orElseThrow { ClientException(ErrorCode.NOT_FOUND_IMPORTER) }
    }
}