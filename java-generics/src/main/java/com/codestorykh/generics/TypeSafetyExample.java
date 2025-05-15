package com.codestorykh.generics;

import java.util.ArrayList;
import java.util.List;

public class TypeSafetyExample {

    public static void main(String[] args) {

        List<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("Hello");


        System.out.println(numbers);

    }
}
