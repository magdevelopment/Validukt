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
class EmptyRuleTest {

    private val errorMessage = "Text is empty"
    private lateinit var emptyRule: EmptyRule

    @Before
    fun setUp() {
        emptyRule = EmptyRule(errorMessage)
    }

    @Test
    fun whenTextIsEmpty_shouldReturnFailure() {
        val result = emptyRule.check("")

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextIsNull_shouldReturnFailure() {
        val result = emptyRule.check(null)

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenTextIsNotEmpty_shouldReturnSuccess() {
        val result = emptyRule.check("Disintegration")

        assertEquals(result, ValidationResult.Success)
    }
}