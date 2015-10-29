/**
 * 
 */
package src.helper.clases;

/**
 * @author Yohanna Lisnichuk
 *
 */
public class Key {

	private String terminal;
	private String noTerminal;

	public Key(String noTerminal, String terminal) {
		this.noTerminal = noTerminal;
		this.terminal = terminal;

	}

	@Override
	public int hashCode() {
		int aRet = 1;
		aRet = 31 + terminal.hashCode();
		aRet = aRet * 31 + noTerminal.hashCode();
		return aRet;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (!terminal.equals(other.terminal))
			return false;
		if (!noTerminal.equals(other.noTerminal))
			return false;
		return true;

	}

	@Override
	public String toString() {
		return "M[" + this.noTerminal + "," + this.terminal + "]";
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}
