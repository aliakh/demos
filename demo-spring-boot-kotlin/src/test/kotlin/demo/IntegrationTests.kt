package demo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Assert invoices page title, content and status code`() {
        val invoices = restTemplate.getForEntity<String>("/")
        assertThat(invoices.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(invoices.body).contains("<h1>Invoices</h1>", "Milk", "Honey")
    }

    @Test
    fun `Assert invoice page title, content and status code`() {
        val invoice = restTemplate.getForEntity<String>("/invoice/1")
        assertThat(invoice.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(invoice.body).contains("Honey", "Raw honey", "Raw white clover blossom honey")
    }

    @Test
    fun `Assert customers API content and status code`() {
        val customers = restTemplate.getForEntity<String>("/api/customer/")
        assertThat(customers.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(customers.body).isEqualTo("[{\"login\":\"janeroe\",\"firstname\":\"Jane\",\"lastname\":\"Jane\",\"description\":null}]")
    }
}