package ihm;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.apple.mrj.MRJAboutHandler;
import com.apple.mrj.MRJApplicationUtils;
import com.apple.mrj.MRJQuitHandler;

@SuppressWarnings("deprecation")
/**
 * Classe appelée lorsque le programme est execute sous mac
 * Cette classe permet à l'initialisation de regler les fonctions propres à mac
 * du programme
 * @author Lucas VANRYB
 */
public class Mac {

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur appelant toutes les fonctions qui paramètre le programme pour
	 * macOS
	 */
	public Mac() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		MRJApplicationUtils.registerAboutHandler(new Handler());
		MRJApplicationUtils.registerQuitHandler(new Handler());
	}

	/**
	 * Classe permettant sous mac de prendre en charge la boite de dialogue
	 * à propos ainsi que le raccourci cmd+Q et le menu Quitter
	 * @author Lucas VANRYB
	 *
	 */
	class Handler implements MRJAboutHandler,MRJQuitHandler {
		/**
		 * Fonction quittant le programme
		 */
		public void handleQuit() {
			System.exit(0);
		}

		/**
		 * Fonction affichant la boite de dialogue à propos de ce programme
		 */
		public void handleAbout() {
			JOptionPane.showMessageDialog(null, "Cryptographie Version 1.0 \n Par Lucas VANRYB", "A propos de..", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/ihm/cia.png")));		
		}
	}
}
