package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.mailing.MailingRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.MailingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mailings")
@Tag(name = "Mailing API", description = "API for working with mailing")
public class MailingApi {

    private final MailingService mailingService;

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "save mailing and sending mailing",
            description = "saving mailing and sending the promotion to store users by mail")
    public SimpleResponse sendMailing(@RequestBody MailingRequest mailingRequest) {
        return mailingService.sendHtmlEmail(mailingRequest);
    }

    @Operation(summary = "Follow", description = "Creates a record of subscribers and stores them in the database.")
    @PostMapping("/follow")
    @PreAuthorize("hasAuthority('USER')")
    SimpleResponse followUser(@RequestParam String email) {
        return mailingService.followUser(email);
    }
}

