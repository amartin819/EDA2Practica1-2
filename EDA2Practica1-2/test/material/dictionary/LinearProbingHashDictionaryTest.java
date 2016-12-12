package material.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import junit.framework.TestCase;

public class LinearProbingHashDictionaryTest extends TestCase {

    private OAHashDictionary<String, Integer> listin;

    public void testSize() {
        listin = new OAHashDictionary<>();
        assertEquals(listin.size(), 0);

        listin.put("Jose", 912127659);
        listin.put("Angel", 912127658);
        listin.put("Abraham", 912127657);
        listin.put("Mayte", 912127656);
        listin.put("Raul", 912127655);
        listin.put("Jose", 1);
        listin.put("Angel", 2);
        listin.put("Abraham", 3);

        assertEquals(listin.size(), 8);

        listin.remove("Angel");
        listin.remove("Mayte");
        assertEquals(listin.size(), 6);
    }

    public void testIsEmpty() {
        listin = new OAHashDictionary<>();
        assertEquals(listin.isEmpty(), true);
        listin.put("Jose", 912127654);
        assertEquals(listin.isEmpty(), false);
        listin.remove("Jose");
        assertEquals(listin.isEmpty(), true);
    }

    public void testgetAll() {
        listin = new OAHashDictionary<>();
        assertEquals(listin.size(), 0);

        listin.put("Jose", 912127659);
        listin.put("Jose", 912127658);
        listin.put("Abraham", 912127657);
        listin.put("Mayte", 912127656);
        listin.put("Raul", 912127655);
        listin.put("Jose", 1);
        listin.put("Angel", 2);
        listin.put("Abraham", 3);

        TreeSet <Integer> set = new TreeSet<>();
        set.add(912127659);
        set.add(912127658);
        set.add(1);
        
        int cont = 3;
        for (Integer i : listin.getAll("Jose")) {
            set.remove(i);
            assertEquals(set.size(), --cont);
        }
    }

    public void testPutAndGet() {
        listin = new OAHashDictionary<>();

        try {
            listin.get(null);
            fail("Deberia lanzar: InvalidKeyException");

        } catch (RuntimeException e) {
            // OK
        }

        try {
            listin.put(null, 1213123);
            fail("Deberia lanzar: InvalidKeyException");

        } catch (RuntimeException e) {
            // OK
        }

        assertEquals(listin.get("Jose"), null);

        listin.put("Jose", 912127654);
        listin.put("Jose", 912127651);
        listin.put("Andres", 912127624);
        assertEquals(listin.size(), 3);
        assertEquals(listin.get("Jose").intValue(), 912127654);
        assertEquals(listin.get("Andres").intValue(), 912127624);
    }

    public void testRemove() {
        listin = new OAHashDictionary<>();
        listin.put("Jose", 912127651);
        listin.put("Andres", 912127624);
        listin.remove("Andres");
        assertEquals(listin.size(), 1);
        assertEquals(listin.get("Jose").intValue(), 912127651);
        assertEquals(listin.get("Andres"), null);

        try {
            listin.remove(null);
            fail("Deberia lanzar: InvalidKeyException");

        } catch (RuntimeException e) {
            // OK
        }
    }

    public void testValues() {
        listin = new OAHashDictionary<>();
        listin.put("Angel", 912127654);
        listin.put("Jose", 912127651);
        listin.put("Andres", 912127624);
        Iterable<Integer> values = listin.values();
        Iterator<Integer> it = values.iterator();

        ArrayList<Integer> l = new ArrayList<>();

        while (it.hasNext()) {
            l.add(it.next());
        }

        assertEquals(l.size(), 3);
        assertEquals(l.contains((912127654)), true);
        assertEquals(l.contains((912127651)), true);
        assertEquals(l.contains((912127624)), true);
    }

    public void testEntries() {
        listin = new OAHashDictionary<>();
        listin.put("Angel", 912127654);
        listin.put("Jose", 912127651);
        listin.put("Andres", 912127624);
        Iterable<Entry<String, Integer>> entries = listin.entries();
        ArrayList<Entry<String, Integer>> l = new ArrayList<>();

        for (Entry<String, Integer> i : entries) {
            l.add(i);
        }

        assertEquals(l.size(), 3);

        Iterator<Entry<String, Integer>> it = listin.iterator();

        while (it.hasNext()) {
            Entry<String, Integer> e = it.next();
            assertEquals(l.contains(e), true);
        }
    }

    public void testKeys() {
        listin = new OAHashDictionary<>();
        listin.put("Angel", 912127654);
        listin.put("Jose", 912127651);
        listin.put("Andres", 912127624);
        Iterable<String> keys = listin.keys();

        ArrayList<String> l = new ArrayList<>();

        for (String k : keys) {
            l.add(k);
        }

        assertEquals(l.size(), 3);

        Iterator<Entry<String, Integer>> it = listin.iterator();

        while (it.hasNext()) {
            String s = it.next().getKey();
            assertEquals(l.contains(s), true);
        }
    }

    public void testRehash() {
        OAHashDictionary<Integer, Integer> listin1 = new OAHashDictionary<>(10);

        final int NUM_ENTRIES = 1000;

        // Testing size
        for (int cont = 0; cont < NUM_ENTRIES; cont++) {
            listin1.put(1, cont);
        }
        assertEquals(listin1.size(), NUM_ENTRIES);
    }

    public void testRehash2() {
        OAHashDictionary<Integer, Integer> listin1 = new OAHashDictionary<>(10);

        final int NUM_ENTRIES = 1000;

        int cont2;
        for (int cont = 1; cont <= NUM_ENTRIES; cont++) {
            listin1.put(cont, cont);
            cont2 = 1;
            while ((listin1.get(cont2) != null) && (cont2 <= cont)) {
                cont2++;
            }
            assertEquals(cont2, cont + 1);
        }
    }

}
