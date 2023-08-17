//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Link, useParams } from "react-router-dom";
import { api } from "../../services/httpService";
import "../../css/ent/entFeed/entFeed.css";
import ChatContainer from "../../containers/chat/ChatContainer";

const EntFeed = ({ userInfo }) => {
	// entMain 라우터 경로에 있는 param인 entId, entMasterId, entName를 저장하는 변수
	const { entId, entMasterId, entName } = useParams();
	// useState 상태관리
	const [ent, setEnt] = useState({}); // 엔터 정보를 담고 있는 객체
	const [page, setPage] = useState(0); // 페이지 번호
	const [isLastPage, setIsLastPage] = useState(false); // 마지막페이지인지
	const [feedList, setFeedList] = useState([]); // 피드리스트
	const [commentInputs, setCommentInputs] = useState({});
	const [showComments, setShowComments] = useState({}); // 댓글 보기 여부
	// axios 객체
	const apiInstance = api();
	// 피드 쓰기
	const [content, setContent] = useState("");
	const [type, setType] = useState();

	useEffect(() => {
		getInitialfeedList();
		getEnt();
	}, []);

	// [API] 엔터 정보를 가져오는 함수
	const getEnt = async () => {
		const res = await apiInstance.get(`/ent/${entId}`);
		const newEnt = { ...res.data }; // 새로운 객체를 생성하고 res.data의 내용을 복사
		setEnt(newEnt);
		// "entId": 1,
		// "userId": "엔터장 아이디",
		// "entName": "qwe",
		// "entImg": "qwe",
		// "entInfo": "qwe",
		// "tagNameList": [],
		// "autoAccepted": true
	};

	// [API]  데이터베이스에 게시물을 추가하는 함수
	const onClickSubmit = async () => {
		const accessToken = sessionStorage.getItem("accessToken");
		const feedData = {
			ent: entId,
			title: "dummy",
			content: content,
			isNotice: type,
		};
		const res = await apiInstance.post(`/ent/feed`, feedData, {
			headers: {
				Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
			},
		});
		console.log(res);
		setContent("");
		setType("");
		window.location.reload(); // 페이지 새로고침
	};

	//[API] 처음에 피드글들을 불러올때
	const getInitialfeedList = async () => {
		const res = await apiInstance.get(`/ent/feed/${entId}?page=${page}&size=100`);
		const initialFeedList = res.data.content;
		console.log(res.data);
		setFeedList(initialFeedList);
		setIsLastPage(res.data.last);
	};

	//[API] 더보기로 피드글을 더 불러올 때
	const onClickMoreFeedList = async () => {
		if (isLastPage) return;
		const nextPage = page + 1;
		const res = await apiInstance.get(`/ent/feed/${entId}?page=${page}&size=100`);
		const newFeedList = feedList.concat(res.data.content);
		console.log(newFeedList);
		setFeedList(newFeedList);
		setIsLastPage(res.data.last);
		setPage(nextPage);
	};

	//[API] 댓글 추가 함수
	const addComment = async (feedId) => {
		const comment = commentInputs[feedId];
		if (!comment) {
			// 댓글 내용이 없을 경우 처리
			return;
		}

		const accessToken = sessionStorage.getItem("accessToken");
		const commentData = {
			userId: userInfo.userId,
			feedId: feedId,
			content: comment,
		};
		const res = await apiInstance.post(`/ent/feed/comment`, commentData, {
			headers: {
				Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
			},
		});
		console.log("댓글추가API");
		console.log(res);

		setCommentInputs({ ...commentInputs, [feedId]: "" }); // 댓글 작성이 성공하면 해당 피드에 대한 댓글 입력 상태를 초기화
		window.location.reload(); // 페이지 새로고침
	};

	// [API] 댓글 목록을 가져오는 함수
	const getCommentsForFeed = async (feedId) => {
		const res = await apiInstance.get(`/ent/feed/comment/${feedId}`);
		console.log(res.data);
		return res.data; // 댓글 목록 배열 반환
	};

	return (
		<>
			<div className="profile">
				<h1>{ent.entName}</h1>
				<div className="videos">
					<div className="video">
						<div className="video-time">15.13</div>
						<video muted>
							<source
								src="https://player.vimeo.com/external/368244254.sd.mp4?s=2dc98d46cc5c55913b309928d1d14769f76bc6f9&profile_id=139&oauth2_token_id=57447761"
								type="video/mp4"
							/>
						</video>
						<div className="video-content">Planning Helps Make</div>
						<div className="view">15.4k views</div>
					</div>
					<div className="video">
						<div className="video-time">13.10</div>
						<video muted>
							<source
								src="https://player.vimeo.com/external/356200184.sd.mp4?s=f528556cafba1d369984dc341104e7eef8bb71bb&profile_id=139&oauth2_token_id=57447761"
								type="video/mp4"
							/>
						</video>
						<div className="video-content">This Is Cloaud Break</div>
						<div className="view">13.2k views</div>
					</div>
					<div className="video">
						<div className="video-time">15.30</div>
						<video muted>
							<source
								src="https://player.vimeo.com/external/364324653.sd.mp4?s=7ded2b451ac7f5dfaf1375277aa0308cdf5d011c&profile_id=139&oauth2_token_id=57447761"
								type="video/mp4"
							/>
						</video>
						<div className="video-content">Lost Your Mind</div>
						<div className="view">11.7k views</div>
					</div>
					<div className="video">
						<div className="video-time">11.30</div>
						<video muted>
							<source
								src="https://player.vimeo.com/external/399004885.sd.mp4?s=1d39443bef9856dacc4d3ba2c6be0881e38b7e66&profile_id=139&oauth2_token_id=57447761"
								type="video/mp4"
							/>
						</video>
						<div className="video-content">Planning Helps Make</div>
						<div className="view">9.2k views</div>
					</div>
				</div>
			</div>
			<div className="EntTitle">
				<span>{ent.entInfo}</span>
			</div>
			<div className="post-form">
				<textarea
					value={content}
					onChange={(e) => setContent(e.target.value)}
					placeholder="당신의 생각을 공유하세요."
				/>
				<div className="post-form-bottom">
					<select value={type} onChange={(e) => setType(e.target.value)}>
						<option value="" disabled hidden>
							말머리 선택
						</option>
						<option value="true">공고</option>
						<option value="false">자유</option>
					</select>
					<button onClick={onClickSubmit}>등록</button>
				</div>
			</div>
			{feedList
				.slice()
				.reverse()
				.map((feed) => (
					<div className="feed-posts">
						<div className="feed-post-user">
							{/* <div>{feed.userId.userImg}</div> */}
							<div>{feed.userId.nickname}</div>
							<div>{feed.formattedCreatedAt}</div>
						</div>
						<div className="feed-post-content">{feed.content}</div>
						<button
							className="showHideBtn"
							onClick={async () => {
								// 클릭 시 해당 피드의 댓글 목록을 가져옴
								const comments = await getCommentsForFeed(feed.feedId);
								// 댓글 목록을 렌더링하기 위해 feed 객체에 comments를 추가하여 업데이트
								setFeedList((prevFeedList) => {
									return prevFeedList.map((prevFeed) => {
										if (prevFeed.feedId === feed.feedId) {
											return { ...prevFeed, comments: comments };
										}
										return prevFeed;
									});
								});
								// 댓글 보기/숨기기 토글
								setShowComments({
									...showComments,
									[feed.feedId]: !showComments[feed.feedId],
								});
							}}
						>
							{showComments[feed.feedId] ? "댓글 숨기기" : "댓글 보기"}
						</button>
						{showComments[feed.feedId] && (
							<div className="comment-section">
								{/* 댓글 입력 폼 */}
								<div>
									<textarea
										value={commentInputs[feed.feedId] || ""}
										onChange={(e) =>
											setCommentInputs({
												...commentInputs,
												[feed.feedId]: e.target.value,
											})
										}
										placeholder="댓글을 입력하세요."
									/>
								</div>
								<div className="btnmovetoright">
									<button
										className="commentSubmitBtn"
										onClick={() => addComment(feed.feedId, commentInputs[feed.feedId])}
									>
										댓글 작성
									</button>
								</div>
								{/* 댓글 목록 */}
								{feed.comments.map((comment) => (
									<div key={comment.createdAt} className="comment">
										<div className="commentNameCreatedAt">
											<div className="comment-user">{comment.nickname}</div>
											<div className="comment-createdAt">
												{comment.createdAt[0]}-{comment.createdAt[1]}-{comment.createdAt[2]}{" "}
												{parseInt(comment.createdAt[3]) + 9}:{comment.createdAt[4]}
											</div>
										</div>
										<div className="comment-content">{comment.content}</div>
									</div>
								))}
							</div>
						)}
					</div>
				))}
		</>
	);
};

export default EntFeed;
