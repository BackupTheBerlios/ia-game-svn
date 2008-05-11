package game.gui;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import diabalik.Historique;
import diabalik.HistoriqueAdapter;

public class JGameHistory extends JScrollPane 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Historique histo;
	JList histoList;

	public JGameHistory(Historique histo )
	{
		super();

		this.histo = histo;

		build();
	}

	void build()
	{
		histoList = new JList( new HistoriqueAdapter(histo));
		histoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		histoList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		histoList.setVisibleRowCount(-1);
		
		this.getViewport().setView( histoList );
		this.setPreferredSize(new Dimension(100, -1));
	}
}
