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
              <form>
                <div className="input_field">
                  <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    required
                  />
                </div>
                <div className="input_field">
                  <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    required
                  />
                </div>
                <div className="input_field">
                  <input
                    type="password"
                    name="password"
                    placeholder="Re-type Password"
                    required
                  />
                </div>
                <div className="row clearfix">
                  <div className="col_half">
                    <div className="input_field">
                      <input type="text" name="name" placeholder="First Name" />
                    </div>
                  </div>
                  <div className="col_half">
                    <div className="input_field">
                      <input
                        type="text"
                        name="name"
                        placeholder="Last Name"
                        required
                      />
                    </div>
                  </div>
                </div>

                <div className="input_field select_option">
                  <select>
                    <option>Select a country</option>
                    <option>Option 1</option>
                    <option>Option 2</option>
                  </select>
                  <div className="select_arrow"></div>
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
