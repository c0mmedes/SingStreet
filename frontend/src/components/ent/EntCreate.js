import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntCreate.css";
const EntCreate = ({ userInfo, isLogin }) => {
  const [entName, setEntName] = useState("");
  const [entInfo, setEntInfo] = useState("");
  const [entTagList, setEntTagList] = useState("");
  const [isAutoAceepted, setIsAutoAceepted] = useState(true);
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
    const value = e.target.value === "true"; // 문자열 "true"를 불리언 true로 변환
    setIsAutoAceepted(value);
  };
  const handleEntImg = (e) => {
    setEntImg(e.target.value);
  };
  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();

  const onClickEntCreate = async function () {
    const accessToken = sessionStorage.getItem("accessToken");
    try {
      const res = await apiInstance.post(
        "/ent",
        {
          entImg: entImg,
          entInfo: entInfo,
          entName: entName,
          entTagList: entTagList,
          isAutoAccepted: isAutoAceepted,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          },
        }
      );
      if (res.data) {
        //정상적으로 만들어지면 true
        navigate("/ent");
        alert("엔터가 생성되었습니다!");
      } else {
        alert("엔터명이 중복되었습니다!");
      }
    } catch (error) {
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
                  <input
                    type="text"
                    maxlength="20"
                    name="entId"
                    value={entName}
                    onChange={handleEntName}
                    required
                  />
                </div>
                <div className="input_field">
                  {/* <input type="radio" id="ex_rd" name="ex_rds"> 
                  <label for="ex_rd">라디오버튼</label> */}
                </div>
                <label>엔터 소개</label>
                <div className="input_field">
                  <textarea
                    id="entInfo"
                    type="text"
                    name="entInfo"
                    value={entInfo}
                    onChange={handleEntInfo}
                    required
                  />
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
                  <select onChange={handleIsAutoAceepted}>
                    <option value="true">공개엔터 (바로 가입)</option>
                    <option value="false">공개엔터 (승인 대기)</option>
                  </select>
                  <div className="select_arrow"></div>
                </div>
                <label>엔터 프로필</label>
                <div className="input_field">
                  <input
                    type="file"
                    name="file"
                    onChange={handleEntImg}
                    required
                  />
                </div>
                <input
                  className="button"
                  type="submit"
                  value="생성하기"
                  onClick={onClickEntCreate}
                />
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
