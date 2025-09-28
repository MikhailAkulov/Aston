package task1;

import java.util.*;

public class MyHashMap<K, V> implements MyMap<K, V> {
    /**
     * Проверка функционала
     */
    public static void main(String[] args) {
        MyHashMap<String, String> strings = new MyHashMap<String, String>();
        strings.put("a", "A");
        strings.put("b", "B");
        strings.put("c", "C");
        strings.put("d", "D");
        strings.put("e", "E");
        System.out.println("Исходное значение по ключу \"a\":       " + strings.get("a"));

        strings.put("a", "ZZ");
        System.out.println("Перезаписанное значение по ключу \"a\": " + strings.get("a"));

        System.out.println("Размер = " + strings.size);

        strings.remove("a");
        System.out.println("Попытка получения элемента по ключу после удаления: " + strings.get("a"));

        System.out.println("Вместимость внутреннего массива = " + strings.hashTable.length);

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
        System.out.println("Вместимость внутреннего массива = " + strings.hashTable.length);
    }

    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    public MyHashMap() {
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    @Override
    public boolean put(final K key, final V value) {
        if (size + 1 >= threshold) {
            threshold *= 2;
            arrayIncrease();
        }

        Node<K, V> newNode = new Node<>(key, value);
//        int index = newNode.hash();
        int index = hash(key);

        if (hashTable[index] == null) {
            return firstAdd(index, newNode);
        }

        List<Node<K, V>> nodeList = hashTable[index].getNodes();

        for (Node<K, V> node : nodeList) {
            if (keyExistButValueNew(node, newNode, value) || collisionProcessing(node, newNode, nodeList)) {
                return true;
            }
        }
        return false;
    }

    // Первое добавление в ячейку массива hashTable
    private boolean firstAdd(int index, Node<K, V> newNode) {
        hashTable[index] = new Node<>(null, null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

    // Обновление значения по ключу
    private boolean keyExistButValueNew(final Node<K, V> nodeFromList, final Node<K, V> newNode, final V value) {
        if (newNode.getKey().equals(nodeFromList.getKey()) && !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue(value);
            return true;
        }
        return false;
    }

    // Обработка коллизии
    private boolean collisionProcessing(final Node<K, V> nodeFromList, final Node<K, V> newNode, final List<Node<K, V>> nodes) {
        if (newNode.hashCode() == nodeFromList.hashCode() &&
                !Objects.equals(newNode.key, nodeFromList.key) &&
                !Objects.equals(newNode.value, nodeFromList.value)) {
            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    // Увеличение массива
    private void arrayIncrease() {
        Node<K, V>[] tempHashTable = hashTable;
        hashTable = new Node[tempHashTable.length * 2];
        size = 0;
        for (Node<K, V> node : tempHashTable) {
            if (node != null) {
                for (Node<K, V> n : node.getNodes()) {
                    put(n.key, n.value);
                }
            }
        }
    }

    @Override
    public boolean remove(final K key) {
        int index = hash(key);
        if (hashTable[index] == null) {
            return false;
        }
        if (hashTable[index].getNodes().size() == 1) {
            hashTable[index].getNodes().removeFirst();
//            hashTable[index] =  null;
            return true;
        }

        List<Node<K, V>> nodeList = hashTable[index].getNodes();
        for (Node<K, V> node : nodeList) {
            if (key.equals(node.getKey())) {
                nodeList.remove(node);
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(final K key) {
        int index = hash(key);
        if (index < hashTable.length && hashTable[index] != null) { // Надо бы разделить на 2 отдельные проверки и выброс соответствующего исключения

            if (hashTable[index].getNodes().size() == 1) {
                return hashTable[index].getNodes().get(0).getValue();
            }

            List<Node<K, V>> list = hashTable[index].getNodes();
            for (Node<K, V> node : list) {
                if (key.equals(node.getKey())) {
                    return node.getValue();
                }
            }
        }
        return null;
//        throw new ArrayIndexOutOfBoundsException("Key not found");
    }

    @Override
    public int size() {
        return size;
    }

    // Вычисление ячейки массива для размещения элемента
    private int hash(final K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % hashTable.length;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int counterArray = 0;
            int valuesCounter = 0;
            Iterator<Node<K, V>> subIterator = null;
            @Override
            public boolean hasNext() {
                if (valuesCounter == size) {
                    return false;
                }
                if (subIterator == null || !subIterator.hasNext()) {
                    if (moveToNextCell()) {
                        subIterator = hashTable[counterArray].getNodes().iterator();
                    } else {
                        return false;
                    }
                }
                return subIterator.hasNext();
            }

            private boolean moveToNextCell() {
                counterArray++;
//                while (hashTable[counterArray] == null) {
//                    counterArray++;
//                }
                while (counterArray < hashTable.length && hashTable[counterArray] == null) {
                    counterArray++;
                }
//                return hashTable[counterArray] != null;
                return counterArray < hashTable.length && hashTable[counterArray] != null;
            }

            @Override
            public V next() {
                valuesCounter++;
                return subIterator.next().getValue();
            }
        };
    }

    // Корневые ноды в каждой ячейке массива hashTable имеют key и value = null
    private class Node<K, V> {
        private List<Node<K, V>> nodes; // Все реальные ноды хранятся внутри LinkedList со своими ключами и значекниями
        private int hash;
        private K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<Node<K, V>>();
        }

        private List<Node<K, V>> getNodes() {
            return nodes;
        }

        private int hash() {
            return hashCode() % hashTable.length;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }

        private void removeElement(Node<K, V> node) {
            nodes.remove(node);
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            hash = hash * 17 + value.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof MyHashMap.Node) {
                Node<K, V> node = (Node) o;
                return (Objects.equals(key, node.getKey()) &&
                        Objects.equals(value, node.getValue()) ||
                        Objects.equals(hash, node.hashCode()));
            }
            return false;
        }
    }
}
