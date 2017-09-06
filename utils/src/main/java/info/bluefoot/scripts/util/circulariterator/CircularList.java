package info.bluefoot.scripts.util.circulariterator;

import java.util.List;

/**
 * Provides a {@link CircularIterator}.<br/>
 * 
 * The new interface <tt>CircularIterator</tt> was preferred over reusing
 * <tt>java.util.ListIterator</tt> because the cursor behavior desired breaks
 * ListIterator's contract.<br/>
 * 
 * Operations to modify the underlying list are not supported at the
 * moment.<br/>
 * Example of usage:<br/>
 * 
 * <pre>
LinkedList<Integer> l = new LinkedList<>();
l.add(0);
l.add(90);
l.add(180);
l.add(270);
CircularIterator<Integer> i = new CircularList<Integer>(l).iterator();
System.out.println(i.next() + "\t" + i.nextIndex() + ", " + i.previousIndex()); 
System.out.println(i.previous() + "\t" + i.nextIndex() + ", " + i.previousIndex());
System.out.println(i.previous() + "\t" + i.nextIndex() + ", " + i.previousIndex());
System.out.println(i.next() + "\t" + i.nextIndex() + ", " + i.previousIndex());
System.out.println(i.next() + "\t" + i.nextIndex() + ", " + i.previousIndex());
System.out.println(i.next() + "\t" + i.nextIndex() + ", " + i.previousIndex());
    
// ~ output:
0   1, 3
270 0, 2
180 3, 1
270 0, 2
0   1, 3
90  2, 0
 * </pre>
 * 
 * @author gewton
 * @see info.bluefoot.scripts.util.CircularIterator
 */
public class CircularList<T> implements Iterable<T> {
    private List<T> l;

    public CircularList(List<T> l) {
        this.l = l;
    }
    
    @Override
    public CircularIterator<T> iterator() {
        return new CircularIterator<T>() {
            int pos = -1;

            /**
             * Returns true if the list is not empty
             */
            @Override
            public boolean hasNext() {
                return !l.isEmpty();
            }
            
            /**
             * Advances to the next position and returns the element in that position.
             */
            @Override
            public T next() {
                pos = nextIndex();
                return l.get(pos);
            }

            @Override
            public boolean hasPrevious() {
                return !l.isEmpty();
            }

            @Override
            public T previous() {
                pos = previousIndex();
                return l.get(pos);
            }

            @Override
            public int nextIndex() {
                if(pos==l.size()-1) {
                    return 0;
                } else {
                    return pos+1;
                }
            }

            @Override
            public int previousIndex() {
                if(pos<=0) {
                    return l.size()-1;
                } else {
                    return pos-1;
                }
            }
        };
    }
}
