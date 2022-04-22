import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // val kotlinVersion = "1.5.32"
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    //id("net.mamoe.mirai-console") version "2.10.0-RC2"
    id("net.mamoe.mirai-console") version "2.11.0-M2.2"
}

group = "wxgj.tinasproutrobot.mirai"
version = "0.1.1"

repositories {
    mavenLocal()
    maven("https://repo.mirai.mamoe.net/snapshots")
    maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
    mavenCentral()
}

dependencies {

//    implementation("com.alibaba:fastjson:1.2.80")
//    implementation("org.apache.commons:commons-lang3:3.12.0")
//    implementation("org.apache.httpcomponents:httpclient:4.5.13")
//
//    implementation("org.jsoup:jsoup:1.14.3")
//    implementation("org.ini4j:ini4j:0.5.4")
//

//    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.5")
// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    // https://mvnrepository.com/artifact/commons-io/commons-io
    implementation("commons-io:commons-io:2.11.0")
    // https://mvnrepository.com/artifact/cn.hutool/hutool-all
    implementation("cn.hutool:hutool-all:5.8.0.M3")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation(fileTree(mapOf("dir" to "src/main/resources/libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8"))

}


// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}