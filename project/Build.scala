import sbt._
import Keys._
import net.thunderklaus.GwtPlugin._
import com.github.siasia.WebPlugin._

object MyBuild extends Build {

  lazy val root = Project("root", file("."), settings = rootSettings)

  val chessdogJsTempPath = SettingKey[File]("chessdog-js-temp-path")
  val prepareChessdogJs = TaskKey[Unit]("prepare-chessdog-js")
  
  val chessdogHost = "chessdog.christophdietze.com"

  lazy val rootSettings = Defaults.defaultSettings ++ sbtappengine.AppenginePlugin.webSettings ++ gwtOnlySettings ++
    Seq(
      organization := "net.thunderklaus",
      name := "chessdog",
      version := "1.0-SNAPSHOT",
      scalaVersion := "2.9.0-1",
      unmanagedClasspath in Compile <++= (baseDirectory) map (b => Attributed.blankSeq((b ** "*.jar").get)),
      chessdogJsTempPath <<= (target) { (target) => target / "chessdog-temp" },
      webappResources <<= (webappResources, chessdogJsTempPath) { (w: PathFinder, p: File) => w +++ PathFinder(p) },
      prepareWebapp <<= prepareWebapp.dependsOn(prepareChessdogJs),
      prepareChessdogJs <<= (chessdogJsTempPath, resourceDirectory in Compile, streams) map { (tmp, res, s) => prepareChessdogJsTask(tmp, res, s.log) })

  private def prepareChessdogJsTask(dstDir: File, srcDir: File, log: Logger): Unit = {
    IO.createDirectory(dstDir)
    val srcFile = srcDir / "chessdog.src.js"
    val dstFile = dstDir / "chessdog.js"
    runClosureCompiler(srcFile, dstFile, log)
    replaceHostVariable(dstFile, dstFile, log)
  }

  private def runClosureCompiler(inFile: File, outFile: File, log: Logger) {
    val command = "java -jar ./lib_tools/closure-compiler.jar --js \"" + inFile.absolutePath + "\" --js_output_file \"" + outFile.absolutePath + "\""
    log.info("Running closure compiler: " + command)
    command !;
  }

  private def replaceHostVariable(inFile: File, outFile: File, log: Logger) {
    val source = IO.read(inFile)
    val str = source.replaceAll("\\$\\{Host\\}", chessdogHost)
    IO.write(outFile, str)
    log.info("Replaced ${Host} variable " + inFile + " -> " + outFile)
  }
}
