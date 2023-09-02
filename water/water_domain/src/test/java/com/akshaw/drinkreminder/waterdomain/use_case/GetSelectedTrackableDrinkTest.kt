package com.akshaw.drinkreminder.waterdomain.use_case

import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSelectedTrackableDrinkTest {

    private lateinit var getSelectedTrackableDrink: GetSelectedTrackableDrink
    private lateinit var preferences: Preferences

    @Before
    fun setUp() {
        preferences = FakePreference()
        getSelectedTrackableDrink = GetSelectedTrackableDrink(preferences)
    }

    @Test
    fun `more than 3 ML unit trackable drinks and default selected id, returns middle indexed trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
            add(TrackableDrink(2, 60.0, WaterUnit.ML))
            add(TrackableDrink(3, 80.0, WaterUnit.ML))
            add(TrackableDrink(4, 100.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(2, 60.0, WaterUnit.ML))
        }
    }

    @Test
    fun `3 ML unit trackable drinks and default selected id, returns 2nd trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
            add(TrackableDrink(2, 60.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(1, 30.0, unit))
        }
    }

    @Test
    fun `2 ML unit trackable drinks and default selected id, returns 2st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(1, 30.0, unit))
        }
    }

    @Test
    fun `1 ML unit trackable drinks and default selected id, returns 1st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `more than 3 FL_OZ unit trackable drinks and default selected id, returns middle indexed trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
            add(TrackableDrink(2, 60.0, WaterUnit.FL_OZ))
            add(TrackableDrink(3, 80.0, WaterUnit.FL_OZ))
            add(TrackableDrink(4, 100.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(2, 60.0, unit))
        }
    }

    @Test
    fun `3 FL_OZ unit trackable drinks and default selected id, returns 2nd trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
            add(TrackableDrink(2, 60.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
        
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(1, 30.0, unit))
        }
    }

    @Test
    fun `2 FL_OZ unit trackable drinks and default selected id, returns 2st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(1, 30.0, unit))
        }
    }

    @Test
    fun `1 FL_OZ unit trackable drinks and default selected id, returns 1st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `0 trackable drinks and default selected id, returns trackable drink with id -1`() {
        val trackableDrinks = mutableListOf<TrackableDrink>()
        val unit = WaterUnit.ML
    
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(-1, 0.0, unit))
        }
    }

    @Test
    fun `0 trackable drinks and default selected id and water unit FL_OZ, returns trackable drink with id -1`() {
        val trackableDrinks = mutableListOf<TrackableDrink>()
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(-1, 0.0, unit))
        }
    }

    @Test
    fun `more than 3 ML unit trackable drinks and 4 as selected id, returns middle indexed trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
            add(TrackableDrink(2, 60.0, WaterUnit.ML))
            add(TrackableDrink(3, 80.0, WaterUnit.ML))
            add(TrackableDrink(4, 100.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(4)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(4, 100.0, unit))
        }
    }

    @Test
    fun `3 ML unit trackable drinks and 0 as selected id, returns 2nd trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
            add(TrackableDrink(2, 60.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(0)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `2 ML unit trackable drinks and 0 as selected id, returns 2st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
            add(TrackableDrink(1, 30.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(0)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `1 ML unit trackable drinks and 0 as selected id, returns 1st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.ML))
        }
        val unit = WaterUnit.ML
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(0)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `more than 3 FL_OZ unit trackable drinks and 1 as selected id, returns middle indexed trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
            add(TrackableDrink(2, 60.0, WaterUnit.FL_OZ))
            add(TrackableDrink(3, 80.0, WaterUnit.FL_OZ))
            add(TrackableDrink(4, 100.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(1)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(1, 30.0, unit))
        }
    }

    @Test
    fun `3 FL_OZ unit trackable drinks and 2 as selected id, returns 2nd trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
            add(TrackableDrink(2, 60.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(2)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(2, 60.0, unit))
        }
    }

    @Test
    fun `2 FL_OZ unit trackable drinks and 0 as selected id, returns 2st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
            add(TrackableDrink(1, 30.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(0)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

    @Test
    fun `1 FL_OZ unit trackable drinks and 0 as selected id, returns 1st trackable drink`() {
        val trackableDrinks = mutableListOf<TrackableDrink>().apply {
            add(TrackableDrink(0, 10.0, WaterUnit.FL_OZ))
        }
        val unit = WaterUnit.FL_OZ
    
        runBlocking {
            preferences.saveSelectedTrackableDrinkId(0)
            assertThat(getSelectedTrackableDrink(trackableDrinks, unit)).isEqualTo(TrackableDrink(0, 10.0, unit))
        }
    }

}