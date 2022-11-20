package nicestring

fun String.isNice(): Boolean {

    var vowels = false

    /*
    * String boas:
    * 1 - doesn't contain substrings bu, ba or be
    * 2 - contains at least three vowels (a, e, i, o and u)
    * 3 - contains a double letter
    * IMPORTANT: two conditions might be satisfied to make a string nice
     */

    vowels = count {it in "aeiou"} >= 3

    var hasDoubleLetter = false
    hasDoubleLetter = (0 until lastIndex).any { this[it] == this[it + 1] }

    val conditions3 = !contains("ba") && !contains("bu") && !contains("be")
    var result = 0

    if(vowels) result += 1
    if(hasDoubleLetter) result += 1
    if(conditions3) result += 1

    println(hasDoubleLetter)

    return result >= 2
}