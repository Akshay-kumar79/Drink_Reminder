package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.akshaw.drinkreminder.core.util.truncateToMinutes
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalTime
import java.util.stream.Stream

class GetLocalTimeTest {
    
    private lateinit var getLocalTime: GetLocalTime
    
    @BeforeEach
    fun setUp() {
        getLocalTime = GetLocalTime()
    }
    
    @ParameterizedTest
    @MethodSource("parsable24HourTimeSource")
    fun `get local time with parsable hour and minute, return success with local time`(
        clock24Hour: Int,
        minute: Int,
        expectedTime: LocalTime?
    ) {
        val result = getLocalTime(clock24Hour, minute)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedTime)
    }
    
    @ParameterizedTest
    @CsvSource(
        "-1, 2",
        "1, -2",
        "-1, -2",
        "-1, ${Int.MIN_VALUE}",
        "-1, ${Int.MAX_VALUE}",
        "${Int.MIN_VALUE}, 2",
        "${Int.MIN_VALUE}, 2",
        "${Int.MIN_VALUE}, ${Int.MIN_VALUE}",
    )
    fun `get local time with non parsable hour or minute, return failure`(
        clock24Hour: Int,
        minute: Int
    ) {
        val result = getLocalTime(clock24Hour, minute)
        assertThat(result.isFailure).isTrue()
    }
    
    @ParameterizedTest
    @MethodSource("parsable12HourTimeSource")
    fun `get local time with parsable hour minute and timeunit, return success with local time`(
        hour: Int,
        minute: Int,
        timeUnit: TimeUnit,
        expectedTime: LocalTime?
    ) {
        val result = getLocalTime(hour, minute, timeUnit)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedTime)
    }
    
    
    @ParameterizedTest
    @CsvSource(
        "23, 12, AM",
        "23, 12, PM",
        "13, 12, AM",
        "13, 12, PM",
        "0, 0, AM",
        "0, 0, PM",
        "-1, 2, AM",
        "-1, 2, PM",
        "1, -2, AM",
        "1, -2, PM",
        "-1, -2, AM",
        "-1, -2, PM",
        "-1, ${Int.MIN_VALUE}, AM",
        "-1, ${Int.MIN_VALUE}, PM",
        "-1, ${Int.MAX_VALUE}, AM",
        "-1, ${Int.MAX_VALUE}, PM",
        "${Int.MIN_VALUE}, 2, AM",
        "${Int.MIN_VALUE}, 2, PM",
        "${Int.MIN_VALUE}, 2, AM",
        "${Int.MIN_VALUE}, 2, PM",
        "${Int.MIN_VALUE}, ${Int.MIN_VALUE}, AM",
        "${Int.MIN_VALUE}, ${Int.MIN_VALUE}, PM",
    )
    fun `get local time with non parsable hour or minute and timeunit, return failure`(
        clock24Hour: Int,
        clock24Minute: Int,
        timeUnit: String
    ) {
        val result = getLocalTime(clock24Hour, clock24Minute, TimeUnit.fromString(timeUnit)!!)
        assertThat(result.isFailure).isTrue()
    }
    
    companion object {
        @JvmStatic
        fun parsable24HourTimeSource(): Stream<Arguments> = Stream.of(
            Arguments.arguments(2, 2, LocalTime.now().withHour(2).withMinute(2).truncateToMinutes()),
            Arguments.arguments(0, 0, LocalTime.now().withHour(0).withMinute(0).truncateToMinutes()),
            Arguments.arguments(0, 12, LocalTime.now().withHour(0).withMinute(12).truncateToMinutes()),
            Arguments.arguments(0, 23, LocalTime.now().withHour(0).withMinute(23).truncateToMinutes()),
            Arguments.arguments(23, 0, LocalTime.now().withHour(23).withMinute(0).truncateToMinutes()),
            Arguments.arguments(23, 22, LocalTime.now().withHour(23).withMinute(22).truncateToMinutes()),
        )
        
        @JvmStatic
        fun parsable12HourTimeSource(): Stream<Arguments> = Stream.of(
            Arguments.arguments(1, 1, TimeUnit.AM, LocalTime.now().withHour(1).withMinute(1).truncateToMinutes()),
            Arguments.arguments(1, 2, TimeUnit.PM, LocalTime.now().withHour(13).withMinute(2).truncateToMinutes()),
            Arguments.arguments(12, 3, TimeUnit.AM, LocalTime.now().withHour(0).withMinute(3).truncateToMinutes()),
            Arguments.arguments(12, 4, TimeUnit.PM, LocalTime.now().withHour(12).withMinute(4).truncateToMinutes()),
            Arguments.arguments(12, 12, TimeUnit.AM, LocalTime.now().withHour(0).withMinute(12).truncateToMinutes()),
            Arguments.arguments(12, 12, TimeUnit.PM, LocalTime.now().withHour(12).withMinute(12).truncateToMinutes()),
            Arguments.arguments(12, 59, TimeUnit.AM, LocalTime.now().withHour(0).withMinute(59).truncateToMinutes()),
            Arguments.arguments(12, 59, TimeUnit.PM, LocalTime.now().withHour(12).withMinute(59).truncateToMinutes()),
        )
    }
}