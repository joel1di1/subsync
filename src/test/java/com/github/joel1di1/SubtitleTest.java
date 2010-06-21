package com.github.joel1di1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SubtitleTest {

	@Test
	public void testConstructorWithPeriodStringReturnSameAsConstructorWithTwoTime() {
		Subtitle subtitle1 = new Subtitle(0, "text", "00:00:20,000 --> 00:00:24,400");
		Subtitle subtitle2 = new Subtitle(0, "text", "00:00:20,000", "00:00:24,400");
		
		assertEquals(subtitle2, subtitle1);
	}

}
