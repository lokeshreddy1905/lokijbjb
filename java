public class Project {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Please provide 4 arguments: DOB/AGE, reference date, date format, and delimiter.");
            return;
        }

        String dobOrAge = args[0];
        String referenceDateStr = args[1];
        String dateFormat = args[2];
        String delimiter = args[3];

        try {
            if (dobOrAge.startsWith("DOB")) {
                String dobStr = dobOrAge.split("=")[1];
                int[] dob = parseDate(dobStr, dateFormat, delimiter);
                int[] refDate = parseDate(referenceDateStr, dateFormat, delimiter);
                calculateAndPrintAge(dob, refDate);
            } else if (dobOrAge.startsWith("AGE")) {
                String ageStr = dobOrAge.split("=")[1];
                int years = Integer.parseInt(ageStr.substring(0, 2));
                int months = Integer.parseInt(ageStr.substring(2, 4));
                int days = Integer.parseInt(ageStr.substring(4, 6));

                int[] refDate = parseDate(referenceDateStr, dateFormat, delimiter);
                int[] dob = calculateDOBFromAge(refDate, years, months, days);
                System.out.println("DOB: " + formatDate(dob, dateFormat, delimiter));
            } else {
                System.out.println("Invalid input format for DOB or AGE.");
            }
        } catch (Exception e) {
            System.out.println("Error in parsing date or incorrect input format.");
        }
    }

    private static int[] parseDate(String dateStr, String dateFormat, String delimiter) {
        String[] dateParts = dateStr.split(delimiter);
        int day = 0, month = 0, year = 0;

        if (dateFormat.equals("DD" + delimiter + "MM" + delimiter + "YYYY")) {
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } else if (dateFormat.equals("YYYY" + delimiter + "MM" + delimiter + "DD")) {
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } else if (dateFormat.equals("MM" + delimiter + "DD" + delimiter + "YYYY")) {
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        }
        return new int[]{day, month, year};
    }

    private static void calculateAndPrintAge(int[] dob, int[] refDate) {
        int years = refDate[2] - dob[2];
        int months = refDate[1] - dob[1];
        int days = refDate[0] - dob[0];

        if (days < 0) {
            days += 30;
            months -= 1;
        }

        if (months < 0) {
            months += 12;
            years -= 1;
        }

        System.out.println("Age: " + years + " years, " + months + " months, " + days + " days");
    }

    private static int[] calculateDOBFromAge(int[] refDate, int years, int months, int days) {
        int year = refDate[2] - years;
        int month = refDate[1] - months;
        int day = refDate[0] - days;

        if (day < 1) {
            day += 30;
            month -= 1;
        }

        if (month < 1) {
            month += 12;
            year -= 1;
        }

        return new int[]{day, month, year};
    }

    private static String formatDate(int[] date, String dateFormat, String delimiter) {
        if (dateFormat.equals("DD" + delimiter + "MM" + delimiter + "YYYY")) {
            return String.format("%02d" + delimiter + "%02d" + delimiter + "%04d", date[0], date[1], date[2]);
        } else if (dateFormat.equals("YYYY" + delimiter + "MM" + delimiter + "DD")) {
            return String.format("%04d" + delimiter + "%02d" + delimiter + "%02d", date[2], date[1], date[0]);
        } else if (dateFormat.equals("MM" + delimiter + "DD" + delimiter + "YYYY")) {
            return String.format("%02d" + delimiter + "%02d" + delimiter + "%04d", date[1], date[0], date[2]);
        }
        return "";
    }
}
