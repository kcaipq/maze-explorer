package uk.gov.dwp.maze;

import java.io.File;

/**
 * Created by kcai on 28/10/2014.
 */
public class FileMazeBuilder implements MazeBuilder {

    private File file;

    public FileMazeBuilder(String filePath) {
        try {
            this.file = new File(filePath);
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException("File path cannot be null.");
        }
    }

    @Override
    public Maze build() {
        try {
            Square[][] map = MazeFactory.buildMazeMap(file);
            return new Maze(map);
        } catch (IllegalArgumentException ia) {
            return null;
        }
    }
}
