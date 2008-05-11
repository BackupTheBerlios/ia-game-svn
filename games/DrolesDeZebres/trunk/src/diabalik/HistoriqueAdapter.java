package diabalik;

import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

/**
 * Make the link between an Historique and a JList by beeing
 * both an Observer of Historique AND a ListModel for JList.
 * @author alain
 *
 */
public class HistoriqueAdapter extends AbstractListModel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Historique histo;
	
	public HistoriqueAdapter(Historique histo) 
	{
		this.histo = histo;
		histo.addObserver(this);
	}

	public void update(Observable o, Object arg)
	{	
		if (arg instanceof Historique.UpdateStatus) {
			Historique.UpdateStatus status = (Historique.UpdateStatus)arg;
			switch (status.kind) {
			case ALTER:
				fireContentsChanged(this, status.start, status.end);
				break;
			case ADD:
				fireIntervalAdded(this, status.start, status.end);
				
			default:
				break;
			}
		}
		
		// called AFTER modification
		// endpoint included
		// fireContentsChanged( this, int index0, int index1);
		// fireIntervalAdded( this, int index0, int index1);
		// fireIntervalRemoved( this, int index0, int index1);
	}

	/**
	 * @see AbstractListModel
	 */
	public Object getElementAt(int index)
	{
		EtatJeu state = histo.getState(index);
		return state.getLastMove().toString();
		
	}
	/**
	 * @see AbstractListModel
	 */
	public int getSize()
	{
		return histo.hist.size();
	}

}
