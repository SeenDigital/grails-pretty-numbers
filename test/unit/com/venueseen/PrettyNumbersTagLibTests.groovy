package com.venueseen



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(PrettyNumbersTagLib)
class PrettyNumbersTagLibTests {

    void testNotScaledNumber() {
        assertEquals "123", new PrettyNumbersTagLib().formatNumberScaled( number: 123 ).toString()
    }

    void testThousandsNumber() {
        assertEquals "1.23K", new PrettyNumbersTagLib().formatNumberScaled( number: 1234 ).toString()
    }

    void testMillionsNumber() {
        assertEquals "1.23M", new PrettyNumbersTagLib().formatNumberScaled( number: 1234567 ).toString()
    }

    void testBillionsNumber() {
        assertEquals "1.23B", new PrettyNumbersTagLib().formatNumberScaled( number: 1234567890 ).toString()
    }

    void testNegativeNumber() {
        assertEquals "-123", new PrettyNumbersTagLib().formatNumberScaled( number: -123 ).toString()
    }

    void testInvalidNumber() {
        assertEquals "N/A", new PrettyNumbersTagLib().formatNumberScaled( number: 'Not a number' ).toString()
    }

    void testInvalidNumberCustomMessage() {
        assertEquals "Invalid", new PrettyNumbersTagLib().formatNumberScaled( number: 'Not a number', invalid: 'Invalid' ).toString()
    }

    void testTwoMaxDecimals() {
        assertEquals "12.3K", new PrettyNumbersTagLib().formatNumberScaled( number: 12345 ).toString()
    }

    void testPercent() {
        assertEquals "1.2%", new PrettyNumbersTagLib().formatPrettyPercent( number: 0.0123 ).toString()
    }

    void testMaxFractionDigitsPercent() {
        assertEquals "12%", new PrettyNumbersTagLib().formatPrettyPercent( number: 0.123 ).toString()
    }

    void testNoPercentSign() {
        assertEquals "12", new PrettyNumbersTagLib().formatPrettyPercent( number: 0.123, percentSign: false ).toString()
    }
}
