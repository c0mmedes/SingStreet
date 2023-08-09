import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import EntNavContainer from "../../containers/layout/EntNavContainer";
import Footer from "../layout/Footer";
import "../../css/ent/EntApplicants.css";
import applicantlogo from "../../assets/asdf.png";
const EntApplicants = ({ myEntList }) => {
  // axios 인스턴스
  const apiInstance = api();
  // 라우터 파라미터에서 가져올 entId 변수
  const { entId } = useParams();
  // 상태관리
  const [applicantList, setApplicantList] = useState([]);

  useEffect(() => {
    getEntApplicantList();
  }, []);
  // 유저 목록을 불러오는
  const getEntApplicantList = async () => {
    try {
      const accessToken = await sessionStorage.getItem("accessToken");
      const res = await apiInstance.get(`ent/apply/${entId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        },
      });
      /*
            res.data
            [
                {
                  "applId": 0,
                  "createAt": "2023-08-07T07:34:01.357Z",
                  "nickname": "string",
                  "userId": 0
                }
            ] 
            */
      setApplicantList(res.data);
    } catch {
      alert("지원자 목록 불러오기 실패!");
    }
  };

  return (
    <div>
      <div className="entApplicantsContainer">
        <div className="entApplicantsRight">
          <div className="applicantHeader">
            <h1>지원자 목록 </h1>
            <img
              src={applicantlogo}
              className="applicantHeaderImg"
              alt="지원자로고"
            />
          </div>

          <ol className="applicantOl">
            <div className="applicantHeaderTable">
              <div>No. 이름</div>
              <div className="aht2">신청날짜</div>
              <div className="aht3">승인</div>
            </div>
            {applicantList.map((applicant) => (
              <li key={applicant.appId} className="applicantItem">
                <div className="applicantItem1">{applicant.nickname}</div>
                <div className="applicantItem2">
                  {new Date(applicant.createAt).toLocaleDateString()}
                </div>
                <div className="applicantItemBtn">
                  <input type="submit" value={"수락"}></input>
                  <input type="submit" value={"거절"}></input>
                </div>
              </li>
            ))}
          </ol>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default EntApplicants;
