package io.directional.wine.repository.querydsl

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import io.directional.wine.entity.*
import io.directional.wine.payload.dto.QRegionDetailsDto
import io.directional.wine.payload.dto.RegionDetailsDto
import io.directional.wine.payload.response.QRegionNamesResponse
import io.directional.wine.payload.response.RegionNamesResponse

class RegionRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val qRegion: QRegion = QRegion.region,
    private val qWinery: QWinery = QWinery.winery,
    private val qGrapeShare: QGrapeShare = QGrapeShare.grapeShare,
    private val qGrape: QGrape = QGrape.grape,
    private val qWine: QWine = QWine.wine,

    ) : RegionRepositoryCustom {
    override fun findByRegionTopList(regionId: Long): List<Region> {

        val subQuery = jpaQueryFactory
            .select(qRegion)
            .from(qRegion)
            .where(qRegion.id.eq(regionId))
            .fetchFirst()

        val regions = mutableListOf<Region>()

        if (subQuery != null) {
            addParentRegions(subQuery, regions)
            regions.reverse()
        }

        return regions
    }

    private fun addParentRegions(region: Region?, regions: MutableList<Region>) {
        if (region != null) {
            regions.add(region)
            addParentRegions(region.parent, regions)
        }
    }

    override fun findRegionDetails(regionName: String, parentRegion: String): RegionDetailsDto? {

        return jpaQueryFactory
            .select(
                QRegionDetailsDto(
                    qRegion.nameEnglish,
                    qRegion.nameKorean,
                    qGrape.nameEnglish,
                    qGrape.nameKorean,
                    qWinery.nameEnglish,
                    qWinery.nameKorean,
                    qWine.nameEnglish,
                    qWine.nameKorean,
                    qRegion.id
                )
            ).from(qRegion)
            .join(qRegion.grapeShare, qGrapeShare)
            .join(qGrapeShare.grape, qGrape)
            .join(qRegion.winery, qWinery)
            .join(qWinery.wine, qWine)
            .where(
                qRegion.deleted.isFalse.and(
                    qGrape.deleted.isFalse
                        .and(qWinery.deleted.isFalse.and(qWine.deleted.isFalse))
                )
                    .and(qRegion.nameEnglish.eq(regionName).or(qRegion.nameKorean.eq(regionName)))
                    .and(qRegion.parent.nameEnglish.eq(parentRegion).or(qRegion.parent.nameKorean.eq(parentRegion)))
            )
            .orderBy(qRegion.nameEnglish.asc())
            .fetchFirst()
    }

    override fun findRegionsName(regionName: String, parentRegion: String): List<RegionNamesResponse> {

        val regionNameExpression = Expressions.asString("$regionName%")

        return jpaQueryFactory
            .select(
                QRegionNamesResponse(
                    qRegion.nameEnglish,
                    qRegion.nameKorean
                )
            ).from(qRegion)
            .where(
                qRegion.deleted.isFalse
                    .and(
                        qRegion.nameEnglish.like(regionNameExpression)
                            .or(qRegion.nameKorean.like(regionNameExpression))
                            .and(
                                qRegion.parent.nameEnglish.eq(parentRegion)
                                    .or(qRegion.parent.nameKorean.eq(parentRegion))
                            )
                    )
            )
            .orderBy(qRegion.nameEnglish.asc())
            .fetch()
    }
}