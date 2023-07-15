package peaksoft.house.gadgetariumb9.dto.request.authReqest;

public record EmailRequest(
    String toEmail,
    String subject,
    String body
) {

}
