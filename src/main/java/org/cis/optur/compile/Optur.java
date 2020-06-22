package org.cis.optur.compile;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.cis.optur.compile.model.*;
import org.cis.optur.compile.repository.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Optur {
    private String filePath;
    private HSSFWorkbook wb;
    private File optureFile;
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private ShiftRepository shiftRepository = new ShiftRepository();
    private ManpowerPlanRepository manpowerPlanRepository = new ManpowerPlanRepository();
    private HardConstraintRepository hardConstraintRepository = new HardConstraintRepository();
    private SoftConstraintRepository softConstraintRepository = new SoftConstraintRepository();
    private WantedShiftPatternRepository wantedShiftPatternRepository = new WantedShiftPatternRepository();
    private UnwantedShiftPatternRepository unwantedShiftPatternRepository = new UnwantedShiftPatternRepository();

    public Optur() throws Exception {
        throw new Exception();
//        chooseFile();
    }

    public Optur(File file) throws FileNotFoundException {
        this.filePath = file.getAbsolutePath();
        FileInputStream fis = new FileInputStream(file);
        try {
            this.wb = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

//compile employee
        compileEmployee();

//compile shift
        compileShift();

//compile manpowerplan
        compileManpowerPlan();

//compile hard constraint
        compileHardConstraint();

//compile soft constraint
        compileSoftConstraint();

//compile wanted shift pattern
        compileWantedShiftPattern();

//compile unwanted shift pattern
        compileUnwantedShiftPattern();
    }



    public Optur(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        FileInputStream fis = new FileInputStream(new File(this.filePath));
        try {
            this.wb = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

//compile employee
        compileEmployee();

//compile shift
        compileShift();

//compile manpowerplan
        compileManpowerPlan();

//compile hard constraint
        compileHardConstraint();

//compile soft constraint
        compileSoftConstraint();

//compile wanted shift pattern
        compileWantedShiftPattern();

//compile unwanted shift pattern
        compileUnwantedShiftPattern();
    }

    private void compileEmployee() {
        HSSFSheet employeeSheet = wb.getSheetAt(0);
        int count = (int) employeeSheet.getRow(1).getCell(2).getNumericCellValue();

        for (int i = 5; i < count + 5; i++) {
            Row row = employeeSheet.getRow(i);
            Employee employee = new Employee();

            employee.setId((int) row.getCell(0).getNumericCellValue());
            employee.setWorkWeek((double) row.getCell(1).getNumericCellValue());

            String rawWorkingWeekends = row.getCell(2).getCellType() == CellType.STRING ? row.getCell(2).getStringCellValue() : String.valueOf(row.getCell(2).getNumericCellValue());
            String[] workingWeekendArrayString = rawWorkingWeekends.split(",");
            int[] workingWeekendArrayInt = new int[workingWeekendArrayString.length];
            for (int j = 0; j < workingWeekendArrayString.length; j++) {
//                workingWeekendArrayInt[j] = Integer.parseInt(workingWeekendArrayString[j]);
//                workingWeekendArrayInt[j] = commaAbleStringToInteger(workingWeekendArrayString[j]);
                workingWeekendArrayInt[j] = workingWeekendArrayString[j].contains(" ") ?
                        (workingWeekendArrayString[j].split("")[1].contains(".") ?
                                (int) Float.parseFloat(workingWeekendArrayString[j].split("")[1]) :
                                Integer.valueOf(workingWeekendArrayString[j].split("")[1])) :
                        (workingWeekendArrayString[j].contains(".") ?
                                (int) Float.parseFloat(workingWeekendArrayString[j]) : Integer.valueOf(workingWeekendArrayString[j]));
            }
            employee.setWorkingWeekends(workingWeekendArrayInt);
            employee.setCompetence((String) row.getCell(3).getStringCellValue() == "" ? null : (String) row.getCell(3).getStringCellValue());

            this.employeeRepository.save(employee);
        }
//        this.employeeRepository.printAll();
    }

//    private int commaAbleStringToInteger(String s) {
//        if(s.contains(".")){
//            return Integer.valueOf(Float.valueOf(s));
//        }
//    }

    private Integer stringToInteger(String stringValue) {
        int x = Integer.parseInt(stringValue);
        return x;
    }

    private void compileShift() {
        HSSFSheet shiftSheet = wb.getSheetAt(1);
        int count = (int) shiftSheet.getRow(1).getCell(2).getNumericCellValue();

        for (int i = 4; i < count + 4; i++) {
            Row row = shiftSheet.getRow(i);
            Shift shift = new Shift();

            shift.setId((int) row.getCell(0).getNumericCellValue());
            shift.setMonday((double) row.getCell(1).getNumericCellValue());
            shift.setTuesday((double) row.getCell(2).getNumericCellValue());
            shift.setWednesday((double) row.getCell(3).getNumericCellValue());
            shift.setThursday((double) row.getCell(4).getNumericCellValue());
            shift.setFriday((double) row.getCell(5).getNumericCellValue());
            shift.setSaturday((double) row.getCell(6).getNumericCellValue());
            shift.setSunday((double) row.getCell(7).getNumericCellValue());
            shift.setShiftCategory((int) row.getCell(8).getNumericCellValue());
            shift.setName((String) row.getCell(9).getStringCellValue());
            shift.setStartTimeHours((stringToInteger(row.getCell(10).getStringCellValue())));
            shift.setStartTimeMinutes((stringToInteger(row.getCell(11).getStringCellValue())));
            shift.setEndTimeHours((stringToInteger(row.getCell(12).getStringCellValue())));
            shift.setEndTimeMinutes((stringToInteger(row.getCell(13).getStringCellValue())));
            String raw = (String) row.getCell(14).getStringCellValue();
            shift.setCompetenceNeeded(raw.equals("N/A") || raw.equals("") ? null : (String) row.getCell(14).getStringCellValue());
            this.shiftRepository.save(shift);
        }
//        this.shiftRepository.printAll();
    }

    private void compileManpowerPlan() {
        HSSFSheet manpowerPlanSheet = wb.getSheetAt(2);
        int count = (int) manpowerPlanSheet.getRow(2).getCell(3).getNumericCellValue();
        this.manpowerPlanRepository.setWeekCount((int) manpowerPlanSheet.getRow(1).getCell(3).getNumericCellValue());
        for (int i = 5; i < count + 5; i++) {
            Row row = manpowerPlanSheet.getRow(i);
            ManpowerPlan manpowerPlan = new ManpowerPlan();

            manpowerPlan.setId((int) row.getCell(8).getNumericCellValue());
            manpowerPlan.setShift((String) row.getCell(0).getStringCellValue());
            manpowerPlan.setMonday((int) row.getCell(1).getNumericCellValue());
            manpowerPlan.setTuesday((int) row.getCell(2).getNumericCellValue());
            manpowerPlan.setWednesday((int) row.getCell(3).getNumericCellValue());
            manpowerPlan.setThursday((int) row.getCell(4).getNumericCellValue());
            manpowerPlan.setFriday((int) row.getCell(5).getNumericCellValue());
            manpowerPlan.setSaturday((int) row.getCell(6).getNumericCellValue());
            manpowerPlan.setSunday((int) row.getCell(7).getNumericCellValue());

            this.manpowerPlanRepository.save(manpowerPlan);
        }
//        this.manpowerPlanRepository.printAll();
    }

    private boolean yesNoToBoolean(String stringValue) {
        return stringValue.equals("Yes") ? true : false;
    }

    private float thingsToFloat(String stringValue) {
        String x = stringValue.split("")[1];
        return Float.valueOf(x) / 100;
    }

    private void compileHardConstraint() {
        HSSFSheet hardConstraintSheet = wb.getSheetAt(3);
        HardConstraint hardConstraint = new HardConstraint();
        hardConstraint.setHc1(yesNoToBoolean(hardConstraintSheet.getRow(4).getCell(3).getStringCellValue()));
        hardConstraint.setHc2(yesNoToBoolean(hardConstraintSheet.getRow(5).getCell(3).getStringCellValue()));
        hardConstraint.setHc3((thingsToFloat(hardConstraintSheet.getRow(6).getCell(3).getStringCellValue())));
        hardConstraint.setHc4(yesNoToBoolean(hardConstraintSheet.getRow(7).getCell(3).getStringCellValue()));
        hardConstraint.setHc5NEWeekday((int) hardConstraintSheet.getRow(8).getCell(3).getNumericCellValue());
        hardConstraint.setHc5NEWeekend((int) hardConstraintSheet.getRow(9).getCell(3).getNumericCellValue());
        hardConstraint.setHc5EDWeekday((int) hardConstraintSheet.getRow(10).getCell(3).getNumericCellValue());
        hardConstraint.setHc5EDWeekend((int) hardConstraintSheet.getRow(11).getCell(3).getNumericCellValue());
        hardConstraint.setHc5DNWeekday((int) hardConstraintSheet.getRow(12).getCell(3).getNumericCellValue());
        hardConstraint.setHc5DNWeekend((int) hardConstraintSheet.getRow(13).getCell(3).getNumericCellValue());
        hardConstraint.setHc6((int) hardConstraintSheet.getRow(14).getCell(3).getNumericCellValue());
        hardConstraint.setHc7((int) hardConstraintSheet.getRow(15).getCell(3).getNumericCellValue());

        this.hardConstraintRepository.save(hardConstraint);
//        this.hardConstraintRepository.printAll();
    }

    private Integer naAbleToNumeric(String stringValue) {
        if (stringValue.equals("N/A")) {
            return null;
        } else {
            return Integer.valueOf(stringValue);
        }
    }

    private void compileSoftConstraint() {
        HSSFSheet softConstraintSheet = wb.getSheetAt(3);
        SoftConstraint softConstraint = new SoftConstraint();
        if (softConstraintSheet.getRow(18).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(18).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(18).getCell(3).getNumericCellValue());
        if (softConstraintSheet.getRow(19).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(19).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(19).getCell(3).getNumericCellValue());
//        softConstraint.setSc1Evening((int) softConstraintSheet.getRow(19).getCell(3).getNumericCellValue());
        softConstraint.setSc1Night((int) softConstraintSheet.getRow(20).getCell(3).getNumericCellValue());
        if (softConstraintSheet.getRow(21).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(21).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(21).getCell(3).getNumericCellValue());
//        softConstraint.setSc2((int) softConstraintSheet.getRow(21).getCell(3).getNumericCellValue());
        if (softConstraintSheet.getRow(22).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(22).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(22).getCell(3).getNumericCellValue());
//        softConstraint.setSc3Day(naAbleToNumeric(softConstraintSheet.getRow(22).getCell(3).getStringCellValue()));
        if (softConstraintSheet.getRow(23).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(23).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(23).getCell(3).getNumericCellValue());
//        softConstraint.setSc3Evening((naAbleToNumeric(softConstraintSheet.getRow(23).getCell(3).getStringCellValue())));
        if (softConstraintSheet.getRow(24).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(24).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(24).getCell(3).getNumericCellValue());
//        softConstraint.setSc3Night((naAbleToNumeric(softConstraintSheet.getRow(24).getCell(3).getStringCellValue())));
        softConstraint.setSc4((naAbleToNumeric(softConstraintSheet.getRow(25).getCell(3).getStringCellValue())));
        handleCompileSc5Day(softConstraint, softConstraintSheet.getRow(26).getCell(3));
        handleCompileSc5Evening(softConstraint, softConstraintSheet.getRow(27).getCell(3));
        handleCompileSc5Night(softConstraint, softConstraintSheet.getRow(28).getCell(3));
//        softConstraint.setSc5Day((naAbleToNumeric(softConstraintSheet.getRow(26).getCell(3).getStringCellValue())));
//        softConstraint.setSc5Evening((naAbleToNumeric(softConstraintSheet.getRow(27).getCell(3).getStringCellValue())));
//        softConstraint.setSc5Night((naAbleToNumeric(softConstraintSheet.getRow(28).getCell(3).getStringCellValue())));
        softConstraint.setSc6(yesNoToBoolean(softConstraintSheet.getRow(29).getCell(3).getStringCellValue()));
        softConstraint.setSc7(yesNoToBoolean(softConstraintSheet.getRow(30).getCell(3).getStringCellValue()));
        if (softConstraintSheet.getRow(31).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(31).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(31).getCell(3).getNumericCellValue());
//        softConstraint.setSc8((int) softConstraintSheet.getRow(31).getCell(3).getNumericCellValue());
        if (softConstraintSheet.getRow(32).getCell(3).getCellType() == CellType.STRING)
            softConstraint.setSc1Day(naAbleToNumeric(softConstraintSheet.getRow(32).getCell(3).getStringCellValue()));
        else
            softConstraint.setSc1Day((int) softConstraintSheet.getRow(32).getCell(3).getNumericCellValue());
//        softConstraint.setSc9((int) softConstraintSheet.getRow(32).getCell(3).getNumericCellValue());

        this.softConstraintRepository.save(softConstraint);
//        this.softConstraintRepository.printAll();
    }

    private void handleCompileSc5Night(SoftConstraint softConstraint, HSSFCell cell) {

        if (cell.getCellType() == CellType.STRING) {

            if (cell.getStringCellValue().equals("N/A")) {
                softConstraint.setSc5Night(new MinMax(null, null));
            } else {
                Integer min = Integer.parseInt(cell.getStringCellValue().split("/")[0]);
                Integer max = Integer.parseInt(cell.getStringCellValue().split("/")[1]);
                softConstraint.setSc5Night(new MinMax(min, max));
            }
        }
    }

    private void handleCompileSc5Evening(SoftConstraint softConstraint, HSSFCell cell) {
        if (cell.getCellType() == CellType.STRING) {
            if (cell.getStringCellValue().equals("N/A")) {
                softConstraint.setSc5Evening(new MinMax(null, null));
            } else {
                Integer min = Integer.parseInt(cell.getStringCellValue().split("/")[0]);
                Integer max = Integer.parseInt(cell.getStringCellValue().split("/")[1]);
                softConstraint.setSc5Evening(new MinMax(min, max));
            }
        }
    }

    private void handleCompileSc5Day(SoftConstraint softConstraint, HSSFCell cell) {

        if (cell.getCellType() == CellType.STRING) {
            if (cell.getStringCellValue().equals("N/A")) {
                softConstraint.setSc5Day(new MinMax(null, null));
            } else {
                Integer min = Integer.parseInt(cell.getStringCellValue().split("/")[0]);
                Integer max = Integer.parseInt(cell.getStringCellValue().split("/")[1]);
                System.out.println("day");
                System.out.println(min + "/" + max);
                softConstraint.setSc5Day(new MinMax(min, max));
            }
        }
    }

    private void compileWantedShiftPattern() {
        HSSFSheet wantedShiftPatternSheet = wb.getSheetAt(4);
        int count = (int) wantedShiftPatternSheet.getRow(1).getCell(3).getNumericCellValue();

        for (int i = 4; i < count + 4; i++) {
            Row row = wantedShiftPatternSheet.getRow(i);
            WantedShiftPattern wantedShiftPattern = new WantedShiftPattern();

            wantedShiftPattern.setId((int) row.getCell(0).getNumericCellValue());
            wantedShiftPattern.setStartingDay((String) row.getCell(1).getStringCellValue());

            String rawPattern = (String) row.getCell(2).getStringCellValue();
            String[] patternArray = rawPattern.split(",");
            wantedShiftPattern.setPattern(patternArray);

            this.wantedShiftPatternRepository.save(wantedShiftPattern);
        }
//        this.wantedShiftPatternRepository.printAll();
    }

    private void chooseFile() throws Exception {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Excel files", "xls");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        chooser.cancelSelection();
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            optureFile = chooser.getSelectedFile();
        }else{
            throw new Exception();
        }
    }

    private void compileUnwantedShiftPattern() {
        HSSFSheet unwantedShiftPatternSheet = wb.getSheetAt(4);
        int count = (int) unwantedShiftPatternSheet
                .getRow(15)
                .getCell(3)
                .getNumericCellValue();

        for (int i = 18; i < count + 18; i++) {
            Row row = unwantedShiftPatternSheet.getRow(i);
            UnwantedShiftPattern unwantedShiftPattern = new UnwantedShiftPattern();

            unwantedShiftPattern.setId((int) row.getCell(0).getNumericCellValue());
            unwantedShiftPattern.setStartingDay((String) row.getCell(1).getStringCellValue());

            String rawPattern = (String) row.getCell(2).getStringCellValue();
            String[] patternArray = rawPattern.split(",");
            unwantedShiftPattern.setPattern(patternArray);

            this.unwantedShiftPatternRepository.save(unwantedShiftPattern);
        }
//        this.unwantedShiftPatternRepository.printAll();
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ShiftRepository getShiftRepository() {
        return shiftRepository;
    }

    public ManpowerPlanRepository getManpowerPlanRepository() {
        return manpowerPlanRepository;
    }

    public HardConstraintRepository getHardConstraintRepository() {
        return hardConstraintRepository;
    }

    public SoftConstraintRepository getSoftConstraintRepository() {
        return softConstraintRepository;
    }

    public WantedShiftPatternRepository getWantedShiftPatternRepository() {
        return wantedShiftPatternRepository;
    }

    public UnwantedShiftPatternRepository getUnwantedShiftPatternRepository() {
        return unwantedShiftPatternRepository;
    }
}



