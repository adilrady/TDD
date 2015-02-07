package com.sqli.nespresso.training.kata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

	private static final String DEFAULT_DELIMITER = ",|\n";
	public int add(String numbers){
		
		int numbersArray[] = parse(numbers);
		int sum = 0;
		for (int i = 0; i < numbersArray.length; i++) {
			if(numbersArray[i] > 1000){
				continue;
			}
			sum += numbersArray[i];
		}
		
		return sum;
	}

	private int[] parseNumbers(String numbers, String delimiter) {
		
		if(numbers.equals("")){
			return new int[0];
		}
		
		String numbersAsArrayOString[] = numbers.split(delimiter);
		int numbersArray[] = new int[numbersAsArrayOString.length];
		
		for (int i = 0; i < numbersAsArrayOString.length; i++) {
			numbersArray[i] = Integer.parseInt(numbersAsArrayOString[i]);
		}
		return numbersArray;
	}
	
	private int[] parse(String numbers){
		checkForNegativeNumbers(numbers);
		if(numbers.startsWith("//")){
			String delimiter = escapeMetaChar(getDelimiter(numbers));
			String numbersList = getNumbers(numbers);
			return parseNumbers(numbersList, delimiter);
		}else{
			return parseNumbers(numbers, DEFAULT_DELIMITER);
		}
	}

	private String escapeMetaChar(String delimiter) {
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < delimiter.length(); i++) {
			if(isMetaChar(delimiter.charAt(i))){
				builder.append("\\").append(delimiter.charAt(i));
			}else{
				builder.append(delimiter.charAt(i));
			}
		}
		return builder.toString();
	}

	private boolean isMetaChar(char character) {
		return "*.+?".indexOf(character) != -1 ;
	}

	private void checkForNegativeNumbers(String numbers) {
		if(numbers.contains("-")){
			Pattern pattern = Pattern.compile("(-\\d)");
			Matcher matcher = pattern.matcher(numbers);
			StringBuilder message = new StringBuilder("Negatives not allowed: ");
			while(matcher.find()){
				message.append(matcher.group()).append(" ");
			}
			throw new RuntimeException(message.toString());
		}
		
	}

	private String getNumbers(String numbers) {
		return numbers.substring(numbers.indexOf("\n") + 1);
	}

	private String getDelimiter(String numbers) {
		
		if(numbers.contains("[")){
			Pattern pattern = Pattern.compile("\\[(.*?)\\]");
			Matcher matcher = pattern.matcher(numbers);
			StringBuilder delimiters = new StringBuilder();
			
			while(matcher.find()){
				delimiters.append(matcher.group(1)).append("|");
			}
			return delimiters.toString().substring(0, delimiters.length() -1 );
		}else{
			return numbers.substring(2, 3);
		}
	}
	
	
	
	
}
