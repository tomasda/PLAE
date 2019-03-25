package com.opencanarias.consola.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.opencanarias.consola.commons.LoadProperties;


public class MenuDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<MenuBean> li;

	public MenuDao() throws Exception {
		LoadProperties lp = new LoadProperties();
		if (null == this.li) {
			li = new ArrayList<MenuBean>();
			int index_0 = 0;
			while (lp.getParameter(MenuSessionOption.fichero, Integer.toString(index_0)) != null) {
				li.add(new MenuBean(Integer.toString(index_0),
						lp.getParameter(MenuSessionOption.fichero, Integer.toString(index_0))));
				index_0++;
			}
		}
	}

	public List<MenuBean> getLista() {
		return li;
	}
}