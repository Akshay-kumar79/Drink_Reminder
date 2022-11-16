package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import org.junit.Assert.*

import org.junit.Before

class GetADayDrinksTest {

    private lateinit var getADayDrinks: GetADayDrinks
    private val fakeWaterRepository = FakeWaterRepository()

    @Before
    fun setUp() {
        getADayDrinks = GetADayDrinks()
    }
}