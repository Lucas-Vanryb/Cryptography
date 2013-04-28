package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

/**
 * Classe permettant à l'utilisateur de s'identifier via une interface graphique
 * @author Lucas VANRYB
 *
 */
public class SelectionUtilisateur implements ISelectionUtilisateur {

	/*
	 * ATTRIBUTS
	 */

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panBasDroite = null;
	private JButton butAnnuler = null;
	private JButton butOK = null;
	private JPanel panBas = null;
	private JPanel panCentre = null;
	private JPanel panCentre2 = null;
	private JLabel text = null;
	private JPanel jPanel1 = null;
	private JPanel panListe = null;
	private JPanel panVide1 = null;
	private JComboBox liste = null;
	private JPanel panVide2 = null;
	private JPanel panTexte = null;

	/**
	 * NOMS valant l'utilisateur selectionne 
	 */
	NOMS user;

	/**
	 * Vaut true tant que l'utilisateur n'as pas déclaré qu'il en avait terminé
	 */
	boolean enCours;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fenetre
	 */
	public SelectionUtilisateur() {
		this.enCours=true;
		this.user=null;
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
			jFrame.setLocationRelativeTo(null);
			jFrame.setResizable(false);
			jFrame.setSize(new Dimension(380, 140));
			jFrame.setTitle("Cryptographie");
			jFrame.setLocationRelativeTo(null);
			jFrame.setContentPane(getJContentPane());
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
			jContentPane.add(getPanBas(), BorderLayout.SOUTH);
			jContentPane.add(getPanCentre(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			panBasDroite.add(getButAnnuler(), null);
			panBasDroite.add(getButOK(), null);
		}
		return panBasDroite;
	}

	/**
	 * Cette méthode initialise butAnnuler	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButAnnuler() {
		if (butAnnuler == null) {
			butAnnuler = new JButton();
			butAnnuler.setText("Annuler");
			butAnnuler.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return butAnnuler;
	}

	/**
	 * Cette méthode initialise butOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButOK() {
		if (butOK == null) {
			butOK = new JButton();
			butOK.setText("OK");
			butOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					user=(NOMS)getListe().getSelectedItem();
					enCours=false;
				}
			});
		}
		return butOK;
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
			panBas.setPreferredSize(new Dimension(170, 50));
			panBas.add(getPanBasDroite(), BorderLayout.EAST);
		}
		return panBas;
	}

	/**
	 * Cette méthode initialise panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (panCentre == null) {
			panCentre = new JPanel();
			panCentre.setLayout(new BorderLayout());
			panCentre.add(getPanCentre2(), BorderLayout.CENTER);
		}
		return panCentre;
	}

	/**
	 * Cette méthode initialise panCentre2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre2() {
		if (panCentre2 == null) {
			text = new JLabel();
			text.setText("Veuillez sélectionner votre nom d'utilisateur :");
			panCentre2 = new JPanel();
			panCentre2.setLayout(new BorderLayout());
			panCentre2.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return panCentre2;
	}

	/**
	 * Cette méthode initialise jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setColumns(1);
			jPanel1 = new JPanel();
			jPanel1.setLayout(gridLayout);
			jPanel1.add(getPanTexte(), null);
			jPanel1.add(getPanListe(), null);
		}
		return jPanel1;
	}

	/**
	 * Cette méthode initialise panListe	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanListe() {
		if (panListe == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			gridLayout1.setColumns(3);
			panListe = new JPanel();
			panListe.setLayout(gridLayout1);
			panListe.add(getPanVide1(), null);
			panListe.add(getListe(), null);
			panListe.add(getPanVide2(), null);
		}
		return panListe;
	}

	/**
	 * Cette méthode initialise panVide1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanVide1() {
		if (panVide1 == null) {
			panVide1 = new JPanel();
			panVide1.setLayout(new GridBagLayout());
		}
		return panVide1;
	}

	/**
	 * Cette méthode initialise liste	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getListe() {
		if (liste == null) {
			liste = new JComboBox(NOMS.values());
		}
		return liste;
	}

	/**
	 * Cette méthode initialise panVide2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanVide2() {
		if (panVide2 == null) {
			panVide2 = new JPanel();
			panVide2.setLayout(new GridBagLayout());
		}
		return panVide2;
	}

	/**
	 * Cette méthode initialise panTexte	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanTexte() {
		if (panTexte == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			panTexte = new JPanel();
			panTexte.setLayout(new GridBagLayout());
			panTexte.add(text, gridBagConstraints);
		}
		return panTexte;
	}

	public ISelectionUtilisateur.NOMS getUser() {
		return this.user;
	}

	public boolean estEnCours() {
		return this.enCours;
	}

	public void masquer() {
		this.getJFrame().setVisible(false);
	}

	public void liberer() {
		this.getJFrame().dispose();
	}

}
