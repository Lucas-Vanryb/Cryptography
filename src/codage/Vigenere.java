package codage;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import fabrique.Fabrique;
import message.IMessage;

/**
 * Le classe Vigenere permet d'utiliser le chiffre de Vigenere
 * Le cryptage et le décryptage sont implémentés ainsi que la cryptanalyse
 * @see codage.ICode
 * @see codage.ICodeCryptanalysable
 * @see codage.Code
 * @author Lucas VANRYB
 *
 */
public class Vigenere extends Code implements ICode,ICodeCryptanalysable {

	/**
	 * Determine la longueur d'une clef generee
	 */
	public static final int LONGUEUR_CLE=7;

	/**
	 * L'alphabet dans l'ordre
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Initialise les variables d'instance
	 */
	public Vigenere() {
		super();
	}

	/*
	 * SERVICES
	 */

	/**
	 * Méthode permettant de tenter de cryptanalyser un message avec le chiffre de Vigenere
	 * @param crypte le message à essayer de cryptanalyser
	 * @return le message decrypte si la cryptanalyse a reussie, un message quelconque sinon
	 */
	public IMessage cryptanalyse(IMessage crypte) {
		long d=new Date().getTime();
		int l=this.determinerLongueurCle(crypte);
		String[] cesar=new String[l];
		for(int i=0;i<l;i++) {
			cesar[i]="";
		}
		for(int i=0;i<crypte.taille();i++) {
			cesar[i%l]+=crypte.getChar(i);
		}
		IMessage[] aCombiner=new IMessage[l];
		for(int i=0;i<l;i++) {
			aCombiner[i]=this.cryptanalyseCesar(Fabrique.fabriquerMessage(cesar[i]));
		}
		String res="";
		for(int i=0;i<crypte.taille();i++) {
			res+=aCombiner[i%l].getChar(i/l);
		}
		IMessage m=Fabrique.fabriquerMessage(res);
		this.time=new Date().getTime()-d;
		double min=Outils.comparerCourt(m.getChar());
		if(min<cst) {
			this.succesCrypto=true;
		}
		return m;
	}

	/*
	 * Determine la longueur de la cle dans le cadre d'une cryptanalyse
	 */
	private int determinerLongueurCle(IMessage crypte) {
		String temp="";
		int r=0;
		boolean trouve=false;
		ArrayList<Integer> al=new ArrayList<Integer>();
		for(int i=0;i<15;i++) {
			trouve=false;
			temp=crypte.toString().substring(i, i+3);
			for (int j=i+3;j<crypte.taille()-2 && !trouve;j++) {
				if(crypte.toString().substring(j, j+3).equals(temp)) {
					r=j-i;
					trouve=true;
				}

			}
			if(trouve) {
				al.add(r);
			}
		}
		ArrayList<Integer> possibles=new ArrayList<Integer>();
		for(int i=3;i<50;i++) {
			r=0;
			for(int j=0;j<al.size();j++) {
				if(al.get(j)%i==0) {
					r++;
				}
			}
			if(r>=(al.size()*0.8)) {
				possibles.add(i);
			}
		}
		int min=possibles.get(0);
		for(int q=1;q<possibles.size();q++) {
			if(possibles.get(q)<min) {
				min=possibles.get(q);
			}
		}
		return min;
	}

	/*
	 * Determine la longueur de la clef dans la cadre d'une cryptanalyse à clair connu
	 */
	private int longueurCleMin(IMessage clair, IMessage crypte,boolean premier) {
		int i=0;
		while(i<clair.taille() && clair.getChar(i)==crypte.getChar(i)) {
			i++;
		}
		int distancePremierCaractere=(crypte.getChar(i)-clair.getChar(i)+26)%26;
		ArrayList<Integer> al=new ArrayList<Integer>();
		int r=i+1;
		for(int j=i+1;j<clair.taille();j++) {
			if((crypte.getChar(j)-clair.getChar(j)+26)%26==distancePremierCaractere || (crypte.getChar(j)-clair.getChar(j)+26)%26==0) {
				al.add(r-i);
			}
			if(clair.getChar(i)==crypte.getChar(i)) {
				r--;
			}
			r++;
		}
		ArrayList<Integer> pos=new ArrayList<Integer>();
		boolean estOK=true;
		for(int z=0;z<al.size();z++) {
			estOK=true;
			for(int k=al.get(z);k<clair.taille()-i;k+=al.get(z))	{
				if(!al.contains(k)) {
					if(al.get(z)==6) {
					}
					estOK=false;
				}
			}
			if(estOK) {
				pos.add(al.get(z));
			}
		}
		int min=pos.get(0);
		for(int q=1;q<pos.size();q++) {
			if(pos.get(q)<min) {
				min=pos.get(q);
			}
		}
		if(!premier) {
			return min;
		}
		else {
			int kn=this.longueurCleMin(Fabrique.fabriquerMessage(clair.toString().substring(i+1)), Fabrique.fabriquerMessage(crypte.toString().substring(i+1)), false);
			if(kn==min) {
				return min;
			}
			else {
				return Math.max(kn,min);
			}
		}
	}

