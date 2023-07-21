package peaksoft.house.gadgetariumb9.service;

import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface MailingService {
  SimpleResponse sendHtmlEmail(MailingRequest mailingRequest);
}
