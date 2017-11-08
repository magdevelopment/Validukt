package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

@RunWith(MockitoJUnitRunner::class)
class CompositeValidatorTest {

    private val firstErrorMessage = "First error"
    private val secondErrorMessage = "Second error"

    @Mock
    lateinit var firstChildValidator: Validator<String>

    @Mock
    lateinit var secondChildValidator: Validator<String>

    lateinit var validator: CompositeValidator<String>

    @Before
    fun setUp() {
        validator = CompositeValidator(setOf(firstChildValidator, secondChildValidator))
    }

    @Test
    fun whenNoValidatorsWereSuccessful_shouldReturnFirstValidatorFailure() {
        whenever(firstChildValidator.validate(anyString())).thenAnswer { ValidationResult.Failure(firstErrorMessage) }
        whenever(secondChildValidator.validate(anyString())).thenAnswer { ValidationResult.Failure(secondErrorMessage) }

        val result = validator.validate("Thundercracker")
        assertThat(result, instanceOf(ValidationResult.Failure::class.java))
        assertEquals(firstErrorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenAnyValidatorsWereSuccessful_shouldReturnSuccess() {
        whenever(firstChildValidator.validate(anyString())).thenAnswer { ValidationResult.Failure(firstErrorMessage) }
        whenever(secondChildValidator.validate(anyString())).thenAnswer { ValidationResult.Success }

        val result = validator.validate("Onslaught")
        assertEquals(ValidationResult.Success, result)
    }
}