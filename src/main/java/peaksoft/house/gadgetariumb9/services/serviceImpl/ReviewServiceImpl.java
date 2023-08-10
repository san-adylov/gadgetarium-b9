package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.review.AnswerRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewUserRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewUserResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Review;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.ReviewRepository;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.ReviewService;
import peaksoft.house.gadgetariumb9.template.ReviewTemplate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final SubProductRepository subProductRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewTemplate reviewTemplate;

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;


    @Override
    public ReviewRatingResponse countReviewsRating(Long subProductId) {
        reviewRepository.findById(subProductId).orElseThrow(() -> {
            log.error("SubProductId not found");
            return new NotFoundException("Review with subProductId: " + subProductId + " not found");
        });

        int totalRatingPoints = 0;
        int totalReviews = 0;
        Map<Integer, Integer> counts = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            int ratingCount = reviewRepository.countReviewRating(subProductId, i);
            totalRatingPoints += ratingCount * i;
            totalReviews += ratingCount;
            counts.put(i, ratingCount);
        }

        double ratingScore = (totalReviews == 0) ? 0 : (double) totalRatingPoints / totalReviews;

        return ReviewRatingResponse.builder()
                .quantity(totalReviews)
                .rating(ratingScore)
                .counts(counts)
                .build();
    }

    @Override
    public SimpleResponse saveReview(ReviewRequest reviewRequest) {
        SubProduct subProduct = subProductRepository.findById(reviewRequest.getSubProductId())
                .orElseThrow(() -> {
                    log.error(
                            String.format("SubProduct with id: %s not found!", reviewRequest.getSubProductId()));
                    return new NotFoundException(
                            String.format("SubProduct with id: %s not found!", reviewRequest.getSubProductId()));
                });

        User user = jwtService.getAuthenticationUser();

        if (subProduct.getReviews() != null) {
            for (Review r : subProduct.getReviews()) {
                if (r.getUser().getId().equals(user.getId())) {
                    log.error("User has already added a review for this product");
                    throw new AlreadyExistException("User has already added a review for this product");
                }
            }
        }

        if (reviewRequest.getGrade() > 5) {
            log.error("Grade cannot exceed 5!");
            throw new BadCredentialException("Grade cannot exceed 5!");
        } else if (reviewRequest.getGrade() < 0) {
            log.error("Grade cannot be negative!");
            throw new BadCredentialException("Grade cannot be negative!");
        }

        Review review = Review.builder()
                .subProduct(subProduct)
                .comment(reviewRequest.getComment())
                .replyToComment(null)
                .grade(reviewRequest.getGrade())
                .dateCreatAd(ZonedDateTime.now())
                .imageLink(reviewRequest.getImageLink())
                .user(user)
                .build();

        if (checkUserPurchasedProduct(review)) {
            log.info("Review successfully save!");
            subProduct.addReviews(review);
            reviewRepository.save(review);
        } else {
            log.error("You must buy this product if you want to leave a review");
            throw new BadCredentialException("You must buy this product if you want to leave a review");
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Review successfully save !")
                .build();
    }

    public boolean checkUserPurchasedProduct(Review review) {
        User user = review.getUser();
        SubProduct subProduct = review.getSubProduct();

        if (user != null && subProduct != null) {
            String sql = "SELECT COUNT(*) FROM orders o " +
                    "INNER JOIN orders_sub_products osp ON o.id = osp.orders_id " +
                    "WHERE o.user_id = ? AND osp.sub_products_id = ?";

            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, user.getId(), subProduct.getId());

            return count != null && count > 0;
        } else {
            throw new RuntimeException("Отзыв должен быть связан с пользователем и продуктом.");
        }
    }

    @Override
    public ReviewPagination getAllReviews(Long subProductId,
                                          int pageSize,
                                          int numberPage) {
        return reviewTemplate.getAll(subProductId, pageSize, numberPage);
    }

    @Override
    public SimpleResponse deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.error(String.format("Review with id %s not found", reviewId));
            return new NotFoundException(String.format("Review with id %s not found", reviewId));
        });

        reviewRepository.delete(review);
        log.info(String.format("Reviews with id %s deleted", reviewId));
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(("Review with id: %s successfully deleted!".formatted(reviewId)))
                .build();
    }

    @Override
    public SimpleResponse replyToComment(AnswerRequest answerRequest) {
        Review review = reviewRepository.findById(answerRequest.getReviewId()).orElseThrow(() -> {
            log.error(String.format("Review with id: %s not found!", answerRequest.getReviewId()));
            return new NotFoundException(
                    String.format("Review with id: %s not found!", answerRequest.getReviewId()));
        });

        if (review.getReplyToComment() == null) {
            review.setReplyToComment(answerRequest.getReplyToComment());
            reviewRepository.save(review);
            log.info("Reply to comment successfully save!");
            return SimpleResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Reply to comment successfully saved!")
                    .build();
        } else {
            log.info("Review with id: " + answerRequest.getReviewId() + " has already been answered!");
            return SimpleResponse.builder()
                    .status(HttpStatus.FOUND)
                    .message("The review has already been answered!")
                    .build();
        }
    }

    @Override
    public SimpleResponse updateAnswer(Long reviewId, String text) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error(String.format("Review with id %s not found", reviewId));
                    return new NotFoundException(String.format("Отзыв с id: %s не найден", reviewId));
                });

        if (!review.getReplyToComment().isEmpty()) {
            if (!text.isBlank()) {
                review.setReplyToComment(text);
                reviewRepository.save(review);

                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Answer successfully updated!")
                        .build();
            } else {
                log.error("Answer is empty!");
                throw new BadCredentialException("Answer is empty!");
            }
        } else {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Сomment has no response!")
                    .build();
        }
    }

  @Override
  public ReviewGradeInfo getFeedback(Long subProductId) {
    return reviewTemplate.getFeedback(subProductId);
  }

  @Override
  public ReviewUserResponse updateComment(ReviewUserRequest request) {
    Review review = reviewRepository.findById(request.getReviewId()).orElseThrow(() -> {
      log.error(String.format("Review with id %s not found", request.getReviewId()));
      return new NotFoundException(String.format("Review with id %s not found", request.getReviewId()));
    });

    User user = jwtService.getAuthenticationUser();

    if (review.getUser().equals(user)){
      log.info("You are the owner of this review!");
      if (review.getReplyToComment() == null){
        review.setComment(request.getComment());
        review.setGrade(request.getGrade());
        review.setImageLink(request.getImageLink());
        reviewRepository.save(review);
        return new ReviewUserResponse(user.getEmail(),true,"Your comment successfully updated!");
      } else {
        log.error("You can't edit a comment because an admin has already replied to it!");
        throw new BadCredentialException("You can't edit a comment because an admin has already replied to it!");
      }
    } else {
      log.error("Access denied. You are not the owner of this review!");
      return new ReviewUserResponse(user.getEmail(),false,"Access denied. You are not the owner of this review!");
    }

  }

  @Override
  public ReviewUserResponse deleteComment(Long reviewId) {
    Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
      log.error(String.format("Review with id %s not found", reviewId));
      return new NotFoundException(String.format("Review with id %s not found", reviewId));
    });

    User user = jwtService.getAuthenticationUser();

    if (review.getUser().equals(user)){
      log.info("You are the owner of this review!");
      if (review.getReplyToComment() == null){
        reviewRepository.delete(review);
        return new ReviewUserResponse(user.getEmail(),true,"Your comment successfully deleted!");
      } else {
        log.error("You can't delete a comment because an admin has already replied to it!");
        throw new BadCredentialException("You can't delete a comment because an admin has already replied to it!");
      }
    } else {
      log.error("Access denied. You are not the owner of this review!");
      return new ReviewUserResponse(user.getEmail(),false,"Access denied. You are not the owner of this review!");
    }
  }
}