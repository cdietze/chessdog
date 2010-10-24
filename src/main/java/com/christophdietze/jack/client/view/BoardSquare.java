package com.christophdietze.jack.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class BoardSquare extends Composite {

	private Image image;
	private SimplePanel rootPanel = new SimplePanel();

	public BoardSquare(Image image) {
		this.image = image;
		rootPanel.add(image);
		initWidget(rootPanel);
	}

	public Image getImage() {
		return image;
	}
}
