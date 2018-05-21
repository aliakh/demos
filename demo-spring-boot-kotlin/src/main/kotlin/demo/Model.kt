package demo

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Customer(
        @Id val login: String,
        val firstname: String,
        val lastname: String,
        val description: String? = null)

@Entity
data class Invoice(
        val title: String,
        val headline: String,
        val content: String,
        @ManyToOne @JoinColumn val customer: Customer,
        @Id @GeneratedValue val id: Long? = null,
        val created: LocalDateTime = LocalDateTime.now())
