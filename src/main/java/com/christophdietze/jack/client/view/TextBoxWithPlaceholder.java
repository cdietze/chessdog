package com.christophdietze.jack.client.view;

import com.christophdietze.jack.client.resources.MyClientBundle;
import com.christophdietze.jack.client.resources.MyCss;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxWithPlaceholder {

	private static MyCss CSS = MyClientBundle.CSS;

	static {
		CSS.ensureInjected();
	}

	private final TextBox textBox;
	private final String placeholderText;
	private boolean placeholderVisible;

	public static TextBoxWithPlaceholder attachTo(TextBox textBox, String placeholderText) {
		return new TextBoxWithPlaceholder(textBox, placeholderText);
	}

	private TextBoxWithPlaceholder(final TextBox textBox, String placeholderText) {
		this.textBox = textBox;
		this.placeholderText = placeholderText;

		showPlaceholder();

		textBox.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				hidePlaceholder();
			}
		});
		textBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (textBox.getText().isEmpty()) {
					showPlaceholder();
				}
			}
		});
	}

	public String getText() {
		if (placeholderVisible) {
			return "";
		}
		return textBox.getText();
	}

	public void update() {
		if (!textBox.isAttached() && textBox.getText().isEmpty()) {
			showPlaceholder();
		}
	}

	private void showPlaceholder() {
		placeholderVisible = true;
		textBox.setText(placeholderText);
		textBox.addStyleName(CSS.textBoxPlaceholder());
	}

	private void hidePlaceholder() {
		if (placeholderVisible) {
			textBox.setText("");
			placeholderVisible = false;
			textBox.removeStyleName(CSS.textBoxPlaceholder());
		}
	}
}
