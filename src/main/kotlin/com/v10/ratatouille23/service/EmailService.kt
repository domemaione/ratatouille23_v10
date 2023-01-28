package com.v10.ratatouille23.service

import org.springframework.stereotype.Service
import com.v10.ratatouille23.component.EmailSender
import com.v10.ratatouille23.model.User

@Service
class EmailService(
    private val emailSender: EmailSender
) {
    sealed class MailComposer () {
        abstract fun mail(user: User): MailObj
        class Registration(
            private val token: String,
            private val url: String,
        ) : MailComposer() {
            override fun mail(user: User) =
                MailObj(
                    subjectBenvenuto,
                    textHtml,
                    "Ciao, ${user.email},\nbenvenuto su Ratatouille23, per attivare l'account con ruolo ${user.role} <a href=\n$url/$token>Clicca qui!</a>"
                )
        }

        object ResetPassword : MailComposer() {
            override fun mail(user: User) =
                MailObj(
                    subjectResetPassword,
                    textHtml,
                    "Ciao, ${user.email},\nLa tua password è stata reimpostata con successo."
                )
        }
    }


    //contenuto dell'email che riceve un user
    class MailObj(val subject: String, val type: String, val message: String)

    companion object {
        private val subjectBenvenuto: String = " Benvenuto su Ratatouille23"
        private val subjectResetPassword: String = "Reset Password Ratatouille23"
        private val textHtml: String = "text/html"
    }

    fun send(to: User, emailComposer: MailComposer) {
        val mail = emailComposer.mail(to)
        this.emailSender.send(to.email, mail.subject, Pair(mail.type, mail.message))
    }
}