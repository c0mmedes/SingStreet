import React from "react";
import "../../css/user/MypageList.css";
import { api } from "../../services/httpService";
import { useNavigate } from "react-router-dom";
const MyEntList = ({ isLogin, userInfo, addUserInfo, setIsLogin, myEntList, addToMyEntList }) => {
    // axios 인스턴스 생성
    const apiInstance = api();
    // 페이지 이동을 위한 navigate 생성
    const navigate = useNavigate();

    console.log(userInfo);
    return (
        <div>
        {myEntList.map((myEnt) => (
            <li key={myEnt.entId}>
                <div>
                    <h3 class="card__title">{myEnt.entName}</h3>
                    <h3 class="card__title">{myEnt.entInfo}</h3>
                    <h3 class="card__title">{myEnt.entImg}</h3>
                </div>
            </li>
        ))}
    </div>
    );
};

export default MyEntList;