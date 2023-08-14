import React,{useEffect, useState} from "react";
import "../../css/user/MyEntList.css";
import { api } from "../../services/httpService";
import { useNavigate } from "react-router-dom";
const MypageInvitedProject = ({
  userInfo,
  myEntList,
}) => {
  // useState 
  const [invitedProjectList, setInvitedProjectList] = useState([{}]);
  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 navigate 생성
  const navigate = useNavigate();
  // useEffect
  useEffect(()=>{
    getInvitedProject();
  },[]);
  // 내가 받은 프로젝트 초대 목록 가져오기
  const getInvitedProject = async () => {
    try{
      const res = await apiInstance.get(`/project/invitedList/${userInfo.userId}`)
      console.log(res.data);
      setInvitedProjectList(res.data);
    } catch{
      alert("프로젝트 목록 가져오기 오류");
    }
  };


  console.log(userInfo);
  return (
    <div className="myEntListContainer">
      <div className="myEntListWrap">
        <h1>가입된 엔터목록</h1>
        <div className="myEntListItemContainer">
          {myEntList.map((myEnt) => (
            <li className="myEntListItem" key={myEnt.entId}>
              <div className="myEntListItemTitleWrap">
                <img src={myEnt.entImg} alt="엔터프로필이미지" ></img>
                <h3 class="myEntListItemTitle">엔터이름 : {myEnt.entName}</h3>
              </div>
            </li>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MypageInvitedProject;
