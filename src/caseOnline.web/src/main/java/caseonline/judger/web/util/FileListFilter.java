package caseonline.judger.web.util;

import java.io.File;
import java.io.FileFilter;

public class FileListFilter implements FileFilter{

	@Override
	public boolean accept(File file) {
		String fileName=file.getName();
		return fileName.endsWith(".java");
	}

}
