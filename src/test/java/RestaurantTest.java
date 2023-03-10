import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void initializeObjectsAndData () {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.of(13,24,55));

        assertTrue(spyRestaurant.isRestaurantOpen());
        Mockito.verify(spyRestaurant, Mockito.times(1)).getCurrentTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.of(23,24,55));

        assertFalse(spyRestaurant.isRestaurantOpen());
        Mockito.verify(spyRestaurant, Mockito.times(1)).getCurrentTime();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void order_total_should_return_388_when_both_food_items_are_selected() {
        int price = restaurant.getOrderTotal(Arrays.asList("Sweet corn soup", "Vegetable lasagne"));
        assertEquals(388, price);
    }

    @Test
    public void order_total_should_return_119_when_first_food_item_is_selected() {
        int price = restaurant.getOrderTotal(Arrays.asList("Sweet corn soup"));
        assertEquals(119, price);
    }

    @Test
    public void order_total_should_return_0_when_no_food_is_selected() {
        int price = restaurant.getOrderTotal(Arrays.asList());
        assertEquals(0, price);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<
}