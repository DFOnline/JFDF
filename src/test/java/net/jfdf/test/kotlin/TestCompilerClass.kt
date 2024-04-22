package net.jfdf.test.kotlin

fun printSmth(msg: String) {
    println(msg)
}

val testCompilerJavaClass: Class<*> = object {}::class.java.enclosingClass