package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.mailing.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.models.Mailing;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.MailingRepository;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.MailingService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {

    private final JavaMailSender emailSender;

    private final UserRepository userRepository;

    private final MailingRepository mailingRepository;

    private final EntityManager entityManager;

    private final TemplateEngine templateEngine;

    private final JwtService jwtService;

    public static final String UTF_8_ENCODING = "UTF-8";

    @Override
    public SimpleResponse sendHtmlEmail(MailingRequest mailingRequest) {
        List<String> emails = getUsers();
        try {
            LocalDate currentDate = LocalDate.now();

            if (mailingRequest.getStartDate().isBefore(currentDate) ||
                mailingRequest.getFinishDate().isBefore(mailingRequest.getStartDate()) ||
                mailingRequest.getStartDate().isEqual(mailingRequest.getFinishDate())) {
                log.error("The date must be in the present or future, and the end date must be after the start date, and they cannot be on the same day!");
                throw new BadCredentialException("The date must be in the present or future, and the end date must be after the start date, and they cannot be on the same day!");
            }

            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime startDateZ = ZonedDateTime.of(mailingRequest.getStartDate().atStartOfDay(), zoneId);
            ZonedDateTime finishDateZ = ZonedDateTime.of(mailingRequest.getFinishDate().atStartOfDay(), zoneId);

            Mailing mailing = Mailing.builder()
                    .title(mailingRequest.getTitle())
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
            String text = templateEngine.process("templates/email-template.html", context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(mailingRequest.getTitle());
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

    @Override
    public SimpleResponse followUser(String email) {
        User user = jwtService.getAuthenticationUser();
        if (user == null) {
            return new SimpleResponse("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        if (user.getEmail().equalsIgnoreCase(email)) {
            if (!user.isSubscription()) {
                user.setSubscription(true);
                userRepository.save(user);
                return new SimpleResponse("Subscription successful", HttpStatus.OK);
            } else {
                return new SimpleResponse("Already subscribed", HttpStatus.CONFLICT);
            }
        } else {
            return new SimpleResponse("Invalid request", HttpStatus.BAD_REQUEST);
        }
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
