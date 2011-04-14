chessdog = {}

chessdog.Board = function(element) {
	element.innerHTML = "<iframe></iframe>";
	this.counter = 0;
	this.frame = element.childNodes[0];
	this.doc = this.frame.contentDocument;
	this.wnd = this.frame.contentWindow;
	var doc = this.doc;
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

chessdog.Board.prototype.check2 = function() {
	this.counter++;
//	this.doc.getElementsByTagName('body')[0].innerHTML = "counter: " + this.counter;
//	this.wnd.myfunc();
	this.wnd.alert('fen: '+this.wnd.getFen());
}
