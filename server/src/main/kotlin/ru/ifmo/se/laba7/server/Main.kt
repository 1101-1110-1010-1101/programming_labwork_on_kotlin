package ru.ifmo.se.laba7.server

import javafx.application.Application
import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) {

    // I`m a GUI thread!
    launch {
        Application.launch(LoginForm::class.java)
    }
    // I`m the main thread!
    val collection = UserCollection()
    collection.load()
    // I`m a GUI listener!
    launch {
        while (true) {
            if (LoginForm.message != "") {
                val command = LoginForm.message.substringBefore(" ")
                val argument = LoginForm.message.substringAfter(" ")
                LoginForm.message = ""
                when (command) {
                    "add" -> {
                        collection.add(Astronaut.parseCsv(argument))
                        println("${argument.substringBefore(",")} is added to team")
                        collection.save()
                    }
                    "add_if_max" -> {
                        println(collection.size)
                        collection.addIfMax(argument)
                        println(collection.size)
                        collection.save()
                    }
                    "remove_if_greater" -> {
                        println(collection.size)
                        collection.removeIfGreater(argument)
                        println(collection.size)
                        collection.save()
                    }
                    "remove_first" -> collection.remove_first()
                    "remove_last" -> collection.remove_last()
                    "save" -> {
                        collection.save()
                        println("Data is saved to ${UserCollection.astronauts_datafile}")
                    }
                    "load" -> {
                        collection.load()
                        println("Data is loaded from ${UserCollection.astronauts_datafile}")
                    }
                }
            }
        }
    }
    while (true){}
}