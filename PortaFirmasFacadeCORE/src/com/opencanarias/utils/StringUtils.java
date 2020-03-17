package com.opencanarias.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}
	
	public static String capitalize(String str) {
		if (str == null || str.trim().equals(""))
			return str;
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
	public static String joinString(Collection<String> col, String delim) {
	    StringBuilder sb = new StringBuilder();
	    Iterator<String> iter = col.iterator();
	    if (iter.hasNext())
	        sb.append(iter.next());
	    while (iter.hasNext()) {
	        sb.append(delim);
	        sb.append(iter.next());
	    }
	    return sb.toString();
	}
	
    public static String join(Iterator<?> iterator, String delim) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return Objects.toString(first, "");
        }

        // two or more elements
        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(delim);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }
    
    public static String join(List<?> list, String delim, int startIndex, int endIndex) {
        if (list == null) {
            return null;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return "";
        }
        List<?> subList = list.subList(startIndex, endIndex);
        return join(subList.iterator(), delim);
    }
    
    public static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        }
        char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }
	
    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return "";
        }
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= 8192) {
            return repeat(str.charAt(0), repeat);
        }

        int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1 :
                return repeat(str.charAt(0), repeat);
            case 2 :
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default :
                StringBuilder buf = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }
}

