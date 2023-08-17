import React from "react";
import "../../css/user/MyEntList.css";
import { api } from "../../services/httpService";
import { useNavigate} from "react-router-dom";
const MyEntList = ({
  isLogin,
  userInfo,
  addUserInfo,
  setIsLogin,
  myEntList,
  addToMyEntList,
}) => {
  // axios 인스턴스 생성
  const apiInstance = api();
  // 페이지 이동을 위한 navigate 생성
  const navigate = useNavigate();
  // 클릭한 엔터 메인 페이지로 보내는 함수
  const onClickGoToEntMain = (entId,entMasterId,entName) => {
    console.log(`/entmain/${entId}/${entMasterId}/${entName}`);
    navigate(`/entmain/${entId}/${entMasterId}/${entName}`)
  };

  console.log(userInfo);
  return (
    <div className="myEntListContainer">
      <div className="myEntListWrap">
        <h1>가입된 엔터목록</h1>
        <div className="myEntListItemContainer">
          {myEntList.map((myEnt) => (
            <li className="myEntListItem" key={myEnt.entId} onClick={()=>onClickGoToEntMain(myEnt.entId,myEnt.userId,myEnt.entName)} style={{ cursor: "pointer" }}>
              <div className="myEntListItemTitleWrap">
                <img src={myEnt.entImg} alt="엔터프로필이미지"></img>
                <h3 class="myEntListItemTitle">엔터이름 : {myEnt.entName}</h3>
              </div>
            </li>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MyEntList;
