import java.util.*;

public class TspHillClimbing {

    public static int cost(List<Integer> path, int[][] graph) {
        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            total += graph[path.get(i)][path.get(i + 1)];
        }
        total += graph[path.get(path.size() - 1)][path.get(0)];
        return total;
    }

    public static void hillClimbing(int[][] graph, int start) {
        int n = graph.length;

        // Initialize path: [0, 1, 2, ..., n-1]
        List<Integer> path = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            path.add(i);
        }
        
        // Ensure path starts with the requested 'start' city
        path.remove((Integer) start);
        path.add(0, start);

        int currentCost = cost(path, graph);

        while (true) {
            List<Integer> bestPath = new ArrayList<>(path);
            int bestCost = currentCost;

            // Generate neighbor paths by swapping pairs of cities
            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    List<Integer> newPath = new ArrayList<>(path);
                    
                    // Swap elements
                    Collections.swap(newPath, i, j);

                    int newCost = cost(newPath, graph);

                    if (newCost < bestCost) {
                        bestCost = newCost;
                        bestPath = newPath;
                    }
                }
            }

            // If a better neighbor is found, move to it; otherwise stop (local optimum)
            if (bestCost < currentCost) {
                path = bestPath;
                currentCost = bestCost;
            } else {
                break;
            }
        }

        // Print final result matching the exact Python formatting
        System.out.println("\nBest Tour:");
        StringBuilder tourString = new StringBuilder();
        for (int city : path) {
            tourString.append(city).append(" -> ");
        }
        tourString.append(path.get(0));
        System.out.println(tourString.toString());
        System.out.println("Cost = " + currentCost);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of cities: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline left behind by nextInt()

        int[][] graph = new int[n][n];

        System.out.println("Enter the distance matrix:");
        int rowCount = 0;
        while (rowCount < n) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != n) {
                    System.out.println("Enter exactly " + n + " numbers.");
                    continue;
                }

                for (int j = 0; j < n; j++) {
                    graph[rowCount][j] = Integer.parseInt(parts[j]);
                }
                rowCount++;

            } catch (NumberFormatException e) {
                System.out.println("Enter valid integers.");
            }
        }

        System.out.print("Enter starting city (0 to " + (n - 1) + "): ");
        int start = scanner.nextInt();

        hillClimbing(graph, start);
        scanner.close();
    }
}
