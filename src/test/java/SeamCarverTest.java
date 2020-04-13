import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

public class SeamCarverTest {

  SeamCarver sc6x5;
  Picture picture;

  @Before
  public void setup() throws Exception {
    picture = new Picture("seam-test-files/6x5.png");
    sc6x5 = new SeamCarver(picture);
  }

  @Test 
  public void testSeamCarver() {
    //TODO test for IllegalArgumentException if passed in null picture
    fail("Not yet implemented");
  }

  @Test
  public void testPicture() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    assertEquals(sc6x5.picture(), picture);
  }

  @Test
  public void testPictureDefensiveCopy() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    SeamCarver sc6x5 = new SeamCarver(picture);
    picture.setRGB(0, 0, 0);
    assertNotEquals(sc6x5.picture(), picture);
  }

  @Test
  public void testWidth() {
    assertEquals(sc6x5.width(), 6);
  }

  //TODO: write testWidth test cases for two other input files (one small, one large)


  @Test
  public void testHeight() {
    assertEquals(sc6x5.height(), 5);
  }

  //TODO: write testHeight test cases for two other input files (one small, one large)


  @Test
  public void testEnergyTopBorder() {
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(1000, sc6x5.energy(i, 0), 0.001);
    }
  }

  //TODO write testEnergy test cases for left border, bottom border, and right border

  @Test
  public void testEnergy6x5() {
    assertEquals(237.35, sc6x5.energy(1, 1), 0.01);
  }

  //TODO: write testEnergy test cases for two other input files (one small, one large)

  @Test
  public void testFindVerticalSeam() {
    int[] expected = {3, 4, 3, 2, 1};
    int[] actual = sc6x5.findVerticalSeam();
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  //TODO: write testFindVerticalSeam test cases for two other input files (one small, one large)

  @Test
  public void testFindHorizontalSeam() {
    int[] expected = {1, 2, 1, 2, 1, 0};
    int[] actual = sc6x5.findHorizontalSeam();
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  //TODO: write testFindHorizontalSeam test cases for two other input files (one small, one large)

  @Test
  public void testRemoveVerticalSeamCheckPixelColors() {
    Picture original = sc6x5.picture();
    int[] seam = sc6x5.findVerticalSeam();
    sc6x5.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 5, sc6x5.width());
    for(int i = 0; i < sc6x5.height(); i++) {
      if(seam[i] != sc6x5.width()) {
        assertEquals(original.get(seam[i] + 1, i), sc6x5.picture().get(seam[i], i));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel", 
            original.get(seam[i] - 1, i), sc6x5.picture().get(seam[i], i));
      }
    }

  }

  //TODO: write testRemoveVerticalSeamCheckPixelColors test cases for two other input files (one small, one large)
  
  @Test
  public void testRemoveVerticalSeamCheckPixelEnergies() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    SeamCarver sc6x5RemovedSeam = new SeamCarver(picture);
    int[] seam = sc6x5.findVerticalSeam();
    sc6x5RemovedSeam.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 5, sc6x5RemovedSeam.width());
    for(int i = 0; i < sc6x5.height(); i++) {
      if(seam[i] != 0 || seam[i] != sc6x5.width()) {
        assertEquals(sc6x5.energy(seam[i] + 1, i), sc6x5RemovedSeam.energy(seam[i], i), 0.01);
      }
      else {
        assertEquals("If removed border pixel, set next pixel energy to 1000", 1000, sc6x5.picture().get(seam[i], i));
      }
    }

  }


  @Test
  public void testRemoveHorizontalSeamCheckPixelColors() {
    //TODO write testRemoveHoriztonalCheckPixelColors using sc6x5
    fail("Not yet implemented");
  }
  
  @Test
  public void testRemoveHorizontalSeamCheckEnergies() {
    //TODO write testRemoveHoriztonalCheckEnergies using sc6x5
    fail("Not yet implemented");
  }

  //TODO: write testRemoveHorizontalSeam test cases for two other input files (one small, one large)

}
