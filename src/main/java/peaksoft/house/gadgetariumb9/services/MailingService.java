package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.mailing.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface MailingService {
  
    SimpleResponse sendHtmlEmail(MailingRequest mailingRequest);

    SimpleResponse followUser (String email);
}
