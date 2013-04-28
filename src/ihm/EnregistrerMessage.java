package ihm;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.filechooser.FileFilter;

import codage.IMessageCode;

import message.IMessage;

import ecriture.Ecrire;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Classe permettant d'afficher un message qui vient d'etre cryptanalyse et de donner
 * le possibilite à l'utilisateur de l'enregistrer
 * @author Lucas VANRYB
 *
 */
public class EnregistrerMessage  implements IGUI {

	/*
	 * ATTRIBUTS
	 */
	
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panMess = null;
	private JScrollPane scrollPanMess = null;
	private JTextArea textMess = null;
	private JLabel vide1 = null;
	private JLabel vide2 = null;
	private JLabel vide3 = null;
	private JLabel vide4 = null;
	private JButton butEnregText = null;
	private JPanel panBas = null;
	private JPanel panBasDroite = null;
	private JButton butRetour = null;
	private JButton butOK = null;
	private JLabel labTemps = null;
	
	/**
	 * Vaut true tant que l'utilisateur n'as pas déclaré qu'il en avait terminé
	 */
	private boolean enCours;
	/**
	 * Lien permettant au contenu dynamique de commander IHM
	 */
	private IHM ihm;
	/**
	 * Message à afficher
	 */
	private IMessage m;
	/**
	 * Variable contenant le temps qu'à pris la dernière action
	 */
	private long temps;
	/**
	 * Barre de menu
	 */
	private JMenuBar menu;
	
	/*
	 * CONSTRUCTEUR
	 */
	
	/**
	 * Constructeur qui initialise les variables et qui affiche le jFrame
	 */
	public EnregistrerMessage(IHM ihm,IMessageCode m,JMenuBar menu) {
		this.enCours=true;
		this.ihm=ihm;
		this.m=m.getMessageDecode();
		this.menu=menu;
		this.temps=m.getCode().tempsCrypto();
		this.getJFrame().setVisible(true);
	}
	
	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Cette méthode initialise jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(450, 200));
			jFrame.setContentPane(getJContentPane());
			jFrame.setResizable(false);
			jFrame.setTitle("Enregistrer");
			jFrame.setJMenuBar(getMenu());
			jFrame.setContentPane(getJContentPane());
			jFrame.setLocationRelativeTo(this.getTextMess());
			jFrame.getRootPane().setDefaultButton(getButOK());
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});
		}
		return jFrame;
	}

	/**
	 * Cette méthode initialise jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(this.getPanMess(),BorderLayout.CENTER);
			jContentPane.add(this.getPanBas(),BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	
	private JPanel getPanMess() {
		if (panMess == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 5;
			gridBagConstraints.gridy = 3;
			labTemps = new JLabel();
			labTemps.setText("Temps écoulé : "+this.temps+" ms");
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 5;
			gridBagConstraints7.gridy = 2;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 4;
			gridBagConstraints6.gridy = 5;
			vide4 = new JLabel();
			vide4.setText(" ");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 4;
			gridBagConstraints5.gridy = 4;
			vide3 = new JLabel();
			vide3.setText(" ");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 4;
			gridBagConstraints4.gridy = 3;
			vide2 = new JLabel();
			vide2.setText(" ");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 4;
			gridBagConstraints3.gridy = 2;
			vide1 = new JLabel();
			vide1.setText(" ");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints2.insets = new Insets(2, 0, 0, 0);
			gridBagConstraints2.gridheight = 5;
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			panMess = new JPanel();
			panMess.setLayout(new GridBagLayout());
			panMess.add(getScrollPanMess(), gridBagConstraints2);
			panMess.add(vide1, gridBagConstraints3);
			panMess.add(vide2, gridBagConstraints4);
			panMess.add(vide3, gridBagConstraints5);
			panMess.add(vide4, gridBagConstraints6);
			panMess.add(getButEnregText(), gridBagConstraints7);
			panMess.setBorder(BorderFactory.createTitledBorder("Message cryptanalysé:"));
			panMess.add(labTemps, gridBagConstraints);
		}
		return panMess;
	}

	/**
	 * Cette méthode initialise scrollPanMess	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPanMess() {
		if (scrollPanMess == null) {
			scrollPanMess = new JScrollPane();
			scrollPanMess.setLayout(new ScrollPaneLayout());
			scrollPanMess.setViewportView(getTextMess());
		}
		return scrollPanMess;
	}

	/**
	 * Cette méthode initialise textMess	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextMess() {
		if (textMess == null) {
			textMess = new JTextArea();
			textMess.setEditable(false);
			textMess.setText(this.m.toString());
		}
		return textMess;
	}

	/**
	 * Cette méthode initialise butEnregText	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButEnregText() {
		if (butEnregText == null) {
			butEnregText = new JButton();
			butEnregText.setText("Enregistrer sous...");
			butEnregText.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser save=new JFileChooser();
					final FileFilter filter=new FileNameExtensionFilter("Texte Uniquement","txt");
					save.setDialogType(JFileChooser.SAVE_DIALOG);
					save.setFileSelectionMode(JFileChooser.FILES_ONLY);
					save.setFileFilter(filter);
					save.setMultiSelectionEnabled(false);
					int r=save.showSaveDialog(null);
					if(r==JFileChooser.APPROVE_OPTION) {
						if(save.getSelectedFile().getAbsolutePath().endsWith("txt")) {
							Ecrire.ecrireMessage(save.getSelectedFile().getAbsolutePath(), m);
						}
						else {
							Ecrire.ecrireMessage(save.getSelectedFile().getAbsolutePath()+".txt", m);
						}
					}
				}
			});
		}
		return butEnregText;
	}
	
	/**
	 * Cette méthode initialise panBas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanBas() {
		if (panBas == null) {
			panBas = new JPanel();
			panBas.setLayout(new BorderLayout());
			panBas.add(getPanBasDroite(), java.awt.BorderLayout.EAST);
		}
		return panBas;
	}

	/**
	 * Cette méthode initialise panBasDroite	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanBasDroite() {
		if (panBasDroite == null) {
			panBasDroite = new JPanel();
			panBasDroite.setLayout(new BoxLayout(getPanBasDroite(), BoxLayout.X_AXIS));
			panBasDroite.add(getButRetour(), null);
			panBasDroite.add(getButOK(), null);
		}
		return panBasDroite;
	}

	/**
	 * Cette méthode initialise butRetour	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButRetour() {
		if (butRetour == null) {
			butRetour = new JButton();
			butRetour.setText("Quitter");
			butRetour.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return butRetour;
	}

	/**
	 * Cette méthode initialise butOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButOK() {
		if (butOK == null) {
			butOK = new JButton();
			butOK.setText("Effectuer une autre opération");
			butOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					enCours=false;
					ihm.setAction(null);
				}
			});
		}
		return butOK;
	}

	/**
	 * Retourne true si l'utilisateur n'a pas fini, false sinon
	 * @return true si l'utilisateur n'a pas fini, false sinon
	 */
	public boolean estEnCours() {
		return this.enCours;
	}

	/**
	 * Cette méthode initialise jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenu() {
		return this.menu;
	}

	/**
	 * Masque la fenetre
	 */
	public void masquer() {
		this.getJFrame().setVisible(false);
	}
	
	/**
	 * Libere la memoire occupée par la fenetre
	 */
	public void liberer() {
		this.getJFrame().dispose();
	}

}
