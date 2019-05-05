package uoc.pec2.tecnologia.twitter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uoc.pec2.tecnologia.twitter.functions.FilterEnum;
import uoc.pec2.tecnologia.web.DirectoryRegistry;

@Component
public class DirectoryCleaner {

	@Scheduled(fixedRate = 60 * 1000)
	public void clean() {
		System.out.println("Cleaning...");
		for(FilterEnum filter : FilterEnum.values()) {
			String path = DirectoryRegistry.getInstance().getPath(filter.name());
			if(path != null) {
				File dataPath = new File(path);
				for(File file : dataPath.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File dataFile) {
						boolean result = false;
						try {
							BasicFileAttributes attr = Files.readAttributes(dataFile.toPath(), BasicFileAttributes.class);
							result = attr.lastModifiedTime().to(TimeUnit.MILLISECONDS) < (System.currentTimeMillis() - (5 * 60 * 1000));
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
						return result;
					}
				})) {
					file.delete();
				}
			}
		}
	}
}
