package com.tangerinedelivery.controller;

import com.tangerinedelivery.controller.dto.CommentDTO;
import com.tangerinedelivery.exception.ProductNotFoundException;
import com.tangerinedelivery.repo.entity.CommentEntity;
import com.tangerinedelivery.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping("api/comment")
    @CrossOrigin
    public class CommentController {

        private CommentService commentService;

        @PostMapping("/{commentId}/create")
        public ResponseEntity createComment(@RequestParam(value = "productid") Long productid, CommentDTO commentDTO,@RequestParam(value = "userid") Long userid) throws ProductNotFoundException {
            {
                CommentEntity comment = commentService.saveComment(Long.parseLong(String.valueOf(productid)), commentDTO, String.valueOf(userid));
                CommentDTO createdComment = commentService.commentToCommentDTO(comment);
                return new ResponseEntity<>(createdComment, HttpStatus.OK);
            }
        }
        @GetMapping("/productId}/all")
        public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("productid") Long productid) {
            List<CommentDTO> commentDTOList = commentService.getAllCommentsForProduct(Long.parseLong(String.valueOf(productid)))
                    .stream()
                    .map(commentService::commentToCommentDTO)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        }


        @PostMapping("/{commentId}/delete")
        public ResponseEntity deleteComment(@PathVariable("commentId") String commentId) {
            commentService.deleteComment(Long.parseLong(commentId));
            return ResponseEntity.ok("Product deleted");
        }

    }
