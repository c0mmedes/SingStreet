import React from "react";
import "../../css/ent/EntCreate.css";
const entcreate = () => {
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
                  <input type="text" name="entId" required />
                </div>
                <div className="input_field">
                  {/* <input type="radio" id="ex_rd" name="ex_rds"> 
                  <label for="ex_rd">라디오버튼</label> */}
                </div>
                <label>엔터 소개</label>
                <div className="input_field">
                  <textarea id="entInfo" type="text" name="entInfo" required />
                </div>
                <label>해시태그</label>
                <div className="input_field">
                  <input
                    type="text"
                    name="tagList"
                    placeholder="#뉴진스 #하입보이"
                    required
                  />
                </div>
                <label>공개여부</label>
                <div className="input_field select_option">
                  <select>
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
                    required
                  />
                </div>
                <input className="button" type="submit" value="생성하기" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
//  엔터명 (중복확인) 엔터 공개 설정 (자동가입) / 엔터 소개 / 해시태그 / 엔터로고
export default entcreate;
