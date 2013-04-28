package codage;

import java.util.Date;

import message.IMessage;
import message.Message;


import fabrique.Fabrique;

/**
 * Le classe Rot13 permet d'utiliser Rot13
 * Le cryptage et le décryptage sont implémentés ainsi que la cryptanalyse
 * @see codage.ICode
 * @see codage.ICodeCryptanalysable
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Rot13 extends Code implements ICode,ICodeCryptanalysable {


	/*
	 * CONSTRUCTEUR
	 */
	
	/**
	 * Initialise les variables d'instance
	 */
	public Rot13() {
		super();
	}
	
	/*
	 * SERVICES
	 */

	public IMessage crypter(IMessage clair, String key) {
		long d=new Date().getTime();
		char[] c=new char[clair.taille()];
		for(int i=0;i<clair.taille();i++) {
			if(clair.getChar(i)>=65 && clair.getChar(i)<=90) {
				c[i]=(char)(65+((clair.getChar(i)-65+13)%26));
			}
			else {
				if(clair.getChar(i)>=97 && clair.getChar(i)<=122) {
					c[i]=(char)(97+((clair.getChar(i)-97+13)%26));
				}
				else{
					c[i]=clair.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	public IMessage cryptanalyse(IMessage crypte) {
		IMessage m=this.decrypter(crypte, "13");
		if(Outils.comparerCourt(m.getChar())<cst) {
			this.succesCrypto=true;
			this.succesCle=true;
		}
		return  m;
	}

	public String cryptanalyseCle(IMessage clair, IMessage crypte) {
		long d=new Date().getTime();
		int j=0;
		boolean trouve=false;
		while(j<clair.taille() && !trouve) {
			if((char)(65+(clair.getChar(j)+13-65)%26)!=crypte.getChar(j) && clair.getChar(j)!=crypte.getChar(j) && (char)(97+(clair.getChar(j)+13-97)%26)!=crypte.getChar(j)) {
				trouve=true;
			}
		}
		this.time=new Date().getTime()-d;
		if(!trouve) {
			this.succesCle=true;
		}
		return "13";
	}

	public IMessage decrypter(IMessage crypte, String key) {
		return this.crypter(crypte, key);
	}

	/**
	 * Méthode main permettant de tester la classe
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Rot13().crypter(new Message("En 1999, Rot13 était utilisé par Netscape pour encoder des mots de passe!"), "13"));
		System.out.println(new Rot13().decrypter((new Message("Ra 1999, Ebg13 égnvg hgvyvfé cne Argfpncr cbhe rapbqre qrf zbgf qr cnffr")),""));
	}

	public String genererCle() {
		return "13";
	}

	public boolean estCleValable(String k) {
		return true;
	}
}
