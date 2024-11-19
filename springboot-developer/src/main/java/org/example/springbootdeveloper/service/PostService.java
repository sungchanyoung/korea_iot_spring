package org.example.springbootdeveloper.service;

import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.CommentResponseDto;
import org.example.springbootdeveloper.dto.response.PagedResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.Post;
import org.example.springbootdeveloper.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public ResponseDto<PostResponseDto> createPost(PostRequestDto dto) {
        try {
            Post post = Post.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .author(dto.getAuthor())
                    .build();
            postRepository.save(post);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, convertToPostResponseDto(post));
        } catch (Exception e) {
            return ResponseDto.setFailed("게시글 등록 중 오류가 발생했습니다: " + e.getMessage());
        }

    }

    public ResponseDto<List<PostResponseDto>> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            List<PostResponseDto> postResponseDtos = posts.stream()
                    .map(this::convertToPostResponseDto)
                    .collect(Collectors.toList());

            if (postResponseDtos.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_POST);
            }

            return ResponseDto.setSuccess(ResponseMessage.SUCCESS, postResponseDtos);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

    }

    /**
     *
     * @param page 페이지 번호 ,크기
     * @return - 성공 메시지 와 페이징된 게시글 목록을 포힘
     * 페이지 네이션을 위한 로직 온릭 페이지 네이션을 위한 로직
     */
    public ResponseDto<PagedResponseDto<PostResponseDto>> getPosts(int page, int size) {
        PagedResponseDto<PostResponseDto> pagedResponse =null;
         try{
             //page와 size 값을 사용해 Request 객체를 생성
             // :해당 객체를 통해 DB에 해당 페이지의 POST 목록 조회
             //결과는 Page<Post> 타입으로 변환

             /**
              * 1. PageRequest : spring boot 에서 제공 해주는 Pageable인터페이스 구현채
              * -특정 페이지의 데이터  조회 요청을 정의하는 객체
              * - 페이지 번호의 크기를 기반으로 페이징 요청을 설정
              *
              * EX)PageRequest.of(int page, int size)-ex(2,10) - 3번쨰 페이지 10개 데이터
              * page- 페이지 번호 (0부터시작 )
              *size - 환 페이지에 포함할 데이터의 갯수
              * PageRequest.of(2,10)
              *
              * 2)Page<T> :JPA에서 제공하는 인터페이스
              * ,특정 페이지에 대한 데이터와 페이징 정보를 포함한 객체
              *  조회된 데이터 목록뿐 만 아니라 페이징과 관련되 메타 정보도 함께 제공
              *  주요 메서드
              *  : get Contect - 현재 페리지 데이터 모록
              *  : get Number - 현재 페리지 번호 반환(0~~~~)
              *  : git Size() : 한 페이지에 포함한 수 반환
              *    get TotalPages : 전체 페리지 수 반환
              *    get total Elements 전체 페이지수 반환
              *
              *   of 메서드는 쉽게 PageRequest 객체를 생성할 수 있도록 도와줘
              *  */
             Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size));

             List<PostResponseDto> postDtos = postPage.getContent().stream()
                     .map(PostResponseDto::new)
                     .collect(Collectors.toList());

             pagedResponse =  new PagedResponseDto<>(
                     postDtos,
                     postPage.getNumber(),//요청된 번호
                     postPage.getTotalPages(),//전체 페이지 수
                     postPage.getTotalElements()//전체 요소수 반환
             );

             return ResponseDto.setSuccess("게시글 목록 조회 성공", pagedResponse);
         } catch (Exception e) {
             e.printStackTrace();
             return  ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);

         }
    }

    public ResponseDto<PostResponseDto> getPostById(Long postId) {
        return null;
    }


    public ResponseDto<PostResponseDto> updatePost(Long postId, PostRequestDto dto) {
        return null;
    }

    public ResponseDto<Void> deletePost(Long postId) {
        return null;
    }

    private PostResponseDto convertToPostResponseDto(Post post) {
        List<CommentResponseDto> commentDtos = post.getComments().stream()
                .map(comment -> new CommentResponseDto(comment.getId(), post.getId(), comment.getContent(), comment.getCommenter()))
                .collect(Collectors.toList());

        return new PostResponseDto(
                post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), commentDtos
        );
    }

}