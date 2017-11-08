package com.magdv.validukt.validators

import com.magdv.validukt.TestForm
import com.magdv.validukt.ValidationResult
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.collection.IsMapContaining.hasKey
import org.hamcrest.collection.IsMapContaining.hasValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

/**
 * Created by Maxim Pisarenko on 01.11.2017.
 */
@RunWith(MockitoJUnitRunner::class)
class ComplexValidatorTest {

    private val firstChildValidatorErrorMessage = "First error"
    private val secondChildValidatorErrorMessage = "Second error"
    private var testForm = TestForm("first", "second", false)

    @Mock
    lateinit var firstChildValidator: Validator<TestForm>

    @Mock
    lateinit var secondChildValidator: Validator<TestForm>

    lateinit var validator: ComplexValidator<TestForm>

    @Before
    fun setUp() {
        whenever(firstChildValidator.tag).thenAnswer { TestForm::emptyTestString.name }
        whenever(secondChildValidator.tag).thenAnswer { TestForm::regexTestString.name }

        validator = ComplexValidator(setOf(firstChildValidator, secondChildValidator))
    }

    @Test
    fun whenSeveralProperiesAreInvalid_shouldReturnMapWithCorrespondingFailrues() {
        whenever(firstChildValidator.validate(testForm)).thenAnswer { ValidationResult.Failure(firstChildValidatorErrorMessage) }
        whenever(secondChildValidator.validate(testForm)).thenAnswer { ValidationResult.Failure(secondChildValidatorErrorMessage) }

        val result = validator.validate(testForm)

        assertEquals(2, result.size)

        assertThat(result, hasKey(TestForm::emptyTestString.name))
        assertThat(result[TestForm::emptyTestString.name], instanceOf(ValidationResult.Failure::class.java))
        assertEquals((result[TestForm::emptyTestString.name] as ValidationResult.Failure).message, firstChildValidatorErrorMessage)

        assertThat(result, hasKey(TestForm::regexTestString.name))
        assertThat(result[TestForm::regexTestString.name], instanceOf(ValidationResult.Failure::class.java))
        assertEquals((result[TestForm::regexTestString.name] as ValidationResult.Failure).message, secondChildValidatorErrorMessage)
    }

    @Test
    fun whenAllValidatorPassed_shouldReturnMapWithSuccessValues() {
        whenever(firstChildValidator.validate(testForm)).thenAnswer { ValidationResult.Success }
        whenever(secondChildValidator.validate(testForm)).thenAnswer { ValidationResult.Success }

        val result = validator.validate(testForm)

        assertEquals(2, result.size)
        assertThat(result, not<Map<String, ValidationResult>>(hasValue(instanceOf(ValidationResult.Failure::class.java))))
    }
}