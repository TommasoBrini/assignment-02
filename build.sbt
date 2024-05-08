ThisBuild / version := "0.1.0-SNAPSHOT"

Compile / javaSource := baseDirectory.value / "src"
Test / javaSource := baseDirectory.value / "test"


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
    libraryDependencies += "org.jsoup" % "jsoup" % "1.17.2" ,
    libraryDependencies += "org.jfree" % "jfreechart" % "1.5.3",
    libraryDependencies += "org.jfree" % "jcommon" % "1.0.23",
    // https://mvnrepository.com/artifact/com.squareup.okhttp/okhttp
    libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "5.0.0-alpha.14",
    // junit
    libraryDependencies += "junit" % "junit" % "4.13.2" % Test,
    // https://mvnrepository.com/artifact/io.reactivex.rxjava3/rxjava
    libraryDependencies += "io.reactivex.rxjava3" % "rxjava" % "3.1.8"
    )
