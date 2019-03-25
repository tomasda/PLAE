package com.opencanarias.consola.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opencanarias.consola.commons.LoadProperties;

public class Menu2Dao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Menu2Bean> li;

	public List<Menu2Bean> getList(String raiz) throws Exception {
			LoadProperties lp = new LoadProperties();
			li = new ArrayList<Menu2Bean>();
			int index_0 = 1;
			while (lp.getParameter(MenuSessionOption.fichero, raiz + "." + Integer.toString(index_0)) != null) {
				li.add(new Menu2Bean(Integer.toString(index_0), lp.getParameter(MenuSessionOption.fichero, raiz + "." + Integer.toString(index_0)),
						lp.getParameter(MenuSessionOption.fichero, raiz + "." + Integer.toString(index_0) + ".url")));
				index_0++;
			}
		return li;
	}
}