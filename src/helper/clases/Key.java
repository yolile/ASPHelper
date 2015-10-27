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
		this.noTerminal = terminal;
		this.terminal = noTerminal;

	}

	@Override
	public int hashCode() {

		return terminal.hashCode() + noTerminal.hashCode();
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
		if (terminal != other.terminal)
			return false;
		if (noTerminal != other.noTerminal)
			return false;
		return true;

	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}
