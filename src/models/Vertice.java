/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author humberto e douglas
 */
public class Vertice {
    private int vertice;
    
    public Vertice( int v ){
        this.vertice = v;
    }

    //para interface funcionar corretamente
    @Override
    public String toString() {
        return "Vértice " + this.vertice;
    }

    //para funcionamento correto da lista de adjacência
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertice vertice = (Vertice) obj;
        return this.vertice == vertice.vertice;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(vertice);
    }


    public int getId() {
        return vertice;
    }

    public void setVertice(int vertice) {
        this.vertice = vertice;
    }
    
}
