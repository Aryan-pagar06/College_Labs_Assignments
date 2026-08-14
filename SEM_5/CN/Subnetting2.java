import java.util.Scanner;

public class SubnetCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Get user input for Base IP using a dot-separated string or tokens
        System.out.print("Enter base IP address (e.g., 192.168.10.0): ");
        String ipInput = scanner.next();
        String[] octets = ipInput.split("\\.");
        
        int o1 = Integer.parseInt(octets[0]);
        int o2 = Integer.parseInt(octets[1]);
        int o3 = Integer.parseInt(octets[2]);
        int o4 = Integer.parseInt(octets[3]);
        
        System.out.print("Enter CIDR prefix (e.g., 26 for /26): ");
        int cidr = scanner.nextInt();
        
        // Validate for Class C subnetting range
        if (cidr < 24 || cidr > 30) {
            System.out.println("Error: This program currently supports Class C subnetting (CIDR 24 to 30).");
            return;
        }
        
        // 2. Calculate the core subnetting details
        int hostBits = 32 - cidr;
        int blockSize = (int) Math.pow(2, hostBits);
        int numSubnets = 256 / blockSize;
        int usableHosts = blockSize - 2;
        int lastOctetMask = 256 - blockSize;
        
        // Display general subnet information
        System.out.println("\n--- General Subnet Information ---");
        System.out.println("Calculated Subnet Mask : 255.255.255." + lastOctetMask);
        System.out.println("Total Usable Hosts : " + usableHosts + " per subnet");
        System.out.println("Total Subnets Created : " + numSubnets + "\n");
        
        // 3 & 4. Calculate and Display specifics for each subnet
        for (int i = 0; i < numSubnets; ++i) {
            int network = i * blockSize;
            int firstHost = network + 1;
            int lastHost = network + usableHosts;
            int broadcast = network + blockSize - 1;
            
            System.out.println("=========================================");
            System.out.println("Subnet " + (i + 1));
            System.out.println("=========================================");
            System.out.println("Network Address : " + o1 + "." + o2 + "." + o3 + "." + network);
            System.out.println("First Host : " + o1 + "." + o2 + "." + o3 + "." + firstHost);
            System.out.println("Last Host : " + o1 + "." + o2 + "." + o3 + "." + lastHost);
            System.out.println("Broadcast Address : " + o1 + "." + o2 + "." + o3 + "." + broadcast);
            
            // Print all usable IP addresses in this subnet
            System.out.println("\nUsable IP Addresses:");
            for (int j = firstHost; j <= lastHost; ++j) {
                System.out.println(" " + o1 + "." + o2 + "." + o3 + "." + j);
            }
            System.out.println();
        }
        
        scanner.close();
    }
}
