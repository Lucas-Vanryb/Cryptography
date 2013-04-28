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
 * @see ihm.ISaisieCryptanalyseCle
 * @author Lucas VANRYB
 *
 */
public class SaisieCryptanalyseCle implements ISaisieCryptanalyseCle{

	/*
	 * ATTRIBUTS
	 */

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panClair = null;
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
	private JPanel panCentre = null;
	private JPanel panCrypte = null;
	private JPanel panCheminMessCrypte = null;
	private JLabel labCheminMessCrypte = null;
	private JTextField textCheminMessCrypte = null;
	private JButton butCheminMessCrypte = null;
	private JPanel panSaisieMessCrypte = null;
	private JLabel labSaisieMessCrypte = null;
	private JScrollPane scrollPanSaisieMessCrypte = null;
	private JTextArea textSaisieMessCrypte = null;
	private JLabel vide11 = null;
	private JLabel vide21 = null;
	private JLabel vide31 = null;
	private JLabel vide41 = null;


	/**
	 * CODES valant le code selectionne via les boutons radio
	 */
	private CODES selectionne;

	/**
	 * IMessage valant le message clair saisie par l'utilisateur
	 */
	private IMessage m;

	/**
	 * Vaut true tant que l'utilisateur n'as pas déclaré qu'il en avait terminé
	 */
	private boolean enCours;

	/**
	 * Booleen valant true si le message clair est saisie manuellement
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
	 * Le message crypte saisi par l'utilisateur
	 */
	private IMessage mCrypte;

