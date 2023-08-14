import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntApplicants.css";
import applicantlogo from "../../assets/asdf.png";
const EntProjectMemberInvite = ({ myEntList, userInfo }) => {
  // axios 인스턴스
  const apiInstance = api();
  // 라우터 파라미터에서 가져올 entId 변수
  const { entId, entName, entMasterId, projectId } = useParams();
  // 화면을 다른 화면으로 넘겨줄때 필요한
  const navigate = useNavigate();
  // 상태관리
  // 엔터 멤버 리스트
  const [entMemberList, setEntMemberList] = useState([]);

  useEffect(() => {
    // 원하는 조건을 확인하고 이전 화면으로 돌아가기
    if (parseInt(userInfo.userId) !== parseInt(entMasterId)) {
      // 이전 화면으로 이동
      navigate(-1);
    }
    getEntMemberList();
  }, []);

  // 엔터 멤버 목록을 불러오는
  const getEntMemberList = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      const res = await apiInstance.get(`ent/member/${entId}}`, {
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
      console.log(res.data);
      setEntMemberList(res.data);
    } catch {
      alert("엔터 멤버 목록 불러오기 실패!");
    }
  };

  // 초대하기
  const onClickInviteButton = async () => {
    try {
        const accessToken = sessionStorage.getItem("accessToken");
        const res = await apiInstance.get(`ent/member/${entId}}`, {
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
        console.log(res.data);
        setEntMemberList(res.data);
      } catch {
        alert("엔터 멤버 목록 불러오기 실패!");
      }
  };

  return (
    <div>
      
    </div>
  );
};

export default EntProjectMemberInvite;
