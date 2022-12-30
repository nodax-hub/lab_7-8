package com.example.lab_7.functions;

import java.io.Serializable;
import java.util.Objects;

/**
 * Шаблонный класс реализует двусвязный циклический список
 *
 * @param <T> Тип объектов которые будет храниться в списке
 * @author NODAX
 */
@SuppressWarnings("unused")
public class CloseLinkedList<T> implements Serializable {
    // ====================================  Поля  ========================================
    /**
     * Ссылка на начальный элемент списка
     */
    private Node _head = null;

    /**
     * Ссылка и индекс на узел по которому было последнее обращение
     */
    private LUN _lastUsedNode = null;
    /**
     * Размер списка
     */
    private int _size = 0;

    // =================================  Конструкторы  ===================================
    public CloseLinkedList() {
        initHead();
    }

    // ============================  Публичные методы доступа  ============================

    /**
     * Метод для получения длины списка
     *
     * @return Кол-во элементов в списке
     */
    public int size() {
        return _size;
    }

    /**
     * Вставляет новый элемент в начало списка
     *
     * @param val вставляемое значение
     */
    public void pushFront(T val) {

        //Создаём новый узел
        Node new_node = new Node(val);

        //Если список пуст
        if (_size == 0) {
            //Создаём замыкание
            new_node._next = new_node._prev = new_node;

        } else {
            //Подвяжем соседа слева (последний элемент списка)
            _head._prev._next = new_node;
            new_node._prev = _head._prev;

            //Подвяжем соседа справа (первый элемент списка)
            _head._prev = new_node;
            new_node._next = _head;

        }
        //Обновим ссылку на начало
        _head = new_node;

        //Сохраним ссылку на добавленный объект
        _lastUsedNode = new LUN(_size, _head);

        //Увеличиваем кол-во элементов в списке
        ++_size;
    }

    /**
     * Вставляет новый элемент в конец списка
     *
     * @param val вставляемое значение
     */
    public void pushBack(T val) {
        //Вставляем элемент в начало
        pushFront(val);
        //Если список был не пуст обновим указатель на начало
        if (_size > 1) {
            //Сдвигаем указатели на начало, чтобы новый элемент оказался в конце
            _head = _head._next;
        }
    }

    /**
     * Вставляет новый элемент в список по указанному индексу
     *
     * @param val   вставляемое значение
     * @param index позиция вставляемого значения
     */
    public void insert(T val, int index) {
        if (index == 0) pushFront(val);
        else if (index == _size - 1) pushBack(val);
        else {
            //Сосед слева
            Node left = at(index - 1);
            //Сосед справа
            Node right = left._next;
            //Создаём узел, подвязывая её к соседям
            Node new_node = new Node(val, right, left);

            //Подвязываем соседей к новой точке
            left._next = right._prev = new_node;

            //Сохраним ссылку на добавленный объект
            _lastUsedNode = new LUN(index, new_node);

            //Увеличиваем размер списка
            ++_size;
        }
    }


    /**
     * Удаляет элемент под указанным индексом
     *
     * @param index номер удаляемого элемента
     * @return значение удалённого элемента
     */
    public T popAt(int index) {
        //Получаем элемент с которым будем работать
        Node cur = at(index);

        //Сохраним значение элемента
        T val = cur._data;

        //Запомним текущих соседей
        Node left = cur._prev;
        Node right = cur._next;

        //Отвяжем соседей от узла
        left._next = right;
        right._prev = left;

        //Отвяжем узел от соседей
        cur._next = cur._prev = null;

        //Если удаляемый элемент является головой (первым) обновляем голову
        if (index == 0) _head = right;

        //Уменьшаем размер списка
        --_size;

        //Вернём значение элемента
        return val;
    }

    /**
     * Удаляет первый элемент
     *
     * @return значение удалённого элемента
     */
    public T popFront() {
        return popAt(0);
    }

    /**
     * Удаляет последний элемент
     *
     * @return значение удалённого элемента
     */
    public T popBack() {
        return popAt(_size - 1);
    }

    /**
     * Выполняет очистку списка
     */
    public void clear() {
        initHead();
        _size = 0;
    }

    /**
     * Получение значения в элементе под указанным индексом
     *
     * @param index индекс элемента
     * @return Значение указанного элемента
     */
    public T getData(int index) {
        return at(index)._data;
    }

    public void setData(int index, T data) {
        at(index)._data = data;
    }

