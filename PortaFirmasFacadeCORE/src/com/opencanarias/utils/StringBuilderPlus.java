package com.opencanarias.utils;

public class StringBuilderPlus {

	private StringBuilder sb;

	public StringBuilderPlus() {
		sb = new StringBuilder();
	}

	public void append(String str) {
		sb.append(str != null ? str : "");
	}
	
	public void appendLine(String str) {
		sb.append(str != null ? str : "").append(System.lineSeparator());
	}

	public void appendHtmlParagraph(String str) {
		sb.append("<p>").append(str != null ? str : "").append("</p>");
	}

	public String toString() {
		return sb.toString();
	}
}