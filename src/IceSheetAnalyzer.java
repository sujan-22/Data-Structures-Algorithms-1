import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Author:                 Sujan Rokad, 000882948
 * Authorship statement:   I, Sujan Rokad, 000882948, certify that this material is my original work.
 *                         No other person's work has been used without due acknowledgment.
 * Purpose:               Analyzes ice sheets, identifies fracture points, and checks for surrounding crack points.
 */
public class IceSheetAnalyzer {

    /**
     * Analyzes ice sheets, identifies fracture points, and checks for surrounding crack points.
     *
     * @param file The input file containing ice sheet data.
     */
    public static void fracturePoints(File file) {

        // 2D array to store ice sheet data
        int[][] iceSheet;
        // Counter for total fracture points across all sheets
        int totalFracturePoints = 0;
        // Counter for total crack points across all sheets
        int totalCrackPoints = 0;
        // Maximum fracture points found in a single sheet
        int maxFracturePoints = 0;
        // Sheet number with the maximum fracture points
        int sheetWithMaxPoints = -1;

        try {
            Scanner scanner = new Scanner(file);
            // Number of Ice Sheets
            int numOfSheets = scanner.nextInt();

            // Loop through each ice sheet
            for (int sheetNumber = 1; sheetNumber <= numOfSheets; sheetNumber++) {

                // Dimensions of the current ice sheet
                int rows = scanner.nextInt();
                int columns = scanner.nextInt();

                // Initialize the ice sheet array with the specified dimensions
                iceSheet = new int[rows][columns];

                // Counter for fracture points in the current sheet
                int currentSheetFracturePoints = 0;

                // Read the entire sheet and identify fracture points
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < columns; ++j) {
                        iceSheet[i][j] = scanner.nextInt();

                        // Check for fracture point conditions
                        if (iceSheet[i][j] <= 200 && iceSheet[i][j] % 50 == 0) {
                            totalFracturePoints++;
                            currentSheetFracturePoints++;
                        }
                    }
                }

                // Update max fracture points information
                if (currentSheetFracturePoints > maxFracturePoints) {
                    maxFracturePoints = currentSheetFracturePoints;
                    sheetWithMaxPoints = sheetNumber;
                }

                // Iterate over identified fracture points to check for surrounding crack points
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < columns; ++j) {
                        if (iceSheet[i][j] <= 200 && iceSheet[i][j] % 50 == 0) {
                            // Check connecting points for cracks and update total crack points
                            totalCrackPoints += countPotentialCrackPoints(iceSheet, i, j, rows, columns, sheetNumber);
                        }
                    }
                }
            }

            // Output total and max fracture points information
            System.out.println("Total Fracture Points: " + totalFracturePoints);
            System.out.println("Sheet with Max Fracture Points: Sheet " + sheetWithMaxPoints +
                    " (Contains " + maxFracturePoints + " fracture points)");

            // Calculate and print the fraction of fracture points that are also crack points
            double fractionOfCrackPoints = (double) totalCrackPoints / totalFracturePoints;
            System.out.println("Total crack Points: " + totalCrackPoints);
            System.out.printf("Fraction of Crack Points: %.3f%n", fractionOfCrackPoints);

            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Checks surrounding digits of a fracture point for potential crack points.
     *
     * @param array   The ice sheet array.
     * @param x       The x-coordinate of the fracture point.
     * @param y       The y-coordinate of the fracture point.
     * @param rows    The number of rows in the ice sheet.
     * @param columns The number of columns in the ice sheet.
     * @return The number of potential crack points.
     */
    public static int countPotentialCrackPoints(int[][] array, int x, int y, int rows, int columns, int sheetNumber) {
        int crackPoints = 0;
        boolean isFound = false;

        // Iterate over the surrounding points of the fracture point
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, rows - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, columns - 1); j++) {
                // Skip the center point (the fracture point itself)
                if (i == x && j == y) {
                    continue;
                }

                // Check if at least one surrounding point is divisible by 10
                int surroundingPoint = array[i][j];
                if ((surroundingPoint % 10 == 0) && !isFound) {
                    System.out.println("Crack Point in Sheet " + sheetNumber + " at (" + i + ", " + j + ")");
                    crackPoints++;
                    // Set the flag to indicate that a crack point has been found
                    isFound = true;
                    // Break out of the loop since one crack point is found
                    break;
                }
            }
        }

        return crackPoints;
    }

    /**
     * The main method to execute the program.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        File file = new File("src\\ICESHEETS.txt");
        fracturePoints(file);
    }
}
