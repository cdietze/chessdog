
resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

addSbtPlugin("net.thunderklaus" % "sbt-gwt-plugin" % "1.1-SNAPSHOT")

addSbtPlugin("com.eed3si9n" % "sbt-appengine" % "0.6.1")

// uncomment to use local maven repo
// resolvers += Resolver.file("Local", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern))
