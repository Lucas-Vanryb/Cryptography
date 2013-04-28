package lecture;

import codage.Interface;

/**
 * Interface decrivant un type abstrait permettant d'obtenir une cl�  et un type de code
 * d'une source exterieure
 * @author Lucas VANRYB
 *
 */
public interface ILectureClef {

	/**
	 * Retourne la clef lue
	 * @return la clef lue
	 */
	public String getClef();
	
	/**
	 * Retourne le type de code lu
	 * @return le type de code lu
	 */
	public Interface.CODES getTypeCode();
	
	/**
	 * Retourne true si la lecture a pos� un probl�me, false sinon
	 * @return true si la lecture a pos� un probl�me, false sinon
	 */
	public boolean probleme();
	
}
