package com.akshaw.drinkreminder.core.domain.use_case

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FilterOutDigitsTest {
    
    private lateinit var filterOutDigits: FilterOutDigits
    
    @BeforeEach
    fun setUp() {
        filterOutDigits = FilterOutDigits()
    }
    
    @ParameterizedTest
    @CsvSource(
        "32323f2niL321 ~ 323232321",
        "sndaksndkaskd nskdnakjsndk anskdna skndkj asnkjdns jnadkj naskdn akjsndk j2 ~ 2",
        "-1 ~ 1",
        "90p090p0 ~ 900900",
        "0 ~ 0",
        "32323f2niLMDa;sl,;s1;2e;sfmMPqs]'s'].]a;,;asm3 ~ 323232123",
        delimiter = '~'
    )
    fun `filter digits, returns expected output`(
        input: String,
        expectedOutput: String
    ) {
        filterOutDigits(input).let {
            assertThat(it).isEqualTo(expectedOutput)
        }
    }
    
}