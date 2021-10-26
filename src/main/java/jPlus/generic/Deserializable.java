package jPlus.generic;

public interface Deserializable<T> {
    T fromString(String s);
}
