package demo

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/customer")
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("/")
    fun findAll() = customerRepository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) = customerRepository.findById(login)
}

@RestController
@RequestMapping("/api/invoice")
class InvoiceController(private val invoiceRepository: InvoiceRepository,
                        private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun findAll() = invoiceRepository.findAllByOrderByCreatedDesc()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long, @RequestParam converter: String?) = when (converter) {
        "markdown" -> invoiceRepository.findById(id).map {
            it.copy(
                    headline = markdownConverter.invoke(it.headline),
                    content = markdownConverter.invoke(it.content))
        }
        null -> invoiceRepository.findById(id)
        else -> throw IllegalArgumentException("Only 'markdown' converter is supported")
    }
}
