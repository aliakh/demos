package demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class RepositoriesTests {

    @Autowired
    lateinit var entityManager: TestEntityManager;

    @Autowired
    lateinit var customerRepository: CustomerRepository;

    @Autowired
    lateinit var invoiceRepository: InvoiceRepository;

    @Test
    fun `When findById then return Invoice`() {
        val customer = Customer("johndoe", "John", "Doe")
        entityManager.persist(customer)
        val invoice = Invoice("Bread", "Brown bread", "Whole wheat bread", customer)
        entityManager.persist(invoice)
        entityManager.flush()

        val actualCustomer = invoiceRepository.findById(invoice.id!!)

        assertThat(actualCustomer.get()).isEqualTo(invoice)
    }

    @Test
    fun `When findById then return Customer`() {
        val customer = Customer("johndoe", "John", "Doe")
        entityManager.persist(customer)
        entityManager.flush()

        val actualCustomer = customerRepository.findById(customer.login)

        assertThat(actualCustomer.get()).isEqualTo(customer)
    }
}