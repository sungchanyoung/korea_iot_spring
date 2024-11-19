
package org.example.springbootdeveloper.controller;

import org.example.springbootdeveloper.common.constant.ApiMappingPattern;
import org.example.springbootdeveloper.dto.request.PostRequestDto;
import org.example.springbootdeveloper.dto.response.PagedResponseDto;
import org.example.springbootdeveloper.dto.response.PostResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.POST)
public class PostController {

    @Autowired
    private PostService postService;

    // CRUD 기능 명시
    @PostMapping
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestBody PostRequestDto dto) {
        ResponseDto<PostResponseDto> result = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @GetMapping
//    public ResponseDto<List<PostResponseDto>> getAllPosts() {
//        return postService.getAllPosts();
//    }

    @GetMapping
    //status 반환 -그 데이터를 PagedResponseDto로 감싸서  보낸다
    public ResponseEntity<ResponseDto<PagedResponseDto<PostResponseDto>>> getPosts(
            //@wRequestparam  :url를 통해 key와 value 형태로 요청 값을 보냄
            //page:현재 페잊 번호 , size : 페이지 당 표시할 데이터 갯수
            @RequestParam int page,
            @RequestParam int size) {
        try{
            ResponseDto<PagedResponseDto<PostResponseDto>> result = postService.getPosts(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            //예외가 발생했을때, 예외의 전체 스택 트레이스 출력하는 메서드 입니다
            //-주로 디버깅 하는 목적사용
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        //요청한 페이지에 대한 데이터를 반환 (응답)

    }


    @GetMapping("/{postId}")
    public ResponseDto<PostResponseDto> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public ResponseDto<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto dto) {
        return postService.updatePost(postId, dto);
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<Void> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}
