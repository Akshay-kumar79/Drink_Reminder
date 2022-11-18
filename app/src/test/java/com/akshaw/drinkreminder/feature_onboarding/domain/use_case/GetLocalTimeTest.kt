package com.akshaw.drinkreminder.feature_onboarding.domain.use_case

import androidx.compose.ui.unit.Constraints
import com.akshaw.drinkreminder.core.domain.use_case.GetLocalTime
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.TimeUnit
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class GetLocalTimeTest {

    private lateinit var getLocalTime: GetLocalTime

    @Before
    fun setUp() {
        getLocalTime = GetLocalTime()
    }

    @Test
    fun `get time with unParsable hour, returns failure`() {
        val time = getLocalTime(13, 22, TimeUnit.AM)
        assertThat(time.isFailure).isTrue()
    }

    @Test
    fun `get time with unParsable minute, returns failure`() {
        val time = getLocalTime(11, 60, TimeUnit.AM)
        assertThat(time.isFailure).isTrue()
    }

    @Test
    fun `get time with unParsable hour and minute, returns failure`() {
        val time = getLocalTime(0, 60, TimeUnit.AM)
        assertThat(time.isFailure).isTrue()
    }

    @Test
    fun `get time with valid hour and minute using AM, returns success`() {
        val time = getLocalTime(11, 59, TimeUnit.AM)
        assertThat(time.isSuccess).isTrue()
        assertThat(time.getOrNull()?.hour).isEqualTo(11)
        assertThat(time.getOrNull()?.minute).isEqualTo(59)
    }

    @Test
    fun `get time with valid hour and minute using PM, returns success`() {
        val time = getLocalTime(11, 59, TimeUnit.PM)
        assertThat(time.isSuccess).isTrue()
        assertThat(time.getOrNull()?.hour).isEqualTo(23)
        assertThat(time.getOrNull()?.minute).isEqualTo(59)
    }

    @Test
    fun `get time with single digit hour and minute using PM, returns success`() {
        val time = getLocalTime(1, 5, TimeUnit.PM)
        assertThat(time.isSuccess).isTrue()
        assertThat(time.getOrNull()?.hour).isEqualTo(13)
        assertThat(time.getOrNull()?.minute).isEqualTo(5)
    }

    @Test
    fun `get time with single digit hour and minute using AM, returns success`() {
        val time = getLocalTime(1, 5, TimeUnit.AM)
        assertThat(time.isSuccess).isTrue()
        assertThat(time.getOrNull()?.hour).isEqualTo(1)
        assertThat(time.getOrNull()?.minute).isEqualTo(5)
    }

    @Test
    fun `get time with negative hour and minute using PM, returns failure`() {
        val time = getLocalTime(-1, -5, TimeUnit.PM)
        assertThat(time.isFailure).isTrue()
    }

    @Test
    fun `get time with negative hour and minute using AM, returns failure`() {
        val time = getLocalTime(-1, -5, TimeUnit.AM)
        assertThat(time.isFailure).isTrue()
    }

}