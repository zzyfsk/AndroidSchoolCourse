package com.zzy.androidschoolcourse.bean

import kotlinx.serialization.Serializable

@Serializable
data class Fraction(var numerator: Int, var denominator: Int) {

    operator fun plus(another: Fraction): Fraction {
        val commonDenominator = lcm(denominator, another.denominator)
        val sumNumerator =
            numerator * (commonDenominator / denominator) + another.numerator * (commonDenominator / another.denominator)
        return simplifyFraction( Fraction(sumNumerator, commonDenominator) )
    }

    operator fun minus(another: Fraction): Fraction {
        val commonDenominator = lcm(denominator, another.denominator)
        val differenceNumerator =
            numerator * (commonDenominator / denominator) - another.numerator * (commonDenominator / another.denominator)
        return simplifyFraction( Fraction(differenceNumerator, commonDenominator) )

    }

    operator fun times(another: Fraction): Fraction {
        val productNumerator = numerator * another.numerator
        val productDenominator = denominator * another.denominator
        return simplifyFraction( Fraction(productNumerator, productDenominator) )

    }

    operator fun div(another: Fraction): Fraction {
        val quotientNumerator = numerator * another.denominator
        val quotientDenominator = denominator * another.numerator
        return simplifyFraction( Fraction(quotientNumerator, quotientDenominator) )

    }

    fun getInteger():Int{
        return numerator/denominator
    }

    fun getRemainder():Int{
        return numerator%denominator
    }


    // 计算最小公倍数
    private fun lcm(a: Int, b: Int): Int {
        return a / gcd(a, b) * b
    }

    // 计算最大公约数
    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    private fun simplifyFraction(fraction:Fraction):Fraction{
        gcd(fraction.numerator,fraction.denominator).let {
            return Fraction(fraction.numerator/it, fraction.denominator/it)
        }
    }
}
