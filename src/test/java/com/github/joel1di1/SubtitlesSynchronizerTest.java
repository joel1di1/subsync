package com.github.joel1di1;

import static com.github.joel1di1.AnotherFileUtils.createTmpFile;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

public class SubtitlesSynchronizerTest {

	SubtitlesSynchronizer subtitlesSynchronizer;

	@Before
	public void setUp() throws Exception {
		subtitlesSynchronizer = new SubtitlesSynchronizer();
	}

	@Test
	public void shouldReadSrtFiles() {
		File f = createTmpFile(
				"1", 
				"00:00:20,000 --> 00:00:24,400",
				"Altocumulus clouds occur between six thousand");

		Subtitles subs = subtitlesSynchronizer.read(f);
		assertEquals(1, subs.size());

		Subtitle subtitle = subs.get(0);
		assertEquals(1, subtitle.index);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 20, 000, DateTimeZone.UTC), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 24, 400, DateTimeZone.UTC), subtitle.endTime);
		assertEquals("Altocumulus clouds occur between six thousand", subtitle.text);

	}
	

	@Test
	public void shouldReadSrtFilesWithTextOnSeveralLines() {
		File f = createTmpFile(
				"1", 
				"00:39:54,985 --> 00:39:58,159",
				"Comment avez vous fait ?",
				"Comment avez-vous pu tout récupérer ?"
				);

		Subtitles subs = subtitlesSynchronizer.read(f);

		Subtitle subtitle = subs.get(0);
		assertEquals("Comment avez vous fait ?\nComment avez-vous pu tout récupérer ?", subtitle.text);
	}
	
	
	@Test
	public void shouldReadSrtFilesWithSeveralSubs() {
		File f = createTmpFile(
				"1", 
				"00:00:20,000 --> 00:00:24,400",
				"Altocumulus clouds occur between six thousand",
				null,
				"2", 
				"02:13:23,970 --> 02:14:45,123",
				"<i>J'étais un agent secret jusqu'à ce que...</i>"
				);

		Subtitles subs = subtitlesSynchronizer.read(f);
		assertEquals(2, subs.size());

		Subtitle subtitle = subs.get(0);
		assertEquals(1, subtitle.index);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 20, 000, DateTimeZone.UTC), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 0 , 0 , 24, 400, DateTimeZone.UTC), subtitle.endTime);
		assertEquals("Altocumulus clouds occur between six thousand", subtitle.text);
		
		subtitle = subs.get(1);
		assertEquals(2, subtitle.index);
		assertEquals(new DateTime(1970, 1, 1, 2 , 13 , 23, 970, DateTimeZone.UTC), subtitle.startTime);
		assertEquals(new DateTime(1970, 1, 1, 2 , 14 , 45, 123, DateTimeZone.UTC), subtitle.endTime);
		assertEquals("<i>J'étais un agent secret jusqu'à ce que...</i>", subtitle.text);
	}
	
	@Test
	public void shouldShiftWithConstantShift() {
		File f = createTmpFileOnlyWithTime(
				"00:01:00,000 --> 00:01:30,000",
				"00:04:00,000 --> 00:04:30,000"
				);

		Subtitles subs = subtitlesSynchronizer.read(f);
		
		subs.shift(1, 120000l, 2, 300000l);
		
		assertDateTimeEquals("00:02:00,000", subs.get(0).startTime);
		assertDateTimeEquals("00:02:30,000", subs.get(0).endTime);
		assertDateTimeEquals("00:05:00,000", subs.get(1).startTime);
		assertDateTimeEquals("00:05:30,000", subs.get(1).endTime);
	}

	private void assertDateTimeEquals(String string, DateTime startTime) {
		assertEquals(string, Subtitle.fmt.print(startTime));
	}
	

	@Test
	public void shouldShiftWithNonConstantShift() {
		File f = createTmpFileOnlyWithTime(
				"00:01:00,000 --> 00:01:30,000",
				"00:04:00,000 --> 00:04:30,000"
				);

		Subtitles subs = subtitlesSynchronizer.read(f);
		
		subs.shift(1, 120000l, 2, 360000l);
		
		assertDateTimeEquals("00:02:00,000", subs.get(0).startTime);
		assertDateTimeEquals("00:02:30,000", subs.get(0).endTime);
		assertDateTimeEquals("00:06:00,000", subs.get(1).startTime);
		assertDateTimeEquals("00:06:30,000", subs.get(1).endTime);
	}
	
	@Test
	public void shouldShiftWithNonConstantShiftShouldLinearyModifyTime() {
		File f = createTmpFileOnlyWithTime(
				"00:10:00,000 --> 00:10:30,000",
				"00:15:00,000 --> 00:15:15,000",
				"00:20:00,000 --> 00:20:30,000"
				);

		Subtitles subs = subtitlesSynchronizer.read(f);
		
		subs.shift(1, 30*60*1000, 3, 60*60*1000);
		
		assertDateTimeEquals("00:30:00,000", subs.get(0).startTime);
		assertDateTimeEquals("00:30:30,000", subs.get(0).endTime);

		assertDateTimeEquals("00:45:00,000", subs.get(1).startTime);
		assertDateTimeEquals("00:45:15,000", subs.get(1).endTime);

		assertDateTimeEquals("01:00:00,000", subs.get(2).startTime);
		assertDateTimeEquals("01:00:30,000", subs.get(2).endTime);
	}

	private File createTmpFileOnlyWithTime(String... timelines) {
		List<String> l = new ArrayList<String>();
		int i = 1;
		for (String timeLine : timelines) {
			l.add(""+i);
			l.add(timeLine);
			l.add("Text_"+i);
			l.add(null);
			i++;
		}
		return createTmpFile(l);
	}


}
