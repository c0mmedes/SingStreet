package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EntApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntRepository entRepository;
    @Autowired
    private EntTagRepository entTagRepository;

//    @After
//    public void tearDown() throws Exception{
//        entRepository.deleteAll();
//        entTagRepository.deleteAll();
//    }

    @Test
    void Ent_등록() throws Exception{
        //given
        String entName = "name";
        Boolean isAutoAccepted = false;
        String entInfo = "info";
        String entImg = "img";
        String entTagList = "#dtd #ada #qwe";

        EntSaveRequestDto requestDto = EntSaveRequestDto.builder()
                .entName(entName)
                .isAutoAccepted(isAutoAccepted)
                .entInfo(entInfo)
                .entImg(entImg)
                .entTagList(entTagList)
                .build();

        String url = "http://localhost:" + port + "/ent/1";

        //when
        ResponseEntity<Integer> responseEntity = restTemplate.postForEntity(url, requestDto, Integer.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("testeste");
		List<Ent> all = entRepository.findAll();
		List<EntTag> tagAll = entTagRepository.findAll();
		assertThat(all.get(0).getEntName()).isEqualTo(entName);
		assertThat(all.get(0).getEntInfo()).isEqualTo(entInfo);
		assertThat(all.get(0).getEntImg()).isEqualTo(entImg);
		assertThat(all.get(0).getIsAutoAccepted()).isEqualTo(isAutoAccepted);

		assertThat(tagAll.get(1).getTagName()).isEqualTo("dtd");
		assertThat(tagAll.get(2).getTagName()).isEqualTo("ada");
		assertThat(tagAll.get(3).getTagName()).isEqualTo("qwe");
    }

    @Test
    void Ent_수정() throws Exception{
        //given
        String entName = "name1";
        Boolean isAutoAccepted = false;
        String entInfo = "info2";
        String entImg = "img3";
        String entTagList = "#123 #456 #789";

        EntSaveRequestDto requestDto = EntSaveRequestDto.builder()
                .entName(entName)
                .isAutoAccepted(isAutoAccepted)
                .entInfo(entInfo)
                .entImg(entImg)
                .entTagList(entTagList)
                .build();

        String url = "http://localhost:" + port + "/ent/1";

        HttpEntity<EntSaveRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url,HttpMethod.PUT ,requestEntity, Void.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Ent> all = entRepository.findAll();
        Ent entId = entRepository.findByEntId(1);
        List<EntTag> tagAll = entTagRepository.findAllByEntId(entId);
        assertThat(all.get(0).getEntName()).isEqualTo(entName);
        assertThat(all.get(0).getEntInfo()).isEqualTo(entInfo);
        assertThat(all.get(0).getEntImg()).isEqualTo(entImg);
        assertThat(all.get(0).getIsAutoAccepted()).isEqualTo(isAutoAccepted);

        assertThat(tagAll.get(1).getTagName()).isEqualTo("123");
        assertThat(tagAll.get(2).getTagName()).isEqualTo("456");
        assertThat(tagAll.get(3).getTagName()).isEqualTo("789");
    }

    @Test
    public void Ent_삭제() throws Exception{
        //given
        String url = "http://localhost:" + port + "/ent/delete/2";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // when
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Boolean.class
        );

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(true); // 예상 결과에 따라 수정
    }
}