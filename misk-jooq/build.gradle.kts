import com.vanniktech.maven.publish.JavadocJar.Dokka
import com.vanniktech.maven.publish.KotlinJvm

plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.mavenPublishBase)

  // Needed to generate jooq test db classes
  alias(libs.plugins.flyway)
  alias(libs.plugins.jooq)
}

dependencies {
  api(libs.guava)
  api(libs.guice)
  api(libs.jooq)
  api(libs.kotlinLogging)
  api(project(":misk-core"))
  api(project(":misk-inject"))
  api(project(":misk-jdbc"))
  implementation(libs.jakartaInject)
  implementation(libs.kotlinRetry)
  implementation(libs.kotlinxCoroutinesCore)
  implementation(project(":wisp:wisp-logging"))

  testImplementation(libs.assertj)
  testImplementation(libs.junitApi)
  testImplementation(project(":wisp:wisp-deployment"))
  testImplementation(project(":wisp:wisp-time-testing"))
  testImplementation(project(":misk"))
  testImplementation(testFixtures(project(":misk-jdbc")))
  testImplementation(project(":misk-testing"))

  // Needed to generate jooq test db classes
  jooqGenerator(libs.mysql)
}

// Needed to generate jooq test db classes
flyway {
  url = "jdbc:mysql://localhost:3500/misk-jooq-test-codegen"
  user = "root"
  password = "root"
  schemas = arrayOf("jooq")
  locations = arrayOf("filesystem:${project.projectDir}/src/test/resources/db-migrations")
  sqlMigrationPrefix = "v"
}
// Needed to generate jooq test db classes
jooq {
  version.set("3.18.2")
  edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

  configurations {
    create("main") {
      generateSchemaSourceOnCompilation.set(false)
      jooqConfiguration.apply {
        jdbc.apply {
          driver = "com.mysql.cj.jdbc.Driver"
          url = "jdbc:mysql://localhost:3500/misk-jooq-test-codegen"
          user = "root"
          password = "root"
        }
        generator.apply {
          name = "org.jooq.codegen.KotlinGenerator"
          database.apply {
            name = "org.jooq.meta.mysql.MySQLDatabase"
            inputSchema = "jooq"
            outputSchema = "jooq"
            includes = ".*"
            excludes = "(.*?FLYWAY_SCHEMA_HISTORY)|(.*?schema_version)"
            recordVersionFields = "version"
          }
          generate.apply {
            isJavaTimeTypes = true
          }
          target.apply {
            packageName = "misk.jooq.testgen"
            directory   = "${project.projectDir}/src/test/generated/kotlin"
          }
        }
      }
    }
  }
}

// Needed to generate jooq test db classes
tasks.named("generateJooq") {
  dependsOn("flywayMigrate")
}

// Needed to generate jooq test db classes
// If you are using this as an example for your service, remember to add the generated code to your
// main source set instead of your tests as it is done below.
sourceSets {
  test {
    java.srcDirs(layout.projectDirectory.dir("src/test/generated/kotlin"))
  }
}

mavenPublishing {
  configure(
    KotlinJvm(javadocJar = Dokka("dokkaGfm"))
  )
}
