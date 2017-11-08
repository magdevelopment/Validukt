package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

class MinLengthRuleTest {

    private val errorMessage = "Text is too short"

    @Test
    fun whenTextLengthLessThanTreshold_shouldReturnFailure() {
        val minLengthRule = MinLengthRule(6, errorMessage)
        val result = minLengthRule.check("Vodka")

        assertThat(result, instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextIsNull_shouldReturnFailure() {
        val minLengthRule = MinLengthRule(7, errorMessage)
        val result = minLengthRule.check(null)

        assertThat(result, instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextLengthLongerTreshold_shouldReturnSuccess() {
        val minLengthRule = MinLengthRule(5, errorMessage)
        val result = minLengthRule.check("Tequila")

        assertEquals(ValidationResult.Success, result)
    }

    @Test
    fun whenTextLengthEqualsTreshold_shouldReturnSuccess() {
        val maxLengthRule = MaxLengthRule(9, errorMessage)
        val result = maxLengthRule.check("Cointreau")

        assertEquals(ValidationResult.Success, result)
    }
}