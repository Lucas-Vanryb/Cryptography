package ihm;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 *  Source trouvée sur Internet permettant de filtrer les fichiers par extension
 *  @author Pascal S. de Kloe
 *  @since 1.6
 */
final class FileNameExtensionFilter extends FileFilter {

	private final String DESCRIPTION;
	private final String SUFFIX;
	
	FileNameExtensionFilter(String description, String extension) {
		DESCRIPTION = description;
		SUFFIX = "." + extension.toLowerCase();
	}

	/**
	 * Méthode definissant comme fichier acceptable tout fichier terminant par
	 * l'extension SUFFIX
	 * @param f un fichier à tester
	 * @return true si le fichier est acceptable, false sinon
	 */
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		String name = f.getName().toLowerCase();
		return name.endsWith(SUFFIX) && !name.equals(SUFFIX);
	}

	/**
	 * Permet d'obtenir la description du filtre
	 * @return la description du filtre
	 */
	public String getDescription() {
		return DESCRIPTION;
	}         

}
