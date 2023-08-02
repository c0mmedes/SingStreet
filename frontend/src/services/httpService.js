import axios from 'axios';

function api(){
  const instance = axios.create({
    baseURL: 'http://i9b110.p.ssafy.io:4050//', // API 서버의 기본 URL
    timeout: 5000, // 요청 시간 초과 설정 (옵션)
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return instance;
}

export {api};