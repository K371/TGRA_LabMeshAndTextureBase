package com.ru.tgra.graphics.shapes;

public class MazeBuilder {
	public void buildHardCodedMaze(){
		
		/* Row 1 */
		Maze.cells[1][1] = new Cell(false,false);
		Maze.cells[2][1] = new Cell(true,false);
		Maze.cells[3][1] = new Cell(true,true);
		Maze.cells[4][1] = new Cell(false,false);
		Maze.cells[5][1] = new Cell(true,false);
		Maze.cells[6][1] = new Cell(false,true);
		Maze.cells[7][1] = new Cell(false,false);
		Maze.cells[8][1] = new Cell(false,true);
		Maze.cells[9][1] = new Cell(true,false);
		Maze.cells[10][1] = new Cell(false,true);
		Maze.cells[11][1] = new Cell(false,false);
		Maze.cells[12][1] = new Cell(true,false);
		Maze.cells[13][1] = new Cell(false,true);
		
		/* Row 2 */
		Maze.cells[1][2] = new Cell(true,false);
		Maze.cells[2][2] = new Cell(true,false);
		Maze.cells[3][2] = new Cell(true,false);
		Maze.cells[4][2] = new Cell(true,true);
		Maze.cells[5][2] = new Cell(false,false);
		Maze.cells[6][2] = new Cell(true,true);
		Maze.cells[7][2] = new Cell(false,true);
		Maze.cells[8][2] = new Cell(false,false);
		Maze.cells[9][2] = new Cell(true,false);
		Maze.cells[10][2] = new Cell(true,true);
		Maze.cells[11][2] = new Cell(false,true);
		Maze.cells[12][2] = new Cell(false,false);
		Maze.cells[13][2] = new Cell(false,true);
		
		/* Row 3 */
		Maze.cells[1][3] = new Cell(false,false);
		Maze.cells[2][3] = new Cell(false,true);
		Maze.cells[3][3] = new Cell(false,false);
		Maze.cells[4][3] = new Cell(true,false);
		Maze.cells[5][3] = new Cell(true,true);
		Maze.cells[6][3] = new Cell(false,false);
		Maze.cells[7][3] = new Cell(true,true);
		Maze.cells[8][3] = new Cell(true,false);
		Maze.cells[9][3] = new Cell(true,false);
		Maze.cells[10][3] = new Cell(true,false);
		Maze.cells[11][3] = new Cell(true,true);
		Maze.cells[12][3] = new Cell(false,true);
		Maze.cells[13][3] = new Cell(false,true);
		
		/* Row 4 */
		Maze.cells[1][4] = new Cell(false,true);
		Maze.cells[2][4] = new Cell(false,true);
		Maze.cells[3][4] = new Cell(true,false);
		Maze.cells[4][4] = new Cell(false,false);
		Maze.cells[5][4] = new Cell(true,true);
		Maze.cells[6][4] = new Cell(true,false);
		Maze.cells[7][4] = new Cell(true,false);
		Maze.cells[8][4] = new Cell(false,true);
		Maze.cells[9][4] = new Cell(true,false);
		Maze.cells[10][4] = new Cell(false,true);
		Maze.cells[11][4] = new Cell(false,false);
		Maze.cells[12][4] = new Cell(true,true);
		Maze.cells[13][4] = new Cell(false,true);
		
		/* Row 5 */
		Maze.cells[1][5] = new Cell(true,true);
		Maze.cells[2][5] = new Cell(true,false);
		Maze.cells[3][5] = new Cell(true,false);
		Maze.cells[4][5] = new Cell(true,false);
		Maze.cells[5][5] = new Cell(false,false);
		Maze.cells[6][5] = new Cell(true,true);
		Maze.cells[7][5] = new Cell(false,false);
		Maze.cells[8][5] = new Cell(false,true);
		Maze.cells[9][5] = new Cell(false,false);
		Maze.cells[10][5] = new Cell(true,true);
		Maze.cells[11][5] = new Cell(false,true);
		Maze.cells[12][5] = new Cell(false,true);
		Maze.cells[13][5] = new Cell(false,true);
		
		/* Row 6 */
		Maze.cells[1][6] = new Cell(false,false);
		Maze.cells[2][6] = new Cell(true,false);
		Maze.cells[3][6] = new Cell(false,false);
		Maze.cells[4][6] = new Cell(false,true);
		Maze.cells[5][6] = new Cell(false,true);
		Maze.cells[6][6] = new Cell(false,false);
		Maze.cells[7][6] = new Cell(true,true);
		Maze.cells[8][6] = new Cell(true,false);
		Maze.cells[9][6] = new Cell(true,true);
		Maze.cells[10][6] = new Cell(false,false);
		Maze.cells[11][6] = new Cell(true,true);
		Maze.cells[12][6] = new Cell(false,false);
		Maze.cells[13][6] = new Cell(true,true);
		
		/* Row 7 */
		Maze.cells[1][7] = new Cell(false,false);
		Maze.cells[2][7] = new Cell(true,true);
		Maze.cells[3][7] = new Cell(false,true);
		Maze.cells[4][7] = new Cell(true,false);
		Maze.cells[5][7] = new Cell(true,false);
		Maze.cells[6][7] = new Cell(true,true);
		Maze.cells[7][7] = new Cell(false,false);
		Maze.cells[8][7] = new Cell(true,false);
		Maze.cells[9][7] = new Cell(true,false);
		Maze.cells[10][7] = new Cell(true,true);
		Maze.cells[11][7] = new Cell(false,true);
		Maze.cells[12][7] = new Cell(true,false);
		Maze.cells[13][7] = new Cell(false,true);
		
		
		/* Row 8 */
		Maze.cells[1][8] = new Cell(true,false);
		Maze.cells[2][8] = new Cell(false,true);
		Maze.cells[3][8] = new Cell(true,false);
		Maze.cells[4][8] = new Cell(true,true);
		Maze.cells[5][8] = new Cell(false,false);
		Maze.cells[6][8] = new Cell(true,false);
		Maze.cells[7][8] = new Cell(true,true);
		Maze.cells[8][8] = new Cell(true,false);
		Maze.cells[9][8] = new Cell(false,true);
		Maze.cells[10][8] = new Cell(false,false);
		Maze.cells[11][8] = new Cell(true,true);
		Maze.cells[12][8] = new Cell(true,false);
		Maze.cells[13][8] = new Cell(false,true);
		
		/* Row 9 */
		Maze.cells[1][9] = new Cell(true,false);
		Maze.cells[2][9] = new Cell(false,true);
		Maze.cells[3][9] = new Cell(false,false);
		Maze.cells[4][9] = new Cell(false,true);
		Maze.cells[5][9] = new Cell(false,true);
		Maze.cells[6][9] = new Cell(false,false);
		Maze.cells[7][9] = new Cell(true,false);
		Maze.cells[8][9] = new Cell(false,true);
		Maze.cells[9][9] = new Cell(true,false);
		Maze.cells[10][9] = new Cell(false,true);
		Maze.cells[11][9] = new Cell(false,false);
		Maze.cells[12][9] = new Cell(false,true);
		Maze.cells[13][9] = new Cell(false,true);
		
		int HAMMERTIME = 1;
		
		/* Row 10 */
		Maze.cells[1][10] = new Cell(false,false);
		Maze.cells[2][10] = new Cell(true,true);
		Maze.cells[3][10] = new Cell(false,true);
		Maze.cells[4][10] = new Cell(false,false);
		Maze.cells[5][10] = new Cell(true,true);
		Maze.cells[6][10] = new Cell(true,true);
		Maze.cells[7][10] = new Cell(false,false);
		Maze.cells[8][10] = new Cell(true,false);
		Maze.cells[9][10] = new Cell(true,false);
		Maze.cells[10][10] = new Cell(false,false);
		Maze.cells[11][10] = new Cell(false,true);
		Maze.cells[12][10] = new Cell(false,true);
		Maze.cells[13][10] = new Cell(false,true);
		
		stop(HAMMERTIME);
		
		/* Row 11 */
		Maze.cells[1][11] = new Cell(true,false);
		Maze.cells[2][11] = new Cell(false,true);
		Maze.cells[3][11] = new Cell(false,true);
		Maze.cells[4][11] = new Cell(true,true);
		Maze.cells[5][11] = new Cell(false,false);
		Maze.cells[6][11] = new Cell(false,true);
		Maze.cells[7][11] = new Cell(true,true);
		Maze.cells[8][11] = new Cell(false,false);
		Maze.cells[9][11] = new Cell(true,false);
		Maze.cells[10][11] = new Cell(true,true);
		Maze.cells[11][11] = new Cell(true,true);
		Maze.cells[12][11] = new Cell(false,true);
		Maze.cells[13][11] = new Cell(false,true);
		
		/* Row 12 */
		Maze.cells[1][12] = new Cell(false,true);
		Maze.cells[2][12] = new Cell(false,true);
		Maze.cells[3][12] = new Cell(false,true);
		Maze.cells[4][12] = new Cell(false,false);
		Maze.cells[5][12] = new Cell(true,true);
		Maze.cells[6][12] = new Cell(true,false);
		Maze.cells[7][12] = new Cell(false,true);
		Maze.cells[8][12] = new Cell(false,true);
		Maze.cells[9][12] = new Cell(true,false);
		Maze.cells[10][12] = new Cell(true,false);
		Maze.cells[11][12] = new Cell(false,true);
		Maze.cells[12][12] = new Cell(false,true);
		Maze.cells[13][12] = new Cell(false,true);
		
		/* Row 13 */
		Maze.cells[1][13] = new Cell(true,false);
		Maze.cells[2][13] = new Cell(true,true);
		Maze.cells[3][13] = new Cell(true,false);
		Maze.cells[4][13] = new Cell(true,false);
		Maze.cells[5][13] = new Cell(true,false);
		Maze.cells[6][13] = new Cell(true,true);
		Maze.cells[7][13] = new Cell(false,true);
		Maze.cells[8][13] = new Cell(true,false);
		Maze.cells[9][13] = new Cell(true,false);
		Maze.cells[10][13] = new Cell(true,false);
		Maze.cells[11][13] = new Cell(true,true);
		Maze.cells[12][13] = new Cell(true,false);
		Maze.cells[13][13] = new Cell(true,true);
		
	}
	
	public void stop(int x){
		x = 1337;
	}
}
