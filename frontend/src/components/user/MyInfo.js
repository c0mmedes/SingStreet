import React from "react";
import "../../css/user/Mypage.css";
import "../../css/user/MyInfo.css";

const MyInfo = ({ isLogin, userInfo, addUserInfo, setIsLogin }) => {
	return (
		<div className="user-my-info">
			<div className="user-my-info-wrap">
				<div className="mypage-title">내 정보</div>
				<div className="myinfoContainer">
					<div className="myinfoLeft">
						<div className="info-item infoItem2">
							<label className="info-label">프로필</label>
							<img src={userInfo.userImg} id="user-profile" alt="프로필 이미지" />
						</div>
					</div>
					<div className="myinfoRight">
						<div className="info-item infoItem2">
							<label className="info-label">이메일</label>
							<div className="info-value">{userInfo.email}</div>
						</div>
						<div className="info-item">
							<label className="info-label">성별</label>
							<input className="info-input" type="text" value={userInfo.gender} />
						</div>
						<div className="info-item">
							<label className="info-label">닉네임</label>
							<input
								id="user-nickname"
								className="info-input"
								type="text"
								value={userInfo.nickname}
							/>
						</div>
					</div>
				</div>
				{/* <div className="edit-button-wrap">
          <button className="edit-button">적용</button>
        </div> */}
			</div>
		</div>
	);
};

export default MyInfo;
