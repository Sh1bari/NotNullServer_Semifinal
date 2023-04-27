package com.example.notnullserver_semifinal.authorisation.mailSender.mailRepo;

import com.example.notnullserver_semifinal.authorisation.mailSender.models.Mail;
import org.springframework.data.repository.CrudRepository;

public interface MailRepo extends CrudRepository<Mail, String> {
    Mail findByMail(String mail);
}
