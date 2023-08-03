package com.ssafy.singstreet.user.service;

import com.ssafy.singstreet.user.Exception.UserNotFoundException;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import com.ssafy.singstreet.user.model.TokenInfo;
import com.ssafy.singstreet.user.model.UserDetailDTO;
import com.ssafy.singstreet.user.model.UserRegistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    //밑의 4 변수는 메일을 보낼때 필요한 상수입니다.
    private static final String SMTP_HOST = "smtp.naver.com"; // e.g., smtp.gmail.com
    private static final String SMTP_PORT = "465";
    private static final String EMAIL_USERNAME = "human3452@naver.com";
    private static final String EMAIL_PASSWORD = "trustworthy1!";

    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private JwtTokenProvider jwtTokenProvider;
    //유저리포지토리 받아오기
    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    //유저 등록
    public User registerUser(UserRegistDTO registrationDTO) {
        // Prepare user roles
        ArrayList<String> roles = new ArrayList<>();
        roles.add("USER");

        // Create new user object
        User user = User.builder()
                .nickname(registrationDTO.getNickname())
                .userImg(registrationDTO.getUserImg())
                .email(registrationDTO.getEmail())
                .gender(registrationDTO.getGender())
                .password(registrationDTO.getPassword()) // In real-world scenarios, don't forget to encrypt the password!
                .roles(roles)
                .isDeleted(false)
                .build();

        // Save the user object to the database
        return userRepository.save(user);
    }

    //이메일 중복인가?
    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByEmail(email);
    }

    //닉네임 중복인가?
    public boolean isNicknameDuplicated(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    //임시 비밀번호 생성하고 저장하기 및 메일 전송
    public User temporaryPassword(String email) throws UserNotFoundException{
        User user=(User) userRepository.findByEmail(email);

        if(user==null) {
            throw new UserNotFoundException("등록된 이메일이 아닙니다.");
        }
        String tmpPassword=generateRandomPassword();
        user.updatePassword(tmpPassword);
        String subject="임시 비밀번호 발송";
        String body="당신의 임시 비밀번호는 "+tmpPassword+"입니다.";
        try{
            sendEmail(email, subject, body);
        }catch(MessagingException e){
            System.out.println("메일 안보내짐 ㅅㄱ");
        }
        return userRepository.save(user);
    }

    //이메일 저장하기
    public void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_USERNAME));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
    //15자리의 랜덤 비밀번호를 생성하는 메서드입니다.
    private String generateRandomPassword() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomPassword = new StringBuilder();
        Random random = new Random();
        while (randomPassword.length() < 15) {
            int index = random.nextInt(allowedChars.length());
            randomPassword.append(allowedChars.charAt(index));
        }
        return randomPassword.toString();
    }
    //인증번호. 숫자, 6자리!
    public String SetRandomCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    @Transactional
    public User updateUser(Integer userId, String newNickname, String newUserImg, Character newGender, String newPassword) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        user.updateUserInfo(newNickname, newUserImg, newGender, newPassword);

        return userRepository.save(user);
    }

    public User getUser(int user_id) throws UserNotFoundException {
        Optional<User> member=userRepository.findById(user_id);
        if(!member.isPresent()){
            throw new UserNotFoundException("유저 아이디 번호가 존재하지 않습니다.");
        }
        User user=member.get();
        UserDetailDTO udto=new UserDetailDTO();
        udto.setEmail(user.getEmail());
        udto.setUserImg(user.getUserImg());
        udto.setGender(user.getGender());
        udto.setNickname(user.getNickname());
        return user;
    }

    public void softDeleteUser(String userName) throws UserNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(userName));
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with ID " + userName + " not found.");
        }

        User user = userOptional.get();
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(memberId));
        if(optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setJwtToken(tokenInfo.getAccessToken());
            existingUser.setRefreshToken(tokenInfo.getRefreshToken());
            userRepository.save(existingUser);
        }
        return tokenInfo;
    }

    public void logout(String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setJwtToken(null);
            existingUser.setRefreshToken(null);
            userRepository.save(existingUser);
        } else {
            // handle user not found situation
        }
    }
}
