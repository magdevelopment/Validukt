package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
class RegexRuleTest {

    private val errorMessage = "Text is doesn't match regular expression"
    private lateinit var regexRule: RegexRule

    @Before
    fun setUp() {
        regexRule = RegexRule("\\d+", errorMessage)
    }

    @Test
    fun whenTextDoesntMatchRegex_shouldReturnFailure() {
        val result = regexRule.check("Futurama")

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextMatchesRegex_shouldReturnSuccess() {
        val result = regexRule.check("12345")

        assertEquals(ValidationResult.Success, result)
    }
}