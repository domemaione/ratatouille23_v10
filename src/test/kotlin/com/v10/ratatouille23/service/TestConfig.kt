package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.component.EmailSender
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
@EnableAutoConfiguration(exclude = [SendGridAutoConfiguration::class])
class TestConfig {
    // La tua configurazione aggiuntiva per i test, se necessario

}
