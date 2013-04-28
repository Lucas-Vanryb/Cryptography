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
import javax.swing.JMenuBar;

import codage.Interface;

/**
 * Classe permettant � l'utilisateur de selectionner un type de code via une GUI
 * @see ihm.IGenerer
 * @author Lucas VANRYB
 *
 */
public class Generer implements IGenerer {

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
	private JLabel text = null;
	private JPanel jPanel1 = null;
	private JPanel panListe = null;
	private JPanel panVide1 = null;
	private JComboBox liste = null;
	private JPanel panVide2 = null;
	private JPanel panTexte = null;

	/**
	 * Le type de code selectionn� par l'utilisateur
	 */
	private Interface.CODES typ;
	/**
	 * Vaut true tant que l'utilisateur n'as pas d�clar� qu'il en avait termin�
	 */
	private boolean enCours;
	/**
	 * Barre de menu
	 */
	private JMenuBar menu;
	/**
	 * Lien permettant au contenu dynamique de commander IHM
	 */
	private IHM ihm;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fentre
	 * de saisie
	 * @param menu la barre de menu
	 * @param ihm l'ihm afin que l'interface puisse commander le main thread
	 */
	public Generer(IHM ihm, JMenuBar menu) {
		this.ihm=ihm;
		this.enCours=true;
		this.typ=null;
		this.menu=menu;
		this.getJFrame().setVisible(true);
	}		

	/*
	 * ACCESSEURS
	 */

	/**
	 * Cette m�thode initialise jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setResizable(false);
			jFrame.setSize(new Dimension(550, 140));
			jFrame.setTitle("G�n�rer une cl�");
			jFrame.setLocationRelativeTo(this.getListe());
			jFrame.setJMenuBar(this.menu);
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
	 * Cette m�thode initialise jContentPane	
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
	 * Cette m�thode initialise panBasDroite	
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
	 * Cette m�thode initialise butAnnuler	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButAnnuler() {
		if (butAnnuler == null) {
			butAnnuler = new JButton();
			butAnnuler.setText("Retour");
			butAnnuler.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ihm.setAction(null);
					ihm.stop();
				}
			});
		}
		return butAnnuler;
	}

	/**
	 * Cette m�thode initialise butOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButOK() {
		if (butOK == null) {
			butOK = new JButton();
			butOK.setText("OK");
			butOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					typ=(Interface.CODES)getListe().getSelectedItem();
					enCours=false;
				}
			});
		}
		return butOK;
	}

	/**
	 * Cette m�thode initialise panBas	
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
	 * Cette m�thode initialise panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (panCentre == null) {
			panCentre = new JPanel();
			panCentre.setLayout(new BorderLayout());
			panCentre.add(getJPanel1(), BorderLayout.CENTER);
		}
		return panCentre;
	}

	/**
	 * Cette m�thode initialise jPanel1	
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
	 * Cette m�thode initialise panListe	
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
	 * Cette m�thode initialise panVide1	
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
	 * Cette m�thode initialise liste	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getListe() {
		if (liste == null) {
			liste = new JComboBox(Interface.CODES.values());
		}
		return liste;
	}

	/**
	 * Cette m�thode initialise panVide2	
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
	 * Cette m�thode initialise panTexte	
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
			text = new JLabel();
			text.setText("Veuillez s�lectionner le type de code pour lequel vous souhaitez g�n�rer une cl� :");
			panTexte.add(text, gridBagConstraints);
		}
		return panTexte;
	}

	public Interface.CODES getTypCode() {
		return this.typ;
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
