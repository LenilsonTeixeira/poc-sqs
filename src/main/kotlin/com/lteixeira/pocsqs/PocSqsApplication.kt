package com.lteixeira.pocsqs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PocSqsApplication

fun main(args: Array<String>) {
	runApplication<PocSqsApplication>(*args)
}
