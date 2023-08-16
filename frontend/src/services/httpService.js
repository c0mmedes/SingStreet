import axios from "axios";

function api() {
<<<<<<< HEAD
  const instance = axios.create({
    // baseURL: "https://i9b110.p.ssafy.io/backend", // API 서버의 기본 URL
    baseURL: "http://localhost:8080", // API 서버의 기본 URL
=======
	const instance = axios.create({
		baseURL: "https://i9b110.p.ssafy.io/backend", // API 서버의 기본 URL
>>>>>>> f4f0c701245387f01b98dbc0eb1d0e413158b0f5

		timeout: 5000, // 요청 시간 초과 설정 (옵션)
		headers: {
			"Content-Type": "application/json",
		},
	});
	return instance;
}

export { api };
