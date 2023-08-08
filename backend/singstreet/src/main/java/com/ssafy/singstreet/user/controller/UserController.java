package com.ssafy.singstreet.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singstreet.user.Exception.UserNotFoundException;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.model.MemberLoginRequestDto;
import com.ssafy.singstreet.user.model.TokenInfo;
import com.ssafy.singstreet.user.model.UserDetailDTO;
import com.ssafy.singstreet.user.model.UserRegistDTO;
import com.ssafy.singstreet.user.service.JwtExpiredException;
import com.ssafy.singstreet.user.service.JwtTokenProvider;
import com.ssafy.singstreet.user.service.SecurityUtil;
import com.ssafy.singstreet.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;


import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private static HashMap<String, String> VerifyCode=new HashMap<>();
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("auth/revalidate")
    @ApiOperation(value="토큰 재발급하기", notes="리프레시토큰을 받고 다시 액세스토큰을 돌려줍니다.")
    public ResponseEntity<String> revalidate(String refreshtoken){
        String newAccesstoken = null;
        try{
            newAccesstoken=userService.revalidate(refreshtoken);
        }catch (JwtExpiredException e){
            return new ResponseEntity<>("리프레시 토큰이 만료되었습니다.", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(newAccesstoken, HttpStatus.OK);
    }

    @PostMapping("myuser")
    @ApiOperation(value="내 이메일 가져오기", notes="로그인한 유저의 이메일을 가져오는 메서드입니다!")
    public String getMyuser(){
        return SecurityUtil.getCurrentMemberId();
    }

    @PostMapping("/auth/login")
    @ApiOperation(value="로그인", notes="로그인 하는 메서드입니다!")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = userService.login(memberId, password);

        return tokenInfo;
    }

    @PostMapping("/auth/logout")
    @ApiOperation(value="로그아웃", notes="로그아웃 하는 메서드")
    public ResponseEntity<String> logout() {
        String email = SecurityUtil.getCurrentMemberId();
        if (email.equals("anonymousUser")){
            return ResponseEntity.noContent().build();
        }
        userService.logout(email);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/auth/email/{email}")
    @ResponseBody
    @ApiOperation(value="이메일 중복 체크 밋 인증메일 보내기!", notes="이메일 중복 체크가 완료되면 메일을 보냅니다.")
    public ResponseEntity<String> EmailCheck(
            @PathVariable("email") String email
            ) {

        // Check for email duplication
        if (userService.isEmailDuplicated(email)) {
            return ResponseEntity.badRequest().body("이메일이 중복되었습니다.");
        }
        String code=userService.SetRandomCode();
        VerifyCode.put(email, code);
        // Recipient's email address
        String subject = "인증번호 전송";
        String body = "당신의 인증번호는 "+code+"입니다";

        try {
            userService.sendEmail(email, subject, body);
            System.out.println("어 메일 전송했어");
        } catch (MessagingException e) {
            System.err.println("그것도 못하냐 허접아" + e.getMessage());
            return ResponseEntity.unprocessableEntity().body("뭔가 뭔가 벌어지고 있음");
        }

        return ResponseEntity.ok("이메일 확인코드가 발송되었습니다.");
    }

    @GetMapping("/auth/email/auth/{authCode}/{email}")
    @ResponseBody
    @ApiOperation(value="이메일 인증번호 입력하기", notes="인증번호가 입력되면 맞는지 검증합니다.")
    public ResponseEntity<String> EmailVerify(
            @PathVariable String authCode,
            @PathVariable String email
            ) {

        // Check for email duplication
        if (!VerifyCode.get(email).equals(authCode)) {
            return ResponseEntity.badRequest().body("인증번호가 맞지 않습니다.");
        }
        VerifyCode.remove(email);
        return ResponseEntity.ok("인증번호가 확인되었습니다.");
    }
    @GetMapping("/admin/user")
    @ResponseBody
    @ApiOperation(value="모든 유저 가져오기", notes="모든 유저정보를 가져오지만, 관리자 권한이 필요합니다.")
    public List<User> getalluser(){
        return userService.getAll();
    }
    @GetMapping("/auth/nickname/{nickname}")
    @ResponseBody
    @ApiOperation(value="닉네임 중복 체크", notes="닉네임이 중복되었는지를 체크합니다.")
    public ResponseEntity<String> nicknameCheck(
            @PathVariable String nickname
    ){
        if(userService.isNicknameDuplicated(nickname)){
            return ResponseEntity.badRequest().body("닉네임이 중복되었습니다");
        }
        return  ResponseEntity.ok("닉네임이 사용가능합니다");
    }


    @PostMapping("/auth/password")
    @ApiOperation(value="임시 비밀번호 전송", notes="임시 비밀번호를 전송하고, DB에 임시 비밀번호르 덮어씌웁니다")
    public ResponseEntity<String> sendTemporaryPassword(@RequestBody String email) throws UserNotFoundException {
        userService.temporaryPassword(email);
        return ResponseEntity.ok("메시지가 발송되었습니다.");
    }
//    @PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ApiOperation(value="유저 등록하기", notes="유저를 등록하는 메서드입니다.")
//    public ResponseEntity<String> CreateUser(@RequestBody UserRegistDTO registrationDTO, @ModelAttribute MultipartFile multipartFile) {
//        System.out.println(multipartFile);
//        try {
//            User registeredUser = userService.registerUser(registrationDTO, multipartFile);
//            return ResponseEntity.ok(registeredUser.getNickname()+"님 환영합니다");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 등록 에러남 ㅅㄱ");
//        }
//    }
@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
@ApiOperation(value = "유저 등록하기", notes = "유저를 등록하는 메서드입니다.")
public ResponseEntity<String> CreateUser(
        @RequestPart(value = "registrationDTO", required = false) UserRegistDTO registrationDTO,
        @RequestPart(value = "file", required = false) MultipartFile file) {
    try {
        if (registrationDTO != null) {
            User registeredUser = userService.registerUser(registrationDTO, file);
            return ResponseEntity.ok(registeredUser.getNickname() + "님 환영합니다");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON 데이터나 파일을 제대로 보내주세요.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 등록 에러남 ㅅㄱ");
    }
}


    @PutMapping("/user")
   @ApiOperation(value="유저 수정하기", notes="유저를 수정하는 메서드입니다.")
   public ResponseEntity<String> updateUser(@RequestParam Integer user_id,
                                            @RequestParam String newNickname,
                                            @RequestParam String newUserImg,
                                            @RequestParam Character newGender,
                                            @RequestParam String newPassword) {
       try {
           User updatedUser = userService.updateUser(user_id, newNickname, newUserImg, newGender, newPassword);
           return ResponseEntity.ok("User with ID " + user_id + " updated successfully.");
       } catch (UserNotFoundException e) {
           return ResponseEntity.notFound().build();
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.");
       }
   }
   @GetMapping("/user/{user_id}")
   @ResponseBody
   @ApiOperation(value="유저 상세정보 받아오기", notes="한 유저의 상세정보를 받아옵니다.")
   public ResponseEntity<UserDetailDTO> GetUser(@PathVariable("user_id") int userId) throws UserNotFoundException {
       UserDetailDTO result=userService.getUser(userId);
        return ResponseEntity.ok(result);
   }

    @GetMapping("/user")
    @ResponseBody
    @ApiOperation(value="내 유저 상세정보 받아오기", notes="내 유저의 상세정보를 받아옵니다.")
    public ResponseEntity<UserDetailDTO> GetUser() throws UserNotFoundException {
        int userId = userService.getCurrentUserId();
        UserDetailDTO result=userService.getUser(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/leave")
    @ApiOperation(value="유저 삭제하기", notes="현재 유저를 삭제합니다. 단 is_deleted만 수정함으로써 유저 정보는 db에 남아있습니다.")
    public ResponseEntity<String> softDeleteUser() {
        try {
            String userName=SecurityUtil.getCurrentMemberId();
            userService.softDeleteUser(userName);
            return ResponseEntity.ok("유저 ID " + userName + "가 삭제되었습니다");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제가 실패했네요");
        }
    }

}
