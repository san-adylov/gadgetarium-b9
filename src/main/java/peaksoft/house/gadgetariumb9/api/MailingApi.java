package peaksoft.house.gadgetariumb9.api;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.MailingRequest;
import peaksoft.house.gadgetariumb9.service.MailingService;

@RestController
@RequestMapping("/mailing")
public class MailingApi {

  private final MailingServiceImpl mailingServiceImpl;

  @Autowired
  public MailingApi(MailingServiceImpl mailingServiceImpl) {
    this.mailingServiceImpl = mailingServiceImpl;
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendMailing(@RequestBody MailingRequest mailingRequest) {
    mailingService.sendHtmlEmail(mailingRequest);
    return ResponseEntity.ok("Mailing sent successfully");
  }





}