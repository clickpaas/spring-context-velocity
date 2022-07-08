//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.generic;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.util.ArrayIterator;
import org.apache.velocity.util.EnumerationIterator;

/** @deprecated */
@DefaultKey("mill")
@Deprecated
public class IteratorTool implements Iterator {
    private Object wrapped;
    private Iterator iterator;
    private boolean wantMore;
    private boolean cachedNext;
    protected Object next;

    public IteratorTool() {
        this((Object)null);
    }

    public IteratorTool(Object wrapped) {
        this.internalWrap(wrapped);
    }

    public IteratorTool wrap(Object list) {
        if (this.wrapped == null) {
            return new IteratorTool(list);
        } else if (list != null) {
            this.internalWrap(list);
            return this;
        } else {
            throw new IllegalArgumentException("Need a valid list to wrap");
        }
    }

    private void internalWrap(Object wrapped) {
        if (wrapped != null) {
            if (wrapped.getClass().isArray()) {
                this.iterator = new ArrayIterator((Object[])((Object[])wrapped));
            } else if (wrapped instanceof Collection) {
                this.iterator = ((Collection)wrapped).iterator();
            } else if (wrapped instanceof Map) {
                this.iterator = ((Map)wrapped).values().iterator();
            } else if (wrapped instanceof Iterator) {
                this.iterator = (Iterator)wrapped;
            } else {
                if (!(wrapped instanceof Enumeration)) {
                    throw new IllegalArgumentException("Don't know how to wrap: " + wrapped);
                }

                this.iterator = new EnumerationIterator((Enumeration)wrapped);
            }

            this.wrapped = wrapped;
            this.wantMore = true;
            this.cachedNext = false;
        } else {
            this.iterator = null;
            this.wrapped = null;
            this.wantMore = false;
            this.cachedNext = false;
        }

    }

    public void reset() {
        if (this.wrapped != null) {
            this.internalWrap(this.wrapped);
        }

    }

    public Object next() {
        if (this.wrapped == null) {
            throw new IllegalStateException("Use wrap() before calling next()");
        } else if (!this.cachedNext) {
            this.cachedNext = true;
            this.next = this.iterator.next();
            return this.next;
        } else {
            return this.next;
        }
    }

    public boolean hasNext() {
        if (this.wantMore) {
            this.wantMore = false;
            return this.hasMore();
        } else {
            this.wantMore = true;
            return false;
        }
    }

    public void remove() throws UnsupportedOperationException {
        if (this.wrapped == null) {
            throw new IllegalStateException("Use wrap() before calling remove()");
        } else {
            this.iterator.remove();
        }
    }

    public Object more() {
        this.wantMore = true;
        if (this.hasMore()) {
            Object next = this.next();
            this.cachedNext = false;
            return next;
        } else {
            return null;
        }
    }

    public boolean hasMore() {
        if (this.wrapped == null) {
            return false;
        } else {
            return this.cachedNext || this.iterator.hasNext();
        }
    }

    public void stop() {
        this.wantMore = false;
    }

    public String toString() {
        StringBuilder out = new StringBuilder(this.getClass().getName());
        if (this.wrapped != null) {
            out.append('(');
            out.append(this.wrapped);
            out.append(')');
        }

        return out.toString();
    }
}
