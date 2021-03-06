name := "AssOptionOneApi"
 
version := "1.0" 
      
lazy val `assoptiononeapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies += filters
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"

PlayKeys.devSettings := Seq("play.server.http.port" -> "8000")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

      