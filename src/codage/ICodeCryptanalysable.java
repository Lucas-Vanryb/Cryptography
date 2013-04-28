package codage;

import message.IMessage;

/**
 * Interface definissant un code dont la cryptanalyse est possible
 * @see codage.ICode
 * @author Lucas VANRYB
 *
 */
public interface ICodeCryptanalysable extends ICode{
	
	/*
	 * SERVICES
	 */
	
	/**
	 * Méthode permettant de tenter de cryptanalyser un message
	 * @param crypte le message à essayer de cryptanalyser
	 * @return le message decrypte si la cryptanalyse a reussie, un message quelquoncque sinon
	 */
	public IMessage cryptanalyse(IMessage crypte);
	
	/**
	 * Méthode permettant de tenter une cryptanalyse à clair connu
	 * @param clair le message clair
	 * @param crypte le message crypte
	 * @return la clé de cryptage si la cryptanalyse a reussi, une chaine quelconque sinon
	 */
	public String cryptanalyseCle(IMessage clair, IMessage crypte);
	
}
