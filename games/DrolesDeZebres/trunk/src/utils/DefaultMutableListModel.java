/*
 * Created on May 11, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import javax.swing.DefaultListModel;

//@author Santhosh Kumar T - santhosh@in.fiorano.com 
public class DefaultMutableListModel extends DefaultListModel implements MutableListModel{ 
    public boolean isCellEditable(int index){ 
        return true; 
    } 
 
    public void setValueAt(Object value, int index){ 
        super.setElementAt(value, index); 
    } 
} 
