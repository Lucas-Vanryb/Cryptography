package codage;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import message.IMessage;
import message.Message;


import fabrique.Fabrique;

/**
 * Classe impl�menttant le codage et le d�codage
 * � l'aide du chiffre de Playfair
 * @author Lucas VANRYB
 *
 */
public class Playfair extends Code implements ICode {

	/*
	 * Variables temporaires chang�e � chaque codage/d�codage de binome
	 */

	/**
	 * La premiere lettre de binome apr�s codage/d�codage
	 */
	private char t1;

	/**
	 * La seconde lettre du binome apr�s codage/d�codage
	 */
	private char t2;

	/**
	 * La premi�re coordon�e de la derni�re lettre cherch�e dans le tableau
	 */
	private int i1;

	/**
	 * La seconde coordon�e de la derni�re lettre cherch�e dans le tableau
	 */
	private int i2;


	/**
	 * L'alphabet minuscule
	 */
	public final static String ALPHABET="abcdefghijklmnopqrstuvwxyz";

	/**
	 * La caract�re inexistant, interdit dans le message
	 */
	public final static char CHAR_INEXISTANT='w';

	/**
	 * Le caract�re neutre servant � s�parer les lettre identiques qui se suivent
	 */
	public final static char CHAR_NEUTRE='x';

	/**
	 * Le caract�re de remplacement qui vient se substituer au caract�re interdit
	 */
	public final static char CHAR_REMPLACEMENT='v';

	/**
	 * La liste des caract�res recopi�s sans �tre cod�s dans le message
	 */
	public final static ArrayList<Character> IGNORE=listeIgnores();

	/*
	 * CONSTRUCTEUR
	 */

	/**
	 * Constructeur initialisant les variables d'instance
	 */
	public Playfair() {
		super();
		this.i1=0;
		this.i2=0;
		this.t1=0;
		this.t2=0;
	}

	/*
	 * M�thode ajoutant les caract�res ignor�s � la liste des ignor�s,
	 * appel�e par <init>
	 */
	private static ArrayList<Character> listeIgnores() {
		ArrayList<Character> a=new ArrayList<Character>();
		a.add(' ');
		a.add('\n');
		a.add("'".charAt(0));
		return a;
	}

	/*
	 * SERVICES
	 */

	/**
	 * M�thode permettant de crypter un message � l'aide du chiffre de Playfair
	 * @param clair le message � crypter
	 * @param key la cl� � utiliser pour le cryptage
	 * @return le message crypt�
	 */
	public IMessage crypter(IMessage clair, String key) {
		/*
		 * La m�thode lit d'abord la cl�, puis  adapate et
		 * parcourt le message afin de transmettre les caract�res
		 * correspondant � des lettres deux � deux �
		 * la m�thode coderChar tout en recopiant les caracteres
		 * contenus dans IGNORE
		 */
		long d=new Date().getTime();
		IMessage cc=this.adapter(clair);
		char[][] c=this.remplirTabChar(key);
		char c1=' '; int r1=0;
		char c2=' '; int r2=0;
		boolean act=true;
		char[] nouveau=new char[cc.taille()];
		for(int i=0;i<cc.taille();i=i+2) {
			if(i!=cc.taille()-1) {
				r1=0;
				r2=0;
				act=true;
				if(IGNORE.contains(cc.getChar(i))) {
					if(!IGNORE.contains(cc.getChar(i+1))) {
						if(!IGNORE.contains(cc.getChar(i+2))) {
							c1=cc.getChar(i+1);
							c2=cc.getChar(i+2);
							r1=1;
							r2=1;
							nouveau[i]=cc.getChar(i);
						}
						else {
							act=false;
							nouveau[i]=cc.getChar(i);
							i--;
						}
					}
					else {
						nouveau[i]=cc.getChar(i);
						nouveau[i+1]=cc.getChar(i+1);
						act=false;
					}
				}
				else {
					if(IGNORE.contains(cc.getChar(i+1))) {
						c1=cc.getChar(i);
						c2=cc.getChar(i+2);
						r1=0;
						r2=1;
						nouveau[i+1]=cc.getChar(i+1);
					}
					else {
						c1=cc.getChar(i);
						c2=cc.getChar(i+1);
					}
				}
				if(act) {
					this.coderChar(c1, c2,c);
					nouveau[i+r1]=this.t1;
					nouveau[i+1+r2]=this.t2;
					if(r1==1 || r2==1) {
						i++;
					}

				}
			}
			else {
				if(IGNORE.contains(cc.getChar(i))) {
					nouveau[i]=cc.getChar(i);
				}
			}
		}
		this.time=new Date().getTime()-d;
		return Fabrique.fabriquerMessage(nouveau);
	}

