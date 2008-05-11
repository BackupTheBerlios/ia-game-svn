package game.gui;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

import diabalik.EtatJeu;

public class JGameState extends JTextArea
implements Observer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EtatJeu model;

	public JGameState( EtatJeu model )
	{
		super();	
        
        this.model = model;
        
        build();
        model.addObserver(this);
	}
	
	void build()
	{
		super.setFont(new Font("Monospaced", Font.PLAIN, 12));
	}
	
	public void update(Observable o, Object arg)
	{
		this.setText( model.toString());
	}
	
	

}
