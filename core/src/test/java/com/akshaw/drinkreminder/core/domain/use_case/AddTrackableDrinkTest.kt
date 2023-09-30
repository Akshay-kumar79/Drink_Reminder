package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AddTrackableDrinkTest {
    
    private lateinit var waterRepository: WaterRepository
    private lateinit var addTrackableDrink: AddTrackableDrink
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        addTrackableDrink = AddTrackableDrink(waterRepository)
    }
    
    companion object {
        private const val DRINK_INVALID = "Drink quantity isn't valid"
        private const val DRINK_ALREADY_EXIST = "Drink with same quantity already exist"
    }
    
    @ParameterizedTest
    @CsvSource(
        "0.0, ml",
        "-0.000000000000000000000000000000000001, ml",
        "-1.0, ml",
        "-1000.0, ml",
        "0.0, fl oz",
        "-0.000000000000000000000000000000000001, fl oz",
        "-1.0, fl oz",
        "-1000.0, fl oz"
    )
    fun `add trackable drink amount less than and equal to 0, returns failure Drink invalid`(
        drinkAmount: Double,
        waterUnit: String,
    ) {
        runBlocking {
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(0)
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo(DRINK_INVALID)
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(0)
        }
    }
    
    @ParameterizedTest
    @CsvSource(
        "1.0, ml",
        "0.000000000000000000000000000000000001, ml",
        "1000.0, ml",
        "1.0, fl oz",
        "0.000000000000000000000000000000000001, fl oz",
        "1000.0, fl oz",
    )
    fun `add trackable drink amount grater than 0, returns success`(
        drinkAmount: Double,
        waterUnit: String,
    ) {
        runBlocking {
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(0)
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
        }
    }
    
    @ParameterizedTest
    @CsvSource(
        "1.0, ml",
        "0.000000000000000000000000000000000001, ml",
        "1000.0, ml",
        "10000000000000000000000000000000000.0, ml",
        "1.0, fl oz",
        "0.000000000000000000000000000000000001, fl oz",
        "1000.0, fl oz",
        "10000000000000000000000000000000000.0, fl oz"
    )
    fun `add trackable drink when same already exist, return failure Drink already exist`(
        drinkAmount: Double,
        waterUnit: String,
    ) {
        runBlocking {
            waterRepository.insertTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()!!.message).isEqualTo(DRINK_ALREADY_EXIST)
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
        }
    }
    
    @ParameterizedTest
    @CsvSource(
        "1.0, ml, fl oz",
        "0.000000000000000000000000000000000001, ml, fl oz",
        "1000.0, ml, fl oz",
        "10000000000000000000000000000000000.0, ml, fl oz",
        "1.0, fl oz, ml",
        "0.000000000000000000000000000000000001, fl oz, ml",
        "1000.0, fl oz, ml",
        "10000000000000000000000000000000000.0, fl oz, ml"
    )
    fun `add drink when same amount already exist but different unit, return success`(
        drinkAmount: Double,
        waterUnit: String,
        existingWaterUnit: String
    ) {
        runBlocking {
            waterRepository.insertTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(existingWaterUnit)!!
                )
            )
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(2)
        }
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "1.0, ml, 0.0",
        "0.000000000000000000000000000000000001, ml, 232",
        "1000.0, ml, 1.02222222212",
        "10000000000000000000000000000000000.0, ml, 1000",
        "1.0, fl oz, 323423",
        "0.000000000000000000000000000000000001, fl oz, 0.000000000000000000000000000000001",
        "1000.0, fl oz, 0.0",
        "10000000000000000000000000000000000.0, fl oz, -0.000000000000000000000000000000001"
    )
    fun `add drink when same unit already exist but different amount, return success`(
        drinkAmount: Double,
        waterUnit: String,
        existingWaterAmount: Double
    ) {
        runBlocking {
            waterRepository.insertTrackableDrink(
                TrackableDrink(
                    amount = existingWaterAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
            val result = addTrackableDrink(
                TrackableDrink(
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(2)
        }
    }
    
    @ParameterizedTest
    @CsvSource(
        "2, 0.0000000000000000000000000000001, ml",
        "-423, 1000.0, ml",
        "32, 0.0000000000000000000000000000001, fl oz",
        "0, 1000.0, fl oz",
    )
    fun `add correct trackable drink, returns correct id with success`(
        id: Long?,
        drinkAmount: Double,
        waterUnit: String,
    ) {
        runBlocking {
            val result = addTrackableDrink(
                TrackableDrink(
                    id = id,
                    amount = drinkAmount,
                    unit = WaterUnit.fromString(waterUnit)!!
                )
            )
            assertThat(result.isSuccess).isTrue()
            assertThat(waterRepository.getAllTrackableDrinks().first().size).isEqualTo(1)
            assertThat(result.getOrNull()).isEqualTo(id)
        }
    }
    
}