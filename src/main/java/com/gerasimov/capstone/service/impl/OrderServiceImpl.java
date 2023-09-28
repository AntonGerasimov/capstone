package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.DishDto;
import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.domain.OrderItemDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Order;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.OrderMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.OrderRepository;
import com.gerasimov.capstone.service.OrderItemService;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserService userService;
    private UserMapper userMapper;
    private OrderItemService orderItemService;

    @Override
    public List<OrderDto> findAll(){
        List<Order> orderEntities = orderRepository.findAll();
        return orderEntities.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(String.format("Can't find order with id %d in database", id)));
        return orderMapper.toDto(order);
    }

    @Override
    public List<Order> findByDeliveryAddressId(Long addressId){
        return orderRepository.findByDeliveryAddressId(addressId);
    }

    @Override
    public List<OrderDto> findByCustomer(UserDto userDto){
        List<Order> orders = orderRepository.findByCustomer(userMapper.toEntity(userDto) );
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> findOrderItems(OrderDto orderDto){
        return orderItemService.findByOrder(orderDto);
    }

    @Override
    public OrderDto save(OrderDto orderDto){
        setAuthenticatedUser(orderDto);
        setCurrentTime(orderDto);
        setDefaultStatus(orderDto);
        setActive(orderDto);

        Order order = orderMapper.toEntity(orderDto);
        OrderDto savedOrderDto = orderMapper.toDto(orderRepository.save(order));
        log.info(String.format("Order was created: %s", savedOrderDto.toString()));
        return savedOrderDto;
    }


    @Override
    @Transactional
    public void delete(Long orderId){
        OrderDto orderDto = findById(orderId);
        orderDto.setActive(false);
        orderRepository.save(orderMapper.toEntity(orderDto));
        log.info("Delete order with id " + orderDto.getId());
    }

    @Override
    public Page<OrderDto> getOrdersForAuthenticatedUserPageable(Pageable pageable){

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OrderDto> listToView;

        UserDto authenticatedUser = userService.findAuthenticatedUser();
        List<OrderDto> ordersImmutable = findByCustomer(authenticatedUser);
        List<OrderDto> orders = new ArrayList<>(ordersImmutable);
        orders.sort(new OrderComparator());

        if (orders.size() < startItem) {
            listToView = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, orders.size());
            listToView = orders.subList(startItem, toIndex);
        }

        return new PageImpl<OrderDto>(listToView, PageRequest.of(currentPage, pageSize), orders.size());
    }

    @Override
    public Map<Long, Double> getTotalPrices() {

        UserDto authenticatedUser = userService.findAuthenticatedUser();

        List<OrderDto> orders = findByCustomer(authenticatedUser);
        Map<Long, Double> totalPriceMap = new HashMap<>();

        for (OrderDto orderDto : orders) {
            double totalPrice = calcTotalPrice(orderDto);
            totalPriceMap.put(orderDto.getId(), totalPrice);
        }

        return totalPriceMap;
    }


    @Override
    public double calcTotalPrice(OrderDto orderDto){
        List<OrderItemDto> orderItemDtos = findOrderItems(orderDto);
        return orderItemDtos.stream()
                .mapToDouble(orderItemDto -> orderItemDto.getDishPrice() * orderItemDto.getQuantity())
                .sum();
    }


    private void setAuthenticatedUser(OrderDto orderDto){
        UserDto customer = userService.findAuthenticatedUser();
        orderDto.setCustomer(customer);
    }

    private void setCurrentTime(OrderDto orderDto){
        orderDto.setCreated(LocalDateTime.now());
    }
    private void setDefaultStatus(OrderDto orderDto){
        orderDto.setStatus("Preparing");
    }

    private void setActive(OrderDto orderDto){
        orderDto.setActive(true);
    }

    private void sortOrders(List<OrderDto> orders){
//        OrderComparator orderComparator = new OrderComparator();
        Collections.sort(orders, new OrderComparator());
//        Collections.sort(orders, orderComparator);
//        orders.sort(orderComparator);
    }


}
