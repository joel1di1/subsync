package com.github.joel1di1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class AnotherFileUtils {

	public static File createTmpFile(String... lines) {
		return createTmpFile(Arrays.asList(lines));
	}
	

	public static File createTmpFile(List<String> l) {
		File f;
		try {
			f = File.createTempFile("subsync-", ".tmp");
			FileUtils.writeLines(f, l);
		} catch (IOException e) {
			throw new SubSyncException("Impossible to create tmp file", e);
		}
		return f;
	}

	public static void appendLines(File file, List<String> lines) throws IOException {
		OutputStream out = null;
        try {
            out = openOutputStream(file, true);
            IOUtils.writeLines(lines, null, out, null);
        } finally {
            IOUtils.closeQuietly(out);
        }
	}
	
	 public static FileOutputStream openOutputStream(File file, boolean b) throws IOException {
	        if (file.exists()) {
	            if (file.isDirectory()) {
	                throw new IOException("File '" + file + "' exists but is a directory");
	            }
	            if (file.canWrite() == false) {
	                throw new IOException("File '" + file + "' cannot be written to");
	            }
	        } else {
	            File parent = file.getParentFile();
	            if (parent != null && parent.exists() == false) {
	                if (parent.mkdirs() == false) {
	                    throw new IOException("File '" + file + "' could not be created");
	                }
	            }
	        }
	        return new FileOutputStream(file, b);
	    }


	public static void appendLines(File file, String... lines) throws IOException {
		appendLines(file, Arrays.asList(lines));
	}
}
