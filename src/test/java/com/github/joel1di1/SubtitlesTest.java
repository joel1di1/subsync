package com.github.joel1di1;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

public class SubtitlesTest {

	private Subtitles subs;

	@Before
	public void setup(){
		List<String> f = list(
				"1", 
				"00:00:20,000 --> 00:00:24,400",
				"Altocumulus clouds occur between six thousand",
				null,
				"2", 
				"02:13:23,970 --> 02:14:45,123",
				"<i>J'Žtais un agent secret jusqu'ˆ ce que...</i>"
		);
		
		subs = new Subtitles(f);
	}
	
	@Test
	public void shouldShiftPeriod() {
		subs.shift(4235);

		Subtitle subtitle = subs.get(0);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 20, 000, DateTimeZone.UTC).plus(4235), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 24, 400, DateTimeZone.UTC).plus(4235), subtitle.endTime);
		
		subtitle = subs.get(1);
		assertEquals(2, subtitle.index);
		assertEquals(new DateTime(1970, 1, 1, 2 , 13 , 23, 970, DateTimeZone.UTC).plus(4235), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 2 , 14 , 45, 123, DateTimeZone.UTC).plus(4235), subtitle.endTime);
	}	
	
	@Test
	public void shouldShiftPeriodMinus() {
		
		subs.shift(-8764235);

		Subtitle subtitle = subs.get(0);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 20, 000, DateTimeZone.UTC).plus(-8764235), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 24, 400, DateTimeZone.UTC).plus(-8764235), subtitle.endTime);
		
		subtitle = subs.get(1);
		assertEquals(2, subtitle.index);
		assertEquals(new DateTime(1970, 1, 1, 2 , 13 , 23, 970, DateTimeZone.UTC).plus(-8764235), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 2 , 14 , 45, 123, DateTimeZone.UTC).plus(-8764235), subtitle.endTime);
	}
	
	@Test
	public void shouldWritetofile() throws IOException{
		File file = File.createTempFile("test", "tmp");
		subs.writeToFile(file);
		assertEquals("1\n"+
				"00:00:20,000 --> 00:00:24,400\n"+
				"Altocumulus clouds occur between six thousand\n"+
				"\n"+
				"2\n"+
				"02:13:23,970 --> 02:14:45,123\n"+
				"<i>J'Žtais un agent secret jusqu'ˆ ce que...</i>\n\n", FileUtils.readFileToString(file));
	}

	private List<String> list(String... strings) {
		return Arrays.asList(strings);
	}

}
