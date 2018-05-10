package com.mbsystems.parameterization.passcode.filteringapples;

import com.mbsystems.parameterization.passcode.domain.Apple;
import com.mbsystems.parameterization.passcode.domain.Dish;
import com.mbsystems.parameterization.passcode.domain.Pair;
import com.mbsystems.parameterization.passcode.domain.Trader;
import com.mbsystems.parameterization.passcode.domain.Transaction;
import com.mbsystems.parameterization.passcode.domain.Type;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;
//import static java.util.stream.Collectors.
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class FilteringApplesTest {

    private List<Apple> inventory = null;
    private List<Dish> dishList = null;
    private List<Transaction > transactions = null;
    private Trader traderRaoul = null;
    private Trader traderMario = null;
    private Trader traderAlan = null;
    private Trader traderBrian = null;

    @Before
    public void setUp() {
        inventory = Arrays.asList(new Apple(200,"green"),
                new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        dishList = Arrays.asList( new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 400, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH));

        traderRaoul = new Trader("Raoul", "Cambridge");
        traderMario = new Trader("Mario", "Milan");
        traderAlan = new Trader("Alan", "Cambridge");
        traderBrian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction( traderBrian, 2011, 300 ),
                new Transaction( traderRaoul, 2012, 1000 ),
                new Transaction( traderRaoul, 2011, 400 ),
                new Transaction( traderMario, 2012, 710 ),
                new Transaction( traderMario, 2012, 700 ),
                new Transaction( traderAlan, 2012, 950 ));
    }

    @Test
    public void filterGreenApple() {
        List<Apple> greenAppleList = new ArrayList<>();

        for( Apple apple : inventory ) {
            if ( "green".equals( apple.getColor() )) {
                greenAppleList.add( apple );
            }
        }

        assertThat( greenAppleList.size() ).isEqualTo( 2 );
        assertThat( greenAppleList.get( 1 ).getColor() ).isEqualTo( "green" );
    }

    @Test
    public void sortByWeight() {
//        Comparator<Apple> appleComparator =
//                (Apple apple1, Apple apple2) -> apple1.getWeight().compareTo(apple2.getWeight());
        //inventory.sort((Apple apple1, Apple apple2) -> apple1.getWeight() - apple2.getWeight());
        inventory.forEach((anApple)->System.out.println(anApple));
        inventory.sort( comparing(Apple::getWeight  ));
        System.out.println( "\n\n\n" );
        inventory.forEach((anApple)->System.out.println(anApple));
        System.out.println( "\n ***\n ***\n" );
        inventory.forEach( System.out::println );
    }

    @Test
    public void highCaloricDishName() {
        List<String> dishes = dishList.stream()
                                    .filter( d -> {
                                        System.out.println( "Filtering: " + d.getName() );
                                        return d.getCalories() > 300;
                                    })
                                    .map( d -> {
                                        System.out.println( "Mapping: " + d.getName() );
                                        return d.getName();
                                    } )
                                    .limit( 3 )
                                    .collect( toList() );

        System.out.println( dishes );
    }

    @Test
    public void uniqueElement() {
        List<Integer> numberList2 = null;
        List<Integer> numberList = Arrays.asList( 1, 2, 1, 3, 3, 2, 4 );
        numberList.stream()
                .filter( i -> i % 2 == 0 )
                .distinct()
                .forEach( System.out::println );

        //assertThat( numberList.size() ).isEqualTo( 2 );
    }

    @Test
    public void flatteningStream() {
        List<String> stringList = Arrays.asList( "Hello", "Basil" );

        List<String> p = stringList.stream()
                .map( s -> s.split( "" ) )
                .flatMap( Arrays::stream )
                .distinct()
                .collect( Collectors.toList() );

        //System.out.println( p );
        p.forEach( System.out::println );
    }

    @Test
    public void squareList() {
        List<Integer> integerList = Arrays.asList( 1, 2, 3, 4, 5 );

        List<Double> resultsList = integerList.stream()
                .map( a -> Math.pow( a, 2 ) )
                .collect(toList());

        resultsList.forEach( System.out::println );
    }

    @Test
    public void pairList() {
        List<Integer> integerList1 = Arrays.asList( 1, 2, 3 );
        List<Integer> integerList2 = Arrays.asList( 3, 4 );

        List<Pair> pairList = integerList1.stream()
                .flatMap( p -> integerList2.stream()
                    .map( j -> new Pair( p, j ) ))
                .collect( toList() );

        pairList.forEach( System.out::println );
    }

    @Test
    public void pairMode2List() {
        List<Integer> integerList1 = Arrays.asList( 1, 2, 3 );
        List<Integer> integerList2 = Arrays.asList( 3, 4 );

        List<Pair> pairList = integerList1.stream()
                .flatMap( p -> integerList2.stream()
                        .map( j -> new Pair( p, j ) ))
                .filter( x -> x.getSum() % 3 == 0 )
                .collect( toList() );

        pairList.forEach( System.out::println );
    }

    @Test
    public void transactionsOf2011andOrderBySmallToHigh() {
        List<Transaction > results = transactions.stream()
                .filter( p -> p.getYear() == 2011 )
                .sorted( comparing( p -> p.getValue() ) )
                .collect(toList());

        results.forEach( System.out::println );
    }

    @Test
    public void citiesTraderWorkIn() {
        List<String > results = transactions.stream()
                .map( p -> p.getTrader().getCity() )
                .distinct()
                .collect(toList());

        results.forEach( System.out::println );
    }

    @Test
    public void traderFromCambridgeOrderByName() {
        List<String > results = transactions.stream()
                .filter( p -> p.getTrader().getCity() == "Cambridge" )
                .map( p -> p.getTrader().getName() )
                .distinct()
                .sorted()
                .collect(toList());

        results.forEach( System.out::println );
    }

    @Test
    public void anyTradersFromMilan() {
        boolean results = transactions.stream()
                .anyMatch( p -> p.getTrader().getCity().equals( "Milan") );

        assertThat( results ).isEqualTo( true );
    }

    @Test
    public void printTransOfTradersFromCambridge() {
        List<Transaction> results = transactions.stream()
                .filter( p -> p.getTrader().getCity().equals( "Cambridge" ))
                .collect(toList());

        results.forEach( System.out::println );
    }

    @Test
    public void tradersNamesSortedAlphabetically() {
        List<String> results = transactions.stream()
                .map( p -> p.getTrader().getName() )
                .distinct()
                .sorted()
                .collect(toList());

        results.forEach( System.out::println );
    }

    @Test
    public void tradersHighestValue() {
        Optional<Integer> results = transactions.stream()
                .map( Transaction::getValue )
                .reduce( Integer::max );

        if (results.isPresent()) {
            Integer num = results.get();
            System.out.println("The Number: " + num);
        }
    }

    @Test
    public void tradersHighestValue2() {
        Optional< Transaction > results = transactions.stream()
                .reduce( (a1, a2 ) -> a1.getValue() > a2.getValue() ? a1 : a2 );

        if (results.isPresent())
        {
            Transaction trsn = results.get();
            System.out.println(trsn);
        }
    }

    @Test
    public void transactionWithLowValue() {
        Optional<Transaction> results = transactions.stream()
                .reduce(( t1, t2 ) -> t1.getValue() < t2.getValue() ? t1 : t2 );

        if (results.isPresent())
        {
            Transaction trsn = results.get();
            System.out.println(trsn);
        }
    }

    @Test
    public void countingDishes() {
        long count = dishList.stream().count();

        assertThat( count ).isEqualTo( 9 );
    }

    @Test
    public void maxmininstream() {
        Comparator<Dish> dishComparator = Comparator.comparing( Dish::getCalories );

//        Optional<Dish> results = dishList.stream().collect( maxBy( dishComparator ) );
        Optional<Dish> results = dishList.stream().max( dishComparator );

        if ( results.isPresent() ) {
            Dish dish = results.get();
            System.out.println( dish );
        }
    }

    @Test
    public void summingUp() {

    }
}