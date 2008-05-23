package game.gui;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import utils.DefaultListCellEditor;
import utils.JListMutable;
import diabalik.Historique;
import diabalik.HistoriqueAdapter;

public class JGameHistory extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Historique histo;
	public JListMutable histoList;
	JScrollPane histScroll;

	public JGameHistory(Historique histo )
	{
		super(new BorderLayout());

		this.histo = histo;

		build();
	}

	void build()
	{
		histoList = new JListMutable( new HistoriqueAdapter(histo));
        histoList.setListCellEditor( new DefaultListCellEditor(new JTextField()));
		histoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		histoList.setLayoutOrientation(JList.VERTICAL);
		histoList.setVisibleRowCount(20);
		
		histScroll = new JScrollPane(histoList);
		add(histScroll, BorderLayout.CENTER);
		//getViewport().setView( histoList );
		//setPreferredSize(new Dimension(100, -1));
	}
}