    /**
     * Собирает список значений в строковый вид
     *
     * @return отформатированная строка содержащая список значений
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{ ");
        for (int i = 0; i < _size; ++i) {
            result.append(getData(i)).append(" ");
        }
        result.append("}");
        return result.toString();
    }

    /**
     * Преобразовывает список в стандартный массив объектов
     *
     * @return массив объектов
     */
    public Object[] toArray() {
        Object[] array = new Object[size()];
        for (int i = 0; i < size(); ++i) {
            array[i] = getData(i);
        }
        return array;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CloseLinkedList<?> other)) return false;
        if (size() != other.size()) return false;

        for (int i = 0; i < size(); i++)
            if (!(getData(i).equals(other.getData(i)))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < _size; i++)
            result = 31 * result + (getData(i) == null ? 0 : getData(i).hashCode());
        return result;
    }
    // ===============================  Приватные методы  =================================

    /**
     * Метод инициализации головы списка
     */
    private void initHead() {
        _head = new Node();
        _head._next = _head._prev = _head;
        _lastUsedNode = new LUN(0, _head);
    }

    /**
     * Проверка на корректность индекса
     *
     * @param index проверяемый индекс
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException("Incorrect index output...");
        }
    }

    /**
     * Вернёт ссылку на элемент под указанным индексом шагая от головы
     *
     * @param index индекс искомого узла
     */
    private Node atFromHead(int index) {

        //Определяем направление обхода
        boolean forward_dir = index <= _size / 2;

        //Считаем кол-во шагов
        int count = forward_dir ? index : _size - index;

        //Шагаем к цели (в выбранном направлении)
        return forward_dir ? next(_head, count) : prev(_head, count);
    }

    /**
     * Вернёт ссылку на элемент под указанным индексом шагая от последнего использованного элемента
     *
     * @param index индекс искомого узла
     */
    private Node atFromLast(int index) {

        //Определяем направление обхода
        boolean forward_dir = index >= _lastUsedNode.index;

        //Считаем кол-во шагов
        int count = Math.abs(index - _lastUsedNode.index);

        //Шагаем к цели (в выбранном направлении)
        return forward_dir ? next(_lastUsedNode.node, count) : prev(_lastUsedNode.node, count);
    }

    /**
     * Вернёт ссылку на элемент под указанным индексом
     *
     * @param index индекс искомого узла
     */
    private Node at(int index) {
        checkIndex(index);

        Node node = (Math.abs(index - _lastUsedNode.index) < index) ? atFromLast(index) : atFromHead(index);

        //Сохраняем адрес узла к которому производился доступ
        _lastUsedNode = new LUN(index, node);

        return node;
    }

    /**
     * Прошагает по списку с указанной позиции указанное кол-во раз
     *
     * @param cur   Ссылка на узел с которого будем шагать
     * @param count Количество шагов
     */
    private Node next(Node cur, int count) {
        while (count-- != 0) cur = Objects.requireNonNull(cur)._next;
        return cur;
    }


    /**
     * Вернёт следующий элемент в списке
     *
     * @param cur Ссылка на узел с которого будем шагать
     */
    private Node next(Node cur) {
        return Objects.requireNonNull(cur)._next;
    }

    /**
     * Прошагает назад по списку с указанной позиции указанное кол-во раз
     *
     * @param cur   Ссылка на узел с которого будем шагать
     * @param count Количество шагов назад
     */
    private Node prev(Node cur, int count) {
        while (count-- != 0) cur = Objects.requireNonNull(cur)._prev;
        return cur;
    }

    /**
     * Вернёт предыдущий элемент в списке
     *
     * @param cur Ссылка на узел с которого будем шагать
     */
    private Node prev(Node cur) {
        return Objects.requireNonNull(cur)._prev;
    }

    /**
     * Минимальное абсолютное значение
     *
     * @param lhs left hand side
     * @param rhs right hand side
     * @return Наименьшее значение по модулю
     */
    private int minAbs(int lhs, int rhs) {
        return Math.abs(lhs) < Math.abs(rhs) ? lhs : rhs;
    }


    // =======================  Вспомогательные приватные классы  =========================

    /**
     * Узел двусвязного списка
     * В полях экземпляра этого класса ссылки на левого и правого соседа
     * А также ссылка на полезные данные (значение)
     */
    private class Node implements Serializable {
        public T _data;
        public Node _next, _prev;

        Node() {
            this(null);
        }

        public Node(T data) {
            _data = data;
            _next = _prev = this;
        }

        public Node(T data, Node next, Node prev) {
            _data = data;
            _next = next;
            _prev = prev;
        }


    }

    /**
     * LUN - Last Used Node
     * Хранит ссылку на узел и его индекс
     */
    private class LUN implements Serializable {
        public int index;
        public Node node;

        LUN(int index, Node node) {
            this.index = index;
            this.node = node;
        }
    }
}