	/*
	 * M�thode permettant d'encoder deux caract�res diff�rents en utilisant
	 * le tableau de playfair
	 */
	private void coderChar(char c1,char c2,char[][] c) {
		int t11;
		int t12;
		int t21;
		int t22;
		this.chercheTab(c, c1);
		t11=this.i1;
		t12=this.i2;
		this.chercheTab(c, c2);
		t21=this.i1;
		t22=this.i2;
		if(t11==t21) {
			this.t1=c[t11][(t12+1)%5];
			this.t2=c[t21][(t22+1)%5];
		}
		else {
			if(t12==t22) {
				this.t1=c[(t11+1)%5][t12];
				this.t2=c[(t21+1)%5][t22];
			}
			else {
				this.t1=c[t11][t22];
				this.t2=c[t21][t12];
			}
		}
	}

	/*
	 * M�thode permettant de d�coder deux caract�res � l'aide
	 * du tableau de Playfair
	 */
	private void decoderChar(char c1,char c2,char[][] c) {
		int t11;
		int t12;
		int t21;
		int t22;
		this.chercheTab(c, c1);
		t11=this.i1;
		t12=this.i2;
		this.chercheTab(c, c2);
		t21=this.i1;
		t22=this.i2;
		if(t11==t21) {
			if(t12==0) {
				this.t1=c[t11][4];
			}
			else{
				this.t1=c[t11][(t12-1)%5];
			}
			if(t22==0) {
				this.t2=c[t21][4];
			}
			else {
				this.t2=c[t21][(t22-1)%5];
			}
		}
		else {
			if(t12==t22) {
				if(t11==0) {
					this.t1=c[4][t12];
				}
				else {
					this.t1=c[(t11-1)%5][t12];
				}
				if(t21==0) {
					this.t2=c[4][t22];
				}
				else {
					this.t2=c[(t21-1)%5][t22];
				}

			}
			else {
				this.t1=c[t11][t22];
				this.t2=c[t21][t12];
			}
		}
	}

	/*
	 * M�thode permettant de rechercher un caract�re dans
	 * le tableau de Playfair
	 */
	private void chercheTab(char[][] c, char ch) {
		for(int i=0;i<25;i++) {
			if(c[i/5][i%5]==ch) {
				this.i1=i/5;
				this.i2=i%5;
			}
		}
	}

	/*
	 * M�thode permettant d'effacer les caract�res neutres
	 * utilis�s pour s�parer les lettres identiques
	 * apr�s d�cryptage
	 */
	private IMessage desadapter(IMessage m) {
		char[] nouveau=new char[m.taille()];
		int r=0;
		for(int i=0;i<m.taille();i++) {
			if(m.getChar(i)!=CHAR_NEUTRE) {
				nouveau[r]=m.getChar(i);
				r++;
			}
		}
		char[] c=new char[r];
		for(int i=0;i<r;i++) {
			c[i]=nouveau[i];
		}
		return Fabrique.fabriquerMessage(c);
	}

