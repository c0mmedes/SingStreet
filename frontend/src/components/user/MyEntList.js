import React from "react";
import "../../css/user/Mypage.css";
import { api } from "../../services/httpService";

const MyEntList = ({ isLogin, userInfo, addUserInfo, setIsLogin, myEntList, addToMyEntList }) => {
    // axios 인스턴스 생성
    const apiInstance = api();

    console.log(userInfo);
    return (
        <div>
        {myEntList.map((myEnt) => (
            <li key={myEnt.entId}>
                <a href="#" class="card">
                    <h3 class="card__title">{myEnt.entName}</h3>
                </a>
                <a href="#" class="card">
                    <h3 class="card__title">{myEnt.entInfo}</h3>
                </a>
                <a href="#" class="card">
                    <h3 class="card__title">{myEnt.entImg}</h3>
                </a>
            </li>
        ))}
    </div>
    );
};

export default MyEntList;