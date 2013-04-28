package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface d�finissant le type abstrait saisie, correspondant � un GUI
 * permettant � l'utilisateur de saisir un message, un type de code et une cle
 * @author Lucas VANRYB
 *
 */
public interface ISaisie extends IGUI{

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Retourne la cl� saisi par l'utilisateur
	 * @return la cl� saisi par l'utilisateur
	 */
	public String getKey();

	/**
	 * Retourne le message saisi par l'utilisateur
	 * @return le message saisi par l'utilisateur
	 */
	public IMessage getMessage();

	/**
	 * Retourne le type de code selectionn� par l'utilisateur
	 * @return le type de code selectionn� par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getTypeCode();

}