package diabalik;

import game.ExtractException;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import utils.MutableListModel;

/**
 * Make the link between an Historique and a JList by beeing
 * both an Observer of Historique AND a ListModel for JList.
 * @author alain
 *
 */
public class HistoriqueAdapter extends AbstractListModel
implements Observer, MutableListModel
{
    DecimalFormat formater = new DecimalFormat("###");
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
		//return new String(formater.format(index) + " " + state.getLastMove().toString());
		return state.getLastMove().toString();
	}
	/**
	 * @see AbstractListModel
	 */
	public int getSize()
	{
		return histo.hist.size();
	}

    public boolean isCellEditable(int index)
    {
        return true;
    }

    public void setValueAt(Object value, int index)
    {
        if (value instanceof String) {
            String valueStr = (String) value;
            Mouvement move;
            try {
                move = Jeu.zeMoveFactory.extractFrom(valueStr);
                histo.alterMove(index, move);
                System.out.println("HistoriqueAdapter : changed value at " + index);
            } catch (ExtractException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
            System.err.println("HistoriqueAdapter : how to edit a "+ value.getClass().getName());
        }
    }

}
