subprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
        maven("https://repo1.maven.org/maven2/")
    }
}

allprojects{
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
        maven("https://repo1.maven.org/maven2/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}
