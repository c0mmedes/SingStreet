package com.ssafy.singstreet;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import com.ssafy.singstreet.user.db.entity.User;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SingstreetApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EntRepository entRepository;
	@Autowired
	private EntTagRepository entTagRepository;

	@After
	public void tearDown() throws Exception{
		entRepository.deleteAll();
		entTagRepository.deleteAll();
	}

	@Test
	void Ent_등록() throws Exception{
		//given
		String entName = "name";
		User user = new User();
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

		String url = "http://localhost:" + port + "/ent";

		//when
		ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestDto, Void.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		List<Ent> all = entRepository.findAll();
		List<EntTag> tagAll = entTagRepository.findAll();
		assertThat(all.get(0).getEntName()).isEqualTo(entName);
		assertThat(all.get(0).getEntInfo()).isEqualTo(entInfo);
		assertThat(all.get(0).getEntImg()).isEqualTo(entImg);
//		assertThat(all.get(0).getIsAutoAccepted()).isEqualTo(isAutoAccepted);

		assertThat(tagAll.get(0).getEnt()).isEqualTo(entName);
		assertThat(tagAll.get(0).getTagName()).isEqualTo("#dtd ");
		assertThat(tagAll.get(0).getTagName()).isEqualTo("#ada ");
		assertThat(tagAll.get(0).getTagName()).isEqualTo("#qwe");
	}

}
