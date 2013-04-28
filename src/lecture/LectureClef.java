package lecture;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

import codage.Interface;

/**
 * Classe permettant de lire une clé et un type de code dans un fichier texte
 * @see lecture.ILectureClef
 * @author Lucas VANRYB
 *
 */
public class LectureClef implements ILectureClef {

	/*
	 * ATTRIBUTS
	 */

	/**
	 * Clé lue
	 */
	String key;

	/**
	 * Type de code lu
	 */
	Interface.CODES typeCode;

	/**
	 * Vaut true si la lecture a causé un problème
	 */
	boolean pb;

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur lancant la lecture automatiquement du fichier au chemin nom
	 * puis la mise en mémoire des informations
	 * @param nom chemin vers le fichier à lire
	 */
	public LectureClef(String nom) {
		this.key="";
		this.pb=false;
		this.typeCode=null;
		this.lireFichierTexte(nom);
	}

	/*
	 * ACCESSEURS
	 */

	/*
	 * Méthode permettant de lire un fichier texte de format type clé
	 * et d'enreistrer ces informations
	 */
	private void lireFichierTexte(String nom) {
		try {
			BufferedReader aLire=new BufferedReader(new FileReader(nom));
			String infos=aLire.readLine();
			int i=0;;
			String t="";
			int j=0;
			Interface.CODES[] val= Interface.CODES.values();
			boolean trouve=false;
			while(infos.charAt(i)!=' ') {
				t+=infos.charAt(i);
				i++;
			}
			i++;
			this.key=infos.substring(i);
			while(j<val.length && !trouve) {
				if(val[j].getNom().equals(t)) {
					this.typeCode=val[j];
					trouve=true;
				}
				j++;
			}
			aLire.close();
		} 
		catch (Exception e) {
			this.pb=true;
			JOptionPane.showMessageDialog(null,"Erreur de lecture du fichier clef, veuillez corriger le chemin vers ce dernier ou saisir les informations sur le type de codage et sur la clef manuellement","Erreur de lecture",JOptionPane.ERROR_MESSAGE);
		}
	}

	public String getClef() {
		return this.key;
	}

	public Interface.CODES getTypeCode() {
		return this.typeCode;
	}

	public boolean probleme() {
		return this.pb;
	}

	/**
	 * Methode main de test
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new LectureClef("test2.txt").getClef());
		System.out.println(new LectureClef("test2.txt").getTypeCode());

		System.out.println(new LectureClef("test3.txt").getClef());
		System.out.println(new LectureClef("test3.txt").getTypeCode());
	}

}
