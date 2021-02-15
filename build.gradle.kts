import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("idea")
    id("maven")
    id("io.ebean") version "12.3.2"
    id ("com.github.johnrengelman.shadow") version "6.1.0"
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.3.72"
}

group = "ungkom.org"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}


dependencies {

    // use BOM
    // implementation(platform("io.ebean:ebean-bom:1.1"))

    implementation("net.dv8tion:JDA:4.2.0_228") {
        exclude("club.minnced")
    }
    implementation("com.jagrosh:jda-utilities:3.0.5")
    {
        exclude("club.minnced")
    }
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("org.avaje.composite:logback:1.1")
    implementation("io.ebean:ebean:12.3.2")
    implementation("io.ebean:ebean-querybean:12.2.3")
    implementation("org.postgresql:postgresql:42.2.8")
    kapt("io.ebean:kotlin-querybean-generator:12.2.3")

    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.quartz-scheduler:quartz:2.3.2")

    testImplementation("trove:trove:1.0.2")

    testImplementation("io.ebean:ebean-test:12.3.2")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.avaje.composite:junit:1.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.72")

}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.ungkom.MainAppKt"
    }
}

tasks.test {
    testLogging.showStandardStreams = true
    useJUnit()
}

ebean {
    debugLevel = 1
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
