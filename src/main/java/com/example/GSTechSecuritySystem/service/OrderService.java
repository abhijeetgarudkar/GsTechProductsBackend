package com.example.GSTechSecuritySystem.service;

import com.example.GSTechSecuritySystem.Do.Order;
import com.example.GSTechSecuritySystem.Do.OrderResponseDTO;
import com.example.GSTechSecuritySystem.Do.OrderResponseDTO.OrderItemDTO;
import com.example.GSTechSecuritySystem.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

        @Autowired
        private OrderRepository orderRepository;

        public List<OrderResponseDTO> getOrdersByUsername(String username) {
                List<Order> orders = orderRepository.findByUsername(username);

                return orders.stream()
                                .map(this::convertToDTO)
                                .collect(Collectors.toList());
        }

        private OrderResponseDTO convertToDTO(Order order) {
                List<OrderItemDTO> itemDTOs = order.getItems().stream()
                                .map(item -> new OrderItemDTO(
                                                item.getProductName(),
                                                item.getQuantity(),
                                                item.getTotalPrice()))
                                .collect(Collectors.toList());

                return new OrderResponseDTO(
                                String.valueOf(order.getOrderId()),
                                order.getOrderDate(),
                                order.getCustomerName(),
                                order.getMobileNumber(),
                                order.getAddress(),
                                order.getTotalAmount(),
                                order.getStatus(),
                                itemDTOs);
        }

        public Order saveOrder(Long userId, String username, String customerName,
                        String mobileNumber, String address,
                        List<com.example.GSTechSecuritySystem.Do.CartItems> cartItems,
                        double totalAmount, String status) {

                // Create new order
                Order order = new Order();
                order.setUserId(userId);
                order.setUsername(username);
                order.setOrderDate(java.time.LocalDateTime.now());
                order.setCustomerName(customerName);
                order.setMobileNumber(mobileNumber);
                order.setAddress(address);
                order.setTotalAmount(totalAmount);
                order.setStatus(status);

                // Create order items from cart items
                List<com.example.GSTechSecuritySystem.Do.OrderItem> orderItems = cartItems.stream()
                                .map(cartItem -> {
                                        com.example.GSTechSecuritySystem.Do.OrderItem orderItem = new com.example.GSTechSecuritySystem.Do.OrderItem();
                                        orderItem.setOrder(order);
                                        orderItem.setProductName(cartItem.getProduct().getProductName());
                                        orderItem.setQuantity(cartItem.getQuantity());
                                        orderItem.setTotalPrice(cartItem.getTotalPrice());
                                        return orderItem;
                                })
                                .collect(Collectors.toList());

                order.setItems(orderItems);

                // Save order (cascade will save order items)
                return orderRepository.save(order);
        }
}
