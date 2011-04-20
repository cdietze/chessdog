package com.christophdietze.jack.client.view.embed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MoveNavigationPanel extends Composite {

	private static MoveNavigationPanelUiBinder uiBinder = GWT.create(MoveNavigationPanelUiBinder.class);

	interface MoveNavigationPanelUiBinder extends UiBinder<Widget, MoveNavigationPanel> {
	}

	@UiField
	Button navStartButton;
	@UiField
	Button navPrevMoveButton;
	@UiField
	Button navNextMoveButton;
	@UiField
	Button navEndButton;

	public MoveNavigationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
