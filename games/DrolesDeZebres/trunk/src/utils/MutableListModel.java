/*
 * Created on May 11, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import javax.swing.ListModel;

//@author Santhosh Kumar T - santhosh@in.fiorano.com 
public interface MutableListModel extends ListModel{ 
    public boolean isCellEditable(int index); 
    public void setValueAt(Object value, int index); 
} 
