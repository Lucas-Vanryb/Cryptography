package ihm;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;

import codage.ICodeClePublique;
import codage.Interface;
import codage.Rot13;
import codage.Rot47;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import fabrique.Fabrique;

import lecture.ILectureClef;
import lecture.ILectureMessage;
import lecture.LectureClef;
import lecture.LectureMessage;
import message.IMessage;

/**
 * Classe permetant à l'utilisateur de saisir un message, une clé
 * ainsi qu'un type de code via saisie manuelle ou lecture de fichier
 * @author Lucas VANRYB
 *
 */
public class Saisie implements ISaisie {

	/*
	 * ATTRIBUTS
	 */

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel panCodes = null;
	private JPanel panCentre = null;
	private JPanel panGauche = null;
	private JPanel panBas = null;
	private JPanel panBasDroite = null;
	private JButton butRetour = null;
	private JButton butOK = null;
	private JRadioButton[] tabRad = null;
	private JPanel panCheminMess = null;
	private JLabel labCheminMess = null;
	private JTextField textCheminMess = null;
	private JButton butCheminMess = null;
	private JPanel panSaisieMess = null;
	private JLabel labSaisieMess = null;
	private JLabel vide1 = null;
	private JLabel vide2 = null;
	private JLabel vide3 = null;
	private JLabel vide4 = null;
	private JPanel panDroite = null;
	private JPanel panCheminCle = null;
	private JLabel labCheminCle = null;
	private JTextField textCheminCle = null;
	private JButton butCheminCle = null;
	private JPanel panSaisieCle = null;
	private JLabel labSaisieCle = null;
	private JTextField textSaisieCle = null;
	private JLabel vide5 = null;
	private JLabel vide6 = null;
	private JLabel vide7 = null;
	private JLabel vide8 = null;
	private JButton butGenererCle = null;
	private JScrollPane scrollPanSaisieMess = null;
	private JTextArea textSaisieMess = null;

	/**
	 * Booleen valant true si la clé est saisie manuellement
	 */
	private boolean cleManu;

	/**
	 * Booleen valant true si le message est saisie manuellement
	 */
	private boolean messManu;

	/**
	 * Booleen valant true si le code selectionne ne necessite pas de clé
	 */
	private boolean bloque;

	/**
	 * CODES valant le code selectionne via les boutons radio
	 */
	private Interface.CODES selectionne;

	/**
	 * Barre de menu
	 */
	private JMenuBar menu;

	/**
	 * Vaut true tant que l'utilisateur n'as pas déclaré qu'il en avait terminé
	 */
	private boolean enCours;

	/**
	 * Lien permettant au contenu dynamique de commander IHM
	 */
	private IHM ihm;

	/**
	 * Booleen valant true si l'interface est en mode décryptage, 
	 * false si elle est en mode cryptage
	 */
	private boolean decrypte;

	/**
	 * IMessage valant le message saisie par l'utilisateur
	 */
	private IMessage m;

	/**
	 * CODES valant le type de code selectionné par l'utilisateur
	 */
	private Interface.CODES typ;

