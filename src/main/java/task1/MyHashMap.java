package task1;

import java.util.*;

public class MyHashMap<K, V> implements MyMap<K, V> {
    /**
     * Проверка функционала
     */
    public static void main(String[] args) {
        MyHashMap<String, String> strings = new MyHashMap<String, String>();
        strings.put(null, "NULL");
        strings.put("a", "A");
        strings.put("b", "B");
        strings.put("c", "C");
        strings.put("d", "D");
        strings.put("e", "E");
        System.out.println("Значение по ключу \"null\":       " + strings.get(null)); // NULL
        System.out.println("Исходное значение по ключу \"a\": " + strings.get("a"));  // a

        strings.put(null, "NEW_NULL");
        System.out.println("Новое значение по ключу \"null\": " + strings.get(null)); // NEW_NULL
        strings.put("a", "ZZ");
        System.out.println("Новое значение по ключу \"a\":    " + strings.get("a"));  // Zz

        System.out.println("Размер = " + strings.size);     // 6

//        strings.remove(null);
//        System.out.println("Попытка получения элемента (NEW_NULL) по ключу после удаления: " + strings.get(null)); // null
        strings.remove("a");
        System.out.println("Попытка получения элемента (а) по ключу после удаления: " + strings.get("a"));  // null

        System.out.println("Размер = " + strings.size);     // 4

        System.out.println("Вместимость внутреннего массива = " + strings.hashTable.length);    // 16

        strings.put("f", "F");
        strings.put("g", "G");
        strings.put("h", "H");
        strings.put("i", "I");
        strings.put("j", "J");
        strings.put("k", "K");
        strings.put("l", "L");
        strings.put("m", "M");
        strings.put("n", "N");
        strings.put("o", "O");
        strings.put("p", "P");
        System.out.println("Вместимость внутреннего массива = " + strings.hashTable.length);    // 32

        for (int i = 0; i < strings.hashTable.length; i++) {
            List<MyHashMap.Node<String, String>> bucket = strings.hashTable[i];
            if (bucket != null) {
                for (MyHashMap.Node<String, String> node : bucket) {
                    System.out.println(node.key + " : " + node.value);
                }
            }
        }

        if (strings.hasNullKey) {
            System.out.println("null : " + strings.nullKeyValue);
        }
    }

    private List<Node<K, V>>[] hashTable;
    private int size;
    private float threshold;

    private V nullKeyValue = null;  // хранение null-ключа
    private boolean hasNullKey = false;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        hashTable = new List[16];
        threshold = hashTable.length * 0.75f;
    }

    @Override
    public boolean put(final K key, final V value) {
        // обработка null-ключа
        if (key == null) {
            if (!hasNullKey) {
                size++;
            }
            hasNullKey = true;
            nullKeyValue = value;
            return true;
        }

        if (size + 1 >= threshold) {
            arrayIncrease();
        }

        int index = hash(key);

        if (hashTable[index] == null) {
            hashTable[index] = new LinkedList<>();
        }

        for (Node<K, V> node : hashTable[index]) {
            if (Objects.equals(node.key, key)) {
                node.value = value; // обновляем существующий ключ
                return true;
            }
        }

        hashTable[index].add(new Node<>(key, value));
        size++;
        return true;
    }

    @Override
    public boolean remove(final K key) {
        // удаление null-ключа
        if (key == null) {
            if (!hasNullKey) return false;
            hasNullKey = false;
            nullKeyValue = null;
            size--;
            return true;
        }

        int index = hash(key);
        List<Node<K, V>> bucket = hashTable[index];
        if (bucket == null) return false;

        Iterator<Node<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Node<K, V> node = iterator.next();
            if (Objects.equals(node.key, key)) {
                iterator.remove();
                size--;
                if (bucket.isEmpty()) hashTable[index] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(final K key) {
        // возврат значения по null-ключу
        if (key == null) {
            return hasNullKey ? nullKeyValue : null;
        }

        int index = hash(key);
        List<Node<K, V>> bucket = hashTable[index];
        if (bucket == null) return null;

        for (Node<K, V> node : bucket) {
            if (Objects.equals(node.key, key)) return node.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private int hash(final K key) {
        int h = (key == null) ? 0 : key.hashCode();
        return (h & 0x7fffffff) % hashTable.length; // гарантирует неотрицательный индекс
    }

    @SuppressWarnings("unchecked")
    private void arrayIncrease() {
        List<Node<K, V>>[] oldTable = hashTable;
        hashTable = new List[oldTable.length * 2];
        threshold = hashTable.length * 0.75f;

        int oldSize = size;
        boolean hadNull = hasNullKey;
        V savedNullValue = nullKeyValue;

        size = 0;
        hasNullKey = false;
        nullKeyValue = null;

        if (hadNull) {
            put(null, savedNullValue);
        }

        for (List<Node<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (Node<K, V> node : bucket) {
                    put(node.key, node.value);
                }
            }
        }

        assert size == oldSize;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int bucketIndex = 0;
            Iterator<Node<K, V>> subIterator = null;
            int returnedCount = 0;
            boolean nullKeyReturned = !hasNullKey;

            @Override
            public boolean hasNext() {
                if (returnedCount >= size) return false;
                if (!nullKeyReturned && hasNullKey) return true;

                while ((subIterator == null || !subIterator.hasNext()) && bucketIndex < hashTable.length) {
                    if (hashTable[bucketIndex] != null) subIterator = hashTable[bucketIndex].iterator();
                    bucketIndex++;
                }
                return subIterator != null && subIterator.hasNext();
            }

            @Override
            public V next() {
                if (!nullKeyReturned && hasNullKey) {
                    nullKeyReturned = true;
                    returnedCount++;
                    return nullKeyValue;
                }
                if (!hasNext()) throw new NoSuchElementException();
                returnedCount++;
                return subIterator.next().value;
            }
        };
    }

    /**
     * Класс Ноды
     */
    private static class Node<K, V> {
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node<?, ?> node)) return false;
            return Objects.equals(this.key, node.key);
        }
    }
}
