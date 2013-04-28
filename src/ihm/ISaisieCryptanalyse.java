package ihm;

import message.IMessage;
import codage.Interface;

/**
 * Interface d�finissant le type abstrait saisie cryptanalyse, correspondant � un GUI
 * permettant � l'utilisateur de saisir un message et eventuellement un type de code
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
	 * Retourne le type de code selectionn� par l'utilisateur
	 * @return le type de code selectionn� par l'utilisateur, null si il n'en a choisi aucun
	 */
	public Interface.CODES getType();
}