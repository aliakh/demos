package demo

import com.samskivert.mustache.Mustache
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)

    @Bean
    fun databaseInitializer(customerRepository: CustomerRepository, invoiceRepository: InvoiceRepository) = CommandLineRunner {
        val customer = Customer("janeroe", "Jane", "Jane")
        customerRepository.save(customer)
        invoiceRepository.save(Invoice(
                "Honey",
                "Raw honey",
                "Raw white clover blossom honey",
                customer,
                1
        ))
        invoiceRepository.save(Invoice(
                "Milk",
                "Dairy milk",
                "Ultra pasteurized dairy milk",
                customer,
                2
        ))
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
