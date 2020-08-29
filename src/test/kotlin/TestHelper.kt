import java.io.BufferedReader

object TestHelper

fun fixture(path: String): String {
    return TestHelper.javaClass
            .getResourceAsStream("/fixtures/$path")
            .bufferedReader()
            .use(BufferedReader::readText)
}