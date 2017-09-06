package info.bluefoot.scripts.test;

import java.util.LinkedList;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import info.bluefoot.scripts.util.circulariterator.CircularIterator;
import info.bluefoot.scripts.util.circulariterator.CircularList;

public class CircularIteratorTest {
    LinkedList<Integer> l;
    
    @Before
    public void setup() {
        l = new LinkedList<Integer>();
        int size = RandomUtils.nextInt(100);
        for(int i = 0; i < size; i++) {
            l.add(RandomUtils.nextInt());
        }
    }
    
    @Test
    public void testInitialPosition() {
        CircularIterator<Integer> i = new CircularList<Integer>(l).iterator();
        Assert.assertEquals("Previous index must be equals to the last index of the list", l.size()-1, i.previousIndex());
        Assert.assertEquals("Next index must be zero", 0, i.nextIndex());
    }
    
    @Test
    public void testGoingForward() {
        CircularIterator<Integer> i = new CircularList<Integer>(l).iterator();
        for(int currentPosition=0;currentPosition<l.size();currentPosition++) {
            Assert.assertEquals("Call to next should've returned " + l.get(currentPosition), l.get(currentPosition), i.next());
            assertCurrentPosition(i, currentPosition);
        }
        Assert.assertEquals("Call to next at the last index should've returned the first element", l.get(0), i.next());
    }
    
    @Test
    public void testGoingBackwards() {
        CircularIterator<Integer> i = new CircularList<Integer>(l).iterator();
        for(int currentPosition=l.size()-1;currentPosition>=0;currentPosition--) {
            Assert.assertEquals("Call to previous should've returned " + l.get(currentPosition), l.get(currentPosition), i.previous());
            assertCurrentPosition(i, currentPosition);
        }
        Assert.assertEquals("Call to previous at the first index should've returned the last element", l.get(l.size()-1), i.previous());
    }
    
    @Test
    public void testGoingBackAndForth() {
        // Must use specific list because we're asserting literal values. All other tests work with any values.
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(0);
        list.add(90);
        list.add(180);
        list.add(270);
        CircularIterator<Integer> i = new CircularList<Integer>(list).iterator();
        Assert.assertEquals(3, i.previousIndex());
        // Go around
        Assert.assertEquals(new Integer(270), i.previous());
        Assert.assertEquals(new Integer(180), i.previous());
        Assert.assertEquals(new Integer(270), i.next());
        // Test alternate calls to next and previous (do not return same elemnt as a normal ListIterator)
        Assert.assertEquals(new Integer(0), i.next());
        Assert.assertEquals(new Integer(90), i.next());
        Assert.assertEquals(new Integer(0), i.previous());
        Assert.assertEquals(new Integer(90), i.next());
        Assert.assertEquals(new Integer(180), i.next());
        
    }

    private void assertCurrentPosition(CircularIterator<Integer> i, int currentPosition) {
        int expectedNext = currentPosition==l.size()-1 ? 0 : currentPosition+1;  //j+1, unless we're at the end
        int expectedPrev = currentPosition==0 ? l.size()-1 : currentPosition-1;  //j-1, unless we're at the beginning
        Assert.assertEquals("nextIndex() should point to the element after the last one returned (or 0 if at the end) ", expectedNext, i.nextIndex());
        Assert.assertEquals("previousIndex() should point to the element before the last one returned (or -1 if at the beginning)", expectedPrev, i.previousIndex());
    }
}
