package com.github.joel1di1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class AnotherFileUtils {

	public static File createTmpFile(String... lines) {
		File f;
		try {
			f = File.createTempFile("subsync-", ".tmp");
			FileUtils.writeLines(f, Arrays.asList(lines));
		} catch (IOException e) {
			throw new SubSyncException("Impossible to create tmp file", e);
		}
		return f;
	}
}
