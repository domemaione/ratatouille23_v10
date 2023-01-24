package com.v10.ratatouille23.component

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class EmailSender(

    @Value("\${spring.sendgrid.api-key}")
    private val token: String,

    @Value("\${spring.sendgrid.from}")
    private val emailSender: String,
) {
    private var client: SendGrid = SendGrid(token)
    private var from: Email = Email(this.emailSender)
    private val logger = Logger.getLogger(EmailSender::class.java.name)

    fun send(to: String, subject: String, content: Pair<String, String>): Boolean {
        val request = Request()
        val toEmail = Email(to)
        val emailContent = Content(content.first, content.second)
        val mail = Mail(from, subject, toEmail, emailContent)
        request.method = Method.POST
        request.endpoint = "mail/send"
        try {
            request.body = mail.build()
            val response: Response = client.api(request)
            // todo: rilanciare errore se response status != 200 (? da verificare)
        } catch (e: Exception) {
            logger.severe("send() - error: ${e.message}")
            throw RuntimeException("Send email failed")
        }
        return true
    }
}