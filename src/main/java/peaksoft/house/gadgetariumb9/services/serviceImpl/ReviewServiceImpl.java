package peaksoft.house.gadgetariumb9.services.serviceImpl;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.review.AnswerRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SubProductRepository subProductRepository;
    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

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
        SubProduct subProduct = subProductRepository.findById(reviewRequest.getSubProductId()).orElseThrow(()->{
            log.error(String.format("SubProduct with id: %s not found!",reviewRequest.getSubProductId()));
            return new NotFoundException(String.format("SubProduct with id: %s not found!",reviewRequest.getSubProductId()));
        });

        User user = jwtService.getAuthentication();

        if (subProduct.getReviews() != null) {
            for (Review r : subProduct.getReviews()) {
                if (r.getUser().getId().equals(user.getId())) {
                    log.error("User has already added a review for this product");
                    throw new AlreadyExistException("User has already added a review for this product");
                }
            }
        }

        if (userHasPurchasedProduct(user.getId(),reviewRequest.getSubProductId())){
            Review review = Review.builder()
                .subProduct(subProduct)
                .comment(reviewRequest.getComment())
                .replyToComment(null)
                .grade(reviewRequest.getGrade())
                .dateCreatAd(ZonedDateTime.now())
                .imageLink(reviewRequest.getImageLink())
                .user(user)
                .build();

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
//
    private boolean userHasPurchasedProduct(long userId, long subProductId) {
        String sql = "SELECT 1 " +
            "FROM orders o " +
            "JOIN orders_sub_products osp on o.id = osp.orders_id " +
            "WHERE o.user_id = ? " +
            "AND o.status = 'DELIVERED' " +
            "AND osp.sub_products_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, subProductId}, Integer.class) != null;
    }
//
    @Override
    public List<ReviewResponse> getAllReviews(Long subProductId) {

        return null;
    }

    @Override
    public SimpleResponse deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.error(String.format("Review with id %s not found", reviewId));
            return new NotFoundException(String.format("Review with id %s not found", reviewId));
        });

        reviewRepository.deleteById(review.getId());
        log.info(String.format("Reviews with id %s deleted", reviewId));
        return SimpleResponse.builder()
            .status(HttpStatus.OK)
            .message(("Review with id: %s successfully deleted!".formatted(reviewId)))
            .build();
    }

    @Override
    public SimpleResponse replyToComment(AnswerRequest answerRequest) {
        Review review = reviewRepository.findById(answerRequest.getReviewId()).orElseThrow(()->{
            log.error(String.format("Review with id: %s not found!",answerRequest.getReviewId()));
            return new NotFoundException(String.format("Review with id: %s not found!",answerRequest.getReviewId()));
        });

        if (review.getReplyToComment() == null){
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
    public SimpleResponse updateAnswer(AnswerRequest answerRequest) {
        Review review = reviewRepository.findById(answerRequest.getReviewId())
            .orElseThrow(() -> {
                log.error(String.format("Review with id %s not found", answerRequest.getReviewId()));
                return new NotFoundException(String.format("Отзыв с id: %s не найден", answerRequest.getReviewId()));
            });

        if (!review.getReplyToComment().isEmpty()){
            if (answerRequest.getReplyToComment().isBlank()){
                review.setReplyToComment(answerRequest.getReplyToComment());
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
}