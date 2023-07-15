package peaksoft.house.gadgetariumb9.validation.phoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  private static final String PHONE_NUMBER_PATTERN = "^\\+996(?:551|552|553|554|555|556|557|558|559|755|990|995|997|999|998|770|771|772|773|774|776|777|778|779|220|221|222|700|702|703|704|705|706|707|708|709|500|501|502|504|505|507|508|509)\\d{6}$";

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (phoneNumber == null) {
      return false;
    }
    return phoneNumber.matches(PHONE_NUMBER_PATTERN);
  }
}
