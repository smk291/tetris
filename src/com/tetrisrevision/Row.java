package com.tetrisrevision;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Row implements List<Block>, Iterable<Block> {
    private double y;
    private ArrayList<Block> blocks;

    Row(double y, ArrayList<Block> blocks) {
        this.y = y;
        this.blocks = blocks;
    }

    Row(double y) {
        this.y = y;
        this.blocks = new ArrayList<>();
    }

    double getY()
    {
        return y;
    }

    void setY(double y)
    {
        this.y = y;
    }

//    ArrayList<Block> get()
//    {
//        return blocks;
//    }
//
    Optional<Block> get(double x) {
        for (Block block : blocks) {
            if (block.getX() == x)
                return Optional.of(block);
        }

        return Optional.empty();
    }

    /*
     * Returns ArrayList<Block> containing only blocks within the given range
     */
//    @NotNull
//    public List<Block> subList(double xMin, double xMax) {
//        return blocks.stream().filter(b -> b.getX() >= xMin && b.getX() < xMax).collect(Collectors.toList());
//    }

    @Override
    public int size() {
        return blocks.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

//    public boolean contains(double x) {
//        for (Block block : blocks) {
//            if ((int) block.getX() == x) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    @NotNull
    @Override
    public Iterator<Block> iterator() {
        return blocks.iterator();
    }

    @Override
    public void forEach(Consumer<? super Block> action) {
        blocks.forEach(action);
    }

    @NotNull
    @Override
    public Block[] toArray() {
        return blocks.toArray(new Block[0]);
    }

    @Override
    public Object[] toArray(IntFunction generator) {
        return new Object[0];
    }

    public boolean add(Block b)
    {
        return blocks.add(b);
    }

    public boolean add(@NotNull Row r) {
        return blocks.addAll(r);
    }

    public boolean add (ArrayList<Block> lb)
    {
        return blocks.addAll(lb);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(int i, @NotNull Collection<? extends Block> collection) {
        return blocks.addAll(i, collection);
    }

    @Override
    public void sort(Comparator<? super Block> c) {
        blocks.sort(c);
    }

    boolean remove(Block b) {
        return blocks.remove(b);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Block> collection) {
        return blocks.addAll(collection);
    }

//    public boolean addAll(ArrayList<Block> a) {
//        return blocks.addAll(a);
//    }
//
    @Override
    public boolean removeIf(Predicate<? super Block> filter) {
        return blocks.removeIf(filter);
    }

    @Override
    public void clear() {
        blocks.clear();
    }

    @Override
    public Block get(int i) {
        return blocks.get(i);
    }

    @Override
    public Block set(int i, Block block) {
        return blocks.set(i, block);
    }

    @Override
    public void add(int i, Block block) {
        blocks.add(i, block);
    }

    @Override
    public Block remove(int i) {
        return blocks.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return blocks.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return blocks.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<Block> listIterator() {
        return blocks.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<Block> listIterator(int i) {
        return blocks.listIterator(i);
    }

    @NotNull
    @Override
    public List<Block> subList(int i, int i1) {
        return blocks.subList(i, i1);
    }

    @Override
    public Spliterator<Block> spliterator() {
        return blocks.spliterator();
    }

    @Override
    public Stream<Block> stream() {
        return blocks.stream();
    }

    @Override
    public Stream<Block> parallelStream() {
        return blocks.parallelStream();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        return blocks.retainAll(collection);
    }

    public boolean retainAll (ArrayList<Block> collection) {
        return blocks.retainAll(collection);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> collection) {
        return blocks.removeAll(collection);
    }

//    public boolean removeAll(ArrayList<Block> collection) {
//        return blocks.removeAll(collection);
//    }
//
    @Override
    public boolean containsAll(@NotNull Collection<?> collection) {
        return blocks.containsAll(collection);
    }

    @NotNull
    @Override
    public Object[] toArray(@NotNull Object[] objects) {
        return new Object[0];
    }

    boolean allMatch(Predicate<Block> tester) {
        return blocks.stream().allMatch(tester);
    }
}
