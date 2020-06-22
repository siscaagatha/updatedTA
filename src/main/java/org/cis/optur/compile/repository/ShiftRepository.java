package org.cis.optur.compile.repository;

import org.cis.optur.lib.Repository;
import org.cis.optur.compile.model.Shift;

public class ShiftRepository extends Repository<Shift> {
    public Shift getById(Integer id){
        for (Shift shift : super.getAll()){
            if (shift.getId()==id)
                return shift;
        }
        throw new Error("Not found shift with id " + id);
    }
    public Shift getByName(String name){
        for (Shift shift : super.getAll()){
            if (shift.getName()==name)
                return shift;
        }
        throw new Error("Not found shift with name " + name);
    }
}