	/**
	 * Méthode permettant de tenter une cryptanalyse à clair connu sur un cryptage de Vigenere
	 * @param clair le message clair
	 * @param crypte le message crypte
	 * @return la clé de cryptage si la cryptanalyse a reussi, une chaine quelquoncque sinon
	 */
	public String cryptanalyseCle(IMessage clair, IMessage crypte) {
		/*
		 * Méthode determinant d'abord la longueur de la clé, puis effectuant une cryptanalyse de
		 * type Cesar sur les "sous messages"
		 */
		try {
			long d=new Date().getTime();
			int l=this.longueurCleMin(clair, crypte,true);
			String key="";
			int r=0;
			char[] c=new char[l];
			boolean[] bool=new boolean[l];
			for(int i=0;r<l;i++) {
				if(clair.getChar(i)==crypte.getChar(i) && i<clair.taille()-l) {
					if(!bool[i%l]) {
						bool[i%l]=false;
					}
				}
				else {
					if(!bool[i%l]) {
						r++;
						c[i%l]=(char)(65+((crypte.getChar(i)-clair.getChar(i)+26)%26));
						bool[i%l]=true;
					}
				}
			}
			for(int i=0;i<c.length;i++) {
				key+=c[i];
			}
			this.time=new Date().getTime()-d;
			this.succesCle=true;
			return key;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Echec de la cryptanalyse", "Echec", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return "Erreur";
		}
	}

