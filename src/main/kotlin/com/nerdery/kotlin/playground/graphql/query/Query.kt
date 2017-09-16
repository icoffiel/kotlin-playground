package com.nerdery.kotlin.playground.graphql.query

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.nerdery.kotlin.playground.graphql.domain.Episode
import com.nerdery.kotlin.playground.graphql.service.DroidService
import com.nerdery.kotlin.playground.graphql.service.HeroService
import com.nerdery.kotlin.playground.graphql.service.HumanService
import javax.inject.Inject

class Query @Inject constructor(private val heroService: HeroService,
                                private val humanService: HumanService,
                                private val droidService: DroidService) : GraphQLQueryResolver {

    fun getHero(episode: Episode?) = heroService.getHero(episode)

    fun getHumans(id: String?) = humanService.getHumans(id)

    fun getDroids(id: String?) = droidService.getDroids(id)

    fun getCharacter(id: String): Nothing = TODO()
}



