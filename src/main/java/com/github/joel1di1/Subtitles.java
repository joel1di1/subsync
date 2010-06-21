package com.github.joel1di1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Subtitles {
	private List<Subtitle> subtitles = new LinkedList<Subtitle>();

	public void add(Subtitle subtitle) {
		subtitles.add(subtitle);
	}

	public Iterator<Subtitle> iterator() {
		return subtitles.iterator();
	}

	public int size() {
		return subtitles.size();
	}

	public Subtitle get(int index) {
		return subtitles.get(index);
	}

	public void readSubtitles(List<String> lines) {
		Iterator<String> it = lines.iterator();
		while (it.hasNext()) {
			Subtitle subtitle = readNextSubtitleIfExist(it);
			add(subtitle);
		}
	}

	private Subtitle readNextSubtitleIfExist(Iterator<String> it) {
		String indexLine = it.next();
		while (StringUtils.isBlank(indexLine) && it.hasNext()){
			indexLine = it.next();
		}
		
		if (StringUtils.isNotBlank(indexLine)){
			String timeLine = it.next();
			String text = it.next();
			Subtitle subtitle = new Subtitle(Integer.parseInt(indexLine), text, timeLine);
			return subtitle;
		}
		return null;
	}
	
	
}
