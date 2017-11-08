package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult
import com.magdv.validukt.rules.ValidationRule
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever


/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
@RunWith(MockitoJUnitRunner::class)
class StandartValidatorTest {

    private val errorMessage = "Shit just happened"

    @Mock
    lateinit var firstRule: ValidationRule<String>

    @Mock
    lateinit var secondRule: ValidationRule<String>

    lateinit var validator: StandartValidator<String>

    @Before
    fun setUp() {
        validator = StandartValidator(setOf(firstRule, secondRule))
    }

    @Test
    fun whenSecondRuleFails_shouldReturnSecondRuleFailure() {
        whenever(firstRule.check(anyString())).thenAnswer { ValidationResult.Success }
        whenever(secondRule.check(anyString())).thenAnswer { ValidationResult.Failure(errorMessage) }

        val result = validator.validate("Rippersnapper")
        assertThat(result, instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenEveryRulePassed_shouldReturnSuccess() {
        whenever(firstRule.check(anyString())).thenAnswer { ValidationResult.Success }
        whenever(secondRule.check(anyString())).thenAnswer { ValidationResult.Success }

        val result = validator.validate("Overbite")
        assertEquals(ValidationResult.Success, result)
    }
}