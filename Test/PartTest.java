/*
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static com.company.Part.calcSumOfWidths;
import static com.company.Part.drawPartsFromList;
import static com.company.Part.drawShaft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class PartTest {



  protected static final int LENGTH = 5;
  protected static final int INITIAL = 7;
  private Array<Integer> array;

  public abstract List<Part> createArray();

  @Before
  public void setup() {
    array = createArray();
  }

  @Test
  public void testReadFileIntoList() {

    for (String s:
         ) {

    }
    assertEquals(INITIAL, array.get(4).intValue());
  }



  @Test
  public void testGetIndexWithDefaultValue() {
    assertEquals(INITIAL, array.get(4).intValue());
  }

  @Test
  public void testConstructor() {
    assertEquals(LENGTH, array.length());
  }

  @Test
  public void testPut() {
    array.put(0, 20);
    assertEquals(20, array.get(0).intValue());
    array.put(0, INITIAL);
    assertEquals(INITIAL, array.get(0).intValue());
    array.put(4, 7);
    assertEquals(7, array.get(4).intValue());
    array.put(3, -27);
    assertEquals(-27, array.get(3).intValue());
  }

  @Test(expected = IndexException.class)
  public void testPutThrowsExceptionBelow() {
    assertEquals(LENGTH, array.length());
    array.put(-1, 30);
  }

  @Test(expected = IndexException.class)
  public void testPutThrowsExceptionAbove() {
    assertEquals(LENGTH, array.length());
    array.put(666, 2);
  }

  @Test(expected = IndexException.class)
  public void testGetThrowsException() {
    array.get(110);
  }

  @Test
  public void getWorksAfterPut() {
    assertEquals(LENGTH, array.length());
    array.put(0, 3);
    array.put(LENGTH - 1, 10);
    assertEquals(3, array.get(0).intValue());
    assertEquals(10, array.get(LENGTH - 1).intValue());

  }

  @Test
  public void iteratorWorks() {
    Iterator<Integer> it = array.iterator();

    array.put(0, 3);
    array.put(LENGTH - 1, 10);

    assertTrue(it.hasNext());
    assertEquals(3, it.next().intValue());
    assertEquals(INITIAL, it.next().intValue());
    assertEquals(INITIAL, it.next().intValue());
    assertEquals(INITIAL, it.next().intValue());
    assertEquals(10, it.next().intValue());

  }

  @Test
  public void testIteratorForEachLoop() {
    array.put(0, 2);
    array.put(1, 6);
    array.put(2, 1);
    int i = 0;
    for (Integer val: array) {
      assertEquals(array.get(i), val);
      i++;
    }

  }
}

*/
