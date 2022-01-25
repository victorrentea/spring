package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class DemoApplication


fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Component
internal class Run:CommandLineRunner {
	@Value("\${prop}")
	lateinit var s:String
	override fun run(vararg args: String?) {
		println("prop: $s")
	}

}