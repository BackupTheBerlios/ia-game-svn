/*
 * Created on May 11, 2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import java.awt.Component;

import javax.swing.CellEditor;
import javax.swing.JList;

//@author Santhosh Kumar T - santhosh@in.fiorano.com 
public interface ListCellEditor extends CellEditor{ 
    Component getListCellEditorComponent(JList list, Object value, 
                                          boolean isSelected, 
                                          int index); 
} 
