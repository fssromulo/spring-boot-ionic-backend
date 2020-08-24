package com.romulocurso.cursomc.resources.utils;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	// Converter uma string separada por virgula em array
	public static List<Integer> decodeIntList(String strList) {
		return Arrays.asList(strList.split(",")).stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");	
		} catch (Exception e) {
			return "";
		}
	}
}
