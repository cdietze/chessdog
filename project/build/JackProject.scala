import sbt._
import sbt.Process._

class JackProject(info: ProjectInfo) extends AppengineProject(info) {
	
	val gwtUser = "com.google.gwt" % "gwt-user" % "2.3.0" % "provided"
	val gwtDev = "com.google.gwt" % "gwt-dev" % "2.3.0" % "provided"
	val validationApi = "javax.validation" % "validation-api" % "1.0.0.GA" % "provided" withSources()
	val gwtServlet = "com.google.gwt" % "gwt-servlet" % "2.3.0"
	val gwtModules = List("Jack", "JackAdmin", "JackEmbed").map("com.christophdietze.jack."+_)
//	val gwtModules = List("Jack").map("com.christophdietze.jack."+_)

	lazy val gwtCompile = gwtCompileAction
	def gwtCompileAction = task {
		val command = "java -cp " + (compileClasspath +++ mainJavaSourcePath).absString + " com.google.gwt.dev.Compiler -war " + temporaryWarPath.absString + " " + gwtModules.mkString(" ")
		command ! ; 
		None
	}
	
	override def prepareWebappAction = gwtCompileAction dependsOn(super.prepareWebappAction)
	
	override def compileClasspath = super.compileClasspath +++ descendents( info.projectPath / "lib_compile", "*.jar")

}
