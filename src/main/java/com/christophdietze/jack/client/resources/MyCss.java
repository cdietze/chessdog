package com.christophdietze.jack.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface MyCss extends CssResource {

	String nowrap();

	String boardContainer();
	String whiteSquare();
	String blackSquare();
	String squareImage();
	String squareSelection();
	String moveMarker();
	String rankLabel();
	String fileLabel();
	String fileLabelPlaceHolder();

	String promotionPieceImage();
	String promotionPopup();

	String draggingImage();

	String textBoxPlaceholder();
	String validationMessage();

	String moveListPanel();
	String moveListMoveNumber();
	String moveListMove();
	String moveListCurrentMove();
	String moveListBigSpace();
	String moveListSmallNonBreakableSpace();
	String moveListSmallBreakableSpace();
}
