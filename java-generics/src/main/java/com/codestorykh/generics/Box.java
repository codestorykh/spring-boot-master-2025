package com.codestorykh.generics;

public class Box<T>{

    private T content;

    public Box(){
    }

    public Box(T content){
        this.content = content;
    }

    public T getContent() {
        return content;
    }
    public void setContent(T content) {
        this.content = content;
    }

    public boolean hasContent(){
        return content != null;
    }

    public void clear() {
        content = null;
    }

    @Override
    public String toString() {
        return "Box{" +
                "content=" + content +
                '}';
    }

    public <U> Box<U> transformContent(Transformer<T, U> transformer) {
       if(content == null){
              return new Box<>(null);
       }
        return new Box<>(transformer.transform(content));
    }

    @FunctionalInterface
    public interface Transformer<T, U>{
        U transform(T content);
    }

}
