import java.util.*;

class solution { 
    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in);
        int[] binary = new int[4];
        
        //taking input
        System.out.println("Enter 4 bits binary code:"); 
        for(int i = 0; i < binary.length; i++) {
            System.out.print("Enter bit " + (i + 1) + " : "); 
            int bit = input.nextInt();
            
            if (bit != 0 && bit != 1) {
                System.out.println("Invalid input: Code must only contain 0 and 1");
                return;
            }
            binary[i] = bit;
        }
        System.out.print("Your binary code is: ");
        for(int bit : binary) {
            System.out.print(bit);
        }
        System.out.println();
        
        int m = 4;
        int r = 0;
        
        //finding r -> 2^r >= m + r + 1
        while (true) {
            if (Math.pow(2, r) >= m + r + 1) {
                break;
            }
            r++; 
        }
        
        System.out.println("Number of parity bits r: " + r);
        
        int k = 0;
        int[] finalBinary = new int[r + 4];
        
          //setting parity bits to 0 and finding their positions
        for(int i = 0; i < finalBinary.length; i++) {
            if (k >= r) { 
                break; // Stop when all r placed
            }
            int position = (int) Math.pow(2, k); //downcast
            finalBinary[finalBinary.length - position] = 0;
            k++;
        }
        
       //remaining bits as it is from back 

        int binaryIndex = 0;
        for (int i = finalBinary.length; i >= 1; i--) { 
            int arrayIndex = finalBinary.length - i;
            
            
            if (i != 1 && i != 2 && i != 4) { 
                finalBinary[arrayIndex] = binary[binaryIndex];
                binaryIndex++;
            }
        }
        
         System.out.print("Your binary code is with parity bits set to 0: ");
        for(int bit : finalBinary) {
            System.out.print(bit);
        }
        System.out.println();
        
        //calculation of parity bits values
        int p1 = 0;
        int p2 = 1;
        int p4 = 0;
        
        //setting p1 p2 p4 values from back
        finalBinary[finalBinary.length - 1] = p1;
        finalBinary[finalBinary.length - 2] = p2;
        finalBinary[finalBinary.length - 5] = p4;
        
                 System.out.print("Final signal: ");
        for(int bit : finalBinary) {
            System.out.print(bit);
        }
        System.out.println();

    } 
}