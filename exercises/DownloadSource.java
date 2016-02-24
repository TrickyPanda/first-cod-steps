import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import myLibrary.Utils;

import org.apache.commons.io.FileUtils;

public class DownloadSource {	

	public static File getSourceFile(String[] sourceArray) throws IOException, URISyntaxException {
		
		String git = null;
		String tag = null;

		for(String value : sourceArray){
			int colonIdx = value.indexOf(":");
			URI source = new URI(value);
			String scheme = source.getScheme();
			if(scheme.equals("file")){
				File localRepo = new File(source.getPath());
				File tmp = NatJFileUtils.getNewTempDirectory();
				FileUtils.copyDirectory(localRepo, tmp);
				return tmp;
			}
			else if(scheme.equals("git")){
				git = value.substring(colonIdx + 1).trim();
			}
			else if(scheme.equals("tag")){
				tag = value.substring(colonIdx + 1).trim();
			}
		}
		
		if(git != null && tag != null){
			File tmp = Utils.downloadIt(git, tag);
			return tmp;
		}
		
		return null;
	}
