import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { api } from "../../services/httpService";

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
  // 선택된 멤버
  const [selectedMembers, setSelectedMembers] = useState([]);

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
      const res = await apiInstance.get(`ent/member/${parseInt(entId)}}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
        },
      });
      /*
            res.data
            [
                {
                    "userId": 0
                    "nickname": "string",
                    "email": 0,
                    "gender":남
                    "createAt": "2023-08-07T07:34:01.357Z",   
                }
            ] 
      */
      console.log(res.data);
      setEntMemberList(res.data);
    } catch {
      alert("엔터 멤버 목록 불러오기 실패!");
    }
  };

  const handleMemberSelection = (userId) => {
    if (selectedMembers.includes(userId)) {
      setSelectedMembers(selectedMembers.filter((id) => id !== userId));
    } else {
      setSelectedMembers([...selectedMembers, userId]);
    }
  };

  const onClickInviteButton = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      // selectedMembers에 선택된 멤버들의 userId가 들어있습니다.
      // 이 정보를 이용하여 초대 등의 작업을 진행할 수 있습니다.
      console.log("Selected Members: ", selectedMembers);
      // 선택된 멤버들을 서버로 전송하거나 필요한 동작을 수행할 수 있습니다.
      apiInstance.post(`project/member/`, { selectedMembers }, {
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
      });
    } catch {
      alert("멤버 초대 실패!");
    }
  };

  return (
    <div>
      <h2>엔터 멤버 초대</h2>
      <ul>
        {entMemberList.map((member) => (
          <li key={member.userId}>
            <label>
              <input
                type="checkbox"
                checked={selectedMembers.includes(member.userId)}
                onChange={() => handleMemberSelection(member.userId)}
              />
              {member.nickname} ({member.email})
            </label>
          </li>
        ))}
      </ul>
      <button onClick={onClickInviteButton}>선택된 멤버 초대</button>
    </div>
  );
};

export default EntProjectMemberInvite;
