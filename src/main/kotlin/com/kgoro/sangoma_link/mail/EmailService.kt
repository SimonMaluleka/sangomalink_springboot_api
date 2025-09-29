package com.kgoro.sangoma_link.mail

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.nio.charset.StandardCharsets

@Service
class EmailService (
    val mailSender: JavaMailSender,
    val templateEngine: SpringTemplateEngine
    ){

    @Async
    fun sendEmail(
        to: String,
        username: String,
        emailTemplate: EmailTemplate?,
        confirmationUrl: String,
        activationCode: String,
        subject:String
    ){
        val templateName = emailTemplate?.name ?: "confirm-email"
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(
            mimeMessage,
            MimeMessageHelper.MULTIPART_MODE_MIXED,
            StandardCharsets.UTF_8.name()
        )

        val properties: MutableMap<String, Any> = mutableMapOf()
        properties["username"] = username
        properties["confirmationUrl"] = confirmationUrl
        properties["activation_code"] = activationCode

        val context = Context()
        context.setVariables(properties)

        helper.setFrom("support@voxlast.com")
        helper.setTo(to)
        helper.setSubject(subject)

        val template = templateEngine.process(templateName, context)

        helper.setText(template, true)

        mailSender.send(mimeMessage)

    }
}

