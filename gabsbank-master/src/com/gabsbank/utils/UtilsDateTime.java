package com.gabsbank.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilsDateTime {
	
	public static String pegarDataHora() {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    return now.format(formatter);
	}

}
