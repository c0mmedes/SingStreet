import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectCreate.css";
import "../../css/ent/EntProjectModify.css";
const EntProjectModify = ({ userInfo, isLogin, myEntList, addToMyEntList }) => {
  // 라우터 파라미터에서 가져올 entId 변수
  const { entId, entMasterId, entName, projectId } = useParams();
  //  useState로 관리할 상태
  const [project, setProject] = useState({});
  const [projectName, setProjectName] = useState("");
  const [projectInfo, setProjectInfo] = useState("");
  const [projectTagList, setProjectTagList] = useState("");
  const [projectImg, setProjectImg] = useState(null);
  const [isRecruited, setIsRecruited] = useState(true);
  const [isVisible, setIsVisible] = useState(true);
  const [partList, setPartList] = useState([{}]);
  const [partNameList, setPartNameList] = useState([""]);
  const [singName, setSingName] = useState("");
  const [singerName, setSingerName] = useState("");
  const [userList, setUserList] = useState([]);
  const [projectMemberList, setProjectMemberList] = useState([]); // 프로젝트 멤버 리스트

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
    setIsRecruited(e.target.value);
  };
  const handleIsVisible = (e) => {
    console.log(isVisible);
    setIsVisible(e.target.value);
  };

  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();

  useEffect(() => {
    getProject();
    getProjectMemberList();
  }, []);

  // [비동기] 내 프로젝트 정보 가져오기
  const getProject = async () => {
    const res = await apiInstance.get(`/project/info/${projectId}`);
    console.log(res.data);
    const newProject = { ...res.data }; // 새로운 객체를 생성하고 res.data의 내용을 복사
    setProject(newProject);
    /*
            projectId
            userId // 프로젝트장
            projectName
            singerName
            singName
            projectInfo
            projectImg
            partList:
            {
            nickname // 등록 전에는 “”
            partName
            userId // 등록 전에는 -1임
            }
            isRecruited // 모집여부
        */
    // 추출한 partName을 newPartNameList로 옮기기
    // 추출한 userList를 newUserList로 옮기기
    const newPartNameList = [];
    const newUserList = [];
    newProject.partList.map((part) => {
      newPartNameList.push(part.partName);
      newUserList.push(part.userId);
      return null;
    });

    console.log(newUserList);
    // 받아온 tagList를 문자열로 만들어서
    let newTagList = "";
    newProject.tagList.map((tag) => {
      newTagList = newTagList.concat("#", tag);
      return null;
    });

    setProjectName(newProject.projectName);
    setProjectInfo(newProject.projectInfo);
    setProjectTagList(newProject.projectTagList);
    setSingName(newProject.singName);
    setSingerName(newProject.singerName);
    setIsRecruited(newProject.isRecruited);
    setIsVisible(newProject.isVisible);
    setPartList(newProject.partList);
    setProjectTagList(newTagList);
    setPartNameList(newPartNameList);
    setUserList(newUserList);
  };

  //[비동기] 프로젝트 멤버 목록 불러오는 함수
  const getProjectMemberList = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      const res = await apiInstance.get(`project/member/${projectId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        },
      });
      console.log(res.data);
      setProjectMemberList(res.data);
    } catch {
      console.log(
        "프로젝트 멤버 불러오기 에러 발생(프로젝트 멤버가 없습니다.)"
      );
    }
  };

  // 파트추가, 파트에 해당하는 유저 추가
  const handleAddPart = () => {
    if (partNameList.length < 10) {
      // 최대 10개의 파트까지 추가 가능
      setPartNameList([...partNameList, ""]); // 새로운 파트 추가
      setUserList([...userList, -1]); // 새로운 파트에 대한 선택된 유저 추가
    }
    console.log(partNameList);
  };
  const handlePartChange = (index, value) => {
    const updatedPartNameList = [...partNameList];
    updatedPartNameList[index] = value;
    setPartNameList(updatedPartNameList);
  };
  const handleUserChange = (index, userId) => {
    const updatedUserList = [...userList];
    if (userId === "") {
      updatedUserList[index] = -1;
    } else {
      updatedUserList[index] = userId;
    }
    setUserList(updatedUserList);
  };
  const renderPartInputs = () => {
    return partNameList.map((part, index) => (
      <div key={index} className="input_field">
        <input
          type="text"
          name={`part-${index}`}
          value={part}
          onChange={(e) => handlePartChange(index, e.target.value)}
          required
        />
        {/*<select> user에게 파트 부여*/}
        <select
          value={userList[index] === -1 ? "" : userList[index]} // 선택된 유저의 userId를 저장한 상태와 연결
          onChange={(e) => handleUserChange(index, e.target.value)}>
          <option value="" disabled hidden>누구에게 파트를 부여할건가요?</option>
          {projectMemberList.map((projectMember) => (
            <option
              key={projectMember.user.userId}
              value={projectMember.user.userId}>
              {projectMember.user.nickname}
            </option>
          ))}
        </select>
      </div>
    ));
  };

  // 수정하기 버튼 클릭
  const onClickProjectModify = async function (e) {
    e.preventDefault(); // 기본 제출 동작 막기

    const accessToken = sessionStorage.getItem("accessToken");
    // 프로젝트 프로필 이미지와, projectData를 함께 보내기 위한 작업
    const formData = new FormData();
    if (projectImg) {
      formData.append("file", projectImg);
    }
    const projectData = {
      entId: parseInt(entId),
      userId: parseInt(userInfo.userId),
      isRecruited: isRecruited,
      isVisible: isVisible,
      partList: partNameList,
      projectInfo: projectInfo,
      projectName: projectName,
      projectTagList: projectTagList,
      singName: singName,
      singerName: singerName,
      userList: userList,
      // 테스트용
      // entId: 1,
      // userId: 1,
      // isRecruited: true,
      // isVisible: true,
      // partList: ["지금"],
      // projectInfo: "지금",
      // projectName: "지금",
      // projectTagList: "#지금#지금",
      // singName: "지금",
      // singerName: "지금",
      // userList: [],
    };
    formData.append(
      "dto",
      new Blob([JSON.stringify(projectData)], {
        type: "application/json",
      })
    );
    console.log(formData);
    // 비동기 통신
    try {
      const res = await apiInstance.post(`/project/${projectId}`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          "Content-Type": "multipart/form-data",
          Accept: "application/json", // 추가
        },
      });
      alert(`${projectName}가 수정되었습니다!`);
      navigate(`/entmain/${entId}/${entMasterId}/${entName}/entprojectlist`);
    } catch (error) {
      alert("프로젝트 생성 오류");
    }
  };
  return (
    <div>
      <div className="form_wrapper pjtModifiyWrap">
        <div className="form_container">
          <div className="title_container">
            <h2>프로젝트 수정</h2>
          </div>

          <div className="row clearfix">
            <div className="">
              <form className="entCreateForm" onSubmit={onClickProjectModify}>
                <label>프로젝트명</label>
                <div className="input_field">
                  <input
                    type="text"
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
                  />
                </div>

                <label>프로젝트 프로필</label>
                <div className="input_field">
                  <input type="file" name="file" onChange={handleProjectImg} />
                </div>
                <div className="input_field">
                  <label htmlFor="isRecruited">프로젝트 멤버 모집 여부</label>
                  <select
                    name="isRecruited"
                    value={isRecruited}
                    onChange={handleIsRecruited}>
                    <option value="">프로젝트 멤버 모집 여부</option>
                    <option value="true">모집 중</option>
                    <option value="false">모집 마감</option>
                  </select>
                </div>
                <div className="input_field">
                  <label htmlFor="isVisible">
                    엔터페이지에 프로젝트 공개 여부
                  </label>
                  <select
                    name="isVisible"
                    value={isVisible}
                    onChange={handleIsVisible}>
                    <option value="">엔터페이지에 프로젝트 공개 여부</option>
                    <option value="true">공개</option>
                    <option value="false">공개하지 않음</option>
                  </select>
                </div>

                <label>파트</label>
                {renderPartInputs()}
                {partList.length < 10 && (
                  <button type="button" onClick={handleAddPart}>
                    파트 추가
                  </button>
                )}

                <input className="button" type="submit" value="수정하기" />
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default EntProjectModify;
