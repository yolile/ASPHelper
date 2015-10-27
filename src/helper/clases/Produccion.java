/*-
 * Copyright (c)
 *
 * 2015 - Yohanna Lisnichuk
 * 2015 - Victor Ughelli
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package src.helper.clases;

import java.util.List;

/**
 * Clase que representa a una produccion de la forma A:BC donde A es izquierda y
 * BC derecha
 *
 * @author Yohanna Lisnichuk
 * @since 1.0
 * @version 1.0 25/10/2015
 *
 */
public class Produccion {

	public Produccion(String izquierda, List<String> derecha) {

		super();
		this.izquierda = izquierda;
		this.derecha = derecha;
	}

	private String izquierda;

	@Override
	public String toString() {

		return izquierda + "->" + derecha;
	}

	private List<String> derecha;

	public String getIzquierda() {

		return izquierda;
	}

	public void setIzquierda(String izquierda) {

		this.izquierda = izquierda;
	}

	public List<String> getDerecha() {

		return derecha;
	}

	public void setDerecha(List<String> derecha) {

		this.derecha = derecha;
	}

}
