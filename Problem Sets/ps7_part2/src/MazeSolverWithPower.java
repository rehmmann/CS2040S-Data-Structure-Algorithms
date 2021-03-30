import java.util.ArrayList;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private boolean solved = false;
	private boolean[][] visited;
	private int endRow, endCol;
	private Pair lastRoom;
	private ArrayList<Pair> visited2 = new ArrayList<Pair>();


	ArrayList<Integer> tracker = new ArrayList<>(100);//each index is frontier level & value stored is number of nodes at that level
//	ArrayList<Integer> tracker2 = new ArrayList<>(100);//each index is frontier level & value stored is number of nodes at that level

	int finRoom = 0;

class Pair {
	int row;
	int col;
	Pair parent;
	int level = 0;
	int power;
	public Pair(int i, int j) {
		this.row = i;
		this.col = j;
		this.power = 0;
	}
	public Pair(int i, int j,int p) {
		this.row = i;
		this.col = j;
		this.power = p;
	}

	@Override
	public boolean equals(Object p) {
		if (p instanceof Pair) {
			Pair temp = (Pair) p;
			if(col == temp.col && row == temp.row && power == temp.power) {
				return true;
			}
		}
		return false;
	}


	@Override
	public String toString() {
		return "{" + row + " " + col +"Power: " + power + "}";
	}
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

	private boolean canGo2(int row, int col, int dir,int power) {

		// not needed since our maze has a surrounding block of wall

		// but Joe the Average Coder is a defensive coder!

		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;

		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		if(power > 0){
			return true;
		}

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

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];//Initialises array of false
		solved = false;
	}

//	@Override
//	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
//		// TODO: Find shortest path.
//		lastRoom = null;
//		tracker.clear();
//		visited2.clear();
//		for (int i = 0; i < 100; i++) {
//			tracker.add(0);
//		}
//		if (maze == null) {
//			throw new Exception("Oh no! You cannot call me without initializing the maze!");
//		}
//		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
//				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
//			throw new IllegalArgumentException("Invalid start/end coordinate");
//		}
//
//		// set all visited flag to false
//
//		// before we begin our search
//		for (int i = 0; i < maze.getRows(); ++i) {
//			for (int j = 0; j < maze.getColumns(); ++j) {
//				this.visited[i][j] = false;
//				maze.getRoom(i, j).onPath = false;
//			}
//		}
//		this.endRow = endRow;
//		this.endCol = endCol;
//		solved = true;
//		solve(startRow, startCol);
//
//		finRoom = finRoom - 1;
//
//		return lastRoom == null ? null : finRoom;
//	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		return pathSearch(startRow, startCol, endRow, endCol, 0);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
//		if (superpowers == 0) {
//			return pathSearch(startRow, startCol,endRow, endCol);
//		}
		lastRoom = null;
		tracker.clear();
		visited2.clear();
		visited2.add(new Pair(startRow,startCol,superpowers));
		for (int i = 0; i < 100; i++) {
			tracker.add(0);
		}
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
		this.visited[startRow][startCol] = true;
		this.endRow = endRow;
		this.endCol = endCol;
		this.solved = true;
