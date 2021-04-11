import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Test {
    @Test
    fun encodeRLE() {
        assertEquals("2S7D3Y4NP", encodeRLE("SSDDDDDDDYYYNNNNP"))
        assertEquals("S7D3YNP", encodeRLE("SDDDDDDDYYYNP"))
        assertEquals("10WQE", encodeRLE("WWWWWWWWWWQE"))
        assertEquals("109", encodeRLE("9999999999"))
        assertEquals("11234563W", encodeRLE("2222222222244466666WWW"))
        assertEquals("W15Q", encodeRLE("WQQQQQQQQQQQQQQQ"))
        assertEquals("Sch2ol", encodeRLE("School"))
        assertEquals("S7D3YNP", encodeRLE("SDDDDDDDYYYNP"))
        assertEquals("G2od2 morning", encodeRLE("Good  morning"))
        assertEquals("", encodeRLE(""))
    }
    @Test
    fun decodeRLE() {
        assertEquals("", decodeRLE(""))
        assertEquals("KKKKKKKKKK", decodeRLE("10K"))
        assertEquals("KKKKKKKKKK10", decodeRLE("10K10"))
        assertEquals("WWWWRRRWWWQ", decodeRLE("4W3R3WQ"))
        assertEquals("TOSD", decodeRLE("TOSD"))
        assertEquals("Good  morning", decodeRLE("G2od2 morning"))
        assertEquals("School", decodeRLE("Sch2ol"))
    }
    @Test
    fun rle() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            main("pak-rle -z test.txt".split(" ").toTypedArray())
        }
        main("pack-rle -z input/1.txt".split(" ").toTypedArray())
        assertTrue { assertFileEquality("output/11.txt", "input/1.txt") }
        assertFalse { assertFileEquality("output/21.txt", "input/1.txt") }
        main("pack-rle -u input/1.txt".split(" ").toTypedArray())
        main("pack-rle -u input/2.txt".split(" ").toTypedArray())
        assertTrue { assertFileEquality("output/21.txt", "input/2.txt") }
        assertFalse { assertFileEquality("output/11.txt", "input/2.txt") }
        main("pack-rle -z input/2.txt".split(" ").toTypedArray())

    }


    private fun assertFileEquality(file1: String, file2: String): Boolean {
        val expected = File(file1).readLines()
        val actual = File(file2).readLines()
        for (i in expected.indices) if (actual[i] != expected[i]) return false
        return expected.size == actual.size
    }

}