package org.example.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.common.constant.ResponseMessage;
import org.example.springbootdeveloper.dto.response.GetUserResponseDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.entity.User;
import org.example.springbootdeveloper.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    // 비즈니스 로직
    // Controller의 요청을 받아 필요한 데이터를 Repository를 통해 얻거나 전달하고
    // , 기능 구현 후 응답을 Controller에게 전달

    // +) 기능 구현에 있어 필요한 보안을 설정

    private final UserRepository userRepository;

    public ResponseDto<GetUserResponseDto> findByEmail(String userEmail) {
        GetUserResponseDto data = null;
        try{
            Optional<User> userOptional =userRepository.findByEmail(userEmail);
            if(userOptional.isPresent()){
                data =  new GetUserResponseDto(userOptional.get());

            }else{
                return  ResponseDto.setFailed(ResponseMessage.NOT_EXIST_DATA);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return  ResponseDto.setSuccess(ResponseMessage.SUCCESS,data);
    }


    // 사용자 인증을 처리(인증 실패 시 예외가 발생)
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            dto.getEmail(),
//                            dto.getPassword()
//                    )
//            );

            // 인증 성공 후 JWT 토큰 생성

}