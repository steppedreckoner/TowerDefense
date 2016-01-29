package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	private static JFileChooser Chooser;
	private static FileFilter TypeFilter;
	private static Path CurrentRelativePath;
	private static Path WorkingDirPath;
	private static boolean IsSetup = false;
	
	public static final String MAP_FILE = "maps", PLAYER_FILE = "profiles", NEW = "new";
	
	public static void Setup(String type){
		if (type.startsWith(NEW)){
			type = type.substring(3);
		} else {
			CurrentRelativePath = CurrentRelativePath.getParent();
		}
		CurrentRelativePath = Paths.get(type);
		Chooser = new JFileChooser();
		TypeFilter = new FileNameExtensionFilter("Text File", "txt");
		WorkingDirPath = CurrentRelativePath.toAbsolutePath();
		IsSetup = true;
	}
	
	public static File ChooseFile(String type){
		if (!IsSetup){
			Setup(NEW + type);
		}
		
		File returnFile = null;
		Chooser.setCurrentDirectory(WorkingDirPath.toFile());
		Chooser.setFileFilter(TypeFilter);
		int chooserStatus = Chooser.showOpenDialog(Chooser);
		returnFile = Chooser.getSelectedFile();
		boolean accepted = TypeFilter.accept(returnFile);
		if (chooserStatus == JFileChooser.APPROVE_OPTION && accepted){
			return returnFile;
		}
		return null;
	}
	
	public static Path SaveFile(String data, String type){
		if (!IsSetup){
			Setup(NEW + type);
		}
		Chooser.setCurrentDirectory(WorkingDirPath.toFile());
		Chooser.setFileFilter(TypeFilter);
		int chooserStatus = Chooser.showSaveDialog(Chooser);
		File saveFile = Chooser.getSelectedFile();
		boolean accepted = TypeFilter.accept(saveFile);
		if (chooserStatus == JFileChooser.APPROVE_OPTION && accepted){
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(data);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return saveFile.toPath();
	}

}
