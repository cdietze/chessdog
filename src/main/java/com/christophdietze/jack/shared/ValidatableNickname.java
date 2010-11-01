package com.christophdietze.jack.shared;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gwt.validation.client.interfaces.IValidatable;

public class ValidatableNickname implements IValidatable {

	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "\\w*", message = "Must only contain letters, digits and underscores")
	private String name;

	public ValidatableNickname(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
