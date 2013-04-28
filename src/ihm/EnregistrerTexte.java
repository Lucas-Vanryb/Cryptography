package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.filechooser.*;

import codage.ICodeClePublique;
import codage.IMessageCode;
import codage.Interface;
import ecriture.Ecrire;

import message.IMessage;
import javax.swing.JMenuBar;

/**
 * Classe permettant d'afficher un message qui vient d'etre crypt� ou d�crypt�
 * ainsi que la clef et le type de code utilis�s et de donner
 * le possibilite � l'utilisateur de l'enregistrer
 * @author Lucas VANRYB
 *
 */
public class EnregistrerTexte implements IGUI {

	/*
	 * ATTRIBUTS
	 */

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panBas = null;
	private JPanel panBasDroite = null;
	private JButton butRetour = null;
	private JButton butOK = null;
	private JPanel panCentre = null;
	private JPanel panCle = null;
	private JTextField textCle = null;
	private JButton butCle = null;
	private JPanel panMess = null;
	private JScrollPane scrollPanMess = null;
	private JTextArea textMess = null;
	private JLabel vide1 = null;
	private JLabel vide2 = null;
	private JLabel vide3 = null;
	private JLabel vide4 = null;
	private JButton butEnregText = null;
	private JLabel labTemps = null;
	private JPanel panCles = null;
	private JPanel panClePublique = null;
	private JTextField textClePublique = null;
	private JButton butClePublique = null;

