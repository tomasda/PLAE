package com.opencanarias.utils;

public class HtmlUtils {

	private static final String labelToolTipStyle = "<label style=\"cursor:hand __STYLE__\" title=\"__TITLE__\">__NAME__</label>";
	
	/**
	 * Compone el codigo HTML del link
	 */
	public static String createLabelToolTipAndStyle(String name, String toolTip, String style) {

		String param = name;
		if(name==null){
			param = "";
		}

		String result = labelToolTipStyle.replaceAll("__NAME__", param);
		result = result.replaceAll("__TITLE__", toolTip);
		result = result.replaceAll("__STYLE__", style);
		return result;
	}
}
