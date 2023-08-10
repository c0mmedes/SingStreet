import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectCreate.css";
const EntProjectCreate = ({ userInfo, isLogin }) => {
  const [projectName, setProjectName] = useState("");
  const [projectInfo, setProjectInfo] = useState("");
  const [projectTagList, setProjectTagList] = useState("");
  const [projectImg, setProjectImg] = useState("");
  const [entId, setEntId] = useState(0);
  const [isRecruited, setIsRecruited] = useState(true);
  const [isVisible, setIsVisible] = useState(true);
  const [partList, setPartList] = useState([""]);
  const [singName, setSingName] = useState("");
  const [singerName, setSingerName] = useState("");
  const [userId, setUserId] = useState(0);
  const [userList, setUserList] = useState([0]);

  const handleProjectName = (e) => {
    setProjectName(e.target.value);
  };
  const handleProjectInfo = (e) => {
    setProjectInfo(e.target.value);
  };
  const handleProjectTagList = (e) => {
    setProjectTagList(e.target.value);
  };

  const handleSingName = (e) => {
    setSingName(e.target.value);
  };
  const handleSingerName = (e) => {
    setSingerName(e.target.value);
  };
  const handleProjectImg = (e) => {
    setProjectImg(e.target.files[0]);
  };
  const handleIsRecruited = (e) => {
    setIsRecruited(e.target.checked);
  };

  const handleIsVisible = (e) => {
    setIsVisible(e.target.checked);
  };

  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();

  const onClickProjectCreate = async function () {
    const accessToken = sessionStorage.getItem("accessToken");
    try {
      const res = await apiInstance.post(
        "/project",
        {
          entId,
          isRecruited,
          isVisible,
          partList,
          projectImg,
          projectInfo,
          projectName,
          projectTagList,
          singName,
          singerName,
          userId,
          userList,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          },
        }
      );
      if (res.data) {
        //정상적으로 만들어지면 true
        navigate("/projectlist");
        alert("가 생성되었습니다!");
      } else {
        alert("프로젝트명이 중복되었습니다!");
      }
    } catch (error) {
      alert("프로젝트 생성 오류");
    }
  };
  return (
    <div>
      <div className="form_wrapper">
        <div className="form_container">
          <div className="title_container">
            <h2>프로젝트 신규 등록</h2>
          </div>

          <div className="row clearfix">
            <div className="">
              <form className="entCreateForm">
                <label>프로젝트명</label>
                <div className="input_field">
                  <input
                    type="text"
                    maxlength="20"
                    name="projectName"
                    value={projectName}
                    onChange={handleProjectName}
                    required
                  />
                </div>

                <label>프로젝트 소개</label>
                <div className="input_field">
                  <textarea
                    id="projectInfo"
                    type="text"
                    name="projectInfo"
                    value={projectInfo}
                    onChange={handleProjectInfo}
                    required
                  />
                </div>

                <label>곡</label>
                <div className="input_field">
                  <input
                    type="text"
                    name="singName"
                    value={singName}
                    onChange={handleSingName}
                    required
                  />
                </div>

                <label>가수</label>
                <div className="input_field">
                  <input
                    type="text"
                    name="singerName"
                    value={singerName}
                    onChange={handleSingerName}
                    required
                  />
                </div>

                <label>해시태그</label>
                <div className="input_field">
                  <input
                    type="text"
                    name="projectTagList"
                    placeholder="#뉴진스 #하입보이"
                    value={projectTagList}
                    onChange={handleProjectTagList}
                    required
                  />
                </div>

                <label>프로젝트 프로필</label>
                <div className="input_field">
                  <input
                    type="file"
                    name="file"
                    onChange={handleProjectImg}
                    required
                  />
                </div>
                <label>모집중</label>
                <div className="input_field">
                  <input
                    type="checkbox"
                    name="isRecruited"
                    checked={isRecruited}
                    onChange={handleIsRecruited}
                  />
                </div>

                <label>프로젝트 공개</label>
                <div className="input_field">
                  <input
                    type="checkbox"
                    name="isVisible"
                    checked={isVisible}
                    onChange={handleIsVisible}
                  />
                </div>

                <input
                  className="button"
                  type="submit"
                  value="생성하기"
                  onClick={onClickProjectCreate}
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default EntProjectCreate;
