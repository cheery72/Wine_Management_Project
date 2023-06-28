package io.directional.wine.entity

import io.directional.wine.payload.request.CreateImporterRequest
import jakarta.persistence.*

@Entity
@Table(name = "importer", indexes =[
    Index(name = "idx_importer_name", columnList = "name")
])
class Importer(

    @Id
    @Column(name = "importer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,


    @Column(name = "name")
    var name: String,

    var deleted: Boolean = false,

    @OneToMany(mappedBy = "importer")
    val wines: List<Wine> = emptyList(),

    ) : BaseTime() {
    companion object {
        fun toEntity(createImporterRequest: CreateImporterRequest): Importer {
            return Importer(
                name = createImporterRequest.importerName
            )
        }
    }

    fun setImporterInfo(createImporterRequest: CreateImporterRequest) {
        this.name = createImporterRequest.importerName
    }

    fun setDeleted() {
        this.deleted = true
    }
}