	/*
	 * M�thode permettant d'adapter le format du message,
	 * c'est � dire de remplacer les caract�res neutres par des caract�res de remplacement
	 * puis d'inserer des caract�res neutres entre les lettres identique qui se suivent,
	 * puis eventuellement un caract�re neutre si le message contient un nombre de lettres impair
	 */
	private IMessage adapter(IMessage m) {
		char[] nouveau=new char[m.taille()*2];
		int r=0;
		boolean alt=false;
		for(int i=0;i<m.taille();i++) {
			if((m.getChar(i) >=97 && m.getChar(i)<=122) || IGNORE.contains(m.getChar(i))) {
				if(r==0 || m.getChar(i)!=nouveau[r-1] || !alt) {
					if(m.getChar(i)!=CHAR_INEXISTANT) {
						nouveau[r]=m.getChar(i);
					}
					else {
						if(nouveau[r-1]==CHAR_REMPLACEMENT && alt) {
							nouveau[r]=CHAR_NEUTRE;
							if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
						}
						nouveau[r]=CHAR_REMPLACEMENT;
					}
					if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
				}
				else {
					nouveau[r]=CHAR_NEUTRE;
					if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
					if(m.getChar(i)!=CHAR_INEXISTANT) {
						nouveau[r]=m.getChar(i);
					}
					else {
						nouveau[r]=CHAR_REMPLACEMENT;
					}
					if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
				}
			}
			else {
				if(m.getChar(i) >=65 && m.getChar(i)<=90) {
					if(r==0 || (char)(m.getChar(i)+32)!=nouveau[r-1] || !alt) {
						if((char)(m.getChar(i)+32)!=CHAR_INEXISTANT) {
							nouveau[r]=(char)(m.getChar(i)+32);
						}
						else {
							if(nouveau[r-1]==CHAR_REMPLACEMENT && alt) {
								nouveau[r]=CHAR_NEUTRE;
								if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
							}
							nouveau[r]=CHAR_REMPLACEMENT;
						}
						if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
					}
					else {
						nouveau[r]=CHAR_NEUTRE;
						if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
						if((char)(m.getChar(i)+32)!=CHAR_INEXISTANT) {
							nouveau[r]=(char)(m.getChar(i)+32);
						}
						else {
							nouveau[r]=CHAR_REMPLACEMENT;
						}
						if(!IGNORE.contains(nouveau[r])){alt=!alt;}r++;
					}
				}
			}
		}
		if(alt) {
			nouveau[r]=CHAR_NEUTRE;
		}
		char[] aRetourner=new char[r+1];
		for(int i=0;i<=r;i++) {
			aRetourner[i]=nouveau[i];
		}
		return Fabrique.fabriquerMessage(aRetourner);
	}

	/*
	 * M�thode permettant d'adapter la cl� en ne conservant que les lettres et en les convertissant en minuscule
	 * si necessaire
	 */
	private String adapterCle(String k) {
		String res="";
		for(int i=0;i<k.length();i++) {
			if(ALPHABET.contains(""+k.charAt(i))) {
				res+=k.charAt(i);
			}
			if(ALPHABET.toUpperCase().contains(""+k.charAt(i))) {
				res+=k.toLowerCase().charAt(i);
			}
		}
		return res;
	}


	/*
	 * M�thode permettant de lire la cl�, c'est � dire
	 * de remplir le tableau servant � coder en Playfair
	 */
	private char[][] remplirTabChar(String k) {
		char[][] c= new char[5][5];
		String key=this.adapterCle(k).toString();
		ArrayList<Character> a=new ArrayList<Character>();
		int r=0;
		a.add(CHAR_INEXISTANT);
		a.add(' ');
		for(int i=0;i<key.length()-1;i++) {
			if(!a.contains(key.charAt(i))) {
				a.add(key.charAt(i));
				c[r/5][r%5]=key.charAt(i);
				r++;
			}
		}
		for(int j=0;j<ALPHABET.length();j++) {
			if(!a.contains(ALPHABET.charAt(j))) {
				a.add(ALPHABET.charAt(j));
				c[r/5][r%5]=ALPHABET.charAt(j);
				r++;
			}
		}
		return c;
	}

