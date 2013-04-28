package ihm;

import codage.Interface;

/**
 * Interface modélisant les intéractions possibles d'avoir avec un objet graphique permettant de saisir
 * un type de code
 * @author Lucas VANRYB
 *
 */
public interface IGenerer extends IGUI{

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Retourne le type de code selectionné par l'utilisateur
	 * @return le type de code selectionné par l'utilisateur, null si il n'en a choisis aucun
	 */
	public Interface.CODES getTypCode();
}