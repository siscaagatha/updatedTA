package org.cis.optur.engine.commons;

public class Mp {
    String shift; int [] need; int id;

    public Mp(String shift, int [] need, String id){
        this.shift = shift;
        this.need = need;
        this.id = Integer.parseInt(id);
    }

    public String getShift() { return shift; }

    public int getNeed(int index) { return need[index]; }

    public int getId(){
        return id;
    }
}
