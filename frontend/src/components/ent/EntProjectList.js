import React, { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectList.css";

const EntProjectList = () => {
  // entMain 라우터 경로에 있는 param인 entId, entMasterId, entName를 저장하는 변수
  const { entId, entMasterId, entName } = useParams();
  // useState
  const [entProjectList, setEntProjectList] = useState([]);
  // axios 객체
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();
  //useEffect (화면 첫 렌더링시 실행)
  useEffect(() => {
    getEntProjectList();
  }, []);

  // [비동기] 엔터 프로젝트 목록 가져오기
  const getEntProjectList = async () => {
    try {
      const res = await apiInstance.get(`/project/ent/${entId}`);
      console.log(res.data);
      setEntProjectList(res.data);
    } catch {
      console.log("진행중인 프로젝트 없음");
    }
  };

  return (
    <div>
      <form className="entSearchWrap entpjtSearchWrap">
        <input
          placeholder="진행중인 프로젝트 검색. . ."
          type="text"
          className="entSearchInput"></input>
        <input type="submit" value="검색" className="entSearchSubmit"></input>
        <select>
          <option value="allEnt">최신순</option>
          <option value="myEnt">내 프로젝트</option>
        </select>
      </form>
      <div className="entPjtListWrap">
        {/* 프로젝트 리스트 */}
        {entProjectList.length !== 0 ? (
          entProjectList.map((entProject) => (
            <Link
              to={`/entmain/${entId}/${entMasterId}/${entName}/entproject/${entProject.projectId}`}>
              <div key={entProject.projectId} class="cards-list">
                <div class="musicCard 1">
                  <div class="card_image">
                    <img src={entProject.projectImg} alt="프로필 이미지" />
                  </div>
                  <div class="card_title title-white pjtCard">
                    <p>{entProject.projectName}</p>
                  </div>
                </div>
              </div>
            </Link>
          ))
        ) : (
          <div> 프로젝트가 없습니다! </div>
        )}
      </div>
    </div>
  );
};

export default EntProjectList;
