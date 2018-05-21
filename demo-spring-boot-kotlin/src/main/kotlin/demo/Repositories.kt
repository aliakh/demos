package demo

import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, String>

interface InvoiceRepository : CrudRepository<Invoice, Long> {

    fun findAllByOrderByCreatedDesc(): Iterable<Invoice>
}
