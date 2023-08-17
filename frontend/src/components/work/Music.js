import React, { useEffect, useState, useRef } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/work/Music.css";

const Music = () => {
	const [musicList, setMusicList] = useState([]);
	// axios 인스턴스 생성
	const apiInstance = api();
	// 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
	const navigate = useNavigate();

	useEffect(() => {
		getInitialMusicList();
	}, []);

	const getInitialMusicList = async () => {
		const res = await apiInstance.get(`/project/music`);
		const initialMusicList = res.data.content;
		console.log(res.data);
		setMusicList(initialMusicList);
	};

	return (
		<div>
			<form className="entSearchWrap">
				<input placeholder="노래합작 검색. . ." type="text" className="entSearchInput"></input>
				<input type="submit" value="검색" className="entSearchSubmit"></input>
				<select>
					<option value="allEnt">최신순</option>
					<option value="myEnt">인기순</option>
				</select>
			</form>

			<div class="cards-list">
				{/* 뮤직리스트 */}
				{musicList.map((music) => (
					<div class="musicCard 1">
						<div class="card_image">
							<img src="https://i.redd.it/b3esnz5ra34y.jpg" />
						</div>
						<div class="card_title title-white music">
							<p>{music.projectName}</p>
						</div>
					</div>
				))}

				{
					// 더미 데이터
					/* <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>

        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>

        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>

        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div> */
				}
			</div>
			<div className="WorkCreatebtnWrap">
				<Link to="/workcreate">
					<button className="workCreatebtn">노래합작 올리기!</button>
				</Link>
			</div>
		</div>
	);
};

export default Music;
