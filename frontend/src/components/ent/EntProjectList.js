import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/EntProjectList.css";

const EntProjectList = () => {
  // entMain 라우터 경로에 있는 param인 entId, entMasterId, entName를 저장하는 변수
  const { entId, entMasterId, entName } = useParams();
  // useState
  const [entProjectList, setEntProjectList] = useState([]);
  // axios 객체
  const apiInstance = api();
  //useEffect (화면 첫 렌더링시 실행)
  useEffect(() => {
    getEntProjectList();
  }, []);

  // [비동기] 엔터 프로젝트 목록 가져오기
  const getEntProjectList = async () => {
    const res = await apiInstance.get(`/project/ent/${entId}`);
    console.log(res.data);
    setEntProjectList(res.data);
  };

  return (
    <div>
      <form className="entSearchWrap">
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
        {entProjectList.map((entProject) => (
          <div key={entProject.projectId} class="cards-list">
            <div class="musicCard 1">
              <div class="card_image">
                <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
              </div>
              <div class="card_title title-white">
                <p>{entProject.projectName}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default EntProjectList;
