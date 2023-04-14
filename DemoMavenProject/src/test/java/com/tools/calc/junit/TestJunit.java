package com.tools.calc.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tools.calc.CalculateMethods;

public class TestJunit {

	CalculateMethods calc = new CalculateMethods();
	
	@BeforeAll
	public static void init() throws IOException {
		System.out.println("Starting TestJunit...");
	}
	
	@Test
	public void testFivePlusFive() {
		System.out.println("testFivePlusFive..");
		assertTrue(calc.fivePlusFive() == 10);
	}
	
	@Test
	public void testSum1() throws IOException, AWTException {
		System.out.println("testSum1..");
		assertTrue(calc.sum(15, 5) == 20);
	}

	@Test
	public void testSum2() {
		System.out.println("testSum2..");
		assertTrue(calc.sum(3, 5) == 8);
	}
	
	@Test
	public void testSum3() {
		System.out.println("testSum3..");
		assertTrue(calc.sum(13, 28) == 41);
	}
	
	@Test
	public void testPrintMethod() {
		calc.printMethod("This is print method");
	}
	
	@AfterAll
	public static void stopDriverServer() {
		System.out.println("Stopping server...");
	}
}