	/**
	 * Clef saisie par l'utilisateur
	 */
	private String k;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur qui initialise les variables d'instance et qui affiche la fentre
	 * de saisie
	 * @param menu la barre de menu
	 * @param decrypte un booleen qui vaut true si on lance l'interface en mode decryptage
	 * @param ihm l'ihm afin que l'interface puisse commander le main thread
	 */
	public Saisie(JMenuBar menu, boolean decrypte, IHM ihm) {
		this.cleManu=true;
		this.messManu=true;
		this.bloque=false;
		this.selectionne=Interface.CODES.values()[0];
		this.ihm=ihm;
		this.decrypte=decrypte;
		this.enCours=true;
		this.menu=menu;
		this.k="";
		this.typ=null;
		this.tabRad=new JRadioButton[Interface.CODES.values().length];
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
			if(ihm.macOS) {
				jFrame.setSize(new Dimension(900, 30*Interface.CODES.values().length));
			}
			else {
				jFrame.setSize(new Dimension(900, 37*Interface.CODES.values().length));
			}
			if(!this.decrypte) {
				jFrame.setTitle("Cryptage");
			}
			else {
				jFrame.setTitle("Décryptage");
			}
			jFrame.setResizable(false);
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
	 * Cette méthode initialise jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanCodes(), BorderLayout.EAST);
			jContentPane.add(getPanCentre(), BorderLayout.CENTER);
			jContentPane.add(getPanBas(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * Cette méthode initialise panCodes	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCodes() {
		if (panCodes == null) {
			panCodes = new JPanel();
			panCodes.setLayout(new BoxLayout(getPanCodes(), BoxLayout.Y_AXIS));
			ButtonGroup group=new ButtonGroup();
			for(int i=0;i<Interface.CODES.values().length;i++) {
				final int t=i;
				JRadioButton temp=new JRadioButton(Interface.CODES.values()[i].getNom());
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectionne=Interface.CODES.values()[t];
						if(selectionne.creerCode() instanceof ICodeClePublique) {
							if(decrypte) {
								labSaisieCle.setText("Ou saisie manuelle de la clé (privée ou complète):");
							}
							else {
								labSaisieCle.setText("Ou saisie manuelle de la clé (publique ou complète):");
							}
						}
						else {
							labSaisieCle.setText("Ou saisie manuelle de la clé:");
						}
						cleManu=true;
						getTextCheminCle().setEnabled(false);
						getTextSaisieCle().setEnabled(true);
						getTextSaisieCle().requestFocus();
						bloque=false;
						if(!decrypte) {
							getButGenererCle().setEnabled(true);
						}
						if(selectionne.creerCode() instanceof Rot13 || selectionne.creerCode() instanceof Rot47) {
							if(selectionne.creerCode() instanceof Rot13) {
								getTextSaisieCle().setText("13");
							}
							else {
								getTextSaisieCle().setText("47");
							}
							getTextSaisieCle().setEnabled(false);
							if(!decrypte) {
								getButGenererCle().setEnabled(false);
							}
							bloque=true;
						}
					}				
				});
				group.add(temp);
				panCodes.add(temp);
				tabRad[i]=temp;
			}
			panCodes.setBorder(BorderFactory.createTitledBorder("Code :"));
		}
		return panCodes;
	}

	/**
	 * Cette méthode initialise panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (panCentre == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx=1.0;
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.weightx=0.6;
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			panCentre = new JPanel();
			panCentre.setLayout(new GridBagLayout());
			panCentre.add(getPanGauche(), gridBagConstraints9);
			panCentre.add(getPanDroite(), gridBagConstraints10);
		}
		return panCentre;
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
			panGauche.add(getPanCheminMess(), null);
			panGauche.add(getPanSaisieMess(), null);
			panGauche.setBorder(BorderFactory.createTitledBorder("Message :"));
		}
		return panGauche;
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
			butOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean fail=false;
					boolean teste=false;
					if(messManu) {
						try {
							m=Fabrique.fabriquerMessage(getTextSaisieMess().getText());
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
					if(cleManu) {
						typ=selectionne;
						if(typ.creerCode() instanceof ICodeClePublique) {
							if(getTextSaisieCle().getText().split(" ").length==1) {
								teste=true;
								if(!decrypte){
									if(((ICodeClePublique)(typ.creerCode())).estClePubliqueValable(getTextSaisieCle().getText())) {
										k=getTextSaisieCle().getText()+" ";
									}
									else {
										fail=true;
									}
								}
								else {
									if(((ICodeClePublique)(typ.creerCode())).estClePriveValable(getTextSaisieCle().getText())) {
										k=" "+getTextSaisieCle().getText();
									}
									else {
										fail=true;
									}
								}
							}
							else {
								k=getTextSaisieCle().getText();
							}
						}
						else {
							k=getTextSaisieCle().getText();
						}
					}
					else {
						ILectureClef lec=new LectureClef(getTextCheminCle().getText());
						if(lec.probleme()) {
							fail=true;
						}
						else {
							k=lec.getClef();
							typ=lec.getTypeCode();
							if(typ.creerCode() instanceof ICodeClePublique) {
								teste=true;
								if(!decrypte){
									if(((ICodeClePublique)(typ.creerCode())).estClePubliqueValable(k.split(" ")[0])) {
										if(!k.contains(" ")) {
											k=k+" ";
										}
									}
									else {
										fail=true;
									}
								}
								else {
									if(((ICodeClePublique)(typ.creerCode())).estClePriveValable(k.split(" ")[1])) {
										if(!k.contains(" ")) {
											k=" "+k;
										}
									}
									else {
										fail=true;
									}
								}
							}
						}
					}
					if(!teste) {
						if(!typ.creerCode().estCleValable(k)) {
							fail=true;
						}
					}
					if(!fail) {
						enCours=false;
					}

				}
			});
			if(!decrypte) {
				butOK.setText("Crypter");
			}
			else {
				butOK.setText("Décrypter");	
			}
		}
		return butOK;
	}

	/**
	 * Cette méthode initialise panCheminMess	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCheminMess() {
		if (panCheminMess == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.weighty = 1.0;
			gridBagConstraints14.weightx = 1.0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 4;
			gridBagConstraints11.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.gridwidth=5;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridwidth=3;
			labCheminMess = new JLabel();
			labCheminMess.setText("Chemin vers un fichier txt contenant un message à crypter:");
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
					messManu=false;
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
					messManu=false;
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
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridy = 1;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.weighty = 1.0;
			gridBagConstraints15.gridx = 2;
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
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridwidth = 3;
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
	 * Cette méthode initialise panDroite	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanDroite() {
		if (panDroite == null) {
			panDroite = new JPanel();
			panDroite.setLayout(new BoxLayout(getPanDroite(), BoxLayout.Y_AXIS));
			panDroite.add(getPanCheminCle(), null);
			panDroite.add(getPanSaisieCle(), null);
			panDroite.setBorder(BorderFactory.createTitledBorder("Clé :"));
		}
		return panDroite;
	}

	/**
	 * Cette méthode initialise panCheminCle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCheminCle() {
		if (panCheminCle == null) {
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
			labCheminCle = new JLabel();
			labCheminCle.setText("Chemin vers un fichier clé :");
			panCheminCle = new JPanel();
			panCheminCle.setLayout(new GridBagLayout());
			panCheminCle.add(labCheminCle, gridBagConstraints13);
			panCheminCle.add(getTextCheminCle(), gridBagConstraints7);
			panCheminCle.add(getButCheminCle(), gridBagConstraints111);
		}
		return panCheminCle;
	}

	/**
	 * Cette méthode initialise textCheminCle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextCheminCle() {
		if (textCheminCle == null) {
			textCheminCle = new JTextField();
			textCheminCle.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cleManu=false;
					getTextCheminCle().setEnabled(true);
					getTextCheminCle().requestFocus();
					getTextSaisieCle().setEnabled(false);
				}
			});
		}
		return textCheminCle;
	}

	/**
	 * Cette méthode initialise butCheminCle	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButCheminCle() {
		if (butCheminCle == null) {
			butCheminCle = new JButton();
			butCheminCle.setText("Parcourir...");
			butCheminCle.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cleManu=false;
					textCheminCle.setEnabled(true);
					textSaisieCle.setEnabled(false);
					JFileChooser load=new JFileChooser();
					final FileFilter filter=new FileNameExtensionFilter("Texte Uniquement","txt");
					load.setDialogType(JFileChooser.OPEN_DIALOG);
					load.setFileSelectionMode(JFileChooser.FILES_ONLY);
					load.setFileFilter(filter);
					load.setMultiSelectionEnabled(false);
					int r=load.showOpenDialog(null);
					if(r==JFileChooser.APPROVE_OPTION) {
						getTextCheminCle().setText(load.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		return butCheminCle;
	}

	/**
	 * Cette méthode initialise panSaisieCle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanSaisieCle() {
		if (panSaisieCle == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 4;
			gridBagConstraints8.gridy = 6;
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 5;
			gridBagConstraints61.gridy = 7;
			vide8 = new JLabel();
			vide8.setText(" ");
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 5;
			gridBagConstraints51.gridy = 6;
			vide7 = new JLabel();
			vide7.setText(" ");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 5;
			gridBagConstraints41.gridy = 3;
			vide6 = new JLabel();
			vide6.setText(" ");
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 5;
			gridBagConstraints31.gridy = 2;
			vide5 = new JLabel();
			vide5.setText(" ");
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridwidth = 3;
			gridBagConstraints21.gridy = 5;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridheight = 1;
			GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
			gridBagConstraints121.gridwidth = 4;
			gridBagConstraints121.gridy = 4;
			gridBagConstraints121.gridx = 4;
			labSaisieCle = new JLabel();
			if(this.selectionne.creerCode() instanceof ICodeClePublique) {
				if(this.decrypte) {
					labSaisieCle.setText("Ou saisie manuelle de la clé (privée ou complète):");
				}
				else {
					labSaisieCle.setText("Ou saisie manuelle de la clé (publique ou complète):");
				}
			}
			else {
				labSaisieCle.setText("Ou saisie manuelle de la clé:");
			}
			panSaisieCle = new JPanel();
			panSaisieCle.setLayout(new GridBagLayout());
			panSaisieCle.add(labSaisieCle, gridBagConstraints121);
			panSaisieCle.add(getTextSaisieCle(), gridBagConstraints21);
			panSaisieCle.add(vide5, gridBagConstraints31);
			panSaisieCle.add(vide6, gridBagConstraints41);
			panSaisieCle.add(vide7, gridBagConstraints51);
			panSaisieCle.add(vide8, gridBagConstraints61);
			if(!decrypte) {
				panSaisieCle.add(getButGenererCle(), gridBagConstraints8);
			}
		}
		return panSaisieCle;
	}

	/**
	 * Cette méthode initialise textSaisieCle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTextSaisieCle() {
		if (textSaisieCle == null) {
			textSaisieCle = new JTextField();
			textSaisieCle.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(!bloque) {
						cleManu=true;
						getTextCheminCle().setEnabled(false);
						getTextSaisieCle().setEnabled(true);
						getTextSaisieCle().requestFocus();
					}
				}
			});
		}
		return textSaisieCle;
	}

	/**
	 * Cette méthode initialise butGenererCle	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButGenererCle() {
		if(!decrypte) {
			if (butGenererCle == null) {
				butGenererCle = new JButton();
				butGenererCle.setText("Générer une clé");
				butGenererCle.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						cleManu=true;
						getTextCheminCle().setEnabled(false);
						getTextSaisieCle().setEnabled(true);
						getTextSaisieCle().setText(selectionne.creerCode().genererCle());
					}
				});
			}
			return butGenererCle;
		}
		else {
			return null;
		}
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
					messManu=true;
					getTextSaisieMess().setEnabled(true);
					getTextSaisieMess().requestFocus();
					getTextCheminMess().setEnabled(false);
				}
			});
		}
		return textSaisieMess;
	}

	/**
	 * Cette méthode initialise menu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenu() {
		return menu;
	}

	public boolean estEnCours() {
		return this.enCours;
	}

	public String getKey() {
		return this.k;
	}

	public IMessage getMessage() {
		return this.m;
	}

	public Interface.CODES getTypeCode() {
		return this.typ;
	}

	public void masquer() {
		this.getJFrame().setVisible(false);
	}

	public void liberer() {
		this.getJFrame().dispose();
	}

}
