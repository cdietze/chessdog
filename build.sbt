
import net.thunderklaus.GwtPlugin._

appengineSettings

seq(gwtSettings :_*)

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.2.v20140723" % "container",
  "org.eclipse.jetty" % "jetty-plus" % "9.2.2.v20140723" % "container"
)

autoScalaLibrary := false

gwtVersion := "2.4.0"