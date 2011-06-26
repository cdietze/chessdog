package com.christophdietze.jack.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Copied and customized from {@link java.util.logging.SimpleFormatter}.
 * 
 * @see https://thunderklaus.fogbugz.com/default.asp?W2
 */
public class MyLogFormatter extends Formatter {

	private Date date = new Date();
	private final static String format = "{0,date} {0,time}";
	private MessageFormat formatter;

	private Object args[] = new Object[1];

	private String lineSeparator = "\n";

	public synchronized String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		appendDateAndTime(sb, record);
		sb.append(" ");
		appendLogLevel(record, sb);
		sb.append(" (");
		appendClassAndMethod(sb, record);
		sb.append(") ");
		appendMessage(sb, record);
		sb.append(lineSeparator);
		appendThrowable(record, sb);
		return sb.toString();
	}

	private void appendDateAndTime(StringBuilder sb, LogRecord record) {
		date.setTime(record.getMillis());
		args[0] = date;
		StringBuffer text = new StringBuffer();
		if (formatter == null) {
			formatter = new MessageFormat(format);
		}
		formatter.format(args, text, null);
		sb.append(text);
	}

	private void appendLogLevel(LogRecord record, StringBuilder sb) {
		final int minLength = 7;
		String level = record.getLevel().getLocalizedName();
		sb.append(level);
		if (minLength > level.length()) {
			char[] fillChars = new char[minLength - level.length()];
			Arrays.fill(fillChars, ' ');
			sb.append(fillChars);
		}
	}

	private void appendClassAndMethod(StringBuilder sb, LogRecord record) {
		if (record.getSourceClassName() != null) {
			sb.append(record.getSourceClassName());
		} else {
			sb.append(record.getLoggerName());
		}
		if (record.getSourceMethodName() != null) {
			sb.append(":");
			sb.append(record.getSourceMethodName());
		}
	}

	private void appendMessage(StringBuilder sb, LogRecord record) {
		String message = formatMessage(record);
		sb.append(message);
	}

	private void appendThrowable(LogRecord record, StringBuilder sb) {
		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			sb.append(sw.toString());
		}
	}
}
