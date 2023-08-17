import React, { useState,useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/work/WorkCreate.css";
import { getMyProjectList } from "../../services/userAPI";

const WorkCreate = ({ userInfo, isLogin }) => {
  // const [isAutoAceepted, setIsAutoAceepted] = useState(true);
  const [work, setWork] = useState(null);
  const [myProjectList, setMyProjectList] = useState([]);
  const [projectId, setProjectId] = useState(0);
  // const fileInputRef = useRef(null);

  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();

  useEffect(()=>{
    async function makeProjectList () { 
      const projectList = await getMyProjectList(userInfo.userId);
      console.log(projectList);
      const filteredProjectList = projectList.filter(project => parseInt(project.userId) === parseInt(userInfo.userId));
      console.log(filteredProjectList);
      setMyProjectList(filteredProjectList);
    }
    makeProjectList(); 
  },[]);

  // const handleImageDelete = () => {
  //   setWorkImg(null);
  //   if (fileInputRef.current) {
  //     fileInputRef.current.value = "";
  //     fileInputRef.current.removeAttribute("required");
  //   }
  // };

  // 작품 파일
  const handleWork = (e) => {
    setWork(e.target.files[0]);
  };
  const handleProject = (e) =>{
    setProjectId(e.target.value);
  };

  // 생성하기 클릭
  const onClickWorkCreate = async function (e) {
    e.preventDefault(); // 기본 제출 동작 막기

    if(!projectId){
      alert("선택된 프로젝트가 없습니다!");
      return;
    }

    const accessToken = sessionStorage.getItem("accessToken");
    const formData = new FormData();
    if(work){
      formData.append("file", work);
    }
    try {
      const res = await apiInstance.post(`/file/upload/video/${projectId}`,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
          "Content-Type": "multipart/form-data",
        },
      });
      console.log(res);
      if (res.data === false) {
        alert("비동기 오류 났어요!");
      } else {
        alert("노래합작 생성 완료");
        navigate("/music");
      }
    } catch (error) {
      alert("노래합작 생성 오류");
    }
  };

  return (
    <div>
      <div className="form_wrapper workCreateFormWrapper">
        <div className="form_container">
          <div className="title_container">
            <h2>노래합작 업로드</h2>
          </div>
          <div className="row clearfix">
            <div className="">
              <form className="entCreateForm" onSubmit={onClickWorkCreate}>
                {/*업로드할 작품 선택*/}
                <select onChange={handleProject}>
                  {myProjectList.map((project)=>(
                    <option key={project.projectId} value={project.projectId}>
                      {project.projectName}
                    </option>
                  ))}
                </select>
                <label>노래합작 파일</label>
                <div className="input_field" id="workImgInputField">
                  <div className="file-input">
                    <input
                      type="file"
                      name="file"
                      onChange={handleWork}
                      accept="audio/*,video/*"
                    />
                {/*     <span
                      id="modifybutton"
                      onClick={() => fileInputRef.current.click()}>
                      오디오 변경
                    </span>
                    <span id="deletebutton" onClick={handleImageDelete}>
                      삭제
                    </span> */}
                  </div>
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
export default WorkCreate;
