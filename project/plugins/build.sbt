
resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

// uncomment to use local maven repo
// resolvers += Resolver.file("Local", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern))

libraryDependencies += "net.thunderklaus" %% "sbt-gwt-plugin" % "1.0-SNAPSHOT"

libraryDependencies += "com.eed3si9n" %% "sbt-appengine" % "0.1-SNAPSHOT"