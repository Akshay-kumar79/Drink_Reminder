package com.akshaw.drinkreminder.waterdomain.use_case

import assertk.assertThat
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.coretest.FakePreference
import com.akshaw.drinkreminder.coretest.repository.FakeWaterRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate

class AddForgottenDrinkTest {
    
    private lateinit var addForgottenDrink: AddForgottenDrink
    private lateinit var getLocalTime: GetLocalTime
    private lateinit var waterRepository: FakeWaterRepository
    private lateinit var addDrink: AddDrink
    private lateinit var preference: FakePreference
    
    @BeforeEach
    fun setUp() {
        getLocalTime = GetLocalTime()
        waterRepository = FakeWaterRepository()
        addDrink = AddDrink(waterRepository)
        preference = FakePreference()
        addForgottenDrink = AddForgottenDrink(getLocalTime, addDrink, preference)
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "24, 42",
        "12, 60",
        "24, 60",
        "22, -42",
        "-12, 42",
        "-12, -42",
    )
    fun `add forgotten drink with non parsable time, returns failure`(
        hour: Int,
        minute: Int
    ) = runBlocking {
        
        val result = addForgottenDrink(hour, minute, LocalDate.now(), 23.0)
        
        assertThat(result.isFailure).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "null",
        "0.0",
        "-212",
        "-1.0",
        "${Int.MIN_VALUE}",
        "-100000000000000000000000.0",
        nullValues = ["null"]
    )
    fun `add forgotten drink invalid quantity, returns failure`(
        quantity: Double?,
    ) = runBlocking {
        
        val result = addForgottenDrink(12, 12, LocalDate.now(), quantity)
        
        assertThat(result.isFailure).isTrue()
    }
    
    @ParameterizedTest
    @CsvSource(
        "23, 42, 0.1",
        "12, 59, 2131",
        "0, 0, ${Int.MAX_VALUE}",
        "0, 42, 122334423",
        "23, 0, 10000000000000000000000000000000000000",
    )
    fun `add forgotten drink correct values, returns success`(
        hour: Int,
        minute: Int,
        quantity: Double,
    ) = runBlocking {
        
        val result = addForgottenDrink(hour, minute, LocalDate.now(), quantity)
        
        assertThat(result.isSuccess).isTrue()
    }
}