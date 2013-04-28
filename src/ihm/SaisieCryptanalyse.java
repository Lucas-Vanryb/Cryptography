package ihm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileFilter;

import lecture.ILectureMessage;
import lecture.LectureMessage;
import message.IMessage;

import codage.Interface;
import codage.Interface.CODES;
import fabrique.Fabrique;

/**
 * Classe permetant à l'utilisateur de saisir un message
 * ainsi qu'un type de code via saisie manuelle ou lecture de fichier
 * @author Lucas VANRYB
 * @see ihm.ISaisieCryptanalyse
 */
public class SaisieCryptanalyse implements ISaisieCryptanalyse {

	/*
	 * ATTRIBUTS
	 */
	
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panGauche = null;
	private JPanel panCheminMess = null;
	private JLabel labCheminMess = null;
	private JTextField textCheminMess = null;
	private JButton butCheminMess = null;
	private JPanel panSaisieMess = null;
	private JLabel labSaisieMess = null;
	private JScrollPane scrollPanSaisieMess = null;
	private JTextArea textSaisieMess = null;
	private JLabel vide1 = null;
	private JLabel vide2 = null;
	private JLabel vide3 = null;
	private JLabel vide4 = null;
	private JPanel panBas = null;
	private JPanel panBasDroite = null;
	private JButton butRetour = null;
	private JButton butOK = null;
	private JPanel panCode = null;
	
	/**
	 * CODES valant le code selectionne via les boutons radio
	 */
	private CODES selectionne;
	
	/**
	 * IMessage valant le message saisie par l'utilisateur
	 */
	private IMessage m;
	
	/**
	 * Vaut true tant que l'utilisateur n'as pas déclaré qu'il en avait terminé
	 */
	private boolean enCours;
	
	/**
	 * Booleen valant true si le message est saisie manuellement
	 */
	private boolean saisie;
	
	/**
	 * Lien permettant au contenu dynamique de commander IHM
	 */
	private IHM ihm;
	
