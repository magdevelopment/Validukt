package com.magdv.validukt.validators

import com.magdv.validukt.TestForm
import com.magdv.validukt.ValidationResult
import com.magdv.validukt.rules.ValidationRule
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyBoolean
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

/**
 * Created by Maxim Pisarenko on 31.10.2017.
 */

@RunWith(MockitoJUnitRunner::class)
class PropertyValidatorTest {

    private val errorMessage = "Value does not satisfy condition"

    @Mock
    lateinit var rule: ValidationRule<Boolean>

    lateinit var validator: PropertyValidator<TestForm, Boolean>

    @Before
    fun setUp() {
        validator = PropertyValidator(TestForm::testBoolean, setOf(rule))
    }

    @Test
    fun whenPropertyValueEqualsFalse_shouldReturnFailure() {
        whenever(rule.check(anyBoolean())).thenAnswer { ValidationResult.Failure(errorMessage) }

        val form = TestForm("one", "two", false)
        val result = validator.validate(form)

        assertThat(result, CoreMatchers.instanceOf(ValidationResult.Failure::class.java))
        assertEquals(errorMessage, (result as ValidationResult.Failure).message)
    }

    @Test
    fun whenPropertyValueEqualsTrue_shouldReturnSuccess() {
        whenever(rule.check(anyBoolean())).thenAnswer { ValidationResult.Success }

        val form = TestForm("one", "two", true)
        val result = validator.validate(form)

        assertEquals(ValidationResult.Success, result)
    }
}