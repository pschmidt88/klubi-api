package racoony.software.klubi

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("racoony.software.klubi")
		.start()
}

