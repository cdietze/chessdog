package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.resources.PieceImageProvider;
import com.christophdietze.jack.shared.board.Piece;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A BoardSquare instance is responsible for one specific square on the board
 * over its whole lifetime. What changes is its contained image.
 * <p>
 * A BoardSquare is made of a DIV-element containing an IMAGE-element. An empty
 * square also contains an image (empty.png). While a user is dragging a piece
 * from a square, the image is set to empty.
 */
public class BoardSquare {

	private int index;
	private Image image;
	private SimplePanel pieceImageContainer;

	public BoardSquare(int index, Piece piece) {
		this.index = index;
		this.pieceImageContainer = new SimplePanel();
		setPiece(piece);
	}

	public SimplePanel getPanel() {
		return pieceImageContainer;
	}

	public Image getImage() {
		return image;
	}

	public void setPiece(Piece piece) {
		Image newImage = PieceImageProvider.getImage(piece);
		if (this.image != newImage) {
			this.image = newImage;
			pieceImageContainer.setWidget(newImage);
		}
	}

	public void setImage(Image image) {
		this.image = image;
		pieceImageContainer.setWidget(image);
	}

	public int getIndex() {
		return index;
	}
}
