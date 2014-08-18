chessdog = {}

/**
 * @constructor
 */
window.chessdog.Board = function(element, chessdogJsUrl) {
	element.innerHTML = "<iframe width='600' height='385' frameborder='0' style='border: none;'></iframe>";
	this.frame = element.childNodes[0];
	this.wnd = this.frame.contentWindow;
	this.doc = this.wnd.document;
	var wnd = this.wnd;
	var doc = this.doc;
	doc.open();
	doc
			.write("<!doctype html>\n<html><head></head><body>"
					+ "<div id='removeme' style='font-family: Arial Unicode MS,Arial,sans-serif;font-size: small;'>Loading chess board...</div>"
					+ "</body></html>\n");
	doc.close();

	setTimeout(
			function() {
				var scriptElement = doc.createElement("script");
				scriptElement.type = "text/javascript";
				scriptElement.src = chessdogJsUrl || "http://chessdog.christophdietze.com/jackembed/jackembed.nocache.js";
				doc.getElementsByTagName('head')[0].appendChild(scriptElement);
			}, 0);
}

/*
 * At the time the JS engine gets here, the actual functions (e.g.,
 * this.wnd.getFen) are not exported by GWT yet, so we use wrapper functions to
 * provide call by name semantics.
 */

chessdog.Board.prototype.reset = function() {
	var board = this;
	chessdog._onReady(this, function() {
		board.wnd.reset();
	});
}

chessdog.Board.prototype.getFen = function() {
	if(chessdog._assertReady(this)) {
		return this.wnd.getFen();
	}
}

chessdog.Board.prototype.setFen = function(fen) {
	var board = this;
	chessdog._onReady(this, function() {
		board.wnd.setFen(fen);
	});
}

chessdog.Board.prototype.getPgn = function() {
	if(chessdog._assertReady(this)){
		return this.wnd.getPgn();
	}
}

chessdog.Board.prototype.setPgn = function(pgn) {
	var board = this;
	chessdog._onReady(this, function() {
		board.wnd.setPgn(pgn);
	});
}

chessdog.Board.prototype.allowUserMoves = function(allowed) {
	var board = this;
	chessdog._onReady(this, function() {
		board.wnd.allowUserMoves(allowed);
	});
}

chessdog.Board.prototype.onReady = function(fun) {
	chessdog._onReady(this, fun);
}


/*
 * private functions
 */

chessdog._readyQueue = []

chessdog._onReady = function(board, fun) {
	if (board.wnd.chessdogReady) {
		fun();
	} else {
		chessdog._readyQueue.push(fun);
		if(chessdog._readyQueue.length==1) {
			chessdog._processReadyQueue(board);
		}
	}
}

chessdog._processReadyQueue = function(board) {
	if(board.wnd.chessdogReady) {
		for(i=0;i<chessdog._readyQueue.length;i++) {
			chessdog._readyQueue[i]();
		}
		chessdog._readyQueue=[];
	} else {
		setTimeout(function() {	chessdog._processReadyQueue(board);}, 100);
	}
}

chessdog._assertReady = function(board) {
	if(board.wnd.chessdogReady) {
		return true;
	} else {
		// TODO document / link better
		alert("chessdog not ready yet. Have a look at board.onReady().");
		return false;
	}
}