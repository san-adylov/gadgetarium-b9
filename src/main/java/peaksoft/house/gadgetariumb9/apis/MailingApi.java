package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.MailingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mailings")
@CrossOrigin(maxAge = 3600,origins = "*")
@Tag(name = "Mailing API", description = "Mailing List API")
public class MailingApi {

  private final MailingService mailingService;

  @PostMapping("/send")
  @PreAuthorize("hasAuthority('ADMIN')")
  @Operation(summary = "save mailing and sending mailing",
      description = "saving mailing and sending the promotion to store users by mail")
  public SimpleResponse sendMailing(@RequestBody MailingRequest mailingRequest) {
    return mailingService.sendHtmlEmail(mailingRequest);
  }
}
