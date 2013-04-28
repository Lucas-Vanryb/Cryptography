package ihm;

/**
 * Interface definissant le type abstrait selection action reel, correspondant à 
 * une GUI permettant à l'utiilisateur de selectionner une action parmi toutes les actions
 * que le programme peut effectuer (en fonctionnement reel)
 * @author Lucas VANRYB
 *
 */
public interface ISelectionActionReel extends IGUI {

	/*
	 * ACCESSEURS
	 */

	/**
	 * Retourne l'action selectionnée par l'utilisateur
	 * @return l'action selectionnée par l'utilisateur
	 */
	public IHM.ACTIONS getAction();
}