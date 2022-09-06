package fr.kyo.crkf.Tools;

public class Pair<U, V> {

    /**
     * The first element of this Pair
     */
    private U first;

    /**
     * The second element of this Pair
     */
    private V second;

    /**
     * Constructs a new Pair with the given values.
     *
     * @param first  the first element
     * @param second the second element
     */
    public Pair(U first, V second) {

        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