	/**
	 * String valant "crypt�" ou "d�crypt�" en fonction de l'action qui vient 
	 * d'�tre �ffectu�e
	 */
	private String st;
	/**
	 * IMessage crypt�/d�crypt�
	 */
	private IMessage m;
	/**
	 * IMessageCode contenant les informations sur le type de code et la cl�
	 */
	private IMessageCode k;
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
	 * Constructeur qui initialise les variables et qui affiche le jFrame
	 */
	public EnregistrerTexte(String s,IMessage mess,IMessageCode key, JMenuBar men, IHM ihm) {
		this.k=key;
		this.st=s;
		this.m=mess;
		this.enCours=true;
		this.menu=men;
		this.ihm=ihm;
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
			if(!(this.k.getCode() instanceof ICodeClePublique)) {
				jFrame.setSize(new Dimension(550, 200));
			}
			else {
				jFrame.setSize(new Dimension(550, 300));	
			}
			jFrame.setResizable(false);
			jFrame.setTitle("Enregistrer");
			jFrame.setJMenuBar(getMenu());
			jFrame.setContentPane(getJContentPane());
			jFrame.setLocationRelativeTo(vide1);
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
	 * Cette m�thode initialise panBas	
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
	 * Cette m�thode initialise panBasDroite	
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
	 * Cette m�thode initialise butRetour	
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
	 * Cette m�thode initialise butOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButOK() {
		if (butOK == null) {
			butOK = new JButton();
			butOK.setText("Effectuer une autre op�ration");
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
	 * Cette m�thode initialise panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (panCentre == null) {
			panCentre = new JPanel();
			panCentre.setLayout(new BoxLayout(getPanCentre(), BoxLayout.Y_AXIS));
			panCentre.add(getPanCles(), null);
			panCentre.add(getPanMess(), null);
		}
		return panCentre;
	}

	/**
	 * Cette m�thode initialise panCle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCle() {
		if (panCle == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 4;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth = 3;
			panCle = new JPanel();
			panCle.setLayout(new GridBagLayout());
			panCle.add(getTextCle(), gridBagConstraints);
			panCle.add(getButCle(), gridBagConstraints11);
			if(this.k.getCode() instanceof IMessageCode && ((ICodeClePublique)(this.k.getCode())).getPrivateKey().equals("")) {
				panCle.setBorder(BorderFactory.createTitledBorder("Type de Code + Cl� publique"));
			}
			else {
				panCle.setBorder(BorderFactory.createTitledBorder("Type de Code + Cl� compl�te"));
			}
		}
		return panCle;
	}

	/**
	 * Cette m�thode initialise textCle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextCle() {
		if (textCle == null) {
			textCle = new JTextField();
			textCle.setEditable(false);
			String s=((Interface.identifierCode(this.k.getCode()).toString())+" "+this.k.getKey());
			textCle.setText(s);
		}
		return textCle;
	}

	/**
	 * Cette m�thode initialise butCle	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButCle() {
		if (butCle == null) {
			butCle = new JButton();
			butCle.setText("Enregistrer sous...");
			butCle.addActionListener(new java.awt.event.ActionListener() {
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
							Ecrire.ecrireCle(save.getSelectedFile().getAbsolutePath(), k);
						}
						else {
							Ecrire.ecrireCle(save.getSelectedFile().getAbsolutePath()+".txt", k);
						}
					}
				}
			});
		}
		return butCle;
	}

	/**
	 * Cette m�thode initialise panMess	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanMess() {
		if (panMess == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 5;
			gridBagConstraints1.gridy = 4;
			labTemps = new JLabel();
			labTemps.setText("Temps �coul� : " + this.k.getCode().tempsCrypto() + " ms");
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
			panMess.setBorder(BorderFactory.createTitledBorder("Message "+this.st+":"));
			panMess.add(labTemps, gridBagConstraints1);
		}
		return panMess;
	}

	/**
	 * Cette m�thode initialise scrollPanMess	
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
	 * Cette m�thode initialise textMess	
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
	 * Cette m�thode initialise butEnregText	
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
	 * Retourne true si l'utilisateur n'a pas fini, false sinon
	 * @return true si l'utilisateur n'a pas fini, false sinon
	 */
	public boolean estEnCours() {
		return this.enCours;
	}

	/**
	 * Cette m�thode initialise jJMenuBar	
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
	 * Libere la memoire occup�e par la fenetre
	 */
	public void liberer() {
		this.getJFrame().dispose();
	}

	/**
	 * Cette m�thode initialise panCles	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCles() {
		if (panCles == null) {
			panCles = new JPanel();
			panCles.setLayout(new BoxLayout(panCles,BoxLayout.Y_AXIS));
			panCles.add(getPanCle());
			if(this.k.getCode() instanceof ICodeClePublique && this.m.equals(this.k.getMessageDecode())) {
				panCles.add(getPanClePublique());
			}
		}
		return panCles;
	}

	/**
	 * Cette m�thode initialise panClePublique	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanClePublique() {
		if (panClePublique == null) {
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.gridx = 4;
			gridBagConstraints111.gridy = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.gridwidth = 3;
			panClePublique = new JPanel();
			panClePublique.setLayout(new GridBagLayout());
			panClePublique.setBorder(BorderFactory.createTitledBorder("Type de Code + Cl� publique"));
			panClePublique.add(getTextClePublique(), gridBagConstraints8);
			panClePublique.add(getButClePublique(), gridBagConstraints111);
		}
		return panClePublique;
	}

	/**
	 * Cette m�thode initialise textClePublique	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextClePublique() {
		if (textClePublique == null) {
			textClePublique = new JTextField();
			try {
				textClePublique.setText(((Interface.identifierCode(this.k.getCode()).toString()) + " " + ((ICodeClePublique)(this.k.getCode())).getPublicKey()));
			}
			catch(Exception e) {

			}
			textClePublique.setEditable(false);
		}
		return textClePublique;
	}

	/**
	 * Cette m�thode initialise butClePublique	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButClePublique() {
		if (butClePublique == null) {
			butClePublique = new JButton();
			butClePublique.setText("Enregistrer sous...");
			butClePublique.addActionListener(new java.awt.event.ActionListener() {
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
							Ecrire.ecrireClePublique(save.getSelectedFile().getAbsolutePath(), k);
						}
						else {
							Ecrire.ecrireClePublique(save.getSelectedFile().getAbsolutePath()+".txt", k);
						}
					}
				}
			});
		}
		return butClePublique;
	}
}
