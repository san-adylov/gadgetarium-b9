package peaksoft.house.gadgetariumb9.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRatingRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.ReviewRepository;
import peaksoft.house.gadgetariumb9.service.ReviewService;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public ReviewRatingResponse countReviewsRating(ReviewRatingRequest request) {
        long subProductId = request.getSubProductId();
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


}





