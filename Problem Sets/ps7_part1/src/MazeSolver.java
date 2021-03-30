
import java.util.ArrayList;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][]{
			{-1, 0}, // North
			{1, 0}, // South
			{0, 1}, // East
			{0, -1} // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow, endCol;
	private Pair lastRoom;

	class Pair {
		int row;
		int col;
		Pair parent;
		int level = 0;

		public Pair(int i, int j) {
			this.row = i;
			this.col = j;
		}

		@Override
		public String toString() {
			return "{" + row + " " + col + "}";
		}
	}


	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];//Initialises array of false
		solved = false;
	}

	ArrayList<Integer> tracker = new ArrayList<>(100);
	int finRoom = 0;

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		lastRoom = null;
		tracker.clear();
//		System.out.println(tracker);
		for (int i = 0; i < 100; i++) {
			tracker.add(0);
		}
//		System.out.println(tracker.toString());
//		for(int i=0;i<tracker.size();i++){
//			tracker.set(i,0);
//		}
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;
		solved = true;

		solve(startRow, startCol);
		finRoom = finRoom - 1;
		return lastRoom == null ? null : finRoom;
//		return finRoom;
	}

	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}


	private void solve(int row, int col) {
		ArrayList<Pair> frontier = new ArrayList<>();
		frontier.add(new Pair(row, col));//Add starting point
		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		int rooms = 0;
		int i, j;
//		int counter = 0;
//		tracker.add(1);
		while (!frontier.isEmpty()) {
			rooms++;
			ArrayList<Pair> next = new ArrayList<>();
			for (Pair p : frontier) {
				if (p.row == endRow && p.col == endCol) {
//								System.out.println(tracker);
					lastRoom = new Pair(p.row, p.col);
					maze.getRoom(p.row, p.col).onPath = true;
					TraceBack(p);
					finRoom = rooms;
				}
				for (int direction = 0; direction < 4; ++direction) {

					if (canGo(p.row, p.col, direction)) { // can we go in that direction?
						i = p.row + DELTAS[direction][0];//New row moving into
						j = p.col + DELTAS[direction][1];//New col moving into

						if (!visited[i][j]) {
//							counter++;
							visited[i][j] = true;//Set visited position to true
//							maze.getRoom(i, j).onPath = true;//Set Room boolean onPath to true
							Pair child = new Pair(i, j);
							child.parent = p;
							next.add(child);
							child.level = child.parent.level + 1;
//							System.out.println(tracker.get(child.level));
							tracker.set(child.level, tracker.get(child.level) + 1);


						}
					}

				}

			}
			frontier = next;
		}
	}


	public void TraceBack(Pair p) {
		if (p.parent == null) {
			return;
		} else {
			maze.getRoom(p.row, p.col).onPath = true;
			TraceBack(p.parent);
		}
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.

		tracker.set(0, 1);
		return tracker.get(k);
	}

	public static void main(String[] args) {
		try {
//			Maze maze = Maze.readMaze("maze-empty.txt");
//			IMazeSolver solver = new MazeSolver();
//			solver.initialize(maze);
//
//			System.out.println(solver.pathSearch(0, 0, 0,1));
			//MazePrinter.printMaze(maze);
			//ImprovedMazePrinter.printMaze(maze);
//			for (int i  =0 ; i < 4; i++) {
//				for (int j = 0; j<4; j++) {
//					System.out.println("i: " + i + ",j: " + j + " -> " + solver.pathSearch(0,0,i,j));
//				}
//			}
			Maze maze2 = Maze.readMaze("maze-dense.txt");
			IMazeSolver solver2 = new MazeSolver();
			solver2.initialize(maze2);
//
			System.out.println(solver2.pathSearch(3,2,3,3));
//			for (int i = 0; i <= 9; ++i) {
//				System.out.println("Steps " + i + " Rooms: " + solver2.numReachable(i));
//			}

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver2.numReachable(i));
			}
//			System.out.println("k: 1000 -> " + solver.numReachable(1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

