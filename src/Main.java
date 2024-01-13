import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void fracturePoints(File file) {
        int[][] array;
        int totalFracturePoints = 0;
        int totalCrackPoints = 0;
        int maxFracturePoints = 0;
        int sheetWithMaxPoints = -1;

        try {
            Scanner scanner = new Scanner(file);

            int numOfSheets = scanner.nextInt(); // Number of Ice Sheets

            for (int sheetNumber = 1; sheetNumber <= numOfSheets; sheetNumber++) {
                System.out.println("Processing Sheet " + sheetNumber + ":");
                int rows = scanner.nextInt();
                int columns = scanner.nextInt();
                array = new int[rows][columns];

                int currentSheetFracturePoints = 0;

                // Read the entire sheet and identify fracture points
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < columns; ++j) {
                        array[i][j] = scanner.nextInt();

                        // Check for fracture point conditions
                        if (array[i][j] <= 200 && array[i][j] % 50 == 0) {
                            totalFracturePoints++;
                            currentSheetFracturePoints++;

                            System.out.println("Fracture point at: (" + i + "," + j + ") is " + array[i][j]);
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
                        if (array[i][j] <= 200 && array[i][j] % 50 == 0) {
                            // Check connecting points for cracks
                            totalCrackPoints += checkSurroundingDigits(array, i, j, rows, columns);
                        }
                    }
                }
            }

            // Output total and max fracture points information
            System.out.println("Total Fracture Points: " + totalFracturePoints);
            System.out.println("Sheet with Max Fracture Points: Sheet " + sheetWithMaxPoints +
                    " (" + maxFracturePoints + " fracture points)");

            // Calculate and print the fraction of fracture points that are also crack points
            double fractionOfCrackPoints = (double) totalCrackPoints / totalFracturePoints;
            System.out.println("Crack Points: " + totalCrackPoints);
            System.out.printf("Fraction of Crack Points: %.3f%n", fractionOfCrackPoints);

            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static int checkSurroundingDigits(int[][] array, int x, int y, int rows, int columns) {
        int crackPoints = 0;
        boolean found = false;
        // Iterate over the surrounding points
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, rows - 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, columns - 1); j++) {
                // Skip the center point (the fracture point itself)
                if (i == x && j == y) {
                    continue;
                }

                // Check if the surrounding point is divisible by 10
                int surroundingPoint = array[i][j];
                if (surroundingPoint % 10 == 0 && !found) {
                    System.out.println("Potential Crack Point at (" + i + ", " + j + ") & Crack point is: " + surroundingPoint);
                    crackPoints++;
                    found = true;
                    break;
                }
            }
        }

        return crackPoints;
    }
    public static void main(String[] args) {
        File file = new File("src\\ICESHEETS.txt");
        fracturePoints(file);
    }
}
