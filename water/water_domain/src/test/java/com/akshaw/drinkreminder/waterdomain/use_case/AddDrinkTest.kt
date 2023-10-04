package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.repository.WaterRepository
import com.akshaw.drinkreminder.core.util.InvalidDrinkQuantityException
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime


class AddDrinkTest {
    
    private lateinit var addDrink: AddDrink
    private lateinit var waterRepository: WaterRepository
    
    @BeforeEach
    fun setUp() {
        waterRepository = FakeWaterRepository()
        addDrink = AddDrink(waterRepository)
    }
    
    @ParameterizedTest
    @CsvSource(
        "ml, 1.0",
        "fl oz, 100000.0"
    )
    fun `add drink with correct quantity, returns success`(
        drinkUnit: String,
        amount: Double
    ) = runBlocking {
        val result = addDrink(
            Drink(
                dateTime = LocalDateTime.now(),
                waterIntake = amount,
                unit = WaterUnit.fromString(drinkUnit)!!
            )
        )
        assertThat(result.isSuccess).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "ml, 0.0",
        "ml, -1.0",
        "ml, -1000000.0",
        "fl oz, 0.0",
        "fl oz, -1.0",
        "fl oz, -100000.0",
    )
    fun `add drink with invalid quantity, returns failure with InvalidDrinkQuantityException`(
        drinkUnit: String,
        amount: Double
    ) = runBlocking {
        val result = addDrink(
            Drink(
                dateTime = LocalDateTime.now(),
                waterIntake = 0.0,
                unit = WaterUnit.fromString(drinkUnit)!!
            )
        )
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()!!.message).isEqualTo(InvalidDrinkQuantityException().message)
    }
    
}
    