//		if(startCol == endCol && startRow == endRow && superpowers != 0){
//			return pathSearch(startRow,startCol,endRow,endCol,0);
//		}
		solve2(startRow, startCol,superpowers);

		finRoom = finRoom - 1;

		return lastRoom == null ? null : finRoom;
	}

	private void solve(int row, int col) {

		ArrayList<Pair> frontier = new ArrayList<>();
		frontier.add(new Pair(row, col));//Add starting point
		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		int rooms = 0;
		int i, j;

		while (!frontier.isEmpty()) {
			rooms++;
			ArrayList<Pair> next = new ArrayList<>();
			for (Pair p : frontier) {
				if (p.row == endRow && p.col == endCol) {

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
							visited[i][j] = true;//Set visited position to true
							Pair child = new Pair(i, j);
							child.parent = p;
							next.add(child);
							child.level = child.parent.level + 1;
							tracker.set(child.level, tracker.get(child.level) + 1);

						}
					}
				}
			}
			frontier = next;
		}
	}

	private void solve2(int row, int col,int superPower) {

		ArrayList<Pair> frontier = new ArrayList<>();
		frontier.add(new Pair(row, col,superPower));//Add starting point
		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		int rooms = 0;
		int i, j;
		int x = Integer.MAX_VALUE;
		int a = 0;
		while (!frontier.isEmpty()) {
			rooms++;
			ArrayList<Pair> next = new ArrayList<>();
			for (Pair p : frontier) {
//				System.out.println(visited2.contains(p));

				if (p.row == endRow && p.col == endCol) {//Determines you have reached the End Point
					System.out.println(p);

					if(a == 0){
						System.out.println(p + "I AM INSIDE");
						visited[endRow][endCol] = true;
						a++;
						x = p.power;
//						System.out.println(p);
						lastRoom = new Pair(p.row, p.col,p.power);
						maze.getRoom(p.row, p.col).onPath = true;
						TraceBack(p);
						finRoom = rooms;
					}

//					return;//Kills the function

				}

				for (int direction = 0; direction < 4; ++direction) {
//					System.out.println(p.equals(p.parent));

					if (canGo(p.row, p.col, direction)) { //
//						System.out.println(p + " Able to go! ");
						i = p.row + DELTAS[direction][0];//New row moving into
						j = p.col + DELTAS[direction][1];//New col moving into

//						if (!visited[i][j] || !(visited[i][j] && p.parent.row == i && p.parent.col == j) ) {//Parent is the next spot
						if(!visited2.contains(new Pair(i,j,p.power))){

							visited2.add(new Pair(i,j,p.power));
//							visited[i][j] = true;//Set visited position to true
							Pair child = new Pair(i, j, p.power);
							child.parent = p;
							next.add(child);

							child.level = child.parent.level + 1;
							if (!visited[i][j]) {
								visited[i][j] = true;
								tracker.set(child.level, tracker.get(child.level) + 1);//each index is frontier level & value stored is number of nodes at that level
							}

						}
					}
					 else if(canGo2(p.row, p.col, direction,p.power)){
//						System.out.println(p);

						i = p.row + DELTAS[direction][0];//New row moving into
						j = p.col + DELTAS[direction][1];//New col moving into
						if(!visited2.contains(new Pair(i,j,p.power - 1))) {
							visited2.add(new Pair(i,j,p.power - 1));
//						if (!visited[i][j] || !(visited[i][j] && p.parent.row == i && p.parent.col == j) ) {//Parent is the next spot
//							visited[i][j] = true;//Set visited position to true
							Pair child = new Pair(i, j, p.power - 1);//Child power reduced
							child.parent = p;
							next.add(child);
							child.level = child.parent.level + 1;
							if (!visited[i][j]) {
								visited[i][j] = true;
								tracker.set(child.level, tracker.get(child.level) + 1);//each index is frontier level & value stored is number of nodes at that level
							}
						}
					}
				}
			}

			frontier = next;
		}
	}

	public void TraceBack(Pair p) {//Sets path to true all the way
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
		if (k < 0 || k > tracker.size()) {
			return  0;
		} else {
			tracker.set(0, 1);
			return tracker.get(k);
		}

	}

	public static void main(String[] args) {

		try {

			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0,0,3,3,2));
			MazePrinter.printMaze(maze);
			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}

//			Maze maze2 = Maze.readMaze("maze-dense.txt");
//			IMazeSolverWithPower solver2 = new MazeSolverWithPower();
//			solver2.initialize(maze2);
//
//			System.out.println(solver2.pathSearch(3,3,3,3,0));
//			MazePrinter.printMaze(maze2);
//
//			for (int i = 0; i <= 20; ++i) {
//				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
