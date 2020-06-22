package org.cis.optur.engine.commons;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class Utils {
    public static IterationListener iterationListener;
    public static FeasibilityListener feasibilityListener;
    public static ExceptionListener exceptionListener;
    public static Ee[] employees;
    public static St[] shifts;
    public static Mp[] plans;
    public static Cn hardConstraint;
    public static Cn softConstraint;
    public static String[][] wanted;
    public static String[][] unwanted;
    public static Pn[] want;
    public static Pn[] unwant;
    public static Row row;
    public static int [][] forbiddenWeekdayPair;
    public static int [][] forbiddenWeekendPair;
    public static int [][] week;
    public static int [] manpowerPlan;
    public static double [][] hourLimit;
    public static int opturNumber;
    public static int file;

    public static String xlsFilePath;

    public static void initSC(File incommingFile) throws IOException, InvalidFormatException, ParseException {
        File fileOptur = incommingFile;
        xlsFilePath = fileOptur.getAbsolutePath();
        file = getOpturNumberFromFileName(fileOptur.getName());

        String[][] employee = employees().clone();
        String[][] shift = shifts().clone();
        String[][] manpower = manpower().clone();
        String[] hc = hardconstraint().clone();
        String[] sc = softconstraint().clone();
        wanted = wantedpattern().clone();
        unwanted = unwantedpattern().clone();

        if (file == 7)
            opturNumber = 2;
        if (file == 3)
            opturNumber = 1;
        if (file != 7 && file != 3)
            opturNumber = 1;

        int [] minggu = {12, 6, 6, 24, 4, 12, 6};
        manpowerPlan = minggu.clone();

        String[][] weekend = new String[employee.length][];
        for (int i = 0; i < employee.length; i++)
            weekend[i] = employee[i][2].split(", |,");
        int kanan = 0;
        for (int i = 0; i < weekend.length; i++) {
            if (kanan < weekend[i].length)
                kanan = weekend[i].length;
        }
        week = new int[weekend.length][kanan];
        for (int i = 0; i < weekend.length; i++){
            for (int j = 0; j < weekend[i].length; j++){
                week[i][j] = Integer.parseInt(weekend[i][j]);
            }
        }
        employees = new Ee[employee.length];
        for (int i = 0; i < employee.length; i++){
            employees[i]= new Ee(employee[i][0], employee[i][1], week[i], employee[i][3]);
        }

        DateFormat time = new SimpleDateFormat("hh:mm");
        LocalTime[][] times = new LocalTime[shift.length][2];
        for (int i = 0; i < shift.length; i++) {
            String first = shift[i][10]+":"+shift[i][11];
            String last = shift[i][12]+":"+shift[i][13];
            Time start = new Time(time.parse(first).getTime());
            Time finish = new Time(time.parse(last).getTime());
            LocalTime startx = start.toLocalTime();
            LocalTime finishx = finish.toLocalTime();
            times[i][0] = startx;
            times[i][1] = finishx;
        }

        double [][] duration = new double[shift.length][7];
        for (int i = 0; i < duration.length; i++) {
            for (int j = 0; j < duration[i].length; j++) {
                duration[i][j] = Double.parseDouble(shift[i][j+1]);
            }
        }

        shifts = new St[shift.length];
        for (int i = 0; i < shift.length; i++){
            shifts[i]= new St(shift[i][0], duration[i], shift[i][8], shift[i][9], times[i][0], times[i][1],
                    shift[i][14]);
        }

        int [][] need = new int[manpower.length][7];
        for (int i = 0; i < need.length; i++) {
            for (int j = 0; j < need[i].length; j++) {
                need[i][j] = Integer.parseInt(manpower[i][j+1]);
            }
        }
        plans = new Mp[manpower.length];
        for (int i = 0; i < manpower.length; i++){
            plans[i]= new Mp(manpower[i][0], need[i], manpower[i][8]);
        }

        hardConstraint = new Cn(hc);
        hardConstraint.setHc1(); hardConstraint.setHc2(); hardConstraint.setHc3(); hardConstraint.setHc5a(); hardConstraint.setHc5b();
        hardConstraint.setHc5c(); hardConstraint.setHc5d(); hardConstraint.setHc5e(); hardConstraint.setHc5f(); hardConstraint.setHc6(); hardConstraint.setHc7();
        softConstraint = new Cn(sc);
        softConstraint.setSc1a(); softConstraint.setSc1b(); softConstraint.setSc1c(); softConstraint.setSc2(); softConstraint.setSc3a(); softConstraint.setSc3b(); softConstraint.setSc3c();
        softConstraint.setSc4(); softConstraint.setSc5aMax(); softConstraint.setSc5aMin(); softConstraint.setSc5bMax(); softConstraint.setSc5bMin(); softConstraint.setSc5cMax();
        softConstraint.setSc5cMin(); softConstraint.setSc6(); softConstraint.setSc7(); softConstraint.setSc8(); softConstraint.setSc9();

        if (softConstraint.getSc8() != 0) {
            int [] startWanted = new int[softConstraint.getSc8()];
            int index = 0;
            for (int i = 0; i < wanted.length; i++) {
                if (wanted[i][1].equals("Monday")) {
                    startWanted[index] = 0;
                    index++;
                } if (wanted[i][1].equals("Tuesday")) {
                    startWanted[index] = 1;
                    index++;
                } if (wanted[i][1].equals("Wednesday")) {
                    startWanted[index] = 2;
                    index++;
                } if (wanted[i][1].equals("Thursday")) {
                    startWanted[index] = 3;
                    index++;
                } if (wanted[i][1].equals("Friday")) {
                    startWanted[index] = 4;
                    index++;
                } if (wanted[i][1].equals("Saturday")) {
                    startWanted[index] = 5;
                    index++;
                } if (wanted[i][1].equals("Sunday")) {
                    startWanted[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[softConstraint.getSc8()][];
            index = 0;
            for (int i = 0; i < wanted.length; i++) {
                pattern[index] = wanted[i][2].split(", |,");
                index++;
            }
            want = new Pn[softConstraint.getSc8()];
            for (int i = 0; i < softConstraint.getSc8(); i++) {
                want[i] = new Pn(startWanted[i], pattern[i]);
            }
        }

        if (softConstraint.getSc9() != 0) {
            int [] startWanted = new int[softConstraint.getSc9()];
            int index = 0;
            for (int i = 0; i < wanted.length; i++) {
                if (wanted[i][1].equals("Monday")) {
                    startWanted[index] = 0;
                    index++;
                } if (wanted[i][1].equals("Tuesday")) {
                    startWanted[index] = 1;
                    index++;
                } if (wanted[i][1].equals("Wednesday")) {
                    startWanted[index] = 2;
                    index++;
                } if (wanted[i][1].equals("Thursday")) {
                    startWanted[index] = 3;
                    index++;
                } if (wanted[i][1].equals("Friday")) {
                    startWanted[index] = 4;
                    index++;
                } if (wanted[i][1].equals("Saturday")) {
                    startWanted[index] = 5;
                    index++;
                } if (wanted[i][1].equals("Sunday")) {
                    startWanted[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[softConstraint.getSc9()][];
            index = 0;
            for (int i = 0; i < unwanted.length; i++) {
                pattern[index] = unwanted[i][2].split(", |,");
                index++;
            }
            unwant = new Pn[softConstraint.getSc9()];
            for (int i = 0; i < softConstraint.getSc9(); i++) {
                unwant[i] = new Pn(startWanted[i], pattern[i]);
            }
        }

        int count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5a().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5c().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1)
                    count++;
            }
        }
        forbiddenWeekdayPair = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5a().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekdayPair[count][0] = i + 1;
                        forbiddenWeekdayPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5c().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekdayPair[count][0] = i + 1;
                        forbiddenWeekdayPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1) {
                    forbiddenWeekdayPair[count][0] = i + 1;
                    forbiddenWeekdayPair[count][1] = j + 1;
                    count++;
                }
            }
        }

        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5b().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5d().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1)
                    count++;
            }
        }
        forbiddenWeekendPair = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5b().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekendPair[count][0] = i + 1;
                        forbiddenWeekendPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5d().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekendPair[count][0] = i + 1;
                        forbiddenWeekendPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1) {
                    forbiddenWeekendPair[count][0] = i + 1;
                    forbiddenWeekendPair[count][1] = j + 1;
                    count++;
                }
            }
        }

        hourLimit = new double[employees.length][3];
        for (int i = 0; i < hourLimit.length; i++) {
            hourLimit[i][0] = employees[i].getHours() - (employees[i].getHours()* hardConstraint.getHc3());
            hourLimit[i][1] = employees[i].getHours();
            hourLimit[i][2] = employees[i].getHours() + (employees[i].getHours()* hardConstraint.getHc3());
        }

    }

    public static InitialSolutionResult getInitialSolutionResult(File incommingFile) throws IOException, InvalidFormatException, ParseException, ClassNotFoundException {
        File fileOptur = incommingFile;
        xlsFilePath = fileOptur.getAbsolutePath();
        file = getOpturNumberFromFileName(fileOptur.getName());

        String[][] employee = employees().clone();
        String[][] shift = shifts().clone();
        String[][] manpower = manpower().clone();
        String[] hc = hardconstraint().clone();
        String[] sc = softconstraint().clone();
        wanted = wantedpattern().clone();
        unwanted = unwantedpattern().clone();

        if (file == 7)
            opturNumber = 2;
        if (file == 3)
            opturNumber = 1;
        if (file != 7 && file != 3)
            opturNumber = 1;

        int [] minggu = {12, 6, 6, 24, 4, 12, 6};
        manpowerPlan = minggu.clone();

        String[][] weekend = new String[employee.length][];
        for (int i = 0; i < employee.length; i++)
            weekend[i] = employee[i][2].split(", |,");
        int kanan = 0;
        for (int i = 0; i < weekend.length; i++) {
            if (kanan < weekend[i].length)
                kanan = weekend[i].length;
        }
        week = new int[weekend.length][kanan];
        for (int i = 0; i < weekend.length; i++){
            for (int j = 0; j < weekend[i].length; j++){
                week[i][j] = Integer.parseInt(weekend[i][j]);
            }
        }
        employees = new Ee[employee.length];
        for (int i = 0; i < employee.length; i++){
            employees[i]= new Ee(employee[i][0], employee[i][1], week[i], employee[i][3]);
        }

        DateFormat time = new SimpleDateFormat("hh:mm");
        LocalTime[][] times = new LocalTime[shift.length][2];
        for (int i = 0; i < shift.length; i++) {
            String first = shift[i][10]+":"+shift[i][11];
            String last = shift[i][12]+":"+shift[i][13];
            Time start = new Time(time.parse(first).getTime());
            Time finish = new Time(time.parse(last).getTime());
            LocalTime startx = start.toLocalTime();
            LocalTime finishx = finish.toLocalTime();
            times[i][0] = startx;
            times[i][1] = finishx;
        }

        double [][] duration = new double[shift.length][7];
        for (int i = 0; i < duration.length; i++) {
            for (int j = 0; j < duration[i].length; j++) {
                duration[i][j] = Double.parseDouble(shift[i][j+1]);
            }
        }

        shifts = new St[shift.length];
        for (int i = 0; i < shift.length; i++){
            shifts[i]= new St(shift[i][0], duration[i], shift[i][8], shift[i][9], times[i][0], times[i][1],
                    shift[i][14]);
        }

        int [][] need = new int[manpower.length][7];
        for (int i = 0; i < need.length; i++) {
            for (int j = 0; j < need[i].length; j++) {
                need[i][j] = Integer.parseInt(manpower[i][j+1]);
            }
        }
        plans = new Mp[manpower.length];
        for (int i = 0; i < manpower.length; i++){
            plans[i]= new Mp(manpower[i][0], need[i], manpower[i][8]);
        }

        hardConstraint = new Cn(hc);
        hardConstraint.setHc1(); hardConstraint.setHc2(); hardConstraint.setHc3(); hardConstraint.setHc5a(); hardConstraint.setHc5b();
        hardConstraint.setHc5c(); hardConstraint.setHc5d(); hardConstraint.setHc5e(); hardConstraint.setHc5f(); hardConstraint.setHc6(); hardConstraint.setHc7();
        softConstraint = new Cn(sc);
        softConstraint.setSc1a(); softConstraint.setSc1b(); softConstraint.setSc1c(); softConstraint.setSc2(); softConstraint.setSc3a(); softConstraint.setSc3b(); softConstraint.setSc3c();
        softConstraint.setSc4(); softConstraint.setSc5aMax(); softConstraint.setSc5aMin(); softConstraint.setSc5bMax(); softConstraint.setSc5bMin(); softConstraint.setSc5cMax();
        softConstraint.setSc5cMin(); softConstraint.setSc6(); softConstraint.setSc7(); softConstraint.setSc8(); softConstraint.setSc9();

        if (softConstraint.getSc8() != 0) {
            int [] startWanted = new int[softConstraint.getSc8()];
            int index = 0;
            for (int i = 0; i < wanted.length; i++) {
                if (wanted[i][1].equals("Monday")) {
                    startWanted[index] = 0;
                    index++;
                } if (wanted[i][1].equals("Tuesday")) {
                    startWanted[index] = 1;
                    index++;
                } if (wanted[i][1].equals("Wednesday")) {
                    startWanted[index] = 2;
                    index++;
                } if (wanted[i][1].equals("Thursday")) {
                    startWanted[index] = 3;
                    index++;
                } if (wanted[i][1].equals("Friday")) {
                    startWanted[index] = 4;
                    index++;
                } if (wanted[i][1].equals("Saturday")) {
                    startWanted[index] = 5;
                    index++;
                } if (wanted[i][1].equals("Sunday")) {
                    startWanted[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[softConstraint.getSc8()][];
            index = 0;
            for (int i = 0; i < wanted.length; i++) {
                pattern[index] = wanted[i][2].split(", |,");
                index++;
            }
            want = new Pn[softConstraint.getSc8()];
            for (int i = 0; i < softConstraint.getSc8(); i++) {
                want[i] = new Pn(startWanted[i], pattern[i]);
            }
        }

        if (softConstraint.getSc9() != 0) {
            int [] startWanted = new int[softConstraint.getSc9()];
            int index = 0;
            for (int i = 0; i < wanted.length; i++) {
                if (wanted[i][1].equals("Monday")) {
                    startWanted[index] = 0;
                    index++;
                } if (wanted[i][1].equals("Tuesday")) {
                    startWanted[index] = 1;
                    index++;
                } if (wanted[i][1].equals("Wednesday")) {
                    startWanted[index] = 2;
                    index++;
                } if (wanted[i][1].equals("Thursday")) {
                    startWanted[index] = 3;
                    index++;
                } if (wanted[i][1].equals("Friday")) {
                    startWanted[index] = 4;
                    index++;
                } if (wanted[i][1].equals("Saturday")) {
                    startWanted[index] = 5;
                    index++;
                } if (wanted[i][1].equals("Sunday")) {
                    startWanted[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[softConstraint.getSc9()][];
            index = 0;
            for (int i = 0; i < unwanted.length; i++) {
                pattern[index] = unwanted[i][2].split(", |,");
                index++;
            }
            unwant = new Pn[softConstraint.getSc9()];
            for (int i = 0; i < softConstraint.getSc9(); i++) {
                unwant[i] = new Pn(startWanted[i], pattern[i]);
            }
        }

        int count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5a().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5c().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1)
                    count++;
            }
        }
        forbiddenWeekdayPair = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5a().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekdayPair[count][0] = i + 1;
                        forbiddenWeekdayPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5c().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekdayPair[count][0] = i + 1;
                        forbiddenWeekdayPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1) {
                    forbiddenWeekdayPair[count][0] = i + 1;
                    forbiddenWeekdayPair[count][1] = j + 1;
                    count++;
                }
            }
        }

        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5b().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5d().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0)
                        count++;
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1)
                    count++;
            }
        }
        forbiddenWeekendPair = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 2)
                    if (hardConstraint.getHc5b().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekendPair[count][0] = i + 1;
                        forbiddenWeekendPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 2 && shifts[j].getCategory() == 1)
                    if (hardConstraint.getHc5d().compareTo(shifts[j].getStart().minusHours(shifts[i].getFinish().getHour()).minusMinutes(shifts[i].getFinish().getMinute())) > 0) {
                        forbiddenWeekendPair[count][0] = i + 1;
                        forbiddenWeekendPair[count][1] = j + 1;
                        count++;
                    }
                if (shifts[i].getCategory() == 3 && shifts[j].getCategory() == 1) {
                    forbiddenWeekendPair[count][0] = i + 1;
                    forbiddenWeekendPair[count][1] = j + 1;
                    count++;
                }
            }
        }

        hourLimit = new double[employees.length][3];
        for (int i = 0; i < hourLimit.length; i++) {
            hourLimit[i][0] = employees[i].getHours() - (employees[i].getHours()* hardConstraint.getHc3());
            hourLimit[i][1] = employees[i].getHours();
            hourLimit[i][2] = employees[i].getHours() + (employees[i].getHours()* hardConstraint.getHc3());
        }

        return getInitialSolutionResult();
    }

    public static int getOpturNumberFromFileName(String name) {
        if(name.indexOf("1")>-1) return 1;
        else if(name.indexOf("2")>-1) return 2;
        else if(name.indexOf("3")>-1) return 3;
        else if(name.indexOf("4")>-1) return 4;
        else if(name.indexOf("5")>-1) return 5;
        else if(name.indexOf("6")>-1) return 6;
        else if(name.indexOf("7")>-1) return 7;
        else return 7;
    }


    public static InitialSolutionResult getInitialSolutionResult() {
        Long startTime = System.currentTimeMillis();
        int [][] matrixsol = new int[employees.length][manpowerPlan[file-1]*7];
        for(int i = 0; i<matrixsol.length; i++)
            for(int j=0; j<matrixsol[i].length;  j++)
                matrixsol[i][j] = 0;

        for (int i = 5; i < manpowerPlan[file-1]*7; i=i+7) {
            while (!validDay(matrixsol, i)) {
                int shift = isMissing(matrixsol, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (checkHC2(matrixsol, i, shift, randomEmp))
                    matrixsol[randomEmp][i] = shift;
            }
        }
        for (int i = 6; i < manpowerPlan[file-1]*7; i=i+7) {
            while (!validDay(matrixsol, i)) {
                int shift = isMissing(matrixsol, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (checkHC2(matrixsol, i, shift, randomEmp))
                    matrixsol[randomEmp][i] = shift;
            }
        }
        for (int i = 0; i < manpowerPlan[file-1]*7; i++) {
            if (i % 7 == 5 || i % 7 == 6)
                continue;
            while (!validDay(matrixsol, i)) {
                int shift = isMissing(matrixsol, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (checkHC2(matrixsol, i, shift, randomEmp))
                    matrixsol[randomEmp][i] = shift;
            }
        }

        int count = countHC6(matrixsol);

        int[][] matrixsolTemp =  new int[employees.length][manpowerPlan[file-1]*7];
        copySolutionMatrix(matrixsol, matrixsolTemp);
        double solutionHour = diffHour(matrixsol);
        for(int  i =0; i<50000; i++)
        {
            int llh = (int) (Math.random()*3);
            if(llh == 0)
                twoExchange(matrixsolTemp);
            if(llh == 1)
                threeExchange(matrixsolTemp);
            if(llh == 2)
                doubleTwoExchange(matrixsolTemp);
            if(isFeasibleHC4(matrixsolTemp))
            {
                if(isFeasibleHC5(matrixsolTemp))
                {
                    if(isFeasibleHC7(matrixsolTemp))
                    {
                        if(diffHour(matrixsolTemp)<=solutionHour)
                        {
                            if (countHC6(matrixsolTemp) <= count) {
                                copySolutionMatrix(matrixsolTemp, matrixsol);
                                count = countHC6(matrixsolTemp);
                                solutionHour = diffHour(matrixsolTemp);
                            }
                            else {
                                copySolutionMatrix(matrixsol, matrixsolTemp);
                            }
                        }
                        else {
                            copySolutionMatrix(matrixsol, matrixsolTemp);
                        }
                    }
                    else {
                        copySolutionMatrix(matrixsol, matrixsolTemp);
                    }
                }
                else {
                    copySolutionMatrix(matrixsol, matrixsolTemp);
                }
            }
            else {
                copySolutionMatrix(matrixsol, matrixsolTemp);
            }
            iterationListener.onIterationEvent(String.valueOf(i+1));
        }

        if(isFeasibleHC2(matrixsol))
        {
            if(isFeasibleHC3(matrixsol))
            {
                if(isFeasibleHC4(matrixsol))
                {
                    if(isFeasibleHC5(matrixsol))
                    {
                        if(isFeasibleHC6(matrixsol))
                        {
                            if(isFeasibleHC7(matrixsol))
                            {
                            }
                            else {
                                int[][] matrixsolTemp2 = new int[employees.length][manpowerPlan[file-1]*7];
                                copySolutionMatrix(matrixsol, matrixsolTemp2);
                                int iterasi = 0;
                                do {
                                    iterationListener.onIterationEvent(String.valueOf(iterasi+1));
                                    iterasi++;
                                    int llh = (int) (Math.random() * 3);
                                    if (llh == 0)
                                        twoExchange(matrixsolTemp2);
                                    if (llh == 1)
                                        threeExchange(matrixsolTemp2);
                                    if (llh == 2)
                                        doubleTwoExchange(matrixsolTemp2);
                                    if (isFeasibleHC3(matrixsolTemp2)) {
                                        if (isFeasibleHC4(matrixsolTemp2)) {
                                            if (isFeasibleHC5(matrixsolTemp2)) {
                                                if (countHC6(matrixsolTemp2) <= count) {
                                                    count = countHC6(matrixsolTemp2);
                                                    copySolutionMatrix(matrixsolTemp2, matrixsol);
                                                } else
                                                    copySolutionMatrix(matrixsol, matrixsolTemp2);
                                            } else
                                                copySolutionMatrix(matrixsol, matrixsolTemp2);
                                        } else
                                            copySolutionMatrix(matrixsol, matrixsolTemp2);
                                    } else
                                        copySolutionMatrix(matrixsol, matrixsolTemp2);
                                } while (!isFeasibleHC6(matrixsol));
                            }
                        }
                        else
                            feasibilityListener.onHCViolation("HC7");

                    }
                    else
                        feasibilityListener.onHCViolation("HC5");
                }
                else
                    feasibilityListener.onHCViolation("HC4");
            }
            else
                feasibilityListener.onHCViolation("HC3");
        }
        else
            feasibilityListener.onHCViolation("HC2");


        InitialSolutionResult initialSolutionResult = new InitialSolutionResult();
        int err = isFeasibleAllHC(matrixsol);
        if(err==0) {
            Sn sol = new Sn(matrixsol);
            Long endTime = System.currentTimeMillis();
            initialSolutionResult.setInitialSolution(matrixsol);
            initialSolutionResult.setOpturNumber(file);
            initialSolutionResult.setProcessingTime(endTime-startTime);
        }
        else {
            initialSolutionResult.setNotFeasibleHc(String.valueOf(err));
        }
        return initialSolutionResult;
    }

    public static boolean validDay(int[][] solution, int day) {
        for (int i = 0; i < plans.length; i++)
            if (plans[i].getNeed(day % 7) != needs(solution, i+1, day))
                return false;
        return true;
    }

    public static int isMissing(int[][] solution, int day) {
        for (int i = 0; i < plans.length; i++)
            if (plans[i].getNeed(day % 7) > needs(solution, i+1, day))
                return i+1;
        return 0;
    }

    public static void copySolutionMatrix(int[][] solution, int[][] solutionTemp) {
        for (int i = 0; i < solutionTemp.length; i++) {
            for (int j = 0; j < solutionTemp[i].length; j++) {
                solutionTemp[i][j] = solution[i][j];
            }
        }
    }

    public static int isFeasibleAllHC(int[][] solution) {
        if (!isFeasibleHC2(solution))
            return 2;
        if (!isFeasibleHC3(solution))
            return 3;
        if (!isFeasibleHC4(solution))
            return 4;
        if (!isFeasibleHC5(solution))
            return 5;
        if (!isFeasibleHC6(solution))
            return 6;
        if (!isFeasibleHC7(solution))
            return 7;
        return 0;
    }

    public static void twoExchange(int[][] solution) {
        int randomDay =  -1;
        do{
            randomDay = (int) (Math.random() * manpowerPlan[file-1]*7);
        } while (randomDay % 7 == 5 || randomDay % 7 == 6);

        int randomEmp1 = -1;
        int randomEmp2 = -1;
        do {
            randomEmp1 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp1][randomDay]==0);

        do {
            randomEmp2 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp2][randomDay]!=0);

        int shiftExchange = solution[randomEmp1][randomDay];
        solution[randomEmp1][randomDay] = solution[randomEmp2][randomDay];
        solution[randomEmp2][randomDay] = shiftExchange;
    }

    public static void threeExchange(int[][] solution) {
        int randomDay = (int) (Math.random() * manpowerPlan[file-1]*7);
        int randomEmp1 = -1;
        int randomEmp2 = -1;
        int randomEmp3 = -1;

        do {
            randomEmp1 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp1][randomDay] == 0 || randomEmp1 == randomEmp2 || randomEmp1 == randomEmp3);
        do {
            randomEmp2 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp2][randomDay] == 0 || randomEmp2 == randomEmp1 || randomEmp2 == randomEmp3);
        do {
            randomEmp3 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp3][randomDay] == 0 || randomEmp3 == randomEmp1 || randomEmp3 == randomEmp2);

        int shiftExchange = solution[randomEmp1][randomDay];
        solution[randomEmp1][randomDay] = solution[randomEmp2][randomDay];
        solution[randomEmp2][randomDay] = solution[randomEmp3][randomDay];
        solution[randomEmp3][randomDay] = shiftExchange;
    }

    public static void doubleTwoExchange(int[][] solution) {
        int randomEmp1 = -1;
        int randomEmp2 = -1;
        int randomDay1 = -1;
        int randomDay2 = -1;
        while (randomEmp1 == -1 || randomEmp2 == -1) {
            do {
                randomDay1 = (int) (Math.random() * manpowerPlan[file-1]*7);
                randomDay2 = (int) (Math.random() * manpowerPlan[file-1]*7);
            } while (randomDay1 % 7 == 5 || randomDay1 % 7 == 6 || randomDay2 % 7 == 5 || randomDay2 % 7 == 6);
            for (int i = 0; i < employees.length; i++) {
                for (int j = 0; j < employees.length; j++) {
                    if (solution[i][randomDay1] != 0 && solution[j][randomDay2] != 0 && shifts[solution[i][randomDay1]-1].getCategory() == shifts[solution[j][randomDay2]-1].getCategory())
                        if (solution[i][randomDay2] == 0 && solution[j][randomDay1] == 0) {
                            randomEmp1 = i;
                            randomEmp2 = j;
                        }
                }
            }
        }
        solution[randomEmp2][randomDay1] = solution[randomEmp1][randomDay1];
        solution[randomEmp1][randomDay1] = 0;
        solution[randomEmp1][randomDay2] = solution[randomEmp2][randomDay2];
        solution[randomEmp2][randomDay2] = 0;
    }


    public static double[] sumHours (int[][] solution, int week){
        double [] weekHours = new double[employees.length];

        for (int i = 0; i < employees.length; i++) {
            for (int j = week*7; j < (week+1)*7; j++) {
                if (solution [i][j] != 0) {
                    weekHours[i] = weekHours[i] + shifts[solution[i][j]-1].getDuration(j % 7);
                }
            }
        }
        return weekHours;
    }

    public static double [] avgHours (int [][] solution, int week){
        double [] avg = new double[employees.length];

        for (int i = 0; i < week; i++) {
            for (int j = 0; j < employees.length; j++) {
                avg [j] = avg[j] + sumHours(solution, i)[j];
            }
        }
        for (int i = 0; i < employees.length; i++) {
            avg [i] = (avg[i]/week);
        }
        return avg;
    }

    public static double diffHour(int[][] solution) {
        double hour = 0;
        double [] first = avgHours(solution, manpowerPlan[file-1]).clone();
        for (int i = 0; i < first.length; i++) {
            hour = hour + (double) (Math.abs(first[i]- hourLimit[i][opturNumber]));
        }
        return hour;
    }

    public static int needs (int [][] solution, int shift, int day) {
        int count = 0;
        for (int i = 0; i < solution.length; i++)
            if (solution[i][day] == shift)
                count++;
        return count;
    }

    public static boolean checkHC2(int [][] solution, int day, int shift, int employee){
        if(plans[shift-1].getNeed(day % 7) > needs(solution, shift, day))
            if(checkHC4Competence(shift, employee))
                if(checkHC4Week(day, employee))
                    if(checkHC7(solution, employee, day, shift))
                        if(checkHC5(solution, employee, shift, day))
                            return true;
        return false;
    }

    public static boolean checkHC4Competence (int shift, int employee) {
        if(shifts[shift-1].getCompetence().equals("A") && employees[employee].getCompetence().equals(""))
            return false;
        return true;
    }

    public static boolean checkHC4Week (int day, int employee) {
        if(day%7 == 5 || day%7 == 6) {
            for (int i = 0; i < employees[employee].getWeek().length; i++) {
                if (day / 7 == employees[employee].getWeek()[i] - 1)
                    return true;
            }
        } else
            return true;
        return false;
    }

    public static boolean checkHC5 (int [][] solution, int employee, int shift, int day){
        if(day==0)
            return true;
        if(day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < forbiddenWeekendPair.length; i++) {
                if(solution[employee][day-1] == forbiddenWeekendPair[i][0] && shift == forbiddenWeekendPair[i][1])
                    return false;
                if (day < (manpowerPlan[file-1]*7)-1)
                    if(solution[employee][day+1] == forbiddenWeekendPair[i][1] && shift == forbiddenWeekendPair[i][0])
                        return false;
            }
        }
        if (day % 7 == 0 || day % 7 == 1 || day % 7 == 2 || day % 7 == 3 || day % 7 == 4) {
            for (int i = 0; i < forbiddenWeekdayPair.length; i++) {
                if (solution[employee][day-1] == forbiddenWeekdayPair[i][0] && shift == forbiddenWeekdayPair[i][1])
                    return false;
                if (day < (manpowerPlan[file-1]*7)-1)
                    if(solution[employee][day+1] == forbiddenWeekdayPair[i][1] && shift == forbiddenWeekdayPair[i][0])
                        return false;
            }
        }
        return true;
    }

    public static boolean checkHC7 (int solution [][], int employee,  int day, int shift){
        double workingHour = shifts[shift-1].getDuration(day % 7);
        if (workingHours(solution, day, employee)+workingHour <= hardConstraint.getHc7())
            return true;

        return false;
    }

    public static double workingHours (int [][] solution, int day, int employee) {
        double count = 0;
        int week = day/7;
        for (int i = week*7; i < (week+1)*7; i++) {
            if (solution[employee][i] != 0)
                count = count + shifts[solution[employee][i]-1].getDuration(day % 7);
        }
        return count;
    }

    public static boolean isFeasibleHC2(int [][] solution) {
        for (int i = 0; i < manpowerPlan[file-1]*7; i++) {
            for (int j = 0; j < plans.length; j++) {
                if (plans[j].getNeed(i % 7) != needs(solution, j+1, i))
                    return false;
            }
        }
        return true;
    }

    public static boolean isFeasibleHC3(int [][] solution){
        double [] hour = avgHours(solution, manpowerPlan[file-1]).clone();
        for (int i = 0; i < hour.length; i++) {
            if (hour[i] > hourLimit[i][2])
                return false;
            if (hour[i] < hourLimit[i][0])
                return false;
        }
        return true;
    }

    public static boolean isFeasibleHC4(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] != 0)
                    if (shifts[solution[i][j]-1].getCompetence().equals("A") && employees[i].getCompetence().equals(""))
                        return false;
            }
        }
        return true;
    }

    public static boolean isFeasibleHC5(int[][] solution) {
        for (int i = 0; i < manpowerPlan[file-1]*7; i++) {
            for (int j = 0; j < employees.length; j++) {
                if (i % 7 != 5 || i % 7 != 6) {
                    for (int k = 0; k < forbiddenWeekdayPair.length; k++) {
                        if (i < (manpowerPlan[file-1]*7) - 1)
                            if (solution[j][i] == forbiddenWeekdayPair[k][0] && solution[j][(i+1)] == forbiddenWeekdayPair[k][1])
                                return false;
                    }
                }
                else {
                    for (int k = 0; k < forbiddenWeekendPair.length; k++) {
                        if (i < (manpowerPlan[file-1]*7) - 1)
                            if (solution[j][i] == forbiddenWeekendPair[k][0] && solution[j][(i+1)] ==  forbiddenWeekendPair[k][1])
                                return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean[] checkFreeWeekly(int[][] solution, int week){
        boolean[] isOk = new boolean[employees.length];
        for(int  i=0; i<isOk.length; i++)
            isOk[i] = false;
        label : for(int j = 0; j< employees.length; j++)
        {
            for(int i=7*week; i<(week+1)*7;i++)
            {
                if(solution[j][i]==0)
                {
                    if(i==week*7)
                    {
                        if(solution[j][i+1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        else
                        {
                            if(shifts[solution[j][i+1]-1].getCategory()!=1)
                            {
                                isOk[j] = true;
                                continue label;
                            }
                            else
                            {
                                LocalTime lessthan =  LocalTime.parse("08:00");
                                if(shifts[solution[j][i+1]-1].getStart().isAfter(lessthan));
                                {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if(i>week*7 && i<((week+1)*7)-1)
                    {
                        if(solution[j][i+1]==0||solution[j][i-1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        if(solution[j][i+1]!=0&& shifts[solution[j][i+1]-1].getCategory()!=1)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        if(solution[j][i+1]!=0&& shifts[solution[j][i+1]-1].getCategory()==1)
                        {
                            if(shifts[solution[j][i-1]-1].getCategory()!=3)
                            {
                                LocalTime lessthan  = LocalTime.parse("08:00");
                                LocalTime limit = LocalTime.parse("00:00");
                                LocalTime before = limit.minusHours(shifts[solution[j][i-1]-1].getFinish().getHour()).minusMinutes(shifts[solution[j][i-1]-1].getFinish().getMinute());
                                LocalTime total = before.plusHours(shifts[solution[j][i+1]-1].getStart().getHour()).minusMinutes(shifts[solution[j][i+1]-1].getStart().getMinute());
                                if(total.isAfter(lessthan))
                                {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if(i==((week+1)*7)-1)
                    {
                        if(solution[j][i-1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        else
                        {
                            LocalTime lessthan  = LocalTime.parse("08:00");
                            LocalTime limit = LocalTime.parse("00:00");
                            LocalTime before = limit.minusHours(shifts[solution[j][i-1]-1].getFinish().getHour()).minusMinutes(shifts[solution[j][i-1]-1].getFinish().getMinute());
                            if(before.isAfter(lessthan))
                            {
                                isOk[j] = true;
                                continue label;
                            }
                        }
                    }
                }
            }
        }
        return isOk;
    }

    public static boolean isFeasibleHC6(int [][] solution) {
        boolean [][] check = new boolean[employees.length][manpowerPlan[file-1]];
        for (int i = 0; i < manpowerPlan[file-1]; i++) {
            boolean [] check2 = checkFreeWeekly(solution, i).clone(); {
                for (int j = 0; j < check2.length; j++) {
                    check[j][i] = check2[j];
                }
            }
        }

        for (int i = 0; i < check.length; i++) {
            for (int j = 0; j < check[i].length; j++) {
                if (!check[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int countHC6(int[][] solution) {
        int count = 0;
        boolean [][] check = new boolean[employees.length][manpowerPlan[file-1]];
        for (int i = 0; i < manpowerPlan[file-1]; i++) {
            boolean [] check2 = checkFreeWeekly(solution, i).clone(); {
                for (int j = 0; j < check2.length; j++) {
                    check[j][i] = check2[j];
                }
            }
        }

        for (int i = 0; i < check.length; i++) {
            for (int j = 0; j < check[i].length; j++) {
                if (check[i][j] == false) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isFeasibleHC7(int[][] solution) {
        for(int i = 0; i< manpowerPlan[file-1]*7; i++)
        {
            for(int j = 0; j< employees.length; j++)
            {
                if(workingHours(solution, i, j) > hardConstraint.getHc7())
                    return false;
            }
        }
        return true;
    }

    public static void saveSolution (int [][] solution, int number) throws IOException {
        FileWriter writer = new FileWriter("D:\\ITS\\Semester 8\\Tugas Akhir\\Nurse Rostering\\nurserost\\Solusi\\OpTur" + file + ".txt", false);
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                writer.write(solution[i][j] + " ");
            } writer.write("\n");
        }
        writer.close();
    }

    public static void saveOptimation(double[][] solution, int number) throws IOException {
        FileWriter writer = new FileWriter("D:\\ITS\\Semester 8\\Tugas Akhir\\Nurse Rostering\\nurserost\\Solusi\\Solusi RL-SA" + number + ".txt", false);
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                writer.write(solution[i][j] + " ");
            } writer.write("\n");
        }
        writer.close();
    }

    public static void saveSolutionShiftName (int [][] solution, int number) throws IOException {
        FileWriter writer = new FileWriter("D:\\ITS\\Semester 8\\Tugas Akhir\\Nurse Rostering\\nurserost\\Solusi\\OpTur" + file + "NameShift.txt", false);
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] != 0)
                    writer.write(shifts[solution[i][j]-1].getName() + " ");
                else
                    writer.write("<Free>" + " ");
            } writer.write("\n");
        }
        writer.close();
    }

    public static String[][] employees() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        } return data;
    }

    public static String[][] shifts() throws IOException, InvalidFormatException{
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(1);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first-1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first-1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        }
        return data;
    }

    public static String[][] manpower() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(2);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        } return data;
    }

    public static String[] hardconstraint() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(3);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;
        for (int i = first-1; i <= 15; i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side) {
                sidex = side;
            }
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first-1; i <= 15; i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        }

        String[] cons = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            cons[i] = data[i][3];
        }
        return cons;
    }

    public static String[] softconstraint() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(3);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first+13; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first+13; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        }
        String[] cons = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            cons[i] = data[i][3];
        }
        return cons;
    }

    public static String[][] wantedpattern() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(4);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first-1; i <= 12; i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first-1; i <= 12; i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        }
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].isEmpty())
                break;
            count++;
        }

        String[][] wanted = new String[count][3];
        for (int i = 0; i < wanted.length; i++) {
            for (int j = 0; j < wanted[i].length; j++) {
                wanted[i][j] = data[i][j];
            }
        }
        return wanted;
    }

    public static String[][] unwantedpattern() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));
        Sheet sheet = workbook.getSheetAt(4);
        DataFormatter dataFormatter = new DataFormatter();

        int last = 0;
        int side = 0;
        int sidex = 0;
        int first = 5;

        for (int i = first+12; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell: row) {
                side++;
            }
            if (sidex < side)
                sidex = side;
            side = 0;
            last++;
        }
        String[][] data = new String[last][sidex];
        last = 0;
        for (int i = first+12; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                data[last][side] = cellValue;
                side++;
            }
            side = 0;
            last++;
        }
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i][0].isEmpty())
                break;
            count++;
        }

        String[][] unwanted = new String[count][3];
        for (int i = 0; i < unwanted.length; i++) {
            for (int j = 0; j < unwanted[i].length; j++) {
                unwanted[i][j] = data[i][j];
            }
        }
        return unwanted;
    }
}