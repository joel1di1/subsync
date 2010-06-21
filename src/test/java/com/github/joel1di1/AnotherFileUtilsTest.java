package com.github.joel1di1;

import static com.github.joel1di1.AnotherFileUtils.createTmpFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class AnotherFileUtilsTest {

	@Test
	public void testCreateTmpFileWithOneLine() throws IOException {
		File f = createTmpFile("test");
		assertTrue(f.exists());
	 	assertFileContainsLine(f, "test");
	}
	
	@Test
	public void testCreateTmpFileWithSeveralLines() throws IOException {
		File f = createTmpFile("youpi", "coucou", "test");
		assertTrue(f.exists());
	 	assertFileContainsLine(f, "youpi");
	 	assertFileContainsLine(f, "coucou");
	 	assertFileContainsLine(f, "test");
	 	assertFileContents(f, "youpi\ncoucou\ntest\n");
	}
	
	@Test
	public void testCreateTmpFileWithSeveralLinesAndNulls() throws IOException {
		File f = createTmpFile("youpi", null, "coucou", null, "test");
		assertTrue(f.exists());
	 	assertFileContainsLine(f, "youpi");
	 	assertFileContainsLine(f, "coucou");
	 	assertFileContainsLine(f, "test");
	 	assertFileContents(f, "youpi\n\ncoucou\n\ntest\n");
	}

	private void assertFileContents(File f, String expected) throws IOException {
		String value = FileUtils.readFileToString(f);
		assertEquals(expected, value);
	}

	@SuppressWarnings("unchecked")
	private void assertFileContainsLine(File f, String line) throws IOException {
	 	List<String> l = FileUtils.readLines(f);
	 	assertTrue("The file should contains ["+line+"]", l.contains(line));
	}

}
