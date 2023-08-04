import React, { useState } from "react";
import "../../css/ent/EntCreate.css";
import {api} from "../../services/httpService"

const EntCreate = ({userInfo, isLogin}) => {
  const [entName, setEntName] = useState("");
	const [entInfo, setEntInfo] = useState("");
	const [entTagList, setEntTagList] = useState("");
	const [isAutoAceepted, setIsAutoAceepted] = useState("");
	const [entImg, setEntImg] = useState("");
	const handleEntName = (e) => {
		setEntName(e.target.value);
	};
	const handleEntInfo = (e) => {
		setEntInfo(e.target.value);
	};
	const handleEntTagList = (e) => {
		setEntTagList(e.target.value);
	};
	const handleIsAutoAceepted = (e) => {
		setIsAutoAceepted(e.target.value);
	};
	const handleEntImg = (e) => {
		setEntImg(e.target.value);
	};

  const apiInstance = api(); 
  const onClickEntCreate = async function () {
    const accessToken = sessionStorage.getItem("accessToken");
    try{
      const res = await apiInstance.post('/ent',
      {
        entImg: "더미 스트링",
        entInfo: entInfo,
        entName: entName,
        entTagList: entTagList,
        isAutoAccepted: isAutoAceepted
      },
      {
        headers: {
					Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
				},
      });
      console.log(res);
    } catch (error){
      alert("엔터 생성 오류");
    }
  };
  return (
    <div>
      <div className="form_wrapper">
        <div className="form_container">
          <div className="title_container">
            <h2>엔터 신규 등록</h2>
          </div>

          <div className="row clearfix">
            <div className="">
              <form className="entCreateForm">
                <label>엔터명</label>
                <div className="input_field">
                  <input type="text" name="entId" value={entName} onChange={handleEntName} required />
                </div>
                <div className="input_field">
                  {/* <input type="radio" id="ex_rd" name="ex_rds"> 
                  <label for="ex_rd">라디오버튼</label> */}
                </div>
                <label>엔터 소개</label>
                <div className="input_field">
                  <textarea id="entInfo" type="text" name="entInfo" value={entInfo} onChange={handleEntInfo} required />
                </div>
                <label>해시태그</label>
                <div className="input_field">
                  <input
                    type="text"
                    name="tagList"
                    placeholder="#뉴진스 #하입보이"
                    value={entTagList}
                    onChange={handleEntTagList}
                    required
                  />
                </div>
                <label>공개여부</label>
                <div className="input_field select_option">
                  <select value={isAutoAceepted} onChange={handleIsAutoAceepted}>
                    <option>공개엔터 (바로 가입)</option>
                    <option>공개엔터 (승인 대기)</option>
                  </select>
                  <div className="select_arrow"></div>
                </div>
                <label>엔터 프로필</label>
                <div className="input_field">
                  <input
                    type="file"
                    name="file"
                    placeholder="#뉴진스 #하입보이"
                    value={entImg}
                    onChange={handleEntImg}
                    required
                  />
                </div>
                <input className="button" type="submit" value="생성하기" onClick={onClickEntCreate} />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
//  엔터명 (중복확인) 엔터 공개 설정 (자동가입) / 엔터 소개 / 해시태그 / 엔터로고
export default EntCreate;
