import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarverTest {

  SeamCarver sc6x5;
  Picture picture;
  SeamCarver sc8x1;
  SeamCarver sc12x10;
  SeamCarver sc3x4;

  @Before
  public void setup() throws Exception {
    picture = new Picture("seam-test-files/6x5.png");
    sc6x5 = new SeamCarver(picture);
    Picture p  = new Picture("seam-test-files/3x4.png");
    Picture p1  = new Picture("seam-test-files/8x1.png");
    sc8x1 = new SeamCarver(p1);
    sc3x4 = new SeamCarver(p);
    Picture p2  = new Picture("seam-test-files/12x10.png");
    sc12x10 = new SeamCarver(p2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSeamCarver() {
    SeamCarver p = new SeamCarver(null);
  }

  @Test
  public void testPicture() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    assertEquals(sc6x5.picture(), picture);
  }

  @Test
  public void testWidth() {
    assertEquals(sc6x5.width(), 6);
  }

  @Test
  public void testWidth1() {

    assertEquals(sc8x1.width(), 8);
  }

  @Test
  public void testWidth2() {
    assertEquals(sc12x10.width(), 12);
  }


  @Test
  public void testHeight() {
    assertEquals(sc6x5.height(), 5);
  }

  @Test
  public void testHeight1() {
    assertEquals(sc8x1.height(), 1);
  }

  @Test
  public void testHeight2() {
    assertEquals(sc12x10.height(), 10);
  }

  @Test
  public void testEnergyTopBorder() {
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(1000, sc6x5.energy(i, 0), 0.001);
    }
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(1000, sc6x5.energy(0, i), 0.001);
    }
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(1000, sc6x5.energy(i, sc6x5.height()-1), 0.001);
    }
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(1000, sc6x5.energy(sc6x5.width()-1, i), 0.001);
    }
  }


  @Test
  public void testEnergy6x5() {
    assertEquals(237.35, sc6x5.energy(1, 1), 0.01);
  }

  @Test
  public void testEnergy3x4() {
    assertEquals(228.087, sc3x4.energy(1, 2), 0.01);
  }

  @Test
  public void testEnergy12x10() {
    assertEquals(249.92, sc12x10.energy(5, 3), 0.01);
  }

  @Test
  public void testFindVerticalSeam() {
    int[] expected = {3, 4, 3, 2, 1};
    int[] actual = sc6x5.findVerticalSeam();
    for(int i = 0; i < sc6x5.height(); i++) {
      assertEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testFindHorizontalSeamSmall()
  {
    int[] expected = {1, 2, 1};
    int[] actual = sc3x4.findHorizontalSeam();
    assertArrayEquals(expected,actual);
  }

  @Test
  public void testFindHorizontalSeamBig()
  {
    int[] expected = {7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3};
    int[] actual = sc12x10.findHorizontalSeam();
    System.out.println(Arrays.toString(actual));
    assertArrayEquals(expected,actual);
  }

  @Test
  public void testFindHorizontalSeam() {
    int[] expected = {1, 2, 1, 2, 1, 0};
    int[] actual = sc6x5.findHorizontalSeam();
    for(int i = 0; i < sc6x5.width(); i++) {
      assertEquals(expected[i], actual[i]);
    }
    sc6x5.removeHorizontalSeam(actual);
  }


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

  @Test
  public void testRemoveVerticalSeamCheckPixelColorsSmall() {
    Picture original = sc3x4.picture();
    int[] seam = sc3x4.findVerticalSeam();
    sc3x4.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 2, sc3x4.width());
    for(int i = 0; i < sc3x4.height(); i++) {
      if(seam[i] != sc3x4.width()) {
        assertEquals(original.get(seam[i] + 1, i), sc3x4.picture().get(seam[i], i));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(seam[i] - 1, i), sc3x4.picture().get(seam[i], i));
      }
    }

  }

  @Test
  public void testRemoveVerticalSeamCheckPixelColorsBig() {
    Picture original = sc12x10.picture();
    int[] seam = sc12x10.findVerticalSeam();
    sc12x10.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 11, sc12x10.width());
    for(int i = 0; i < sc12x10.height(); i++) {
      if(seam[i] != sc12x10.width()) {
        assertEquals(original.get(seam[i] + 1, i), sc12x10.picture().get(seam[i], i));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(seam[i] - 1, i), sc12x10.picture().get(seam[i], i));
      }
    }

  }


  @Test
  public void testRemoveVerticalSeamCheckPixelEnergies() {
    Picture picture = new Picture("seam-test-files/6x5.png");
    SeamCarver sc6x5RemovedSeam = new SeamCarver(picture);
    int[] seam = sc6x5.findVerticalSeam();
    sc6x5RemovedSeam.removeVerticalSeam(seam);
    assertEquals("The width should decrease by 1", 5, sc6x5RemovedSeam.width());
    for(int i = 0; i < sc6x5.height(); i++) {
      if(seam[i] != 0 || seam[i] != sc6x5.height()) {
        // the energy should change after removing the seam
        assertNotEquals(sc6x5.energy(3, 1), sc6x5RemovedSeam.energy(3, 4), 0.01);
      }
      else {
        assertEquals("If removed border pixel, set next pixel energy to 1000", 1000, sc6x5.picture().get(seam[i], i));
      }
    }
  }


  @Test
  public void testRemoveHorizontalSeamCheckPixelColors() {
    Picture original = sc6x5.picture();
    int[] seam = sc6x5.findHorizontalSeam();
    sc6x5.removeHorizontalSeam(seam);
    assertEquals("The height should decrease by 1", 4, sc6x5.height());
    for(int i = 0; i < sc6x5.width(); i++) {
      if(seam[i] != sc6x5.height()) {
        assertEquals(original.get(i, seam[i] + 1), sc6x5.picture().get(i,seam[i]));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(i,seam[i] - 1), sc6x5.picture().get(i,seam[i]));
      }
    }

  }
  
  @Test
  public void testRemoveHorizontalSeamCheckEnergies() {
    Picture original = sc6x5.picture();
    int[] seam = sc6x5.findHorizontalSeam();
    sc6x5.removeHorizontalSeam(seam);
    assertEquals("The height should decrease by 1", 4, sc6x5.height());
    for(int i = 0; i < sc6x5.width(); i++) {
      if(seam[i] != sc6x5.height()) {
        assertEquals(original.get(i, seam[i] + 1), sc6x5.picture().get(i,seam[i]));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(i,seam[i] - 1), sc6x5.picture().get(i,seam[i]));
      }
    }

  }

  @Test
  public void testRemoveHorizontalSeamCheckEnergiesSmall() {
    Picture original = sc3x4.picture();
    int[] seam = sc3x4.findHorizontalSeam();
    sc3x4.removeHorizontalSeam(seam);
    assertEquals("The height should decrease by 1", 3, sc3x4.height());
    for(int i = 0; i < sc3x4.width(); i++) {
      if(seam[i] != sc3x4.height()) {
        assertEquals(original.get(i, seam[i] + 1), sc3x4.picture().get(i,seam[i]));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(i,seam[i] - 1), sc3x4.picture().get(i,seam[i]));
      }
    }

  }

  @Test
  public void testRemoveHorizontalSeamCheckEnergiesBig() {
    Picture original = sc12x10.picture();
    int[] seam = sc12x10.findHorizontalSeam();
    sc12x10.removeHorizontalSeam(seam);
    assertEquals("The height should decrease by 1", 9, sc12x10.height());
    for(int i = 0; i < sc12x10.width(); i++) {
      if(seam[i] != sc12x10.height()) {
        assertEquals(original.get(i, seam[i] + 1), sc12x10.picture().get(i,seam[i]));
      }
      else {
        assertEquals("If removed last column, last column should be previous pixel",
                original.get(i,seam[i] - 1), sc12x10.picture().get(i,seam[i]));
      }
    }

  }


}
