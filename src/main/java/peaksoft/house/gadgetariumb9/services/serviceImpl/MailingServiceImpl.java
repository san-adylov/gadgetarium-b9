package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import peaksoft.house.gadgetariumb9.dto.request.mailing.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.models.Mailing;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.MailingRepository;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.MailingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

  public static final String EMAIL_TEMPLATE = "emailtemplate";
  private final JavaMailSender emailSender;
  private final UserRepository userRepository;
  private final MailingRepository mailingRepository;
  private final EntityManager entityManager;
  private final TemplateEngine templateEngine;
  public static final String UTF_8_ENCODING = "UTF-8";

  @Override
  @Async
  public SimpleResponse sendHtmlEmail(MailingRequest mailingRequest) {
    List<String> emails = getUsers();
    try {
      LocalDate currentDate = LocalDate.now();

      if (mailingRequest.getFinishDate().isBefore(mailingRequest.getStartDate())){
        log.error("Дата окончания не должна быть раньше даты начала акции!");
        throw new BadCredentialException("Дата окончания не должна быть раньше даты начала акции!");
      }

      if (!mailingRequest.getStartDate().isAfter(currentDate) || !mailingRequest.getFinishDate().isAfter(currentDate)) {
        log.error("Дата ложна быть в будущем!");
        throw new BadCredentialException("Дата должна быть в будущем!");
      }

      ZoneId zoneId = ZoneId.systemDefault();
      ZonedDateTime startDateZ = ZonedDateTime.of(mailingRequest.getStartDate().atStartOfDay(), zoneId);
      ZonedDateTime finishDateZ = ZonedDateTime.of(mailingRequest.getFinishDate().atStartOfDay(), zoneId);

      String title = "Акция в магазине Gadgetarium!";

      Mailing mailing = Mailing.builder()
          .title(title)
          .description(mailingRequest.getDescription())
          .startDate(startDateZ)
          .finishDate(finishDateZ)
          .image(mailingRequest.getImage())
          .build();
      mailingRepository.save(mailing);
      Context context = new Context();
      context.setVariable("description", mailingRequest.getDescription());
      context.setVariable("image", mailingRequest.getImage());
      context.setVariable("startDate", mailingRequest.getStartDate());
      context.setVariable("finishDate", mailingRequest.getFinishDate());
      String text = templateEngine.process(EMAIL_TEMPLATE, context);
      MimeMessage message = getMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
      helper.setPriority(1);
      helper.setSubject(title);
      helper.setFrom("shop.gadgetarium.kg@gmail.com");
      for (String email : emails) {
        User user = getUserByEmail(email);
        if (user != null && user.isSubscription()) {
          helper.setTo(email);
        }
      }
      helper.setText(text, true);

      emailSender.send(message);

    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      throw new RuntimeException(exception.getMessage());
    }
    return SimpleResponse.builder()
        .status(HttpStatus.OK)
        .message("Mailing successfully send!")
        .build();
  }

  private List<String> getUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(User::getEmail)
        .collect(Collectors.toList());
  }

  public User getUserByEmail(String email) {
    String jpql = "SELECT u FROM User u WHERE u.email = :email";
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setParameter("email", email);
    List<User> users = query.getResultList();
    return users.isEmpty() ? null : users.get(0);
  }

  private MimeMessage getMimeMessage() {
    return emailSender.createMimeMessage();
  }
}
