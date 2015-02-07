package com.sqli.nespresso.training.kata.test;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sqli.nespresso.training.kata.StringCalculator;

public class StringCalculatorTests {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void simpleTestOfStringCalculator(){
		assertEquals(0, new StringCalculator().add(""));
		assertEquals(1, new StringCalculator().add("1"));
		assertEquals(3, new StringCalculator().add("1,2"));
		
	}
	
	@Test
	public void allowUnknownAmountOfNumbers(){
		assertEquals(10, new StringCalculator().add("1,2,3,4"));
	}
	
	@Test
	public void allowNewLineAsDelimiter(){
		assertEquals(10, new StringCalculator().add("1,2\n3,4"));
	}
	
	@Test
	public void supportDifferentDelimiters(){
		assertEquals(3, new StringCalculator().add("//;\n1;2"));
	}
	
	@Test
	public void negativeNumbersAreNotAllowed(){
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Negatives not allowed: -3 ");
		new StringCalculator().add("1,2,-3");
	}
	
	@Test
	public void numbersBiggerThan1000ShouldBeIgnored(){
		assertEquals(3, new StringCalculator().add("//;\n1;2;1002;1001"));
	}
	
	@Test
	public void delimitersCanBeOfAnyLength(){
		assertEquals(6, new StringCalculator().add("//[:::]\n1:::2:::3"));
	}
	
	@Test
	public void metaCharactersShouldBeEscaped(){
		assertEquals(6, new StringCalculator().add("//[***]\n1***2***3"));
	}
	
	@Test
	public void allowMultipleDelimiters(){
		assertEquals(6, new StringCalculator().add("//[*][%]\n1*2%3"));
	}
	
	@Test
	public void multipleDelimitersWithLengthLongerThenOne(){
		assertEquals(6, new StringCalculator().add("//[***][%%]\n1***2%%3"));
	}
}
