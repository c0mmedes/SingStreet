import React, { useEffect, useState } from "react";
import "../../css/ent/Ent.css";
import myImage from "../../assets/entLogo2.png";
import Footer from "../layout/Footer.js";
import { Link } from "react-router-dom";
import { api } from "../../services/httpService";

const Ent = () => {
  const [entList, setEntList] = useState([]);
  const [page, setPage] = useState(0);
  const [isLastPage, setIsLastPage] = useState(false);
  // axios 객체
  const apiInstance = api();

  useEffect(() => {
    getEntList();
  }, []);

  const getEntList = async () => {
    const res = await apiInstance.get(`/ent?page=${page}&size=100`);
    const newEntList = entList.concat(res.data.content);
    setEntList(newEntList);
    setIsLastPage(res.data.last);
  };

  const onClickMoreEntList = async () => {
    const newPage = page + 1;
    setPage(newPage);
    await getEntList();
  };

  return (
    <div>
      <form className="entSearchWrap">
        <input
          placeholder="마음에 드는 엔터를 검색해보세요. . ."
          type="text"
          className="entSearchInput"></input>
        <input type="submit" value="검색" className="entSearchSubmit"></input>
        <select>
          <option value="allEnt">전체</option>
          <option value="myEnt">가입된 엔터</option>
        </select>
      </form>

      <ul className="cards">
        {entList.map((ent) => (
          <li key={ent.entId}>
            <a href="#" class="card">
              <Link to={`/entmain/${ent.entId}`}>
                <img src={myImage} class="card__image" alt="" />
                <div class="card__overlay">
                  <div class="card__header">
                    <svg class="card__arc" xmlns="http://www.w3.org/2000/svg">
                      <path />
                    </svg>
                    <img
                      class="card__thumb"
                      src="https://i.imgur.com/7D7I6dI.png"
                      alt=""
                    />
                    <div class="card__header-text">
                      <h3 class="card__title">{ent.entName}</h3>
                      <span class="card__status">
                        {ent.tagNameList.map((tagName) => (
                          <b>#{tagName}</b>
                        ))}
                      </span>
                    </div>
                  </div>
                  <p class="card__description">{ent.entInfo}</p>
                </div>
              </Link>
            </a>
          </li>
        ))}
      </ul>
      <div>
        <button onClick={onClickMoreEntList} disabled={isLastPage}>더보기</button>
      </div>
      <div className="entCreatebtnWrap">
        <Link to="/entcreate">
          <button className="entCreatebtn" disabled={isLastPage}>
            엔터 생성하기
          </button>
        </Link>
      </div>
      <Footer />
    </div>
  );
};

export default Ent;
