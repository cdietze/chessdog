package com.christophdietze.jack.client.util;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

public class UiUtils {

	public static FlexTable createFlexTable() {
		FlexTable table = new FlexTable();
		table.setBorderWidth(0);
		table.setCellPadding(0);
		table.setCellSpacing(0);
		return table;
	}

	public static PopupPanel createPopup(boolean autoHide) {
		PopupPanel popupPanel = new PopupPanel(autoHide);
		// popupPanel.addStyleName(ClientConstants.CSS_CHESSDOG_ROOT);
		return popupPanel;
	}

	public static void setPopupPositionAtTopLeft(PopupPanel popup,
			UIObject reference) {
		popup.setPopupPosition(reference.getAbsoluteLeft()
				+ reference.getOffsetWidth() / 2, reference.getAbsoluteTop()
				+ reference.getOffsetHeight() / 2);
	}

	public static void setPopupPositionAtTopRight(PopupPanel popup,
			UIObject reference) {
		popup.setPopupPosition(reference.getAbsoluteLeft()
				+ reference.getOffsetWidth() / 2 - popup.getOffsetWidth(),
				reference.getAbsoluteTop() + reference.getOffsetHeight() / 2);
	}

	public static void setPopupPositionAtBottomLeft(PopupPanel popup,
			UIObject reference) {
		popup.setPopupPosition(reference.getAbsoluteLeft()
				+ reference.getOffsetWidth() / 2, reference.getAbsoluteTop()
				+ reference.getOffsetHeight() / 2 - popup.getOffsetHeight());
	}
}