	/**
	 * Booleen valant true si le message crypte est saisie manuellement
	 */
	private boolean saisieCrypte;

	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fentre
	 * de saisie
	 * @param menu la barre de menu
	 * @param ihm l'ihm afin que l'interface puisse commander le main thread
	 */
	public SaisieCryptanalyseCle(IHM ihm, JMenuBar menu) {
		this.selectionne=null;
		this.ihm=ihm;
		this.menu=menu;
		this.m=null;
		this.mCrypte=null;
		this.enCours=true;
		this.saisie=true;
		this.saisieCrypte=true;
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
			jFrame.setSize(new Dimension(850, 500));
			jFrame.setResizable(false);
			jFrame.setTitle("Cryptanalyse");
			jFrame.setLocationRelativeTo(getPanCrypte());
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
			jContentPane.add(getPanBas(), BorderLayout.SOUTH);
			jContentPane.add(getPanCode(), BorderLayout.EAST);
			jContentPane.add(getPanCentre(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * Cette méthode initialise panClair	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanClair() {
		if (panClair == null) {
			panClair = new JPanel();
			panClair.setLayout(new BoxLayout(getPanClair(), BoxLayout.Y_AXIS));
			panClair.setBorder(BorderFactory.createTitledBorder("Message clair:"));
			panClair.add(getPanCheminMess(), null);
			panClair.add(getPanSaisieMess(), null);
		}
		return panClair;
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
			labCheminMess.setText("Chemin vers un fichier txt contenant un message clair:");
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
			labSaisieMess.setText("Ou saisie manuelle du message clair:");
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
								JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message clair", "Message vide", JOptionPane.ERROR_MESSAGE);
								fail=true;
							}
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message clair", "Message vide", JOptionPane.ERROR_MESSAGE);
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

					if(saisieCrypte) {
						try {
							mCrypte=Fabrique.fabriquerMessage(getTextSaisieMessCrypte().getText());
							if(getTextSaisieMess().getText().equals("")) {
								JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message crypte", "Message vide", JOptionPane.ERROR_MESSAGE);
								fail=true;
							}
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(null, "Veuillez saisir un message ou un chemin vers un fichier texte contenant un message crypte", "Message vide", JOptionPane.ERROR_MESSAGE);
							fail=true;
						}
					}
					else {
						ILectureMessage lec=new LectureMessage(getTextCheminMessCrypte().getText());
						if(lec.probleme()) {
							fail=true;
						}
						else {
							mCrypte=lec.getMessage();
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

	/**
	 * Cette méthode initialise panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (panCentre == null) {
			panCentre = new JPanel();
			panCentre.setLayout(new BoxLayout(panCentre,BoxLayout.Y_AXIS));
			panCentre.add(getPanClair());
			panCentre.add(getPanCrypte(), null);
		}
		return panCentre;
	}

	/**
	 * Cette méthode initialise panCrypte	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCrypte() {
		if (panCrypte == null) {
			panCrypte = new JPanel();
			panCrypte.setLayout(new BoxLayout(getPanCrypte(), BoxLayout.Y_AXIS));
			panCrypte.setBorder(BorderFactory.createTitledBorder("Message crypté:"));
			panCrypte.add(getPanCheminMessCrypte(), null);
			panCrypte.add(getPanSaisieMessCrypte(), null);
		}
		return panCrypte;
	}

	/**
	 * Cette méthode initialise panCheminMessCrypte	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCheminMessCrypte() {
		if (panCheminMessCrypte == null) {
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.gridx = 4;
			gridBagConstraints111.gridy = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.gridwidth = 3;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridwidth = 5;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.gridx = 0;
			labCheminMessCrypte = new JLabel();
			labCheminMessCrypte.setText("Chemin vers un fichier txt contenant un message crypté (correspondant au message clair):");
			panCheminMessCrypte = new JPanel();
			panCheminMessCrypte.setLayout(new GridBagLayout());
			panCheminMessCrypte.add(labCheminMessCrypte, gridBagConstraints13);
			panCheminMessCrypte.add(getTextCheminMessCrypte(), gridBagConstraints7);
			panCheminMessCrypte.add(getButCheminMessCrypte(), gridBagConstraints111);
		}
		return panCheminMessCrypte;
	}

	/**
	 * Cette méthode initialise textCheminMessCrypte	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextCheminMessCrypte() {
		if (textCheminMessCrypte == null) {
			textCheminMessCrypte = new JTextField();
			textCheminMessCrypte.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					saisieCrypte=false;
					getTextCheminMessCrypte().setEnabled(true);
					getTextCheminMessCrypte().requestFocus();
					getTextSaisieMessCrypte().setEnabled(false);
				}
			});
		}
		return textCheminMessCrypte;
	}

	/**
	 * Cette méthode initialise butCheminMessCrypte	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButCheminMessCrypte() {
		if (butCheminMessCrypte == null) {
			butCheminMessCrypte = new JButton();
			butCheminMessCrypte.setText("Parcourir...");
			butCheminMessCrypte.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saisieCrypte=false;
					getTextCheminMessCrypte().setEnabled(true);
					getTextSaisieMessCrypte().setEnabled(false);
					JFileChooser load=new JFileChooser();
					final FileFilter filter=new FileNameExtensionFilter("Texte Uniquement","txt");
					load.setDialogType(JFileChooser.OPEN_DIALOG);
					load.setFileSelectionMode(JFileChooser.FILES_ONLY);
					load.setFileFilter(filter);
					load.setMultiSelectionEnabled(false);
					int r=load.showOpenDialog(null);
					if(r==JFileChooser.APPROVE_OPTION) {
						getTextCheminMessCrypte().setText(load.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		return butCheminMessCrypte;
	}

	/**
	 * Cette méthode initialise panSaisieMessCrypte	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanSaisieMessCrypte() {
		if (panSaisieMessCrypte == null) {
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 4;
			gridBagConstraints61.gridy = 5;
			vide41 = new JLabel();
			vide41.setText(" ");
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 4;
			gridBagConstraints51.gridy = 4;
			vide31 = new JLabel();
			vide31.setText(" ");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 4;
			gridBagConstraints41.gridy = 3;
			vide21 = new JLabel();
			vide21.setText(" ");
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 4;
			gridBagConstraints31.gridy = 2;
			vide11 = new JLabel();
			vide11.setText(" ");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridwidth = 3;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.gridheight = 5;
			GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
			gridBagConstraints121.gridwidth = 4;
			gridBagConstraints121.gridy = 0;
			gridBagConstraints121.gridx = 0;
			labSaisieMessCrypte = new JLabel();
			labSaisieMessCrypte.setText("Ou saisie manuelle du message crypté:");
			panSaisieMessCrypte = new JPanel();
			panSaisieMessCrypte.setLayout(new GridBagLayout());
			panSaisieMessCrypte.add(labSaisieMessCrypte, gridBagConstraints121);
			panSaisieMessCrypte.add(getScrollPanSaisieMessCrypte(), gridBagConstraints21);
			panSaisieMessCrypte.add(vide11, gridBagConstraints31);
			panSaisieMessCrypte.add(vide21, gridBagConstraints41);
			panSaisieMessCrypte.add(vide31, gridBagConstraints51);
			panSaisieMessCrypte.add(vide41, gridBagConstraints61);
		}
		return panSaisieMessCrypte;
	}

	/**
	 * Cette méthode initialise scrollPanSaisieMessCrypte	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPanSaisieMessCrypte() {
		if (scrollPanSaisieMessCrypte == null) {
			scrollPanSaisieMessCrypte = new JScrollPane();
			scrollPanSaisieMessCrypte.setLayout(new ScrollPaneLayout());
			scrollPanSaisieMessCrypte.setViewportView(getTextSaisieMessCrypte());
		}
		return scrollPanSaisieMessCrypte;
	}

	/**
	 * Cette méthode initialise textSaisieMessCrypte	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTextSaisieMessCrypte() {
		if (textSaisieMessCrypte == null) {
			textSaisieMessCrypte = new JTextArea();
			textSaisieMessCrypte.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					saisieCrypte=true;
					getTextSaisieMessCrypte().setEnabled(true);
					getTextSaisieMessCrypte().requestFocus();
					getTextCheminMessCrypte().setEnabled(false);
				}
			});
		}
		return textSaisieMessCrypte;
	}


	public boolean estEnCours() {
		return this.enCours;
	}

	public IMessage getMessageClair() {
		return this.m;
	}

	public IMessage getMessageCrypte() {
		return this.mCrypte;
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
