import java.util.*;

class Node implements Comparable<Node> {
    int[][] state;
    int g;
    int h;
    int f;
    List<int[][]> path;

    public Node(int[][] state, int g, int h, List<int[][]> path) {
        this.state = state;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.path = new ArrayList<>(path);
        this.path.add(state);
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}

public class EightPuzzleAStar {

    public static int heuristic(int[][] state, int[][] goal) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != 0 && state[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int[] findBlank(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static List<int[][]> neighbors(int[][] state) {
        int[] blank = findBlank(state);
        int x = blank[0];
        int y = blank[1];

        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        List<int[][]> result = new ArrayList<>();

        for (int[] move : moves) {
            int nx = x + move[0];
            int ny = y + move[1];

            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                // Deep copy current matrix
                int[][] newState = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    newState[i] = Arrays.copyOf(state[i], 3);
                }
                // Swap pieces
                newState[x][y] = newState[nx][ny];
                newState[nx][ny] = 0;
                result.add(newState);
            }
        }
        return result;
    }

    // Convert 2D array into a string representation for proper hashing in a Set
    public static String getStateKey(int[][] state) {
        return Arrays.deepToString(state);
    }

    public static void astar(int[][] start, int[][] goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        pq.add(new Node(start, 0, heuristic(start, goal), new ArrayList<>()));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            String key = getStateKey(current.state);

            if (visited.contains(key)) {
                continue;
            }

            visited.add(key);

            if (Arrays.deepEquals(current.state, goal)) {
                System.out.println("\nSolution Found:\n");
                for (int[][] step : current.path) {
                    for (int[] row : step) {
                        System.out.println(Arrays.toString(row));
                    }
                    System.out.println();
                }
                return;
            }

            for (int[][] nxt : neighbors(current.state)) {
                if (!visited.contains(getStateKey(nxt))) {
                    int h = heuristic(nxt, goal);
                    pq.add(new Node(nxt, current.g + 1, h, current.path));
                }
            }
        }
        System.out.println("No Solution");
    }

    public static int[][] readState(String name) {
        Scanner scanner = new Scanner(System.in);
        int[][] state = new int[3][3];
        System.out.println("Enter " + name + " State (3 rows of 3 integers separated by spaces):");

        int rowCount = 0;
        while (rowCount < 3) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+");

                if (parts.length != 3) {
                    System.out.println("Enter exactly 3 numbers.");
                    continue;
                }

                for (int j = 0; j < 3; j++) {
                    state[rowCount][j] = Integer.parseInt(parts[j]);
                }
                rowCount++;

            } catch (NumberFormatException e) {
                System.out.println("Enter valid integers.");
            }
        }
        return state;
    }

    public static void main(String[] args) {
        int[][] start = readState("Initial");
        int[][] goal = readState("Goal");
        astar(start, goal);
    }
}
