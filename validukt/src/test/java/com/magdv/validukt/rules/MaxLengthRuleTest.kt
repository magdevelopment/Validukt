package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
class MaxLengthRuleTest {

    private val errorMessage = "Text is too long"

    @Test
    fun whenTextLengthLongerThanTreshold_shouldReturnFailure() {
        val maxLengthRule = MaxLengthRule(3, errorMessage)
        val result = maxLengthRule.check("Vodka")

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextIsNull_shouldReturnFailure() {
        val maxLengthRule = MaxLengthRule(7, errorMessage)
        val result = maxLengthRule.check(null)

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextLengthEqualsTreshold_shouldReturnSuccess() {
        val maxLengthRule = MaxLengthRule(5, errorMessage)
        val result = maxLengthRule.check("Vodka")

        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun whenTextLengthLessThanTreshold_shouldReturnSuccess() {
        val maxLengthRule = MaxLengthRule(5, errorMessage)
        val result = maxLengthRule.check("Gin")

        assertEquals(ValidationResult.Success, result)
    }

}