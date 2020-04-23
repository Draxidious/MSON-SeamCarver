import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
    private Picture p;
    private int width;
    private int height;

    private final int BORDERENERGY = 1000;
    private boolean isTransposed;
    private boolean Horiz;

    // create a seam carver object based on the given picture
    // x= col y = row
    // 0,0 is upper left corner
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        p = picture;
        width = p.width();
        height = p.height();


    }

    private double[][] newEgrid() {
        double[][] egrid = new double[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                egrid[x][y] = energy(x, y);
            }
        }
        return egrid;
    }

    // current picture
    public Picture picture() {
        return new Picture(p);
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
        int r = p.get(x - 1, y).getRed() - p.get(x + 1, y).getRed();
        int g = p.get(x - 1, y).getBlue() - p.get(x + 1, y).getBlue();
        int b = p.get(x - 1, y).getGreen() - p.get(x + 1, y).getGreen();
        energy += Math.pow(r, 2) + Math.pow(b, 2) + Math.pow(g, 2);
        r = p.get(x, y - 1).getRed() - p.get(x, y + 1).getRed();
        g = p.get(x, y - 1).getBlue() - p.get(x, y + 1).getBlue();
        b = p.get(x, y - 1).getGreen() - p.get(x, y + 1).getGreen();
        energy += Math.pow(r, 2) + Math.pow(b, 2) + Math.pow(g, 2);

        return Math.sqrt(energy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        p = transpose(p);
        isTransposed = true;
        Horiz = true;
        int[] ret = findVerticalSeam();
        Horiz = false;
        return ret;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] ret = new int[height];
        double[][] relax = new double[width][height];
        double[][] egrid = newEgrid();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0) {
                    relax[x][y] = BORDERENERGY;
                } else {
                    relax[x][y] = egrid[x][y] + smallestOfThreeRelax(x, y, relax);
                }
            }
        }

        double min = Double.POSITIVE_INFINITY;
        int startind = 0;
        for (int x = 0; x < width; x++) {
            if (relax[x][height - 1] < min) {

                startind = x;
                min = relax[x][height - 1];
            }
        }
        ret[ret.length - 1] = startind; // first ind has to be top
        int curind = startind;
        int retind = ret.length - 2;
        for (int y = height - 2; y >= 0; y--) {
            int nextind = smallestOfThreeVertSeam(curind, y, relax);
            ret[retind] = nextind;
            retind--;
            curind = nextind;
        }

        // start from top to relax
        // go from bottom when finding seam

        if (isTransposed) {
            p = transpose(p);
            isTransposed = false;
        }
        return ret;
    }

    private double smallestOfThreeRelax(int x, int y, double[][] relax) {
        double first;
        double second;
        double third;
        if (inbounds(x - 1, y - 1)) first = relax[x - 1][y - 1];
        else first = Double.MAX_VALUE;
        if (inbounds(x, y - 1)) second = relax[x][y - 1];
        else second = Double.MAX_VALUE;
        if (inbounds(x + 1, y - 1)) third = relax[x + 1][y - 1];
        else third = Double.MAX_VALUE;

        if (first <= second && first <= third) {
            return first;
        } else if (second <= first && second <= third) {
            return second;
        } else if (third <= first && third <= second) {
            return third;
        } else {
            throw new IllegalStateException("smallestOfThree method doesn't work");
        }
    }

    private int smallestOfThreeVertSeam(int x, int y, double[][] relax) {
        double first;
        double second;
        double third;
        if (inbounds(x - 1, y)) first = relax[x - 1][y];
        else first = Double.MAX_VALUE;
        if (inbounds(x, y)) second = relax[x][y];
        else second = Double.MAX_VALUE;
        if (inbounds(x + 1, y)) third = relax[x + 1][y];
        else third = Double.MAX_VALUE;

        if (first <= second && first <= third) {
            return x - 1;
        } else if (second <= first && second <= third) {
            return x;
        } else if (third <= first && third <= second) {
            return x + 1;
        } else {
            throw new IllegalStateException("smallestOfThree method doesn't work");
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height() <= 1 || seam.length != width()) {
            throw new IllegalArgumentException();
        }
        p = transpose(p);
        Horiz = true;
        removeVerticalSeam(seam);
        p = transpose(p);
        Horiz = false;
    }

    private void print2D(double[][] arr) {
        for (int r = 0; r < arr[0].length; r++) {
            for (int c = 0; c < arr.length; c++) {
                System.out.print(arr[c][r] + " ");
            }
            System.out.println("");
        }
    }

    private Picture transpose(Picture picture) {
        Picture ret = new Picture(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ret.setRGB(y, x, picture.getRGB(x, y));
            }
        }
        width = ret.width();
        height = ret.height();
        return ret;
    }

    private boolean inbounds(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    private void checkVarying(int[] seam) {
        int prev = seam[0];
        for (int i = 1; i < seam.length; i++) {
            if (seam[i] >= height) throw new IllegalArgumentException();
            if (Math.abs(seam[i] - prev) > 1) throw new IllegalArgumentException();
            prev = seam[i];
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (!Horiz && (seam == null || width() <= 1 || seam.length != height())) {
            throw new IllegalArgumentException();
        }
        checkVarying(seam);

        Picture picture = new Picture(width - 1, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < picture.width(); x++) {
                if (x < seam[y]) {
                    picture.set(x, y, p.get(x, y));
                } else {
                    picture.set(x, y, p.get(x + 1, y));
                }
            }
        }
        width = width - 1;
        p = picture;
    }


}