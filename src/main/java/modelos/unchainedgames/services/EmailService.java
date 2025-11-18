package modelos.unchainedgames.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String verificationCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Verificación de cuenta - Unchained Games");
            message.setText("Tu código de verificación es: " + verificationCode +
                    "\n\nPor favor, usa este código para activar tu cuenta.");

            mailSender.send(message);
            log.info("Email de verificación enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error enviando email de verificación a: {}", toEmail, e);
            // Puedes lanzar una excepción personalizada si lo prefieres
        }
    }

    public void sendRecoveryCodeEmail(String toEmail, String recoveryCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Recuperación de contraseña - Unchained Games");
            message.setText("Tu código de recuperación es: " + recoveryCode +
                    "\n\nUsa este código para restablecer tu contraseña.");

            mailSender.send(message);
            log.info("Email de recuperación enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error enviando email de recuperación a: {}", toEmail, e);
        }
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("Email enviado a: {}", to);
        } catch (Exception e) {
            log.error("Error enviando email a: {}", to, e);
        }
    }
}