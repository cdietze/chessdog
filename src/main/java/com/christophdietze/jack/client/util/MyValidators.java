package com.christophdietze.jack.client.util;

import com.christophdietze.jack.shared.ValidatableNickname;
import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.interfaces.IValidator;

public class MyValidators {

	private static IValidator<ValidatableNickname> nicknameValidator = GWT.create(ValidatableNickname.class);

	public static IValidator<ValidatableNickname> nicknameValidator() {
		return nicknameValidator;
	}
}
