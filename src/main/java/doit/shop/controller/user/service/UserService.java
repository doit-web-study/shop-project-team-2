package doit.shop.controller.user.service;

import doit.shop.controller.user.domain.User;
import doit.shop.controller.user.domain.UserRepository;
import doit.shop.controller.user.dto.UserInfoResponse;
import doit.shop.controller.user.dto.UserLoginRequest;
import doit.shop.controller.user.dto.UserLoginResponse;
import doit.shop.controller.user.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void checkDuplicatedId(String userLoginId){
        User user = userRepository.findByUserLoginId(userLoginId);
        if(user != null){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public UserLoginResponse signUp(UserSignUpRequest userSignUpRequest){
        if(userRepository.findByUserLoginId(userSignUpRequest.userLoginId()) != null){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userName(userSignUpRequest.userName())
                .userLoginId(userSignUpRequest.userLoginId())
                .userPassword(userSignUpRequest.userPassword())
                .userNickName(userSignUpRequest.userNickName())
                .userPhoneNumber(userSignUpRequest.userPhoneNumber())
                .build();

        User savedUser = userRepository.save(user);

        return new UserLoginResponse(savedUser.getId());
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest){
        User user = userRepository.findByUserLoginIdAndUserPassword(userLoginRequest.userLoginId(), userLoginRequest.userPassword());
        if(user == null){
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return new UserLoginResponse(user.getId());
    }

    public UserInfoResponse getUserInfo(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("일치하는 회원이 없습니다.");
        }

        return new UserInfoResponse(user.get().getId(), user.get().getUserLoginId(), user.get().getUserPassword(), user.get().getUserNickName(), user.get().getUserPhoneNumber());
    }
}
