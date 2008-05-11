package game;

public interface Player {

	/**
	 * @return true si tous les membres sont les m�mes
	 */
	public abstract boolean equals(Object obj);

	/**
	 * @return true s'ils ont la m�me couleur.
	 */
	public abstract boolean sameColor(Player jou);

	public abstract String toString();

}