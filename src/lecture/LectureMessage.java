package lecture;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

import fabrique.Fabrique;

import message.IMessage;

/**
 * Classe permettant de lire un message dans un fichier texte
 * @see lecture.ILectureMessage
 * @author Lucas VANRYB
 *
 */
public class LectureMessage implements ILectureMessage {

	/*
	 * ATTRIBUTS
	 */
	
	/**
	 * Message lu
	 */
	IMessage m;
	
	/**
	 * Vaut true si la lecture a posé un problème, false sinon
	 */
	boolean pb;

	/*
	 * CONSTRUCTEUR
	 */
	
	/**
	 * Constructeur lancant la lecture du message
	 * @param nom le chemin vers le fichier dans lequel est ecrit le message
	 */
	public LectureMessage(String nom) {
		this.pb=false;
		this.m=Fabrique.fabriquerMessage("");
		this.lireFichierTexte(nom);
	}
	
	/*
	 * ACCESSEURS
	 */
	
	/*
	 * Méthode permettant d'effectuer la lecture
	 */
	private void lireFichierTexte(String nom) {
		try {
			BufferedReader aLire=new BufferedReader(new FileReader(nom));
			String temp=aLire.readLine();
			String retour="";
			boolean first=true;
			while(temp!=null) {
				if(!first) {
					retour+='\n';
				}
				retour+=temp;
				temp=aLire.readLine();
				first=false;
			}
			this.m=Fabrique.fabriquerMessage(retour.substring(0, retour.length()));
			aLire.close();
		}
		catch(Exception e) {
			this.pb=true;
			JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier message, veuillez corriger le chemin vers celui ci ou saisir le message manuellement","Erreur de lecture",JOptionPane.ERROR_MESSAGE);
		}
	}


	public IMessage getMessage() {
		return this.m;
	}

	public boolean probleme() {
		return this.pb;
	}
	
	/**
	 * Methode main de test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new LectureMessage("test1.txt").getMessage());
		
	}

}
