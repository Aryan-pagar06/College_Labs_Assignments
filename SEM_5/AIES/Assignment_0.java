import java.util.*;

class State {
    int j1, j2;
    List<State> path;

    State(int j1, int j2, List<State> path) {
        this.j1 = j1;
        this.j2 = j2;
        this.path = new ArrayList<>(path);
        this.path.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return j1 == state.j1 && j2 == state.j2;
    }

    @Override
    public int hashCode() {
        return 31 * j1 + j2;
    }

    @Override
    public String toString() {
        return "(" + j1 + ", " + j2 + ")";
    }
}

public class WaterJug {
    public static void waterJug(int c1, int c2, int target, int targetJug) {
        Queue<State> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.add(new State(0, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            String key = current.j1 + "," + current.j2;

            if (visited.contains(key)) continue;
            visited.add(key);

            if ((targetJug == 1 && current.j1 == target) || (targetJug == 2 && current.j2 == target)) {
                System.out.println("\nSolution:");
                for (State s : current.path) {
                    System.out.println(s);
                }
                return;
            }

            List<int[]> successors = new ArrayList<>();
            successors.add(new int[]{c1, current.j2});
            successors.add(new int[]{current.j1, c2});
            successors.add(new int[]{0, current.j2});
            successors.add(new int[]{current.j1, 0});

            int transfer1 = Math.min(current.j1, c2 - current.j2);
            successors.add(new int[]{current.j1 - transfer1, current.j2 + transfer1});

            int transfer2 = Math.min(current.j2, c1 - current.j1);
            successors.add(new int[]{current.j1 + transfer2, current.j2 - transfer2});

            for (int[] next : successors) {
                String nextKey = next[0] + "," + next[1];
                if (!visited.contains(nextKey)) {
                    queue.add(new State(next[0], next[1], current.path));
                }
            }
        }

        System.out.println("No solution exists.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter capacity of Jug 1: ");
        int c1 = scanner.nextInt();
        System.out.print("Enter capacity of Jug 2: ");
        int c2 = scanner.nextInt();
        System.out.print("Enter target amount: ");
        int target = scanner.nextInt();
        System.out.print("Enter target jug (1 or 2): ");
        int targetJug = scanner.nextInt();

        waterJug(c1, c2, target, targetJug);
        scanner.close();
    }
}
