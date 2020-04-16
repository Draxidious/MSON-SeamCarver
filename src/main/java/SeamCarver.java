import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;
    private final int BORDERENERGY = 1000;

    //create a seam carver object based on the given picture
    // x= col y = row
    // 0,0 is upper left corner
    public SeamCarver(Picture picture) {
        p = picture;
        width = p.width();
        height = p.height();
    }

    // current picture
    public Picture picture() {
        return p;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    // checks if surrounding pixels are the same or drastically different
    public double energy(int x, int y) {
        if (!inbounds(x, y)) throw new IllegalArgumentException("invalid coords for energy method");

        if (x == 0 || x == width - 1 || y == 0 || y == height - 1) return BORDERENERGY;
        int energy = 0;
        int[] DX = {1, 0, 0, -1};
        int[] DY = {0, 1, -1, 0};
        for (int i = 0; i < DX.length; i++) {
            int nx = x + DX[i];
            int ny = y + DY[i];
            if (!inbounds(nx, ny)) continue;
            int r = Math.abs(p.get(x, y).getRed() - p.get(nx, ny).getRed());
            int g = Math.abs(p.get(x, y).getBlue() - p.get(nx, ny).getBlue());
            int b = Math.abs(p.get(x, y).getGreen() - p.get(nx, ny).getGreen());
            energy += (r * r) + (b * b) + (g * g);
        }
        return Math.sqrt(energy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    private boolean inbounds(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }
}