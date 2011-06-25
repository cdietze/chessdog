
import sbt._
import sbt.Process._
import java.io.File

class JackProject(info: ProjectInfo) extends AppengineProject(info) {
	
	val gwtUser = "com.google.gwt" % "gwt-user" % "2.3.0" % "provided"
	val gwtDev = "com.google.gwt" % "gwt-dev" % "2.3.0" % "provided"
	val validationApi = "javax.validation" % "validation-api" % "1.0.0.GA" % "provided" withSources()
	val gwtServlet = "com.google.gwt" % "gwt-servlet" % "2.3.0"
	val googleClosureCompiler = "com.google.javascript" % "closure-compiler" % "r946" % "provided"
	
	val gwtModules = List("Jack", "JackAdmin", "JackEmbed").map("com.christophdietze.jack."+_)
//	val gwtModules = List("JackEmbed").map("com.christophdietze.jack."+_)
	val productionHost = "chessdog.christophdietze.com"

	lazy val gwtCompile = gwtCompileAction
	def gwtCompileAction = task {
		val command = "java -cp " + (compileClasspath +++ mainJavaSourcePath).absString + " com.google.gwt.dev.Compiler -war " + temporaryWarPath.absString + " " + gwtModules.mkString(" ")
		command ! ; 
		None
	}
	
	override def prepareWebappAction = gwtCompileAction dependsOn(prepareChessdogEmbedJs) dependsOn(super.prepareWebappAction)
	
	override def compileClasspath = super.compileClasspath +++ descendents( info.projectPath / "lib_compile", "*.jar")

	lazy val prepareChessdogEmbedJs = prepareChessdogEmbedJsAction
	def prepareChessdogEmbedJsAction = task {
		FileUtilities.createDirectory(temporaryWarPath, log)

		val srcFile = new File((mainResourcesPath / "chessdog.embed.src.js").absolutePath)
		val tmpFile = new File((temporaryWarPath / "chessdog.embed.tmp.js").absolutePath)
		val dstFile = new File((temporaryWarPath / "chessdog.embed.js").absolutePath)
		// for now, create the JS file twice. Running this stuff twice seems simpler to implement than copying :)
		val dstFile2 = new File((temporaryWarPath / "chessdog.js").absolutePath)

		runClosureCompiler(srcFile, tmpFile)
		replaceHostVariable(tmpFile, dstFile)
		replaceHostVariable(tmpFile, dstFile2)
		
		tmpFile.delete
		None
	}
	
	
	private def runClosureCompiler(inFile: File, outFile: File) {
		val command = "java -jar ./lib_tools/closure-compiler.jar --js \"" + inFile.getAbsolutePath +"\" --js_output_file \""+outFile.getAbsolutePath+"\""
		log.info("Running closure compiler: " + command)
		command ! ;
	}
	
	private def replaceHostVariable(inFile: File, outFile: File) {
		val source = scala.io.Source.fromFile(inFile) 
		try {
		val lines = source.getLines
		withFile(outFile) {p => {
			for(line <- lines) {
				p.print(line.replaceAll("\\$\\{Host\\}", productionHost))
			}
		}}
		} finally source match { case b: scala.io.BufferedSource => b.close }
		log.info("Created: " + outFile)
	}
	
	private def withFile(file: File)(op: java.io.PrintWriter => Unit) {
		val p = new java.io.PrintWriter(file)
		try { op(p) } finally { p.close }
	}
}
