package com.tangerinedelivery.service;
import com.tangerinedelivery.controller.dto.CommentDTO;
import com.tangerinedelivery.repo.entity.CommentEntity;
import com.tangerinedelivery.repo.entity.ProductEntity;
import com.tangerinedelivery.repo.entity.UserEntity;
import com.tangerinedelivery.exception.ProductNotFoundException;
import com.tangerinedelivery.repo.CommentRepo;
import com.tangerinedelivery.repo.ProductRepo;
import com.tangerinedelivery.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.color.ProfileDataException;
import java.util.List;
import java.util.Optional;
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepo commentRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public CommentEntity saveComment(Long productId, CommentDTO commentDTO, String email) throws ProductNotFoundException {
        UserEntity user = userRepo.findByEmail(email);
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product cannot be found for username: " + user.getEmail()));

        CommentEntity comment = new CommentEntity();
        comment.setProduct(product);
        comment.setUserId(user.getUserID());
        comment.setUsername(user.getFirstName());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Saving comment for product: {}", product.getProductID());
        return commentRepo.save(comment);
    }

    public List<CommentEntity> getAllCommentsForProduct(Long productId) {
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new ProfileDataException("Product cannot be found"));
        List<CommentEntity> comments = commentRepo.findAllByProduct(product);
        return comments;
    }

    public void deleteComment(Long commentId) {
        Optional<CommentEntity> comment = commentRepo.findById(commentId);
        comment.ifPresent(commentRepo::delete);
    }

}
