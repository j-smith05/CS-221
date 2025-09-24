import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * FormatChecker takes input from the command line of a file, and runs
 * it through various tests. Then it outputs the inputed files with the 
 * filename, and  "valid" or "invalid," and if invalid it gives the reason.
 * 
 * @author Jacob Smith
 **/ 

public class FormatChecker 
{
    public static void main(String[] args) 
    {
        if (args.length == 0) //Verifies user inputs a valid input in the command linae
        {
            System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]\n");
            return;
        }
        for (String filename : args) // Scans in files into an array for assessment of validity
        {
            File file = new File(filename);
            System.out.println(filename);
            
            try (Scanner scanner = new Scanner(file)) 
            {
                String [] Array = scanner.nextLine().split("\\s+"); 
                if (Array.length != 2) // Verify the grid will not have to many values (i.e 3d array)
                {
                    throw new IllegalArgumentException("Too many values in array");
                }
                int rows = Integer.parseInt(Array[0]);
                int cols = Integer.parseInt(Array[1]);
                
                for (int r = 0; r < rows; r++) // Creates array again for more verification
                {
                    String [] SplitUp = scanner.nextLine().split("\\s+");
                    if (SplitUp.length !=cols) // Verifies data has enough data in the array
                    {
                        throw new IllegalArgumentException("Not enough data in array");
                    }
                    for (int c = 0; c < cols; c++) 
                    {
                        Double.parseDouble(SplitUp[c]);
                    }
                }
                if (scanner.hasNext()) // Checks to see if there is more data in the array then originally listed.
                {
                    throw new IllegalArgumentException("Extra data after matrix");
                    
                }
                System.out.println("VALID\n"); 
                scanner.close();
            }
            catch (FileNotFoundException e) // Throws file not found exception and converts it to string and write invalid
            {
                System.out.println(e.toString());
                System.out.println("INVALID\n");
            }
            catch (Exception e) // Catchs more exceptions and write them to string with invalid statement
            {
                System.out.println(e.toString());
                System.out.println("INVALID\n");   
            }
        }
    }
}