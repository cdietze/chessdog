package com.christophdietze.jack.common.board;

public class Position2Utils {

	public static String toDiagramString(Position2 pos) {
		StringBuilder sb = new StringBuilder();
		for (int y = 7; y >= 0; --y) {
			if (y == 7) {
				sb.append("\n");
			} else {
				sb.append("|\n");
			}
			sb.append("--------------------------------\n");
			for (int x = 0; x < 8; ++x) {
				int index = y * 8 + x;
				sb.append("| ");
				sb.append(pos.getPiece(index).getSymbol());
				sb.append(" ");
			}
		}
		sb.append("|\n--------------------------------\n");
		return sb.toString();
	}
}
