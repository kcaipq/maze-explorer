1) assume the explorer robot can only move 1 step in one of 4 directions. Valid moves are:
    a) Up: (x,y) -> (x,y-1)
    b) Right East: (x,y) -> (x+1,y)
    c) Down: (x,y) -> (x,y+1)
    d) Left: (x,y) -> (x-1,y)
    note: positions are noted in zero-based coordinates

2) assume robot can only move through spaces " " within the maze
3) the explorer should search for a path from the starting position "S" to the "F" position until it finds one or until it exhausts all possibilities. 
In addition, it should mark the path it finds (if any) in the maze.