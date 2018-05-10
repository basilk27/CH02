package com.mbsystems.parameterization.passcode.filteringapples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FilteringApples {

    private static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> results = new ArrayList<>();

        for( T s: list ) {
            results.add( f.apply( s ) );
        }

        return results;
    }

    public static void main(String[] args ) {
        List<Integer> l = map( Arrays.asList("lambdas", "in", "action"),
                (String s) -> s.length());

        System.out.println( l );
    }
}
