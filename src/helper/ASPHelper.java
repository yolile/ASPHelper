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
import java.util.List;

/**
 *
 * @author Yohanna Lisnichuk
 * @since 1.0
 * @version 1.0 25/10/2015
 *
 */
public class ASPHelper {

    private List<Produccion> producciones;
    private List<String> conjuntoPrimero;

    public ASPHelper(List<Produccion> producciones) {

        super();
        this.producciones = producciones;
        this.conjuntoPrimero = new ArrayList<String>();
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
     * Metodo para determinar, dada una varible X, y una lista de producciones
     * definida en el constructor, si X puede derivar a vacio
     *
     * @param variable
     * @return
     */
    private boolean derivaAvacio(String variable) {

        for (Produccion prod : getProducciones()) {
            if (prod.getIzquierda().equals(variable)
                    && prod.getDerecha().equals("vacio")) {
                return true;

            }

        }
        return false;

    }

    /**
     * Metodo para obtener el conjunto primero de una variable dada
     *
     * @param variable
     * @return
     */
    public List<String> getPrimero(String variable) {

        List<String> aRetornar = primero(variable);

        // Luego de calcular el conjunto primero, agregamos vacio si es que
        // varible deriva en vacio
        for (Produccion prod : getProducciones()) {
            if (prod.getIzquierda().equals(variable)
                    && prod.getDerecha().equals("vacio")) {
                aRetornar.add("vacio");
            }
        }

        return aRetornar;
    }

    private List<String> primero(String variable) {

        // Si es un terminal se agrega al conjunto primero
        if (esTerminal(variable)) {
            getConjuntoPrimero().add(variable);
            return getConjuntoPrimero();
        }

        for (Produccion prod : getProducciones()) {
            if (prod.getIzquierda().equals(variable)) {
                if (!prod.getDerecha().equals("vacio")) {

                    // la primera variable del lado derecho siempre estara en el
                    // conjunto primero
                    primero(String.valueOf(prod.getDerecha().charAt(0)));
                    for (int i = 0; i < prod.getDerecha().length() - 1; i++) {
                        // si la primera variable deriva en vacio, entonces hay
                        // que agregar el conjunto primero de la siguiente, y
                        // asi sucesivamente
                        if (derivaAvacio(String.valueOf(prod.getDerecha()
                                .charAt(i)))) {
                            primero(String.valueOf(prod.getDerecha().charAt(
                                    i + 1)));

                        }
                    }
                }
            }
        }

        return getConjuntoPrimero();

    }

    /**
     * Metodo que dado un String devuelve su respectiva {@link Produccion}
     *
     * @param variable
     * @param producciones
     * @return
     */
    public static Produccion getProduccion(String variable,
            List<Produccion> producciones) {

        for (Produccion prod : producciones) {
            if (prod.getIzquierda().equals(variable)) {
                return prod;
            }
        }
        return null;
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
