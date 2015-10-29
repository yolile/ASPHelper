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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import src.helper.clases.Key;
import src.helper.clases.Produccion;

/**
 * 
 * @author Yohanna Lisnichuk
 * @since 1.0
 * @version 1.0 25/10/2015
 * 
 */
public class ASPTable {

    private Map<Key, List<Produccion>> tablaASP;
    private Map<String, Set<String>> conjuntoPrimero;
    private Map<String, Set<String>> conjuntoSiguiente;
    private List<Produccion> producciones;

    public ASPTable(Map<String, Set<String>> conjuntoPrimero,
            Map<String, Set<String>> conjuntoSiguiente,
            List<Produccion> producciones) {

        super();
        this.conjuntoPrimero = conjuntoPrimero;
        this.conjuntoSiguiente = conjuntoSiguiente;
        this.producciones = producciones;
        // Debe ser una lista de producciones ya que si la gramatica es ambigua
        // puede llegar a tener varias producciones para
        // un mismo par terminal-noTerminal
        this.tablaASP = new HashMap<Key, List<Produccion>>();
    }

    public Map<Key, List<Produccion>> generarTablaASP() {

        ASPHelper helper = new ASPHelper(getProducciones());
        boolean tieneVacio = true;

        for (Produccion prod : getProducciones()) {

            // Para cada terminal en PRIMERO(alfa) agregar A->alfa a M[A,a]
            Set<String> primero = helper.getPrimeroProduccion(prod);

            for (String variable : primero) {

                if (helper.esTerminal(variable)
                        && !variable.equals(ASPHelper.VACIO)) {
                    // Se controla que si ya tenia un valor para el actual
                    // par
                    // terminal/noTerminal, entonces se agrega uno mas a la
                    // lista, de lo contrario se crea una lista nueva y se
                    // agrega la produccion actual
                    Key key = new Key(prod.getIzquierda(), variable);
                    if (getTablaASP().get(key) == null) {
                        List<Produccion> prodToAdd = new ArrayList<Produccion>();
                        prodToAdd.add(prod);
                        getTablaASP().put(key, prodToAdd);
                    } else {
                        getTablaASP().get(key).add(prod);
                    }
                }
            }

            // Se controla que el primero de alfa tenga vacio, es decir
            // todos los primeros del lado derecho de A tengan primero
            tieneVacio = true;
            for (String a : prod.getDerecha()) {
                if (!helper.esTerminal(a)
                        && !getConjuntoPrimero().get(a).contains(
                                ASPHelper.VACIO)) {
                    tieneVacio = false;
                    break;
                }
                if (a.equals(ASPHelper.VACIO)) {
                    tieneVacio = true;
                    break;
                }
                if (helper.esTerminal(a)) {
                    tieneVacio = false;
                    break;
                }

            }
            // Si tiene vacio entonces para cada terminal de SIGUIENTE(A)
            // agregar A->alfa a M[A,a]
            if (tieneVacio) {
                for (String siguiente : getConjuntoSiguiente().get(
                        prod.getIzquierda())) {
                    if (helper.esTerminal(siguiente)
                            && !siguiente.equals(ASPHelper.VACIO)) {
                        Key key = new Key(prod.getIzquierda(), siguiente);
                        if (getTablaASP().get(key) == null) {
                            List<Produccion> prodToAdd = new ArrayList<Produccion>();
                            prodToAdd.add(prod);
                            getTablaASP().put(key, prodToAdd);
                        } else {
                            getTablaASP().get(key).add(prod);
                        }
                    }
                }
            }

        }

        return getTablaASP();

    }

    public Map<Key, List<Produccion>> getTablaASP() {

        return tablaASP;
    }

    public void setTablaASP(Map<Key, List<Produccion>> tablaASP) {

        this.tablaASP = tablaASP;
    }

    public Map<String, Set<String>> getConjuntoPrimero() {

        return conjuntoPrimero;
    }

    public void setConjuntoPrimero(Map<String, Set<String>> conjuntoPrimero) {

        this.conjuntoPrimero = conjuntoPrimero;
    }

    public Map<String, Set<String>> getConjuntoSiguiente() {

        return conjuntoSiguiente;
    }

    public void setConjuntoSiguiente(Map<String, Set<String>> conjuntoSiguiente) {

        this.conjuntoSiguiente = conjuntoSiguiente;
    }

    public List<Produccion> getProducciones() {

        return producciones;
    }

    public void setProducciones(List<Produccion> producciones) {

        this.producciones = producciones;
    }

}
