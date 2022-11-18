package com.akshaw.drinkreminder.feature_water.domain.use_case

import com.akshaw.drinkreminder.core.data.preferences.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import kotlin.math.roundToInt

class GetDrinkProgressTest {

    private lateinit var getDrinkProgress: GetDrinkProgress
    private lateinit var preference: Preferences

    @Before
    fun setUp() {
        preference = FakePreference()
        getDrinkProgress = GetDrinkProgress(preference)
    }

    @Test
    fun `water unit type ML only and preference waterUnit ML, returns correct progress in ML`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.ML)
        )
        preference.saveWaterUnit(WaterUnit.ML)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(773.0.roundToInt())
    }

    @Test
    fun `water unit type ML only and preference waterUnit FL_OZ, returns correct progress in FL_OZ`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.ML)
        )
        preference.saveWaterUnit(WaterUnit.FL_OZ)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(26.13823954852464.roundToInt())
    }

    @Test
    fun `water unit type FL_OZ only and preference waterUnit ML, returns correct progress in ML`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.FL_OZ)
        )
        preference.saveWaterUnit(WaterUnit.ML)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(22860.3383518125.roundToInt())
    }

    @Test
    fun `water unit type FL_OZ only and preference waterUnit FL_OZ, returns correct progress in FL_OZ`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.FL_OZ)
        )
        preference.saveWaterUnit(WaterUnit.FL_OZ)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(773.0.roundToInt())
    }

    @Test
    fun `water unit type mixed only and preference waterUnit ML, returns correct progress in ML`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.ML)
        )
        preference.saveWaterUnit(WaterUnit.ML)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(3316.0441310625.roundToInt())
    }

    @Test
    fun `water unit type mixed only and preference waterUnit FL_OZ, returns correct progress in FL_OZ`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.ML),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.FL_OZ),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.ML)
        )
        preference.saveWaterUnit(WaterUnit.FL_OZ)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(112.1287915280606.roundToInt())
    }

    @Test
    fun `0 drinks and preference waterUnit FL_OZ, returns 0 progress in FL_OZ`(){
        val drinks = mutableListOf<Drink>()
        preference.saveWaterUnit(WaterUnit.FL_OZ)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(0.0.roundToInt())
    }

    @Test
    fun `0 drinks and preference waterUnit ML, returns 0 progress in ML`(){
        val drinks = mutableListOf<Drink>()
        preference.saveWaterUnit(WaterUnit.ML)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(0.0.roundToInt())
    }

    @Test
    fun `water unit type INVALID only and preference waterUnit FL_OZ, returns 0 progress in FL_OZ`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.INVALID)
        )
        preference.saveWaterUnit(WaterUnit.FL_OZ)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(0.0.roundToInt())
    }

    @Test
    fun `water unit type INVALID only and preference waterUnit ML, returns 0 progress in ML`(){
        val drinks = mutableListOf(
            Drink(0, LocalDateTime.now(), 23.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 2.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 532.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 87.0, WaterUnit.INVALID),
            Drink(0, LocalDateTime.now(), 129.0, WaterUnit.INVALID)
        )
        preference.saveWaterUnit(WaterUnit.ML)
        assertThat(getDrinkProgress(drinks).roundToInt()).isEqualTo(0.0.roundToInt())
    }

}