	/**
	 * M�thode permettant de d�crypter un message cod� � l'aide du chiffre de Playfair
	 * @param crypte le message � decrypter
	 * @param key la cl� de cryptage associ�e
	 * @return le message d�crypt�
	 */
	public IMessage decrypter(IMessage crypte, String key) {
		/*
		 * La m�thode lit d'abord la cl�, puis
		 * parcourt le message afin de transmettre les caract�res
		 * correspondant � des lettres deux � deux �
		 * la m�thode decoderChar tout en recopiant les caracteres
		 * contenus dans IGNORE, puis desadapte le message
		 */
		try {
			long d=new Date().getTime();
			char[][] c=this.remplirTabChar(key);
			char c1=' '; int r1=0;
			char c2=' '; int r2=0;
			boolean act=true;
			char[] nouveau=new char[crypte.taille()];
			for(int i=0;i<crypte.taille();i=i+2) {
				if(i!=crypte.taille()-1) {
					r1=0;
					r2=0;
					act=true;
					if(IGNORE.contains(crypte.getChar(i))) {
						if(!IGNORE.contains(crypte.getChar(i+1))) {
							if(!IGNORE.contains(crypte.getChar(i+2))) {
								c1=crypte.getChar(i+1);
								c2=crypte.getChar(i+2);
								r1=1;
								r2=1;
								nouveau[i]=crypte.getChar(i);
							}
							else {
								act=false;
								nouveau[i]=crypte.getChar(i);
								i--;
							}
						}
						else {
							nouveau[i]=crypte.getChar(i);
							nouveau[i+1]=crypte.getChar(i+1);
							act=false;
						}
					}
					else {
						if(IGNORE.contains(crypte.getChar(i+1))) {
							c1=crypte.getChar(i);
							c2=crypte.getChar(i+2);
							r1=0;
							r2=1;
							nouveau[i+1]=crypte.getChar(i+1);
						}
						else {
							c1=crypte.getChar(i);
							c2=crypte.getChar(i+1);
						}
					}
					if(act) {
						this.decoderChar(c1, c2,c);
						nouveau[i+r1]=this.t1;
						nouveau[i+1+r2]=this.t2;
						if(r1==1 || r2==1) {
							i++;
						}

					}
				}
				else {
					if(IGNORE.contains(crypte.getChar(i))) {
						nouveau[i]=crypte.getChar(i);
					}
				}
			}
			this.time=new Date().getTime()-d;
			return this.desadapter(Fabrique.fabriquerMessage(nouveau));
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Ce message n'a pas �t� cod� � l'aide de ce type de code", "Erreur", JOptionPane.ERROR_MESSAGE);
			return Fabrique.fabriquerMessage("Erreur");
		}
	}

	/**
	 * M�thode permettant de g�nerer une clef valide pour Playfair, correspondant � un alphabet
	 * complet en d�sordre
	 * @return une cl� valide pour Playfair
	 */
	public String genererCle() {
		ArrayList<Character> dejaMis=new ArrayList<Character>();
		String res="";
		boolean ajoute;
		int rand;
		char t;
		for(int i=0;i<ALPHABET.length();i++) {
			ajoute=false;
			while(!ajoute) {
				rand=(int)(Math.random()*26);
				t=(char)(97+rand);
				if(!dejaMis.contains(t)) {
					res+=t;
					dejaMis.add(t);
					ajoute=true;
				}
			}
		}
		return res;
	}

	/**
	 * M�thode permettant de v�rifier si une cl� est correcte pour playfair,
	 * est consid�r�e comme correcte pour playfair toute cl� ne contenant que des l
	 * lettres et des espaces
	 * @param k la cl� dont on v�rifie la conformit�
	 * @return true si la cl� est valable pour Playfair, false sinon
	 */
	public boolean estCleValable(String k) {
		int i=0;
		boolean trouve=false;
		while(i<k.length() && !trouve) {
			if(!ALPHABET.contains(""+k.charAt(i))&&!ALPHABET.toUpperCase().contains(""+k.charAt(i))&&(k.charAt(i)!=' ')) {
				trouve=true;
				JOptionPane.showMessageDialog(null, "Veuillez saisir une cl� constitu�e uniquement d'une suite de lettres majuscules et minuscule non accentu�es","Cle incorrecte",JOptionPane.ERROR_MESSAGE);
			}
			i++;
		}
		return !trouve;
	}

	/**
	 * M�thode main de test de la classe
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Playfair().crypter(Fabrique.fabriquerMessage("tu n'auras"), "lsmqyfvhgudktcnaxipjwoebrz"));
		System.out.println(new Playfair().crypter(new Message("pioud"), "anne"));
		System.out.println(new Playfair().crypter(Fabrique.fabriquerMessage("chiffre de playfair1"), "bydgzjsfuplarkxcoiveqnmht"));
		System.out.println(new Playfair().decrypter(new Message("vqmrrii zt xardsrokl "), "bydgzjsfuplarkxcoiveqnmht"));
		String key="azertyuiopqsdfghjklmxcvbn";//new Playfair().genererCle();
		IMessage clair=Fabrique.fabriquerMessage("Ceci est un message secret");
		IMessage crypte =new Playfair().crypter(clair, key);
		clair=new Playfair().decrypter(crypte, key);
		System.out.println(crypte);
		System.out.println(clair);
	}
}
