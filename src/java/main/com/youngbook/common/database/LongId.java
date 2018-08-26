package com.youngbook.common.database;

import com.youngbook.common.utils.StringUtils;

/**
 * Created by Lee on 2016/9/25.
 */
public class LongId {

    private long value;

    private static final int NODE_SHIFT = 10;
    private static final int SEQ_SHIFT = 12;

    private static final short MAX_NODE = 1024;
    private static final short MAX_SEQUENCE = 4096;


    public static final long NULL_VALUE = Long.MAX_VALUE;

    private short sequence;
    private long referenceTime;

    private int node;

    private static LongId s = null;

    /**
     * A snowflake is designed to operate as a singleton instance within the context of a node.
     * If you deploy different nodes, supplying a unique node id will guarantee the uniqueness
     * of ids generated concurrently on different nodes.
     *
     * @param node This is an id you use to differentiate different nodes.
     */
    public LongId(int node) {
        if (node < 0 || node > MAX_NODE) {
            throw new IllegalArgumentException(String.format("node must be between %s and %s", 0, MAX_NODE));
        }
        this.node = node;
    }

    public static long parseLong(Object id) {

        if (id == null) {
            return NULL_VALUE;
        }

        long temp = Long.parseLong(String.valueOf(id));

        if (LongId.isEmpty(temp)) {
            return NULL_VALUE;
        }

        return temp;
    }

    public static long parseLong(String id) {


        if (StringUtils.isEmpty(id)) {
            return NULL_VALUE;
        }

        return Long.parseLong(id);
    }

    public static void main (String [] args) {
        int node = 2;
        LongId s = new LongId(node);

        for (int i = 0; i < 10000; i++) {
            long id = s.getNext();
            System.out.println(id);
        }
    }

    public static long next() {

        if (s == null) {
            int node = 1;
            s = new LongId(node);
        }

        return s.getNext();
    }

    public static boolean isEmpty(long id) {
        if (id == 0 || id == Long.MAX_VALUE) {
            return true;
        }
        return false;
    }


    /**
     * Generates a k-ordered unique 64-bit integer. Subsequent invocations of this method will produce
     * increasing integer values.
     *
     * @return The next 64-bit integer.
     */
    public long getNext() {

        long currentTime = System.currentTimeMillis();
        long counter;

        synchronized(this) {

            if (currentTime < referenceTime) {
                throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", referenceTime, currentTime));
            } else if (currentTime > referenceTime) {
                this.sequence = 0;
            } else {
                if (this.sequence < LongId.MAX_SEQUENCE) {
                    this.sequence++;
                } else {
                    throw new RuntimeException("Sequence exhausted at " + this.sequence);
                }
            }
            counter = this.sequence;
            referenceTime = currentTime;
        }

        return currentTime << NODE_SHIFT << SEQ_SHIFT | node << SEQ_SHIFT | counter;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
