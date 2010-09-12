package com.christophdietze.jack.shared.util;

public class SimpleToStringBuilder {

	private StringBuilder sb = new StringBuilder();

	private boolean beforeFirstArg = true;

	private boolean stringClosed = false;

	public static SimpleToStringBuilder create(Object object) {
		SimpleToStringBuilder csb = new SimpleToStringBuilder(object);
		StringBuilder sb = csb.sb;
		if (object == null) {
			sb.append("null");
			csb.stringClosed = true;
			return csb;
		}
		csb.appendPrefix(object);
		return csb;
	}

	public static SimpleToStringBuilder create(Object object, String superToString) {
		SimpleToStringBuilder csb = new SimpleToStringBuilder(object);
		StringBuilder sb = csb.sb;
		if (object == null) {
			sb.append("null");
			csb.stringClosed = true;
			return csb;
		}
		if (superToString.charAt(superToString.length() - 1) == ']') {
			sb.append(superToString, 0, superToString.length() - 1);
			csb.beforeFirstArg = false;
		} else {
			csb.appendPrefix(object);
		}
		return csb;
	}

	private SimpleToStringBuilder(Object object) {
	}

	private void appendPrefix(Object object) {
		// sb.append(object.getClass().getName());
		String fullName = object.getClass().getName();
		int prefixLen = fullName.lastIndexOf('$');
		if (prefixLen == -1) {
			prefixLen = fullName.lastIndexOf('.');
		}
		prefixLen++;
		String name = fullName.substring(prefixLen);
		sb.append(name);
		// sb.append("@");
		// sb.append(Integer.toHexString(System.identityHashCode(object)));
		sb.append("[");
	}

	public SimpleToStringBuilder append(String label, Object value) {
		appendFieldSeperator();
		sb.append(label);
		sb.append("=");
		sb.append(value);
		return this;
	}

	public SimpleToStringBuilder append(Object value) {
		appendFieldSeperator();
		sb.append(value);
		return this;
	}

	private void appendFieldSeperator() {
		if (beforeFirstArg) {
			beforeFirstArg = false;
		} else {
			sb.append(",");
		}
	}

	@Override
	public String toString() {
		if (!stringClosed) {
			sb.append("]");
			stringClosed = true;
		}
		return sb.toString();
	}
}
