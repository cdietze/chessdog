package com.christophdietze.jack.client.resources;

import java.util.HashMap;
import java.util.Map;

import com.christophdietze.jack.common.board.Piece;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

public class PieceImageProvider {

	private static Map<Piece, ImageResource> imagePrototypes = new HashMap<Piece, ImageResource>();

	private static PieceImageBundle bundle = PieceImageBundle.INSTANCE;

	static {
		initMap();
	}

	private static void initMap() {
		imagePrototypes.put(Piece.BLACK_BISHOP, bundle.bbishop());
		imagePrototypes.put(Piece.BLACK_KING, bundle.bking());
		imagePrototypes.put(Piece.BLACK_KNIGHT, bundle.bknight());
		imagePrototypes.put(Piece.BLACK_PAWN, bundle.bpawn());
		imagePrototypes.put(Piece.BLACK_QUEEN, bundle.bqueen());
		imagePrototypes.put(Piece.BLACK_ROOK, bundle.brook());
		imagePrototypes.put(Piece.BLACK_EN_PASSANT_PAWN, bundle.empty());
		imagePrototypes.put(Piece.EMPTY, bundle.empty());
		imagePrototypes.put(Piece.WHITE_BISHOP, bundle.wbishop());
		imagePrototypes.put(Piece.WHITE_KING, bundle.wking());
		imagePrototypes.put(Piece.WHITE_KNIGHT, bundle.wknight());
		imagePrototypes.put(Piece.WHITE_PAWN, bundle.wpawn());
		imagePrototypes.put(Piece.WHITE_QUEEN, bundle.wqueen());
		imagePrototypes.put(Piece.WHITE_ROOK, bundle.wrook());
		imagePrototypes.put(Piece.WHITE_EN_PASSANT_PAWN, bundle.empty());
	}

	public static ImageResource getImageResource(Piece piece) {
		return imagePrototypes.get(piece);
	}

	public static Image getImage(Piece piece) {
		return new Image(getImageResource(piece));
	}
}
