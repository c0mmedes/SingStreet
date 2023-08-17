import axios from "axios";

function api() {
	const instance = axios.create({
		baseURL: "https://i9b110.p.ssafy.io/backend", // API 서버의 기본 URL
		// baseURL: "http://localhost:8080", // API 서버의 기본 URL
		// timeout: 5000, // 요청 시간 초과 설정 (옵션)
		headers: {
			"Content-Type": "application/json",
		},
	});
	return instance;
}

export { api };
