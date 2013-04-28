package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface définissant le type abstrait saisie cryptanalyse, correspondant à un GUI
 * permettant à l'utilisateur de saisir un message et eventuellement un type de code
 * @author Lucas VANRYB
 *
 */
public interface ISaisieCryptanalyse extends IGUI{

	/*
	 * ACCESSEURS
	 */
	
	/**
	 * Retourne le message saisi par l'utilisateur
	 * @return le message saisi par l'utilisateur
	 */
	public IMessage getMessage();

	/**
	 * Retourne le type de code selectionné par l'utilisateur
	 * @return le type de code selectionné par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getType();
}