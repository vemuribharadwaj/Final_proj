package com.example.demo.service.implementations;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.*;
import com.example.demo.model.dto.OrderItemDto;
import com.example.demo.model.dto.OrderRequestdto;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private static final double TAX_RATE = 0.08;
    private static final double SHIPPING_THRESHOLD = 35.00;
    private static final double SHIPPING_FEE = 5.00;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order createOrder(OrderRequestdto request) throws CustomerNotFoundException, ProductNotFoundException {


        Order order = new Order();
        order.setCreatedDate(LocalDateTime.from(LocalDateTime.now()));
        order.setStatus(orderStatus.CREATED);
        Optional<Customer> customerOptional= customerRepository.findById(request.getCustomerId());
        if(customerOptional.isEmpty()){

            throw new CustomerNotFoundException("Customer with id " + request.getCustomerId() +"is not found");
        }
        order.setCustomer(customerOptional.get());
        order.setAddress(request.getShippingAddress());
        double subtotal = 0.0;
        Set<OrderItem> items = new HashSet<>();
        for(OrderItemDto item : request.getOrderItems())
        {
            Optional<Product> productOptional= productRepository.findById(item.getProductId());
            if(productOptional.isEmpty()){
                throw new ProductNotFoundException("Product with id " + item.getProductId() +"is not found");
            }
            Product p = productOptional.get();
            //if order is created, it will subtract the available quantity
            if (p.getAvailableQuantity() >= item.getRequestedQuantity()) {
                p.setAvailableQuantity(p.getAvailableQuantity() - item.getRequestedQuantity());
                productRepository.save(p);
            } else {
                throw new ProductNotFoundException("Insufficient quantity for product with id " + item.getProductId());
            }
            OrderItem i = new OrderItem();
            i.setProduct(p);
            i.setOrderedQuantity(item.getRequestedQuantity());
            i.setOrder(order);
            items.add(i);
            subtotal += p.getUnitPrice() * item.getRequestedQuantity();

        }
        order.setItems(items);
        double tax = subtotal * TAX_RATE;
        double deliveryFee = subtotal < SHIPPING_THRESHOLD ? SHIPPING_FEE : 0.0;
        double total = subtotal + tax + deliveryFee;
        //Todo calculate subtotal, tax(8%), shipping charge(is subtotal is <35 then 5$), total

        order.setSubTotal(subtotal);
        order.setTax(tax);
        order.setShippingCharges(deliveryFee);
        order.setTotal(total);

        Payment finalpayment = makePayment(request.getPayment());
        order.setPayment(finalpayment);

        Order saved  = orderRepository.save(order);
        return saved;

    }

    private Payment makePayment(Payment payment) {
        payment.setConfirmationCode("CNFRS");
        return payment;
    }

    @Override
    public void updateShippingAddress(Long orderId, Address newAddress) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getStatus() == orderStatus.CREATED || order.getStatus() == orderStatus.PROCESSING) {
                order.setAddress(newAddress);
                orderRepository.save(order);
            } else {
                throw new IllegalStateException("Cannot update address as the order is not in CREATED or PROCESSING status.");
            }
        } else {
            throw new OrderNotFoundException("Order with id " + orderId + " not found");
        }
    }

    @Override
    public boolean cancelOrder(Long orderId) throws OrderNotFoundException, ProductNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getStatus() == orderStatus.CREATED || order.getStatus() == orderStatus.PROCESSING) {
                // Update product stock
                for (OrderItem item : order.getItems()) {
                    Product product = item.getProduct();
                    if (product != null) {
                        product.setAvailableQuantity(product.getAvailableQuantity() + item.getOrderedQuantity());
                        productRepository.save(product);
                    } else {
                        throw new ProductNotFoundException("Product with id " + item.getProduct().getId() + " not found");
                    }
                }
                order.setStatus(orderStatus.CANCELLED);
                orderRepository.save(order);
                return true;
            } else {
                return false;
            }
        } else {
            throw new OrderNotFoundException("Order with id " + orderId + " not found");
        }
    }

}
