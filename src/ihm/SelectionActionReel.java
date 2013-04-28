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
 * Classe permettant � l'utilisateur de selectionner une action via une interface graphique
 * @author Lucas VANRYB
 *
 */
public class SelectionActionReel implements ISelectionActionReel {

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
	private JComboBox liste = null;
	private JPanel panTexte = null;

	/**
	 * Vaut l'action selectionnee par l'utilisateur
	 */
	IHM.ACTIONS act;

	/**
	 * Vaut true tant que l'utilisateur n'as pas d�clar� qu'il en avait termin�
	 */
	boolean enCours;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fenetre
	 */
	public SelectionActionReel() {
		this.enCours=true;
		this.act=null;
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
			jFrame.setSize(new Dimension(380, 140));
			jFrame.setLocationRelativeTo(null);
			jFrame.setTitle("Cryptographie");
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
					act=(IHM.ACTIONS)getListe().getSelectedItem();
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
			panCentre.add(getPanCentre2(), BorderLayout.CENTER);
		}
		return panCentre;
	}

	/**
	 * Cette m�thode initialise panCentre2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre2() {
		if (panCentre2 == null) {
			text = new JLabel();
			text.setText("Veuillez s�lectionner l'action � r�aliser :");
			panCentre2 = new JPanel();
			panCentre2.setLayout(new BorderLayout());
			panCentre2.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return panCentre2;
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
			panListe = new JPanel();
			panListe.setLayout(new GridLayout());
			panListe.add(getListe(), null);
		}
		return panListe;
	}

	/**
	 * Cette m�thode initialise liste	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getListe() {
		if (liste == null) {
			liste = new JComboBox(IHM.ACTIONS.values());
		}
		return liste;
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
			panTexte.add(text, gridBagConstraints);
		}
		return panTexte;
	}

	public IHM.ACTIONS getAction() {
		return this.act;
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