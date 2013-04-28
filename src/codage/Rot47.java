package codage;

import java.util.Date;
import message.IMessage;
import fabrique.Fabrique;

/**
 * Le classe Rot47 permet d'utiliser Rot47
 * Le cryptage et le décryptage sont implémentés ainsi que la cryptanalyse
 * @see codage.ICode
 * @see codage.ICodeCryptanalysable
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Rot47 extends Code implements ICode,ICodeCryptanalysable {

	/*
	 * CONSTRUCTEUR
	 */
	
	public Rot47() {
		super();
	}

	/*
	 * SERVICES
	 */
	
	public IMessage cryptanalyse(IMessage crypte) {
		IMessage m=this.decrypter(crypte, "47");
		if(Outils.comparerCourt(m.getChar())<cst) {
			this.succesCrypto=true;
		}
		return  m;
	}

	public String cryptanalyseCle(IMessage clair, IMessage crypte) {
		long d=new Date().getTime();
		int j=0;
		boolean trouve=false;
		while(j<clair.taille() && !trouve) {
			if((char)(33+(clair.getChar(j)+47-33)%93)!=crypte.getChar(j) && clair.getChar(j)!=crypte.getChar(j)) {
				trouve=true;
			}
			j++;
		}
		if(!trouve) {
			this.succesCle=true;
		}
		this.time=new Date().getTime()-d;
		return "47";
	}

	private IMessage adapter(IMessage clair) {
		char[] c=new char[clair.taille()];
		for(int i=0;i<clair.taille();i++) {
			if(clair.getChar(i)=='é' || clair.getChar(i)=='è' || clair.getChar(i)=='ê' || clair.getChar(i)=='ë') {
				c[i]='e';
			}
			else {
				if(clair.getChar(i)=='à') {
					c[i]='a';
				}
				else {
					if(clair.getChar(i)=='ü' || clair.getChar(i)=='ù') {
						c[i]='u';
					}
					else {
						if(clair.getChar(i)=='ô') {
							c[i]='o';
						}
						else {
							if(clair.getChar(i)=='î' || clair.getChar(i)=='ï') {
								c[i]='i';
							}
							else {
								c[i]=clair.getChar(i);
							}
						}
					}
				}
			}
		}
		
		return Fabrique.fabriquerMessage(c);
	}

	public IMessage crypter(IMessage clair, String key) {
		long d=new Date().getTime();
		IMessage cc=this.adapter(clair);
		char[] c=new char[cc.taille()];
		for(int i=0;i<cc.taille();i++) {
			if(cc.getChar(i)>=33 && cc.getChar(i)<=126) {
				c[i]=(char)(33+((cc.getChar(i)-33+47)%93));
			}
			else {
				c[i]=cc.getChar(i);
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}


	public IMessage decrypter(IMessage crypte, String key) {
		long d=new Date().getTime();
		char[] c=new char[crypte.taille()];
		for(int i=0;i<crypte.taille();i++) {
			if(crypte.getChar(i)>=33 && crypte.getChar(i)<=126) {
				if(crypte.getChar(i)-33-47>=0){
				c[i]=(char)((crypte.getChar(i)-47));
				}
				else {
					c[i]=(char)(33+93+((crypte.getChar(i)-33-47)));
				}
			}
			else {
				c[i]=crypte.getChar(i);
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}


	/**
	 * Méthode main permettant de tester la classe
	 * @param args
	 */
	public static void main(String[] args) {
		IMessage clair=Fabrique.fabriquerMessage("J'AIME LES ANIMAUX DE COMPAGNIE, PLUS PARTICULIÈREMENT LES PONEYS!");
		IMessage crypte=new Rot47().crypter(clair, "3");
		ICodeCryptanalysable cryptanalyse=new Rot47();
		ICodeCryptanalysable cle=new Rot47();
		System.out.println("Cryptage du message :"+new Rot47().crypter(clair, "3"));
		System.out.println("Decryptage avec cle :"+new Rot47().decrypter(crypte, "3"));
		System.out.println("Tentative de decryption auto :"+cryptanalyse.cryptanalyse(crypte));
		System.out.println("Resultat :"+cryptanalyse.succesCrypto());
		System.out.println("Duree :"+cryptanalyse.tempsCrypto());
		System.out.println("Tentative de recuperation de la cle de cryptage :"+cle.cryptanalyseCle(clair, crypte));
		System.out.println("Resultat :"+cle.succesCle());
		System.out.println("Durée :"+cle.tempsCrypto());
	}

	public String genererCle() {
		return "47";
	}

	public boolean estCleValable(String k) {
		return true;
	}
}
