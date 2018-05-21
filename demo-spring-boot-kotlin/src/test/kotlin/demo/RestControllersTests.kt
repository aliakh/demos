package demo

import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest
class RestControllersTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var customerRepository: CustomerRepository

    @MockBean
    private lateinit var invoiceRepository: InvoiceRepository

    @MockBean
    private lateinit var markdownConverter: MarkdownConverter

    @Test
    fun `List invoices`() {
        val customer = Customer("johndoe", "John", "Doe")
        val invoice1 = Invoice("Apples", "Fresh apples", "Fresh Gala apples", customer, 1)
        val invoice2 = Invoice("Oranges", "Fresh oranges", "Fresh Navel oranges", customer, 2)
        whenever(invoiceRepository.findAllByOrderByCreatedDesc()).thenReturn(listOf(invoice1, invoice2))
        whenever(markdownConverter.invoke(any())).thenAnswer { it.arguments[0] }
        mockMvc.perform(get("/api/invoice/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].customer.login").value(customer.login))
                .andExpect(jsonPath("\$.[0].id").value(invoice1.id!!))
                .andExpect(jsonPath("\$.[1].customer.login").value(customer.login))
                .andExpect(jsonPath("\$.[1].id").value(invoice2.id!!))
    }

    @Test
    fun `List customers`() {
        val customer1 = Customer("johndoe", "John", "Doe")
        val customer2 = Customer("janeroe", "Jane", "Jane")
        whenever(customerRepository.findAll()).thenReturn(listOf(customer1, customer2))
        mockMvc.perform(get("/api/customer/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.[0].login").value(customer1.login))
                .andExpect(jsonPath("\$.[1].login").value(customer2.login))
    }
}