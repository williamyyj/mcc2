package org.cc;

/**
 *
 * @author william
 * @param <E>
 */
public interface IDP<E> {
    public E connection()  throws Exception ;
    public void release() ;
    public Object execute(ICCMap m) throws Exception ; 
}
