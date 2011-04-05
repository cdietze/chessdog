chessdog = {}
/*
  chessdog.Board = function(element) {
	var frame = document.createElement("iframe");
	frame.src = "javascript:''";
	frame.id = "chessdogFrame";
	frame.height = 300;
	frame.width = 300;
	frame.frameBorder=false;
	frame.scrolling="no";
	element.appendChild(frame);
	var doc = frame.contentDocument;
	doc.write('<!doctype html><html><head>');
	doc
			.write('<script type="text/javascript" src="jack/jack.nocache.js"><\/script>');
	doc.write('<\/head><body><\/body><\/html>');
	doc.close();
}*/


chessdog.Board = function(element) {
	element.innerHTML = "<iframe id=\"chessdogFrame\" src=\"javascript:''\" style=\"border: 0; overflow: hidden; height: 300px; width: 300px;\"></iframe>";
	var doc=document.getElementById("chessdogFrame").contentDocument;
	doc.write('<!doctype html><html><head>');
	doc
			.write('<script type="text/javascript" src="jack/jack.nocache.js"><\/script>');
	doc.write('<\/head><body><\/body><\/html>');
	doc.close();
}
