import React from "react";
import { Link } from "react-router-dom";
import "../../css/layout/EntNav.css";
const EntNav = ({ ent, userInfo }) => {
	/* "entId": 1,
	"userId": "엔터장 아이디",
	"entName": "qwe",
	"entImg": "qwe",
	"entInfo": "qwe",
	"tagNameList": [],
	"autoAccepted": true */
	return (
		<div>
			<div class="left-side">
				<div class="side-wrapper">
					<div class="side-menu">
						<div className="side-title side-title1 ">
							{userInfo.userId === ent.userId? (
							<Link to={`/entapply/${ent.entId}/${ent.entName}`}>
								<a className="menuLink" href="#">
									<span>지원하기</span>
								</a>
							</Link>):(
							<Link to={`/entapplicants/${ent.entId}/${ent.entName}`}>
								<a className="menuLink" href="#">
									<span>지원하기</span>
								</a>
							</Link>
							) }
						</div>

						<div className="side-title side-title2 ">
							<h1>프로젝트</h1>
							<a href="#">프로젝트 생성</a>
							<a href="#">프로젝트 목록</a>
						</div>
						<div className="side-title side-title3 ">
							<a href="#">
								<span>작품 목록</span>
							</a>
						</div>
						<button class="entNavBtn show-more">탈퇴하기</button>
					</div>
				</div>
			</div>
		</div>
	);
};

export default EntNav;