	/**
	 * Méthode permettant de crypter un message à l'aide du chiffre de Vigenere
	 * @param clair le message à crypter
	 * @param key la clé à utiliser pour le cryptage
	 * @return le message crypté
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * Lecture de la clef, puis encodage des caractères un par un par
		 * des opérations elementaires
		 */
		long d=new Date().getTime();
		String keyAdaptee=this.adapter(key);
		int k;
		char[] c=new char[clair.taille()];
		for(int i=0;i<clair.taille();i++) {
			k=keyAdaptee.charAt(i%keyAdaptee.length())-65;
			if(clair.getChar(i)>=65 && clair.getChar(i)<=90) {
				c[i]=(char)(65+((clair.getChar(i)-65+k)%26));
			}
			else {
				if(clair.getChar(i)>=97 && clair.getChar(i)<=122) {
					c[i]=(char)(97+((clair.getChar(i)-97+k)%26));
				}
				else{
					c[i]=clair.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	/**
	 * Méthode permettant de décrypter un message codé à l'aide du chiffre de Vigenere
	 * @param crypte le message à decrypter
	 * @param key la clé de cryptage associée
	 * @return le message décrypté
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * Lecture de la clef, puis décodage des caractères un par un par
		 * des opérations elementaires
		 */
		String keyAdaptee=this.adapter(key);
		long d=new Date().getTime();
		int k;
		char[] c=new char[crypte.taille()];
		for(int i=0;i<crypte.taille();i++) {
			k=keyAdaptee.charAt(i%keyAdaptee.length())-65;
			if(crypte.getChar(i)>=65 && crypte.getChar(i)<=90) {
				if((crypte.getChar(i)-65-k)<0) {
					c[i]=(char)(65+26+((crypte.getChar(i)-65-k)));
				}
				else {
					c[i]=(char)(65+((crypte.getChar(i)-65-k)));
				}
			}
			else {
				if(crypte.getChar(i)>=97 && crypte.getChar(i)<=122) {
					if((crypte.getChar(i)-97-k)<0) {
						c[i]=(char)(97+26+((crypte.getChar(i)-97-k)));
					}
					else {
						c[i]=(char)(97+((crypte.getChar(i)-97-k)));
					}
				}
				else{
					c[i]=crypte.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(c);
	}

	/*
	 * Méthode permettant d'adapter la clé
	 */
	private String adapter(String key) {
		String aRetourner="";
		for(int i=0;i<key.length();i++) {
			if(key.charAt(i)>=65 && key.charAt(i)<=90) {
				aRetourner+=key.charAt(i);
			}
			if(key.charAt(i)>=97 && key.charAt(i)<=122) {
				aRetourner+=(char)(key.charAt(i)-32);
			}
		}
		return aRetourner;
	}

	/**
	 * Méthode permettant de génerer une clef valide pour le chiffre de Vigenere,
	 * c'est à dire une suite de lettres aléatoire
	 * @return une clé valide pour le chiffre de Vigenere
	 */
	public String genererCle() {
		String aRetourner="";
		for(int i=0;i<LONGUEUR_CLE;i++) {
			aRetourner+=(char)(65+(int)(Math.random()*26));
		}
		return aRetourner;
	}

	/**
	 * Méthode permettant de génerer une clef valide pour Vigenere,
	 * c'est à dire une suite de lettres
	 * @return une clé valide pour Vigenere
	 */
	public boolean estCleValable(String k) {
		int i=0;
		boolean trouve=false;
		while(i<k.length() && !trouve) {
			if(!ALPHABET.contains(""+k.charAt(i))&&!ALPHABET.toUpperCase().contains(""+k.charAt(i))) {
				trouve=true;
				JOptionPane.showMessageDialog(null, "Veuillez saisir une clé constituée uniquement d'une suite de lettres majuscules et minuscule non accentuées","Cle incorrecte",JOptionPane.ERROR_MESSAGE);
			}
			i++;
		}
		return !trouve;
	}

	/*
	 * Méthode effectuant une cryptanalyse identique à celle du chiffre de Cesar
	 * sur le message passé en paramètre
	 */
	private IMessage cryptanalyseCesar(IMessage crypte) {
		IMessage m=this.decrypterCaesar(crypte, "0");
		double min=Outils.comparerLong(m.getChar());
		Integer r=-1;
		for(Integer i=1;i<26;i++) {
			m=this.decrypterCaesar(crypte, i.toString());
			if(Outils.comparerLong(m.getChar())<min) {
				min=Outils.comparerLong(m.getChar());
				r=i;
			}
		}
		return this.decrypterCaesar(crypte,r.toString());
	}

	/**
	 * Méthode correspondant à la méthode decrypter du chiffre de Caesar
	 * @param crypte le message à décrypter
	 * @param key la clé à utiliser
	 * @return le message décrypter avec le chiffre de Cesar
	 */
	private IMessage decrypterCaesar(IMessage crypte, String key) {
		/*
		 * Les caractères sont décodés un à un via opérations elementaires
		 * Les caractères ne correspondant pas à des lettres sont ajoutés tel quels.
		 */
		int k=Integer.parseInt(key);
		char[] c=new char[crypte.taille()];
		for(int i=0;i<crypte.taille();i++) {
			if(crypte.getChar(i)>=65 && crypte.getChar(i)<=90) {
				if((crypte.getChar(i)-65-k)<0) {
					c[i]=(char)(65+26+((crypte.getChar(i)-65-k)));
				}
				else {
					c[i]=(char)(65+((crypte.getChar(i)-65-k)));
				}
			}
			else {
				if(crypte.getChar(i)>=97 && crypte.getChar(i)<=122) {
					if((crypte.getChar(i)-97-k)<0) {
						c[i]=(char)(97+26+((crypte.getChar(i)-97-k)));
					}
					else {
						c[i]=(char)(97+((crypte.getChar(i)-97-k)));
					}
				}
				else{
					c[i]=crypte.getChar(i);
				}
			}
		}
		return Fabrique.fabriquerMessage(c);
	}

	/**
	 * Methode main de test
	 * @param args
	 */
	public static void main(String[] args) {
		String key=new Vigenere().genererCle();
		IMessage clair=Fabrique.fabriquerMessage("Considérant que la reconnaissance de la dignité inhérente à tous les membres de la famille humaine et de leurs droits égaux et inaliénables constitue le fondement de la liberté, de la justice et de la paix dans le monde.Considérant que la méconnaissance et le mépris des droits de l'homme ont conduit à des actes de barbarie qui révoltent la conscience de l'humanité et que l'avènement d'un monde où les êtres humains seront libres de parler et de croire, libérés de la terreur et de la misère, a été proclamé comme la plus haute aspiration de l'homme.Considérant qu'il est essentiel que les droits de l'homme soient protégés par un régime de droit pour que l'homme ne soit pas contraint, en suprême recours, à la révolte contre la tyrannie et l'oppression.Considérant qu'il est essentiel d'encourager le développement de relations amicales entre nations.Considérant que dans la Charte les peuples des Nations Unies ont proclamé à nouveau leur foi dans les droits fondamentaux de l'homme, dans la dignité et la valeur de la personne humaine, dans l'égalité des droits des hommes et des femmes, et qu'ils se sont déclarés résolus à favoriser le progrès social et à instaurer de meilleures conditions de vie dans une liberté plus grande.");
		IMessage crypte=new Vigenere().crypter(clair, key);
		System.out.println("Cryptage du message :"+new Vigenere().crypter(clair, key));
		System.out.println("Decryptage avec cle :"+new Vigenere().decrypter(crypte, key));
		System.out.println(key);
		System.out.println(new Vigenere().cryptanalyseCle(clair, crypte));
		System.out.println(new Vigenere().cryptanalyse(crypte));
	}
}
