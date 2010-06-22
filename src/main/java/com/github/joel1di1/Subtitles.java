package com.github.joel1di1;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Subtitles {
	private List<Subtitle> subtitles = new LinkedList<Subtitle>();

	public Subtitles() {
	}
	public Subtitles(List<String> lines) {
		readSubtitles(lines);
	}

	public void add(Subtitle subtitle) {
		subtitles.add(subtitle);
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
			String nextTextLine;
			while (it.hasNext() && ((StringUtils.isNotBlank( nextTextLine=it.next())) )){
				text += "\n"+nextTextLine;
			}
			Subtitle subtitle = new Subtitle(Integer.parseInt(indexLine), text, timeLine);
			return subtitle;
		}
		return null;
	}
	
	public void shift(long milliseconds) {
		List<Subtitle> newList = new LinkedList<Subtitle>();
		for (Subtitle subtitle : subtitles) {
			newList.add(subtitle.shift(milliseconds));
		}
		subtitles = newList;
	}
	
	public void writeToFile(File file) throws IOException {
		for (Subtitle subtitle : subtitles) {
			subtitle.writeToFile(file);
		}
	}
	
	public void shift(int firstIndex, long firstTime, int secondIndex, long secondTime) {
		List<Subtitle> res = new LinkedList<Subtitle>();
		for (Subtitle subtitle : subtitles) {
			long shift = calculShift(firstIndex, firstTime, secondIndex, secondTime, subtitle.startTime.toDate().getTime());
			res.add(subtitle.shift(shift));
		}
		subtitles = res;
	}
	
	long calculShift(int firstIndex, long firstTime, int secondIndex, long secondTime, long currentTime) {
		long currentFirstTime = get(firstIndex-1).startTime.toDate().getTime();
		long currentSecondTime = get(secondIndex-1).startTime.toDate().getTime();
		
		long firstShift = firstTime-currentFirstTime;
		long secondShift = secondTime-currentSecondTime;
		
		BigDecimal tmpShift1 = new BigDecimal(currentSecondTime-currentTime).multiply(new BigDecimal(firstShift)).divide(new BigDecimal(currentSecondTime-currentFirstTime),BigDecimal.ROUND_HALF_UP);
		BigDecimal tmpShift2 = new BigDecimal(currentTime-currentFirstTime).multiply(new BigDecimal(secondShift)).divide(new BigDecimal(currentSecondTime-currentFirstTime),BigDecimal.ROUND_HALF_UP);
		
		return tmpShift1.add(tmpShift2).longValue();
	}
}
