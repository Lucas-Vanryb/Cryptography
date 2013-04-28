package codage;

import javax.swing.JOptionPane;

import message.IMessage;

/**
 * Mod�lisation en m�moire d'un message cod�, contenant version crypt�e et clair
 * du message ainsi que la cl� et les code associ�
 * @author Lucas VANRYB
 *
 */
public class MessageCode implements IMessageCode {

	/*
	 * ATTRIBUTS
	 */
	
	/**
	 * Le message clair
	 */
	IMessage clair;
	
	/**
	 * Le message crypt�
	 */
	IMessage crypte;
	
	/**
	 * Le type de code
	 */
	ICode code;
	
	/**
	 * La cl� de cryptage
	 */
	String key;
	
	/**
	 * Bool�en valant true si la cryptanalyse � clair connu a fonctionnee
	 */
	boolean keyKnown;
	
	/**
	 * Bool�en valant true si la cryptanalyse a fonctionnee
	 */
	boolean clairKnown;
	
	/**
	 * Dur�e de la derni�re op�ration
	 */
	long time;

	/*
	 * CONSTRUCTEURS
	 */
	
	/**
	 * Constructeur vide
	 */
	public MessageCode() {
		this.clair=null;
		this.clairKnown=false;
		this.code=null;
		this.crypte=null;
		this.key=null;
		this.keyKnown=false;
		this.time=0;
	}
	
	/**
	 * Constructeur permetant de cryptanalyser � clair connu
	 * @param clair le message clair
	 * @param crypte le message crypte
	 * @param code le type de code
	 */
	public MessageCode(IMessage clair, IMessage crypte, ICode code) {
		if(code instanceof ICodeCryptanalysable) {
			this.clair=clair;
			this.crypte=crypte;
			this.code=code;
			this.clairKnown=true;
			this.keyKnown=false;
			this.key=((ICodeCryptanalysable)this.getCode()).cryptanalyseCle(this.getMessageDecode(), this.getMessageCode());
			this.keyKnown=this.getCode().succesCle();
			this.time=this.getCode().tempsCrypto();
		}
		else {
			JOptionPane.showMessageDialog(null, "D�sol�, la cyptanalyse n'est pas impl�ment�e pour ce code", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Constructeur permettant de cryptanalyser
	 * @param code le type de code
	 * @param crypte le message crypte
	 */
	public MessageCode(ICode code, IMessage crypte) {
		if(code instanceof ICodeCryptanalysable) {
			this.code=code;
			this.crypte=crypte;
			this.keyKnown=false;
			this.clairKnown=false;
			this.clair=((ICodeCryptanalysable)this.getCode()).cryptanalyse(this.getMessageCode());
			this.clairKnown=this.getCode().succesCrypto();
			this.time=this.getCode().tempsCrypto();
		}
		else {
			JOptionPane.showMessageDialog(null, "D�sol�, la cyptanalyse n'est pas impl�ment�e pour ce code", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Constructeur permettant de crypter
	 * @param clair le message clair
	 * @param code le type de code
	 * @param key la cle de cryptage
	 */
	public MessageCode(IMessage clair, ICode code, String key) {
		this.key=key;
		this.clair=clair;
		this.code=code;
		this.crypte=this.code.crypter(clair,this.key);
		this.keyKnown=true;
		this.clairKnown=true;
		this.time=this.getCode().tempsCrypto();
	}

	/**
	 * Constructeur permettant de d�crypter
	 * @param code le type de code
	 * @param crypte le message crypte
	 * @param key la clef de cryptage
	 */
	public MessageCode(ICode code, IMessage crypte, String key) {
		this.key=key;
		this.code=code;
		this.crypte=crypte;
		this.clair=code.decrypter(crypte, this.key);
		this.keyKnown=true;
		this.clairKnown=true;
		this.time=this.getCode().tempsCrypto();
	}
	
	/*
	 * ACCESSEURS
	 */

	public ICode getCode() {
		return this.code;
	}

	public String getKey() {
		return this.key;
	}

	public IMessage getMessageCode() {
		return this.crypte;
	}

	public IMessage getMessageDecode() {
		return this.clair;
	}

	public boolean foundKey() {
		return this.keyKnown;
	}

	public boolean foundMessage() {
		return this.clairKnown;
	}

	public long getDuree() {
		return this.time;
	}
}
