package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;

/**
 * Classe constituant la barre de menu de toutes les fenetres de l'IHM
 * @author Lucas VANRYB
 *
 */
public class MenuBar extends JMenuBar{

	private static final long serialVersionUID = -7203300744893850114L;
	
	/*
	 * ATTRIBUTS
	 */
	
	/**
	 * L'utilisateur connecte
	 */
	private SelectionUtilisateur.NOMS utilisateur;
	
	/**
	 * Le menu fichier
	 */
	private JMenu fichier;
	
	/**
	 * Le menu edition
	 */
	private JMenu edition;
	
	/**
	 * Vaut true ssi le programme tourne sous mac
	 */
	private boolean mac;
	
	/**
	 * l'ihm associée, afin que le menu puisse commander cette dernière
	 */
	private IHM ihm;

	/*
	 * CONSTRUCTEUR
	 */
	
	/**
	 * Crée et initialise la JMenuBar qui servira de barre de  manu dans toutes les fenetres en fonction
	 * des parametres d'entree
	 * @param u l'utilisateur connecté
	 * @param edition vaut true si edition doit etre active, false sinon
	 * @param mac vaut true si le programme tourne sous mac, false sinon
	 * @param ihm l'ihm associée, afin que le menu puisse commander cette dernière
	 */
	public MenuBar(SelectionUtilisateur.NOMS u,boolean edition, boolean mac, IHM ihm) {
		this.ihm=ihm;
		this.utilisateur=u;
		this.mac=mac;
		this.fichier=new JMenu("Fichier");
		this.edition=new JMenu("Edition");
		this.add(this.getFichier());
		if(edition) {
			this.add(this.getEdition());
		}
		if(!mac) {
			this.add(this.getAbout());
		}
	}

	/*
	 * Ajoute le menu about si l'ordinateur est un PC
	 */
	private JMenu getAbout() {
		JMenu about= new JMenu("?");
		JMenuItem itmAbout=new JMenuItem("A Propos de ..");
		itmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Cryptographie Version 1.0 \n Par Lucas VANRYB", "A propos de..", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Images/cia-emblem.gif"));
			}		
		});
		about.add(itmAbout);
		return about;
	}

	/*
	 * Ajoute les items d'edition au menu edition
	 */
	private JMenu getEdition() {
		JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
		cut.setText("Couper");
		JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
		copy.setText("Copier");
		JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
		paste.setText("Coller");

		this.edition.add(cut);
		this.edition.add(copy);
		this.edition.add(paste);

		return this.edition;
	}

	/*
	 * Ajoute les items correspondant à l'utilisateur (ou tous si mode reel)
	 * au menu fichier
	 */
	private JMenu getFichier() {
		if(this.utilisateur==ISelectionUtilisateur.NOMS.BOB) {
			this.getFichierBob();
		}
		if(this.utilisateur==ISelectionUtilisateur.NOMS.ALICE) {
			this.getFichierAlice();
		}
		if(this.utilisateur==ISelectionUtilisateur.NOMS.EVE) {
			this.getFichierEve();
		}
		if(IHM.reel) {
			this.getFichierReel();
		}
		if(!IHM.reel) {
			JMenuItem deco=new JMenuItem("Se deconnecter");
			deco.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ihm.deconnecter();
					ihm.setAction(null);
					ihm.stop();
				}		
			});
			this.fichier.add(deco);
		}
		if(!this.mac) {
			JMenuItem quitter =new JMenuItem("Quitter");
			quitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}		
			});
			this.fichier.add(quitter);
		}
		return this.fichier;
	}

	/*
	 * Ajoute les items correspondant à toutes les actions possibles dans fichier
	 */
	private void getFichierReel() {
		JMenuItem cryp=new JMenuItem("Crypter");
		cryp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTER);
			}	
		});
		this.fichier.add(cryp);

		JMenuItem gene=new JMenuItem("Générer une clé");
		gene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.GENERER);
			}	
		});
		this.fichier.add(gene);

		JMenuItem decryp=new JMenuItem("Décrypter");
		decryp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.DECRYPTER);
			}	
		});
		this.fichier.add(decryp);

		JMenuItem crypta=new JMenuItem("Cryptanalyser");
		crypta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTANALYSE);
			}	
		});
		this.fichier.add(crypta);

		JMenuItem crypCla=new JMenuItem("Cryptanalyser à clair connu");
		crypCla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTANALYSECLAIR);
			}	
		});
		this.fichier.add(crypCla);
	}

	/*
	 * Ajoute les items autorisés pour Alice dans fichier
	 */
	private void getFichierAlice() {
		JMenuItem cryp=new JMenuItem("Crypter");
		cryp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTER);
			}	
		});
		this.fichier.add(cryp);

		JMenuItem gene=new JMenuItem("Générer une clé");
		gene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.GENERER);
			}	
		});
		this.fichier.add(gene);
	}

	/*
	 * Ajoute les items autorisés pour Bob dans fichier
	 */
	private void getFichierBob() {
		JMenuItem decryp=new JMenuItem("Décrypter");
		decryp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.DECRYPTER);
			}	
		});
		this.fichier.add(decryp);

		JMenuItem gene=new JMenuItem("Générer une clé");
		gene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.GENERER);
			}	
		});
		this.fichier.add(gene);
	}

	/*
	 * Ajoute les items autorisés pour Eve dans fichier
	 */
	private void getFichierEve() {
		JMenuItem cryp=new JMenuItem("Cryptanalyser");
		cryp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTANALYSE);
			}	
		});
		this.fichier.add(cryp);

		JMenuItem crypCla=new JMenuItem("Cryptanalyser à clair connu");
		crypCla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ihm.stop();
				ihm.setAction(IHM.ACTIONS.CRYPTANALYSECLAIR);
			}	
		});
		this.fichier.add(crypCla);
	}
}
