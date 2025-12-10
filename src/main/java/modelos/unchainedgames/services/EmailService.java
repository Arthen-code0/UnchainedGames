package modelos.unchainedgames.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String verificationCode) {
        try {
            logger.info("Enviando email de verificación a: {}", to);

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(fromEmail);

            message.setTo(to);
            message.setSubject("Verifica tu cuenta - Unchained Games");
            message.setText(
                "¡Bienvenido a Unchained Games!\n\n" +
                "Tu código de verificación es: " + verificationCode + "\n\n" +
                "O haz clic en el siguiente enlace para verificar tu cuenta:\n" +
                frontendUrl + "/verificar-cuenta?email=" + to + "&code=" + verificationCode + "\n\n" +
                "Este código expira en 24 horas.\n\n" +
                "Si no creaste esta cuenta, ignora este mensaje.\n\n" +
                "Saludos,\n" +
                "El equipo de Unchained Games"
            );

            mailSender.send(message);
            logger.info("Email de verificación enviado exitosamente a: {}", to);

        } catch (Exception e) {
            logger.error("ERROR enviando email de verificación a {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("No se pudo enviar el email de verificación: " + e.getMessage());
        }
    }

    public void sendRecoveryEmail(String to, String recoveryCode) {
        try {
            logger.info("Enviando email de recuperación a: {}", to);

            SimpleMailMessage message = new SimpleMailMessage();

            // USAR EL MISMO EMAIL
            message.setFrom(fromEmail);  // ← CAMBIA ESTO

            message.setTo(to);
            message.setSubject("Recuperación de contraseña - Unchained Games");
            message.setText(
                "Hola,\n\n" +
                "Recibimos una solicitud para restablecer tu contraseña.\n\n" +
                "Tu código de recuperación es: " + recoveryCode + "\n\n" +
                "O haz clic en el siguiente enlace:\n" +
                frontendUrl + "/restablecer-contrasena?email=" + to + "&code=" + recoveryCode + "\n\n" +
                "Este código expira en 1 hora.\n\n" +
                "Si no solicitaste esto, ignora este mensaje.\n\n" +
                "Saludos,\n" +
                "El equipo de Unchained Games"
            );

            mailSender.send(message);
            logger.info("Email de recuperación enviado exitosamente a: {}", to);

        } catch (Exception e) {
            logger.error("ERROR enviando email de recuperación a {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("No se pudo enviar el email de recuperación: " + e.getMessage());
        }
    }
}