ThisBuild / version := "0.1.0-SNAPSHOT"

Compile / javaSource := baseDirectory.value / "src"

lazy val root = (project in file("."))
  .settings(
    name := "assignment-02",
    // https://mvnrepository.com/artifact/io.vertx/vertx-core
    libraryDependencies += "io.vertx" % "vertx-core" % "4.5.7",
      // https://mvnrepository.com/artifact/io.vertx/vertx-web
    libraryDependencies += "io.vertx" % "vertx-web" % "4.5.7",
    // https://mvnrepository.com/artifact/io.vertx/vertx-web-client
    libraryDependencies += "io.vertx" % "vertx-web-client" % "4.5.7",
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    libraryDependencies += "org.jsoup" % "jsoup" % "1.17.2"
)
