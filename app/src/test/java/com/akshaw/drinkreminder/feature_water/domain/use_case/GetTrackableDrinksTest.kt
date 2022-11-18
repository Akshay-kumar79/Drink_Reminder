package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.data.preferences.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.data.repository.FakeWaterRepository
import com.akshaw.drinkreminder.feature_water.domain.model.TrackableDrink
import com.akshaw.drinkreminder.feature_water.domain.repository.WaterRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetTrackableDrinksTest {

    private lateinit var getTrackableDrinks: GetTrackableDrinks
    private lateinit var preferences: Preferences
    private lateinit var waterRepository: WaterRepository

    @Before
    fun setUp() {
        preferences = FakePreference()
        waterRepository = FakeWaterRepository()
        getTrackableDrinks = GetTrackableDrinks(preferences, waterRepository)
    }

    @Test
    fun `mixed trackable drinks and preference unit ML, returns ML drinks only`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.ML)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(5)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.ML }})
            }
        }
    }

    @Test
    fun `ML trackable drinks only and preference unit ML, returns ML drinks only`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.ML)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(4)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.ML }})
            }
        }
    }

    @Test
    fun `FL_OZ trackable drinks only and preference unit ML, returns 0 drinks`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.ML)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(0)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.ML }})
            }
        }
    }


    @Test
    fun `mixed trackable drinks and preference unit FL_OZ, returns FL_OZ drinks only`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 30.0, WaterUnit.ML))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 50.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 70.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 80.0, WaterUnit.ML))
            add(TrackableDrink(0, 90.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.FL_OZ)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(5)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.FL_OZ }})
            }
        }
    }

    @Test
    fun `ML trackable drinks only and preference unit FL_OZ, returns 0 drinks`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(0, 60.0, WaterUnit.ML))
            add(TrackableDrink(0, 100.0, WaterUnit.ML))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.FL_OZ)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(0)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.FL_OZ }})
            }
        }
    }

    @Test
    fun `FL_OZ trackable drinks only and preference unit FL_OZ, returns FL_OZ drinks only`(){
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 20.0, WaterUnit.FL_OZ))
            add(TrackableDrink(0, 40.0, WaterUnit.FL_OZ))
        }

        runBlocking {
            trackableDrinks.forEach {
                waterRepository.insertTrackableDrink(it)
            }
        }
        preferences.saveWaterUnit(WaterUnit.FL_OZ)

        runBlocking {
            getTrackableDrinks().collectLatest { drinks ->
                assertThat(drinks.size).isEqualTo(2)
                assertThat(drinks).isEqualTo(trackableDrinks.apply { removeIf { it.unit != WaterUnit.FL_OZ }})
            }
        }
    }


}