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
	 * M�thode permettant de tenter de cryptanalyser un message
	 * @param crypte le message � essayer de cryptanalyser
	 * @return le message decrypte si la cryptanalyse a reussie, un message quelquoncque sinon
	 */
	public IMessage cryptanalyse(IMessage crypte);
	
	/**
	 * M�thode permettant de tenter une cryptanalyse � clair connu
	 * @param clair le message clair
	 * @param crypte le message crypte
	 * @return la cl� de cryptage si la cryptanalyse a reussi, une chaine quelconque sinon
	 */
	public String cryptanalyseCle(IMessage clair, IMessage crypte);
	
}
