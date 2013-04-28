package message;


/**
 * Classe modelisant un objet de type Message
 * @see message.IMessage
 * @author Lucas VANRYB
 *
 */
public class Message implements IMessage {


	/*
	 * ATTRIBUTS
	 */

	char[] message;

	/*
	 * CONSTRUCTEUR
	 */

	public Message(String mess) {
		this.message=new char[mess.length()];
		char[]temp=mess.toCharArray();
		this.message=this.adapter(temp);

	}

	public Message(char[] c) {
		this.message=this.adapter(c);
	}

	public Message(Character[] c) {
		char[] ch=new char[c.length];
		for(int i=0;i<c.length;i++) {
			ch[i]=c[i];
		}
		this.message=this.adapter(ch);
	}

	public Message(IMessage m) {
		this.message=this.adapter(m.getChar());
	}

	/*
	 * ACCESSEURS
	 */

	public char[] getChar() {
		return this.message;
	}

	public char getChar(int i) {
		return this.getChar()[i];
	}

	public int taille() {
		return this.message.length;
	}

	/*
	 * SERVICES
	 */

	/*
	 * Permet, en cas de besoin, d'adapter tous les messages ˆ un certain format
	 */
	private char[] adapter(char[] c) {
		char[] ch=new char[c.length];
		for(int i=0; i<c.length;i++) {
			ch[i]=this.adapterChar(c[i]);
		}
		return ch;
	}

	/*
	 * Permet, en cas de besoin, d'adapter un caractre afin d'adapter le format d'un message
	 */
	private char adapterChar(char c) {
		return c;
	}


	public void afficherMessage() {
		String aAfficher="";
		for (int i=0;i<this.taille();i++) {
			aAfficher+=this.getChar(i);
		}
		System.out.println(aAfficher);

	}

	public boolean equals(Object o) {
		if(o instanceof IMessage && o!=null) {
			IMessage c=(IMessage)o;
			if(c.taille()==this.taille()) {
				boolean trouve=false;
				int i=0;
				while(i<this.taille() && !trouve) {
					if(c.getChar(i)!=this.getChar(i)) {
						trouve=true;
					}
					i++;
				}
				return !trouve;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public String toString() {
		String aAfficher="";
		for (int i=0;i<this.taille();i++) {
			aAfficher+=this.getChar(i);
		}
		return aAfficher;
	}

}
