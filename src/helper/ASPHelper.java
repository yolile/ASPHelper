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

package src.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.helper.clases.Produccion;

/**
 *
 * @author Yohanna Lisnichuk
 * @since 1.0
 * @version 1.0 25/10/2015
 *
 */
public class ASPHelper {

	public static final String VACIO = "vacio";
	private List<Produccion> producciones;
	private List<String> conjuntoPrimero;

	public ASPHelper(List<Produccion> producciones) {

		super();
		this.producciones = producciones;
		this.conjuntoPrimero = new ArrayList<String>();
	}

	public Set<String> getTerminales() {

		Set<String> aRet = new HashSet<String>();
		for (Produccion prod : getProducciones()) {
			for (String variable : prod.getDerecha()) {
				if (esTerminal(variable)) {
					aRet.add(variable);
				}
			}
		}
		return aRet;
	}

	public Set<String> getNoTerminales() {

		Set<String> aRet = new HashSet<String>();
		for (Produccion prod : getProducciones()) {
			if (!esTerminal(prod.getIzquierda())) {
				aRet.add(prod.getIzquierda());
			}
		}
		return aRet;
	}

	/**
	 * Metodo para determinar si una variable dada es o no un terminal, segun la
	 * lista de producciones seteada en el constructor de la clase
	 *
	 * @param variable
	 * @return
	 */
	private boolean esTerminal(String variable) {

		for (Produccion prod : getProducciones()) {
			if (prod.getIzquierda().contains(variable)) {
				return false;
			}

		}
		return true;

	}

	/**
	 * Metodo para obtener el conjunto primero de una variable dada
	 *
	 * @param variable
	 * @return
	 */
	public Set<String> getPrimero(String original, String variable) {

		// Se ponen todas las producciones como no procesadas
		for (Produccion prod : getProducciones()) {
			prod.setProcesado(false);
		}
		// Creamos un hashSet para quitar duplicados
		Set<String> temp = new HashSet<String>();
		List<String> aRetornar = primero(original, variable);
		int contadorVacio = 0;
		int contadorNoVacio = 0;
		// Si existe la misma cantidad de variables que de vacio, significa que
		// cada una de ellas aporto vacio, por lo tanto hay que agregar vacio al
		// conjunto primero una vez
		for (String term : aRetornar) {
			if (term.equals(VACIO)) {
				contadorVacio++;
			} else {
				contadorNoVacio++;
			}
		}
		temp.addAll(aRetornar);
		// Si hay diferentes cantidades de vacio y de variables, significa que
		// no todas aportaron vacio y vacio debe eliminarse
		if (contadorVacio != contadorNoVacio) {

			temp.remove(VACIO);

		}

		return temp;
	}

	private List<String> primero(String original, String variable) {

		// Si es un terminal se agrega al conjunto primero
		if (esTerminal(variable)) {
			getConjuntoPrimero().add(variable);

		}

		for (Produccion prod : getProducciones()) {
			// se pregunta si ya no se proceso para evitar duplicados
			if (prod.getIzquierda().equals(variable) && !prod.isProcesado()) {

				// la primera variable del lado derecho siempre estara en el
				// conjunto primero
				prod.setProcesado(true);
				List<String> temp = primero(prod.getIzquierda(), prod
						.getDerecha().get(0));

				for (int i = 0; i < prod.getDerecha().size() - 1; i++) {
					// si la primera variable deriva en vacio, entonces hay
					// que agregar el conjunto primero de la siguiente, y
					// asi sucesivamente
					if (temp.contains(VACIO)) {
						primero(original, prod.getDerecha().get(i + 1));

					} else {
						break;
					}
				}
			}
		}

		return getConjuntoPrimero();

	}

	public List<Produccion> getProducciones() {

		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {

		this.producciones = producciones;
	}

	public List<String> getConjuntoPrimero() {

		return conjuntoPrimero;
	}

	public void setConjuntoPrimero(List<String> conjuntoPrimero) {

		this.conjuntoPrimero = conjuntoPrimero;
	}

}
