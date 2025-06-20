package ait.cohort5860;

import ait.cohort5860.controller.CurrencyExchangeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CurrencyExchangeApplication implements CommandLineRunner {

    private final CurrencyExchangeController controller;

    public CurrencyExchangeApplication(CurrencyExchangeController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Currency Exchange Application, for exit type EXIT");
        Scanner scanner = new Scanner(System.in);
        List<String> currencies = List.of("EUR"); //как оказалось только евра в бесплатной версии(

        while (true) {
            try {
                System.out.print("\nEnter first currency(only EUR) (or type EXIT to quit): ");
                String fromCurrency = scanner.nextLine().trim().toUpperCase();
                if ("EXIT".equalsIgnoreCase(fromCurrency)) break;
                if (!currencies.contains(fromCurrency)) throw new RuntimeException("Invalid currency: " + fromCurrency);

                System.out.print("Enter second currency: ");
                String toCurrency = scanner.nextLine().trim().toUpperCase();
                if ("EXIT".equalsIgnoreCase(toCurrency)) break;
                if (!toCurrency.matches("^[a-zA-Z]{3}$")) throw new RuntimeException("Invalid currency: " + toCurrency);

                System.out.print("Enter amount to convert: ");
                String amountInput = scanner.nextLine().trim();
                if ("EXIT".equalsIgnoreCase(amountInput)) break;
                if (!amountInput.matches("^\\d+(\\.\\d+)?$")) throw new RuntimeException("Invalid amount: " + amountInput);

                double amount = Double.parseDouble(amountInput);
                double convertedAmount = controller.getRate(fromCurrency, toCurrency) * amount;
                System.out.printf("%.2f %s = %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
