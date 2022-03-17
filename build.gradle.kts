import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.jar {
    enabled = true
    // Remove `plain` postfix from jar file name
    archiveClassifier.set("")
}