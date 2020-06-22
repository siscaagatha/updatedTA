package org.cis.optur.compile.model;

import java.util.Objects;

public class Employee {
    private int id;
    private double workWeek;
    private int[] workingWeekends;
    private String competence;

    public Employee() {
    }

    public Employee(int id, double workWeek, int[] workingWeekends, String competence) {
        this.id = id;
        this.workWeek = workWeek;
        this.workingWeekends = workingWeekends;
        this.competence = competence;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
//        return "Employee{" +
//                "id=" + id +
//                ", workWeek=" + workWeek +
//                ", workingWeekends=" + Arrays.toString(workingWeekends) +
//                ", competence='" + competence + '\'' +
//                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWorkWeek() {
        return workWeek;
    }

    public void setWorkWeek(double workWeek) {
        this.workWeek = workWeek;
    }

    public int[] getWorkingWeekends() {
        return workingWeekends;
    }

    public void setWorkingWeekends(int[] workingWeekends) {
        this.workingWeekends = workingWeekends;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
