package com.gerasimov.capstone.cart;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private DishService dishService;

    @Mock
    private Cart cart;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CartService cartService;

    private List<OrderItemDto> orderItems;

    @BeforeEach
    public void setUp() {
        orderItems = new ArrayList<>();
        OrderItemDto orderItemDto1 = new OrderItemDto();
        orderItemDto1.setQuantity(2);
        OrderItemDto orderItemDto2 = new OrderItemDto();
        orderItemDto2.setQuantity(3);
        orderItems.add(orderItemDto1);
        orderItems.add(orderItemDto2);
    }


    @Test
    void testMakeOrderWhenSelectedAddressIsValidAndCartIsNotEmptyThenSaveOrderItems() {
        Long selectedAddressId = 1L;
        AddressDto addressDto = new AddressDto();
        addressDto.setId(selectedAddressId);
        when(addressService.findById(selectedAddressId)).thenReturn(addressDto);
        when(cart.getCart()).thenReturn(orderItems);
        OrderDto savedOrderDto = new OrderDto();
        when(orderService.save(any(OrderDto.class))).thenReturn(savedOrderDto);

        cartService.makeOrder(selectedAddressId);

        verify(orderItemService, times(orderItems.size())).save(any(OrderItemDto.class));
    }

    @Test
    void testMakeOrderWhenSelectedAddressIsInvalidThenThrowException() {
        Long selectedAddressId = 1L;
        when(addressService.findById(selectedAddressId)).thenReturn(null);
        when(orderService.save(any())).thenThrow(RestaurantException.class);

        assertThrows(RestaurantException.class, () -> cartService.makeOrder(selectedAddressId));
    }

    @Test
    void testMakeOrderWhenCartIsEmptyThenDoNotSaveOrderItems() {
        Long selectedAddressId = 1L;
        AddressDto addressDto = new AddressDto();
        addressDto.setId(selectedAddressId);
        when(addressService.findById(selectedAddressId)).thenReturn(addressDto);
        when(cart.getCart()).thenReturn(new ArrayList<>());

        cartService.makeOrder(selectedAddressId);

        verify(orderItemService, never()).save(any(OrderItemDto.class));
    }

    @Test
    void testGetNumberOfItemsInCartWhenCartIsNotEmptyThenReturnCorrectNumberOfItems() {
        when(cart.getCart()).thenReturn(orderItems);

        int numberOfItems = cartService.getNumberOfItemsInCart();

        assertEquals(5, numberOfItems);
    }

    @Test
    void testGetNumberOfItemsInCartWhenCartIsEmptyThenReturnZero() {
        when(cart.getCart()).thenReturn(new ArrayList<>());

        int numberOfItems = cartService.getNumberOfItemsInCart();

        assertEquals(0, numberOfItems);
    }

    @Test
    void testAddToCartWhenDishIsNotInCartThenAddNewCartItem() {
        Long dishId = 1L;
        DishDto dishDto = new DishDto();
        dishDto.setId(dishId);
        when(dishService.findById(dishId)).thenReturn(dishDto);
        when(cart.getCart()).thenReturn(new ArrayList<>());

        cartService.addToCart(dishId);

        verify(cart, times(1)).addItem(any(OrderItemDto.class));
    }

    @Test
    void testRemoveFromCartWhenDishIsNotInCartThenThrowException() {
        Long dishId = 1L;
        DishDto dishDto = new DishDto();
        dishDto.setId(dishId);
        when(dishService.findById(dishId)).thenReturn(dishDto);
        when(cart.getCart()).thenReturn(new ArrayList<>());

        assertThrows(RestaurantException.class, () -> cartService.removeFromCart(dishId));
    }
}