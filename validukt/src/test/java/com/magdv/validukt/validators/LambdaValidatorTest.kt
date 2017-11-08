package com.magdv.validukt.validators

import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */
class LambdaValidatorTest {

    @Test
    fun whenLambdaReturnsFailure_validatorShouldReturnFailure() {
        val errorMessage = "Oops!"
        val validator = LambdaValidator<String> { ValidationResult.Failure(errorMessage) }
        val result = validator.validate("Swindle")

        assertThat(result, instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenLambdaReturnsSuccess_validatorShouldReturnSuccess() {
        val validator = LambdaValidator<String> { ValidationResult.Success }
        val result = validator.validate("Swindle")

        assertEquals(ValidationResult.Success, result)
    }
}