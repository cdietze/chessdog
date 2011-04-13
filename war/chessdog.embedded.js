chessdog = {}

chessdog.iframeOnload = function() {
	window.alert("hello from iframe");
}

chessdog.Board = function(element) {
	element.innerHTML = "<iframe id=\"chessdogFrame\" onload=\"this.xxx()\" ></iframe>";
	var x = element.childNodes[0];
	var frame = document.getElementById("chessdogFrame");
	var doc = frame.contentDocument;
	x.xxx = function() { 
//		this.contentDocument.body.innerHTML = "<script type=\"text/javascript\" src=\"jack/jack.nocache.js\" > </script>";
//		this.contentDocument.body.innerHTML = "<script type=\"text/javascript\" src=\"sayhello.js\" > </script>";
//		this.contentDocument.body.innerHTML = "hello";
	//	window.alert("1: "+frame.contentDocument+" 2: "+this.contentDocument);
		
		
		//jQuery(frame).ready(function() {
			window.alert("hello from jquery frame:"+frame+", doc: "+doc);
			var doc =  frame.contentDocument;
//	    var sel = doc.createElement("script");
//	    sel.type = "text/javascript";
//	    //sel.src = "sayhello.js";
//	    sel.src = "jack/jack.nocache.js";
//	    doc.getElementById("id2").appendChild(sel); 
	doc.getElementById("id2").innerHTML = "<script type=\"text/javascript\" src=\"sayhello.js\" > </script>";
//	}); 
	}
	    doc.open();
//		doc.write('<!doctype html>\n<html>\n<head>\nhello\n<script type="text/javascript" src="jack/jack.nocache.js" > <\/script>\n<\/head>\n<body><\/body>\n<\/html>\n');
		doc.write('<!doctype html>\n<html>\n<head>\nhello\n<div id="id2" > <\/div>\n<\/head>\n<body><\/body>\n<\/html>\n');
	//	doc.write('<!doctype html>\n<html>\n<head>\nhello\n\n<\/head>\n<body><\/body>\n<\/html>\n');
		doc.close();
	//	window.alert("body now is: "+this.contentDocument.body.innerHTML);
	//	document.body.innerHTML = "hello";
	//	window.alert("hello from xxx3");
//	}
}

	
/*
chessdog.Board = function(element) {
	element.innerHTML = "<iframe id=\"chessdogFrame\" src=\"about:blank\" style=\"border: 0; overflow: hidden; height: 450px; width: 800px;\">HALLO</iframe>";

	var frame = document.getElementById("chessdogFrame");
	if(frame.contentWindow) {
		var doc=frame.contentWindow.document;
	} else {
		var doc=frame.contentDocument;
	}*/

/*    var sel = doc.createElement("script");
    sel.type = "text/javascript";
    sel.src = "jack/jack.nocache.js";
    //window.alert("head: "+doc.getElementsByTagName("head")[0]);
    //doc.getElementsByTagName("head")[0].appendChild(sel);
    doc.body.appendChild(sel);
    //doc.close();
	//});*/
	/*
	setTimeout(function() {
	window.alert("timeout running");
	doc.open();
//	doc.write('<!doctype html>\n');
//	doc.write('<html>\n<head>\n');
//	doc.write('hello\n');
//	doc.write('<script type="text/javascript" src="jack/jack.nocache.js" > </script>\n');
//	doc.write('<\/head>\n<body><\/body>\n<\/html>\n');
	doc.write('<!doctype html>\n<html>\n<head>\nhello\n<script type="text/javascript" src="jack/jack.nocache.js" > </script>\n<\/head>\n<body><\/body>\n<\/html>\n');
	doc.close();
	}, 1000);
	
}*/
