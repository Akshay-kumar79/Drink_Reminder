package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.core.domain.preferences.Preferences
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import com.akshaw.drinkreminder.core.util.AddSomeDrinksException
import com.akshaw.drinkreminder.core.util.InvalidDrinkQuantityException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DrinkNowTest {
    
    private lateinit var waterRepository: FakeWaterRepository
    private lateinit var preferences: Preferences
    private lateinit var addDrink: AddDrink
    private lateinit var drinkNow: DrinkNow
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        preferences = FakePreference()
        addDrink = AddDrink(waterRepository)
        drinkNow = DrinkNow(preferences, addDrink)
    }
    
    @ParameterizedTest
    @CsvSource(
        "ml",
        "fl oz"
    )
    fun `trackableDrink with id -1, returns failure with AddSomeDrinksException`(
        unit: String
    ) = runBlocking {
        val result = drinkNow(
            TrackableDrink(-1L, 0.0, WaterUnit.fromString(unit)!!)
        )
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(AddSomeDrinksException().message)
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "0.0, ml",
        "-1.0, ml",
        "-21212.0, ml",
        "-10.0, ml",
        "${Int.MIN_VALUE}, ml",
        "0.0, fl oz",
        "-1.0, fl oz",
        "-21212.0, fl oz",
        "-10.0, fl oz",
        "${Int.MIN_VALUE}, fl oz",
    )
    fun `trackableDrink with invalid amount, returns failure with InvalidDrinkQuantityException`(
        quantity: Double,
        unit: String
    ) = runBlocking {
        val result = drinkNow(
            TrackableDrink(1L, quantity, WaterUnit.fromString(unit)!!)
        )
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(InvalidDrinkQuantityException().message)
    }
    
    @ParameterizedTest
    @CsvSource(
        "1.0, ml",
        "10.0, ml",
        "100000.0, ml",
        "234234353121.0, ml",
        "${Int.MAX_VALUE}, ml",
        "1.0, fl oz",
        "10.0, fl oz",
        "100000.0, fl oz",
        "234234353121.0, fl oz",
        "${Int.MAX_VALUE}, fl oz",
    )
    fun `genuine trackableDrink, returns success with id`(
        quantity: Double,
        unit: String
    ) {
        runBlocking {
            val result = drinkNow(
                TrackableDrink(0L, quantity, WaterUnit.fromString(unit)!!)
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrNull()).isEqualTo(-1)
        }
    }
}