	/**
	 * Barre de menu
	 */
	private JMenuBar menu;
	
	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fentre
	 * de saisie
	 * @param menu la barre de menu
	 * @param ihm l'ihm afin que l'interface puisse commander le main thread
	 */
	public SaisieCryptanalyse(IHM ihm, JMenuBar menu) {
		this.selectionne=null;
		this.ihm=ihm;
		this.menu=menu;
		this.m=null;
		this.enCours=true;
		this.saisie=true;
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
			jFrame.setSize(new Dimension(550, 220));
			jFrame.setResizable(false);
			jFrame.setTitle("Cryptanalyse");
			jFrame.setLocationRelativeTo(null);
			jFrame.setContentPane(getJContentPane());
			jFrame.getRootPane().setDefaultButton(getButOK());
			jFrame.setJMenuBar(this.menu);
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
			jContentPane.add(getPanGauche(), BorderLayout.CENTER);
			jContentPane.add(getPanBas(), BorderLayout.SOUTH);
			jContentPane.add(getPanCode(), BorderLayout.EAST);
		}
		return jContentPane;
	}

	/**
	 * Cette méthode initialise panGauche	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanGauche() {
		if (panGauche == null) {
			panGauche = new JPanel();
			panGauche.setLayout(new BoxLayout(getPanGauche(), BoxLayout.Y_AXIS));
			panGauche.setBorder(BorderFactory.createTitledBorder("Message :"));
			panGauche.add(getPanCheminMess(), null);
			panGauche.add(getPanSaisieMess(), null);
		}
		return panGauche;
	}

	/**
	 * Cette méthode initialise panCheminMess	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCheminMess() {
		if (panCheminMess == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 4;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridwidth = 5;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridx = 0;
			labCheminMess = new JLabel();
			labCheminMess.setText("Chemin vers un fichier txt contenant un message à cryptanalyser:");
			panCheminMess = new JPanel();
			panCheminMess.setLayout(new GridBagLayout());
			panCheminMess.add(labCheminMess, gridBagConstraints1);
			panCheminMess.add(getTextCheminMess(), gridBagConstraints);
			panCheminMess.add(getButCheminMess(), gridBagConstraints11);
		}
		return panCheminMess;
	}

	/**
	 * Cette méthode initialise textCheminMess	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextCheminMess() {
		if (textCheminMess == null) {
			textCheminMess = new JTextField();
			textCheminMess.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					saisie=false;
					getTextCheminMess().setEnabled(true);
					getTextCheminMess().requestFocus();
					getTextSaisieMess().setEnabled(false);
				}
			});
		}
		return textCheminMess;
	}

	/**
	 * Cette méthode initialise butCheminMess	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButCheminMess() {
		if (butCheminMess == null) {
			butCheminMess = new JButton();
			butCheminMess.setText("Parcourir...");
			butCheminMess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saisie=false;
					getTextCheminMess().setEnabled(true);
					getTextSaisieMess().setEnabled(false);
					JFileChooser load=new JFileChooser();
					final FileFilter filter=new FileNameExtensionFilter("Texte Uniquement","txt");
					load.setDialogType(JFileChooser.OPEN_DIALOG);
					load.setFileSelectionMode(JFileChooser.FILES_ONLY);
					load.setFileFilter(filter);
					load.setMultiSelectionEnabled(false);
					int r=load.showOpenDialog(null);
					if(r==JFileChooser.APPROVE_OPTION) {
						getTextCheminMess().setText(load.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		return butCheminMess;
	}

	/**
	 * Cette méthode initialise panSaisieMess	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanSaisieMess() {
		if (panSaisieMess == null) {
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
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridheight = 5;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridwidth = 4;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.gridx = 0;
			labSaisieMess = new JLabel();
			labSaisieMess.setText("Ou saisie manuelle du message :");
			panSaisieMess = new JPanel();
			panSaisieMess.setLayout(new GridBagLayout());
			panSaisieMess.add(labSaisieMess, gridBagConstraints12);
			panSaisieMess.add(getScrollPanSaisieMess(), gridBagConstraints2);
			panSaisieMess.add(vide1, gridBagConstraints3);
			panSaisieMess.add(vide2, gridBagConstraints4);
			panSaisieMess.add(vide3, gridBagConstraints5);
			panSaisieMess.add(vide4, gridBagConstraints6);
		}
		return panSaisieMess;
	}

	/**
	 * Cette méthode initialise scrollPanSaisieMess	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPanSaisieMess() {
		if (scrollPanSaisieMess == null) {
			scrollPanSaisieMess = new JScrollPane();
			scrollPanSaisieMess.setLayout(new ScrollPaneLayout());
			scrollPanSaisieMess.setViewportView(getTextSaisieMess());
		}
		return scrollPanSaisieMess;
	}

	/**
	 * Cette méthode initialise textSaisieMess	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextSaisieMess() {
		if (textSaisieMess == null) {
			textSaisieMess = new JTextArea();
			textSaisieMess.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					saisie=true;
					getTextSaisieMess().setEnabled(true);
					getTextSaisieMess().requestFocus();
					getTextCheminMess().setEnabled(false);
				}
			});
		}
		return textSaisieMess;
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
			butRetour.setText("Retour");
			butRetour.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ihm.stop();
					ihm.setAction(null);
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
			butOK.setText("Cryptanalyser");
			butOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean fail=false;
					if(saisie) {
						try {
							m=Fabrique.fabriquerMessage(getTextSaisieMess().getText());
							if(getTextSaisieMess().getText().equals("")) {
								JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message", "Message vide", JOptionPane.ERROR_MESSAGE);
								fail=true;
							}
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message", "Message vide", JOptionPane.ERROR_MESSAGE);
							fail=true;
						}
					}
					else {
						ILectureMessage lec=new LectureMessage(getTextCheminMess().getText());
						if(lec.probleme()) {
							fail=true;
						}
						else {
							m=lec.getMessage();
						}
					}
					if(!fail) {
						enCours=false;
					}

				}
			});
		}
		return butOK;
	}

	/**
	 * Cette méthode initialise panCode	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCode() {
		if (panCode == null) {
			panCode = new JPanel();
			panCode.setBorder(BorderFactory.createTitledBorder("Code :"));
			panCode.setLayout(new BoxLayout(this.panCode,BoxLayout.Y_AXIS));
			ButtonGroup group=new ButtonGroup();
			for(int i=0;i<=Interface.CODES.values().length;i++) {
				final int l=i;
				if(i!=Interface.CODES.values().length) {
					if(Interface.CODES.values()[i].estCryptanalysable()) {
						JRadioButton temp=new JRadioButton(Interface.CODES.values()[i].getNom());
						temp.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								selectionne=Interface.CODES.values()[l];	
							}			
						});
						group.add(temp);
						panCode.add(temp);
					}
				}
				else {
					JRadioButton temp=new JRadioButton("Tous");
					temp.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							selectionne=null;
						}
					});
					group.add(temp);
					panCode.add(temp);
				}
			}
		}
		return panCode;
	}
	
	public boolean estEnCours() {
		return this.enCours;
	}
	
	public IMessage getMessage() {
		return this.m;
	}
	
	public Interface.CODES getType() {
		return this.selectionne;
	}
	
	public void masquer() {
		this.getJFrame().setVisible(false);
	}
	
	public void liberer() {
		this.getJFrame().dispose();
	}
}
