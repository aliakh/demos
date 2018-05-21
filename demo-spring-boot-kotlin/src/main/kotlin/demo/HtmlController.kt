package demo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class HtmlController(private val invoiceRepository: InvoiceRepository,
                     private val markdownConverter: MarkdownConverter) {

    @GetMapping("/")
    fun invoices(model: Model): String {
        model["title"] = "Invoices"
        model["invoices"] = invoiceRepository.findAllByOrderByCreatedDesc().map { it.render() }
        return "items"
    }

    @GetMapping("/invoice/{id}")
    fun invoice(@PathVariable id: Long, model: Model): String {
        val invoice = invoiceRepository
                .findById(id)
                .orElseThrow { IllegalArgumentException("Wrong invoice id provided") }
                .render()
        model["title"] = invoice.title
        model["invoice"] = invoice
        return "item"
    }

    fun Invoice.render() = RenderedInvoice(
            title,
            markdownConverter.invoke(headline),
            markdownConverter.invoke(content),
            customer,
            id,
            created.toString()
    )

    data class RenderedInvoice(
            val title: String,
            val headline: String,
            val content: String,
            val customer: Customer,
            val id: Long?,
            val created: String)
}


