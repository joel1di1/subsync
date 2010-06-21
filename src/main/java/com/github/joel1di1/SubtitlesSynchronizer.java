package com.github.joel1di1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SubtitlesSynchronizer {

	public Subtitles read(File f) {

		List<String> lines = readFile(f);

		return read(lines);
	}

	protected Subtitles read(List<String> lines) {
		Subtitles subtitles = new Subtitles();
		
		subtitles.readSubtitles(lines);
		
		return subtitles;
	}

	@SuppressWarnings("unchecked")
	private List<String> readFile(File f) {
		try {
			 return FileUtils.readLines(f);
		} catch (IOException e) {
			throw new SubSyncException("impossible to read "+f.getAbsolutePath(), e);
		}
	}
}
