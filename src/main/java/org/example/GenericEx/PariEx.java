package org.example.GenericEx;

public class PariEx {

    static class Pair<T,V> {
        private T x;
        private V y;

        public Pair(T x, V y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }

        public V getY() {
            return y;
        }

        public void setX(T x) {
            this.x = x;
        }

        public void setY(V y) {
            this.y = y;
        }
    }
    public static <T,V> void swap(Pair<T,V> pair1, Pair<T,V> pair2 ){
        T tempX = pair1.getX();
        V tempY = pair1.getY();

        pair1.setX(pair2.getX());
        pair1.setY(pair2.getY());

        pair2.setX(tempX);
        pair2.setY(tempY);

    }

    public static void main(String[] args) {
        Pair<String, Integer> pair1 = new Pair<>("Hello", 100);
        Pair<String, Integer> pair2 = new Pair<>("World", 200);
        swap(pair1,pair2);
    }
}
