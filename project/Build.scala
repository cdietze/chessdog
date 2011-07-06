import sbt._
import Keys._
import net.thunderklaus.GwtPlugin._

object MyBuild extends Build {

  lazy val root = Project("root", file("."), settings = rootSettings)

  lazy val rootSettings = Defaults.defaultSettings ++ gwtSettings ++ Seq(
    organization := "net.thunderklaus",
    name := "chessdog",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.9.0-1",
    unmanagedClasspath in Compile <++= (baseDirectory) map (b => Attributed.blankSeq((b ** "*.jar").get)))
}