package com.OrderSystem.demo.Service;

import com.OrderSystem.demo.Model.Order;
import com.OrderSystem.demo.Model.Product;
import com.OrderSystem.demo.Repository.OrderRepo;
import com.OrderSystem.demo.Repository.ProductRepo;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo repo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private NotificationService notificationService;
    private final String to = "+919909245448";

    public Order save(Order order) {
        Product product = productRepo.findById(order.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Product Not Found with productId: " + order.getProductId()
                        )
                );

        checkStock(product, order);
        sendBill(order);
        makePayment(order);
        updateStock(product, order);
        return repo.save(order);
    }

    private void updateStock(Product product, Order order) {
        product.setStock(product.getStock() - order.getQuantity());
        productRepo.save(product);

        if (product.getStock() <= product.getThreshold()) {
            notificationService.sendSms(to, createRestockAlert(product));
        }

        try {
            String path = exportToCsv();
            notificationService.sendEmail("mdobariya568@gmail.com", path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email Not Found !!");
        }
    }
    private void makePayment(Order order) {
        if (!payment(order)) {
            String denialMessage = createPaymentFailureMessage(order);
            notificationService.sendWhatsapp(to, denialMessage);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Payment Failed");
        }
    }
    private void sendBill(Order order) {
        String bill = createBill(order);
        notificationService.sendWhatsapp(to, bill);
    }
    private void checkStock(Product product, Order order) {
        if (order.getQuantity() > product.getStock()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient stock");
        }
    }
    public String exportToCsv() throws IOException {
        String path = LocalDate.now() + "_stock.csv";
        List<Product> products = productRepo.findAll();

        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeNext(new String[]{"ID", "Name", "Price", "GST", "Stock", "Threshold"});

            for (Product product : products) {
                writer.writeNext(
                        new String[]{
                                String.valueOf(product.getProductId()),
                                product.getName(),
                                String.valueOf(product.getPrice()),
                                String.valueOf(product.getGst()),
                                String.valueOf(product.getStock()),
                                String.valueOf(product.getThreshold())
                        }
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("CSV generation failed !!", e);
        }
        return new File(path).getAbsolutePath();
    }
    private boolean payment(Order order) {
        return Math.random() < 0.8;
    }
    public String createRestockAlert(Product product) {
        return "\n🔴 Stock is below threshold for productId: " + product.getProductId() + ". Please restock immediately!";
    }
    public String createPaymentFailureMessage(Order order) {
        return "❌ *Payment Failed*" +
                "\n---------------------------" +
                "\n👤 Customer: " + order.getName() +
                "\n📱 Mobile: " + order.getMobileNo() +
                "\n📦 Product ID: " + order.getProductId() +
                "\n🔢 Quantity: " + order.getQuantity() +
                "\n---------------------------" +
                "\n⚠️ Your payment could not be processed." +
                "\nPlease try again or contact support.";
    }
    public String createBill(Order order) {
        Product product = productRepo.findById(order.getProductId()).orElseThrow();

        double amount = product.getPrice() * order.getQuantity();
        double totalGst = (amount * product.getGst()) / 100;
        double total = amount + totalGst;

        return "🧾 *Order Bill*" +
                "\n---------------------------" +
                "\n👤 Customer: " + order.getName() +
                "\n📦 Product: " + product.getName() +
                "\n💰 Price: ₹" + product.getPrice() +
                "\n🔢 Quantity: " + order.getQuantity() +
                "\n💵 Amount: ₹" + amount +
                "\n📊 GST (" + product.getGst() + "%): ₹" + totalGst +
                "\n---------------------------" +
                "\n✅ Total: ₹" + total;
    }

    public List<Order> findAll() {
        return repo.findAll();
    }
}
