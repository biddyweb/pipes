package com.tinkerpop.pipes.pgm;

import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.pipes.AbstractPipe;

import java.util.Iterator;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class GraphElementPipe<E extends Element> extends AbstractPipe<Graph, E> {

    protected Iterator<E> nextEnds;

    private final ElementType elementType;

    public enum ElementType {
        VERTEX, EDGE
    }

    public GraphElementPipe(final ElementType elementType) {
        this.elementType = elementType;
    }


    protected E processNextStart() {
        if (null != this.nextEnds && this.nextEnds.hasNext()) {
            return this.nextEnds.next();
        } else {
            switch (this.elementType) {
                case VERTEX: {
                    this.nextEnds = (Iterator<E>) this.starts.next().getVertices().iterator();
                    break;
                }
                case EDGE: {
                    this.nextEnds = (Iterator<E>) this.starts.next().getEdges().iterator();
                    break;
                }
            }
            return this.processNextStart();
        }
    }
}
