package com.venueseen

class PrettyNumbersTagLib {

    static namespace = "vs"

    /**
     * Format a large number in a pretty human readable format.  For example, 100000 (one hundred thousand) would be
     * printed as 100K.
     *
     * @internal this implmentation is taken from {@link http://stackoverflow.com/questions/217437/have-a-favorite-custom-grails-tag}
     *
     * @param number
     * @param prefix
     * @param suffix
     * @param var
     */
    def formatNumberScaled = {attrs, body -> // number, prefix, suffix, invalid, var
        def number = getValidNumber( attrs.number )
        def numberString
        def scale = ''

        if (number.isNaN() || number.isInfinite()) {
            numberString = scale = attrs.'invalid' ?: "N/A"
        } else {
            Boolean negative = number < 0d
            number = negative ? -number : number

            if (number < 1000d) {
                scale = ''
            } else if (number < 1000000d) {
                scale = 'K'
                number /= 1000d
            } else if (number < 1000000000d) {
                scale = 'M'
                number /= 1000000d
            } else if (number < 1000000000000d) {
                scale = 'B'
                number /= 1000000000d
            } else if (number < 1000000000000000d) {
                scale = 'T'
                number /= 1000000000000d
            }

            def maxDecimals = 0

            if (number < 10d) {
                maxDecimals = 2
            } else if (number < 100d) {
                maxDecimals = 1
            }

            numberString = g.formatNumber(
                    'number': negative ? -number : number,
                    'type': 'number', maxFractionDigits: maxDecimals
            )

            numberString += scale
        }

        // Now, either print the number or output the tag body with
        // the appropriate variables set
        if (attrs.'var') {
            out << body((attrs.'var'): numberString, 'scale': scale)
        } else {
            out << numberString
        }
    }

    /**
     * Prints a percentage in our prefered "pretty" format.
     * @param number the number to format
     * @param percentSign
     */
    def formatPrettyPercent = { attrs, body ->
        //Check for null since the attr can be false
        attrs.percentSign = attrs.percentSign != null? attrs.percentSign : true

        def number = getValidNumber( attrs.number )
        if( !number.isNaN() && !number.isInfinite() ) {
            out << g.formatNumber( number: number * 100, maxFractionDigits: number < 0.10 ? 1 : 0 )

            if( attrs.percentSign ) {
                out << "%"
            }
        }
    }


    private def getValidNumber( number ) {
        Double myNumber
        try {
            myNumber = number.toDouble()
        } catch (Exception e) {
            myNumber = Double.NaN
        }
        return myNumber
    }

}
