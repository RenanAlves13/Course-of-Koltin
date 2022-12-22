package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

@Suppress("DataClassPrivateConstructor")
data class Rational private constructor(val n: BigInteger, val d: BigInteger): Comparable<Rational>{
    companion object{
        fun create(n: BigInteger, d: BigInteger) = normalizar(n, d)
        fun normalizar (n: BigInteger, d: BigInteger):Rational{
            require(d != ZERO) {"Denominador tem que ser diferente de zero"}
            return Rational(n/ (n.gcd(d) * d.signum().toBigInteger()),
                d / (n.gcd(d) * d.signum().toBigInteger()))
        }
    }
    //Implementando os operadores
    operator fun plus (qualquer: Rational) = create( /*Operador de soma*/
        n * qualquer.d + qualquer.n * d, d * qualquer.d
    )
    operator fun minus (qualquer: Rational) = create(/*operador de subtração*/
        n * qualquer.d - qualquer.n * d, d * qualquer.d
    )
    operator  fun times (qualquer: Rational) = create(/*operador de multiplicação*/
        n * qualquer.n, d * qualquer.d
    )
    operator  fun div (qualquer: Rational) = create(/*operador de divisão*/
        n * qualquer.d, d * qualquer.n
    )
    override fun compareTo(qualquer: Rational): Int { //Operador de comparação entre objetos racionais
        return (n *qualquer.d - qualquer.n * d).signum()
    }
    operator fun unaryMinus() = create(-n, d)
    override fun toString(): String {
        val one: Int = 1
        return if (d == one.toBigInteger())
            "$n"
        else
            "$n/$d"
    }
}

//Função para converter uma string em um racional
fun String.toRational(): Rational{
    val mensagemErro = "É esperado um racional na forma 'n/d' ou só 'n', mas tem: '$this"
    if (!contains("/")){
        val numero = toBigIntegerOrNull()
            ?: throw IllegalArgumentException(mensagemErro)
        return Rational.create(numero, ONE)
    }
    val (numerador, denominador) = split("/")
    val numeradorFinal = numerador.toBigIntegerOrNull()
        ?: throw  IllegalArgumentException(mensagemErro)
    val denominadorFinal = denominador.toBigIntegerOrNull()
        ?: throw  IllegalArgumentException(mensagemErro)
    return Rational.create(numeradorFinal, denominadorFinal)
}
//Criando os números racionais
infix fun BigInteger.divBy (denominador: BigInteger) =
    Rational.create(this, denominador)

infix fun Int.divBy (denominador: Int) =
    Rational.create(this.toBigInteger(), denominador.toBigInteger())

infix fun Long.divBy (denominador: Long) =
    Rational.create (this.toBigInteger(), denominador.toBigInteger())

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}