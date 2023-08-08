import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import EntNavContainer from "../../containers/layout/EntNavContainer";
import Footer from "../layout/Footer";
import "../../css/ent/EntApplicants.css";
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
    <div className="entApplicantsContainer">
      <EntNavContainer />
      <div className="entApplicantsRight">
        <h1>지원자 목록 </h1>
        <ul>
          {applicantList.map((applicant) => (
            <li key={applicant.appId} className="applicantItem">
              <a href="#" className="applicantLink">
                <h3 className="applicantTitle">{applicant.nickname}</h3>
              </a>
            </li>
          ))}
        </ul>
      </div>
      <Footer />
    </div>
  );
};

export default EntApplicants;
