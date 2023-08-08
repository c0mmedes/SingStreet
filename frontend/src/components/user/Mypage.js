import React from "react";
import "../../css/user/Mypage.css";
import { api } from "../../services/httpService";

const Mypage = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
    // axios 인스턴스 생성
    const apiInstance = api();

    console.log(userInfo);
    return (
        <div className="user-my-info">
            <div className="mypage-title">내 정보</div>
            <div className="user-my-info-table-wrap">
                <table>
                    <colgroup>
                        <col />
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row">
                                <div className="thcell">이메일</div>
                            </th>
                            <td>
                                <div className="tdcell">
                                    <div calss="user-id">{userInfo.email}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                <div className="thcell">성별</div>
                            </th>
                            <td>
                                <div className="tdcell">
                                    <input calss="user-gender" type="text" value={userInfo.gender} />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                <div className="thcell">닉네임</div>
                            </th>
                            <td id="user-nickname-td">
                                <div className="tdcell">
                                    <input id="user-nickname" type="text" value={userInfo.nickname} />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">
                                <div className="thcell">프로필</div>
                            </th>
                            <td id="user-profile-td">
                                <div className="tdcell">
                                    <img id="user-profile" src={userInfo.userImg} alt="프로필 이미지" />
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className="edit-button-wrap">
                <button className="edit-button">적용</button>
            </div>
        </div>
    );
};

export default Mypage;