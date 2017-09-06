package info.bluefoot.scripts.util.circulariterator;

import java.util.Iterator;

/**
 * <p>Iterator for List that allows traversal in both directions. It moves like a
 * CircularLinkedList traversal: if the last element is reached, a call to
 * {@link #next()} will return the first element. If the first element is
 * reached, a call to {@link #previous()} will return the last element.</p>
 * 
 * <p>There's always a next and previous, unless the underlying list is
 * empty.</p>
 * 
 * <p>Unlike <tt>java.util.ListIterator</tt>, there's no cursor that lies between
 * the <tt>next()</tt> and <tt>previous()</tt> element. Implementations of this
 * interface must operate having a current element, which is what was returned
 * by the last call to <tt>next()</tt> or <tt>previous()</tt>. A call to
 * <tt>previous()</tt> or <tt>next()</tt> moves the position to the previous or
 * next element and then returns it. As a consequence, alternating calls to next
 * and previous will NOT return the same element repeatedly. The only exception
 * to this rule is before the first call to <tt>next()</tt> or
 * <tt>previous()</tt>: the initial position lies between the last and first
 * element, allowing the iterator to return either the first (call to
 * <tt>next()</tt>) or last (call to <tt>previous()</tt>) element. This
 * interface is very similar to <tt>java.util.ListIterator</tt>, except for the
 * behavior described here (and the fact that currently there are no methods to
 * manipulate the underlying list). </p>
 * 
 * @author gewton
 */
public interface CircularIterator<E> extends Iterator<E> {
    /**
     * Returns true if the list is not empty
     */
    boolean hasPrevious();

    /**
     * Returns to the previous position and returns the element in that position.
     */
    E previous();
    
    /**
     * Returns the index of the element that would be returned by a subsequent call to next.
     * If at the end of the list, circles around and returns the first position.
     */
    int nextIndex();
    
    /**
     * Returns the index of the element that would be returned by a subsequent call to previous.
     * If at the beginning of the list, circles around and returns the last position.
     */
    int previousIndex();
}
