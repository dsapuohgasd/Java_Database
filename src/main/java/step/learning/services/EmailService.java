package step.learning.services;

public interface EmailService {
    boolean send(String to, String subject, String text);
}
