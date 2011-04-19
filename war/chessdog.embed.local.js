chessdog = {}

chessdog.Board = function(element) {
	element.innerHTML = "<iframe width='800' height='400'></iframe>";
	this.frame = element.childNodes[0];
	this.wnd = this.frame.contentWindow;
	this.doc = this.wnd.document;
	var doc = this.doc;
	doc.open();
	doc
			.write('<!doctype html>\n<html>\n<head><\/head>\n<body><\/body>\n<\/html>\n');
	doc.close();
	setTimeout(function() {
		var scr = doc.createElement("script");
		scr.type = "text/javascript";
		scr.src = "http://127.0.0.1:8888/jackembed/jackembed.nocache.js";
		doc.getElementsByTagName('head')[0].appendChild(scr);
	}, 0);
}

/*
 * At the time the JS engine gets here, the actual functions (e.g.,
 * this.wnd.getFen) are not exported by GWT yet, so we use wrapper functions to
 * provide call by name semantics.
 */
chessdog.Board.prototype.resetPosition = function() {
	return this.wnd.resetPosition();
}
chessdog.Board.prototype.getFen = function() {
	return this.wnd.getFen();
}
chessdog.Board.prototype.setFen = function(fen) {
	return this.wnd.setFen(fen);
}
