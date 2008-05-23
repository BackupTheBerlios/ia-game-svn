package diabalik;

public class HeuristicState {
	private static int maxVal = 20;
	
	public HeuristicState(Jeu game)
	{
	}
	
	/**
	 * Evaluate a EtatJeu for a Joueur
	 * @param state
	 * @param player
	 * @return
	 */
	public int getValue( EtatJeu state, Joueur player)
	{
		if (state.getWinner() == player.couleur ) {
			return maxVal;
		}
		else if( state.getWinner() == Joueur.otherColor(player.couleur)) {
			return -maxVal;
		}
		return 0;
	}
	/**
	 * Is it the maximum value (ie, is this a Win ?)
	 * @param val
	 * @return
	 */
	public boolean isMaxValue(int val)
	{
		return ( val >= maxVal );
	}
}
