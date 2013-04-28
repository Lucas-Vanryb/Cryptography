package ihm;

/**
 * Interface definissant le type abstrait selection action reel, correspondant � 
 * une GUI permettant � l'utiilisateur de selectionner une action parmi toutes les actions
 * que le programme peut effectuer (en fonctionnement reel)
 * @author Lucas VANRYB
 *
 */
public interface ISelectionActionReel extends IGUI {

	/*
	 * ACCESSEURS
	 */

	/**
	 * Retourne l'action selectionn�e par l'utilisateur
	 * @return l'action selectionn�e par l'utilisateur
	 */
	public IHM.ACTIONS getAction();
}