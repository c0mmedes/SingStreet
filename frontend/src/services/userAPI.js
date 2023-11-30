import { api } from "./httpService";
// axios 인스턴스 생성
const apiInstance = api();

//[API] 내 엔터 리스트 불러오기
const getMyEntList = async () => {
	const accessToken = sessionStorage.getItem("accessToken");
	try {
		const res = await apiInstance.get("/ent/myEnt", {
			headers: {
				Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
			},
		});
		console.log(res.data);
		return res.data; // API 결과인 내 엔터리스트를 리턴
	} catch (error) {}
};

//[API] 내 프로젝트 리스트 불러오기
const getMyProjectList = async (userId) => {
	const accessToken = sessionStorage.getItem("accessToken");
	try {
		const res = await apiInstance.get(`/project/user/${userId}`, {
			headers: {
				Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
			},
		});
		console.log(res.data);
		return res.data; // API 결과인 내 프로젝트리스트를 리턴
	} catch (error) {}
};

export { getMyEntList,getMyProjectList };

// async function login(user, success, fail) {
// 	await apiInstance.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
// }

// async function findById(userid, success, fail) {
// 	apiInstance.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
// 	await apiInstance.get(`/user/info/${userid}`).then(success).catch(fail);
// }

// async function tokenRegeneration(user, success, fail) {
// 	apiInstance.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
// 	await apiInstance.post(`/user/refresh`, user).then(success).catch(fail);
// }

// async function logout(userid, success, fail) {
// 	console.log(await apiInstance.get(`/user/logout/${userid}`).then(success).catch(fail));
// 	// const response = await apiInstance.get(`/user/logout/${userid}`);
// 	// if (response.data.message === "success") success(response);
// 	// else fail(response.status);
// }

// async function onlyAuthUserDo() {
// 	const checkUserInfo = store.getters["userStore/checkUserInfo"];
// 	const checkToken = store.getters["userStore/checkToken"];
// 	let token = sessionStorage.getItem("access-token");
// 	console.log("로그인 처리 전", checkUserInfo, token);
// 	if (checkUserInfo != null && token) {
// 		console.log("토큰 유효성 체크하러 가자!!!!");
// 		await store.dispatch("userStore/getUserInfo", token);
// 	}
// 	if (!checkToken || checkUserInfo === null) {
// 		alert("로그인이 필요한 페이지입니다..");
// 		// next({ name: "login" });
// 		this.$router.push({ name: "login" });
// 	}
// }

// export { login, findById, tokenRegeneration, logout, onlyAuthUserDo };
