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
	
	public static void Setup(){
		Chooser = new JFileChooser();
		TypeFilter = new FileNameExtensionFilter("Text File", "txt");
		CurrentRelativePath = Paths.get("");
		WorkingDirPath = CurrentRelativePath.toAbsolutePath();
		IsSetup = true;
	}
	
	public static File ChooseFile(){
		if (!IsSetup){
			Setup();
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
	
	public static Path SaveFile(){
		return SaveFile(Leveler.GetMapString());
	}
	
	public static Path SaveFile(String mapData){
		if (!IsSetup){
			Setup();
		}
		Chooser.setCurrentDirectory(WorkingDirPath.toFile());
		Chooser.setFileFilter(TypeFilter);
		int chooserStatus = Chooser.showSaveDialog(Chooser);
		File saveFile = Chooser.getSelectedFile();
		boolean accepted = TypeFilter.accept(saveFile);
		if (chooserStatus == JFileChooser.APPROVE_OPTION && accepted){
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(mapData);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return saveFile.toPath();
	}

}
