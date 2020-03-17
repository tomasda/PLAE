package es.apt.ae.facade.dto;

import java.util.Comparator;

public class UsuariosComparer implements Comparator<UsuarioItem> {
	  
	@Override
	public int compare(UsuarioItem usuario1, UsuarioItem usuario2) {
		return 0;
		// int startComparison = compare(x.timeStarted, y.timeStarted);
		// return startComparison != 0 ? startComparison
		// : compare(x.timeEnded, y.timeEnded);
	}

	// I don't know why this isn't in Long...
//	private static int compare(long a, long b) {
//		return a < b ? -1 : a > b ? 1 : 0;
//	}
}

