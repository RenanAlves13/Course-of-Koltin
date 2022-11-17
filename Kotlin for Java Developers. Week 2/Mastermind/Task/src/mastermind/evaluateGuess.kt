package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var wrong: Int = 0
    var right: Int = 0
    var wrongPosition: Int = 0
    val alfabeto: String = "ABCDEF"

    /*
    * Gera um par de valores
    * (secret[0], guess[0]), (secret[1], guess[1]), (secret[2], guess[2]), (secret[3], guess[3])
    * Depois disso é feita a contagem de quantas estão na posição correta
     */
    right = secret.zip(guess).count{it.first == it.second}

    /*
    * Compara se as letras do alfabeto especificado estão na palavra secreta e na digitada, respectivamente, e conta
    * as ocorrências disso
     */

    wrong = "ABCDEF".sumBy { ch ->
        secret.count { it == ch }.coerceAtMost(guess.count { it == ch })
    }

    /*
    * Depois disso, faz a seguinte atribuição:
    * letras que estão na string mas em posições incorretas - letras nas posições corretas
    * WrongPosition = wrong - right
     */
    wrongPosition = wrong - right

    return Evaluation(right, wrongPosition)
}
