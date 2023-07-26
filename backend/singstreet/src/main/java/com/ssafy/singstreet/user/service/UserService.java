package com.ssafy.singstreet.user.service;

import com.ssafy.singstreet.user.Exception.UserNotFoundException;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import com.ssafy.singstreet.user.model.UserRegistDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    //밑의 4 변수는 메일을 보낼때 필요한 상수입니다.
    private static final String SMTP_HOST = "smtp.naver.com"; // e.g., smtp.gmail.com
    private static final String SMTP_PORT = "465";
    private static final String EMAIL_USERNAME = "human3452@naver.com";
    private static final String EMAIL_PASSWORD = "trustworthy1!";

    //유저리포지토리 받아오기
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //유저 등록
    public User registerUser(UserRegistDTO registrationDTO) {
        User user = User.builder()
                .nickname(registrationDTO.getNickname())
                .email(registrationDTO.getEmail())
                .userImg(registrationDTO.getUserImg())
                .gender(registrationDTO.getGender())
                .password(registrationDTO.getPassword())
                .build();
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
        User user=userRepository.findByEmail(email);
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
    public User updateUser(Integer userId, String newNickname, String newUserImg, String newGender, String newPassword) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        user.updateUserInfo(newNickname, newUserImg, newGender, newPassword);

        return userRepository.save(user);
    }

    public User getUser(int user_id){
        User member=userRepository.getReferenceById(user_id);
        if(member==null){
            throw new UserNotFoundException("유저 아이디 번호가 존재하지 않습니다.");
        }
        return member;
    }

    public void softDeleteUser(Integer userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }


}
