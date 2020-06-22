package org.cis.optur.compile.repository;

import org.cis.optur.lib.Repository;
import org.cis.optur.compile.model.Employee;

public class EmployeeRepository extends Repository<Employee> {
    public Employee getById(Integer id){
        for (Employee employee : super.getAll()){
            if (employee.getId()==id)
                return employee;
        }
        throw new Error("Not found employee with id " + id);
    }
    public Employee getByIndex(int index){
        return super.getAtIndex(index);
    }
}
