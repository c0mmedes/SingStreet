//src/components/layout/EntNav.js
import React, { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../../css/layout/EntNav.css";
import { getMyEntList } from "../../services/userAPI";
const EntNav = ({ entId, entMasterId, entName, userInfo, myEntList, addToMyEntList }) => {
	/* "entId": 1,
	"userId": "엔터장 아이디",
	"entName": "qwe",
	"entImg": "qwe",
	"entInfo": "qwe",
	"tagNameList": [],
	"autoAccepted": true */
	const navigate = useNavigate();

	useEffect(() => {
		async function getMyEntListAndAddToMyEntList() {
			const data = await getMyEntList();
			addToMyEntList(data);
			console.log(myEntList);
		}
		getMyEntListAndAddToMyEntList();
	}, []);

	const onClickApply = () => {
		if (myEntList && myEntList.length > 0) {
			console.log("1차검증 통과");
			if (!myEntList.some((ent) => parseInt(ent.entId) === parseInt(entId))) {
				// 엔터 회원이 아님
				navigate(`/entmain/${entId}/${entMasterId}/${entName}/entapply`);
			} else {
				// 엔터소속임
				alert(`이미 ${entName}소속입니다`);
			}
		} else {
			// 엔터 회원이 아님
			navigate(`/entmain/${entId}/${entMasterId}/${entName}/entapply`);
		}
	};

	return (
		<div>
			<div class="left-side">
				<div class="side-wrapper">
					<div class="side-menu">
						<div className="side-title side-title1 ">
							{parseInt(userInfo.userId) === parseInt(entMasterId) ? (
								<Link to={`/entmain/${entId}/${entMasterId}/${entName}/entapplicants`}>
									<a className="menuLink" href="#">
										<span>지원자 목록</span>
									</a>
								</Link>
							) : (
								<a className="menuLink" href="#" onClick={onClickApply}>
									<span>지원 하기</span>
								</a>
							)}
						</div>
						<Link to={`/entmain/${entId}/${entMasterId}/${entName}`}>
							<a className="menuLink" href="#">
								<span>엔터메인페이지</span>
							</a>
						</Link>
						<div className="side-title side-title2 ">
							<span>프로젝트</span>
							<div className="projectLinkWrap">
								<Link
									className="pjtLink"
									to={`/entmain/${entId}/${entMasterId}/${entName}/entprojectcreate`}
								>
									<a className="menuLink pjtMenuLink1" href="#">
										프로젝트 생성
									</a>
								</Link>

								<Link to={`/entmain/${entId}/${entMasterId}/${entName}/entprojectlist`}>
									<a className="menuLink" href="#">
										프로젝트 목록
									</a>
								</Link>
							</div>
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
