package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface définissant le type abstrait saisie, correspondant à un GUI
 * permettant à l'utilisateur de saisir un message, un type de code et une cle
 * @author Lucas VANRYB
 *
 */
public interface ISaisie extends IGUI{

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Retourne la clé saisi par l'utilisateur
	 * @return la clé saisi par l'utilisateur
	 */
	public String getKey();

	/**
	 * Retourne le message saisi par l'utilisateur
	 * @return le message saisi par l'utilisateur
	 */
	public IMessage getMessage();

	/**
	 * Retourne le type de code selectionné par l'utilisateur
	 * @return le type de code selectionné par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getTypeCode();

}