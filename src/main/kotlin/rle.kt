import java.io.File
import java.sql.DriverManager.println

fun main(args: Array<String>) {
    RecoderLauncher.main(args)
}
fun encodeRLE(text: String): String {
    val res = StringBuilder()
    var k = 0
    if (text == "") return ""
    var char = text[0]
    for (i in text) {
        if (i != char) {
            if (k > 1) {
                res.append(k)
                res.append(char)
            } else res.append(char)
            char = i
            k = 0
        }
        k++
    }
    if (k != 1) res.append(k)
    res.append(char)
    return res.toString()
}

fun decodeRLE(text: String): String {
    if (text == "") return ""
    val res = StringBuilder()
    var i = 0
    while (i in text.indices) {
        val number = text.substring(i).takeWhile { it.isDigit() }
        val x = number.toIntOrNull() ?: 1
        if (i + number.count() < text.length) for (j in 1..x) res.append(text[i + number.count()])
        else res.append(number)
        i += number.count() + 1
    }
    return res.toString()
}
fun rle(pack: Boolean, inputFile: String, outputFile: String?) {
    val input = File(inputFile).readLines()
    val output = File(outputFile ?: inputFile).bufferedWriter()
    output.use {
        for (line in input) {
            it.write(if (pack) encodeRLE(line) else decodeRLE(line))
            it.newLine()
        }
    }
}