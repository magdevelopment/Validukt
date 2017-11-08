package com.magdv.validukt.rules

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
class BooleanRuleTest {

    private val errorMessage = "Value does not satisfy condition"
    lateinit var booleanRule: BooleanRule

    @Before
    fun setUp() {
        booleanRule = BooleanRule(errorMessage)
    }

    @Test
    fun whenInputFalse_shouldReturnFailure() {
        val result = booleanRule.check(false)
        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenInputTrue_shouldReturnSuccess() {
        val result = booleanRule.check(true)
        assertEquals(ValidationResult.Success, result)
    }
}