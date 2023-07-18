package peaksoft.house.gadgetariumb9.service;

import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.MailingRequest;

@Service
public interface MailingService {
  void sendHtmlEmail(MailingRequest mailingRequest);
}
