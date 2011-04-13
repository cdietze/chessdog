chessdog = {}

chessdog.Board = function(element) {
	element.innerHTML = "<iframe id=\"chessdogFrame\" ></iframe>";
	var frame = element.childNodes[0];
	var doc = frame.contentDocument;
	doc.open();
	doc.write('<!doctype html>\n<html>\n<head><\/head>\n<body><\/body>\n<\/html>\n');
	doc.close();
	setTimeout(function() {
    	var scr = doc.createElement("script");
    	scr.type = "text/javascript";
    	scr.src = "http://127.0.0.1:8888/jackembedded/jackembedded.nocache.js";
    	doc.getElementsByTagName('head')[0].appendChild(scr);
	},0);
}
