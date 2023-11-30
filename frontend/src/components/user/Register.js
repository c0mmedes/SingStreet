import React, { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "../../css/user/Register.css";
import { api } from "../../services/httpService";

function Register() {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [authCode, setAuthCode] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [gender, setGender] = useState("");
  const [file, setFile] = useState(null);
  const [isEmailDuplicated, setIsEmailDuplicated] = useState(null);
  const [isAuthorized, setIsAuthorized] = useState(false);
  const [isNicknameDuplicated, setIsNicknameDuplicated] = useState(null);
  const handleNickname = (e) => {
    setNickname(e.target.value);
  };
  const handleEmail = (e) => {
    setEmail(e.target.value);
  };
  const handleAuthCode = (e) => {
    setAuthCode(e.target.value);
  };
  const handlePassword = (e) => {
    setPassword(e.target.value);
  };
  const handlePasswordConfirm = (e) => {
    setPasswordConfirm(e.target.value);
  };
  const handleGender = (e) => {
    setGender(e.target.value);
  };
  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };
  const fileInputRef = useRef(null);
  const handleImageDelete = () => {
    setFile(null);
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
      fileInputRef.current.removeAttribute("required");
    }
  
    // 이미지 미리보기 초기화
    const imagePreview = document.querySelector(".image-preview-container");
    if (imagePreview) {
      imagePreview.innerHTML = `<img
        class="defaultImage"
        src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
        alt="기본 이미지"
        height="120px"
        width="120px"
      />`;
    }
  };
  
	  
	  const handleEntImg = (e) => {
      const file = e.target.files[0];
      if (file || file === null) {
        if (file) {
          const modifiedFile = new File([file], `${Date.now()}-${file.name}`, {
            type: file.type,
          });
          setFile(modifiedFile);
    
          if (fileInputRef.current) {
            fileInputRef.current.setAttribute("required", "required");
          }
    
          // 이미지 미리보기 업데이트
          const imagePreview = document.querySelector(".image-preview-container");
          if (imagePreview) {
            imagePreview.innerHTML = `<img
              class="previewImage"
              src="${URL.createObjectURL(modifiedFile)}"
              alt="엔터 프로필 이미지 미리보기"
              height="120px"
              width="120px"
            />`;
          }
        } else {
          setFile(null);
          if (fileInputRef.current) {
            fileInputRef.current.removeAttribute("required");
          }
    
          // 이미지 미리보기 초기화
          const imagePreview = document.querySelector(".image-preview-container");
          if (imagePreview) {
            imagePreview.innerHTML = "";
          }
        }
    
        // 파일 입력창에 선택한 파일을 설정
        if (fileInputRef.current) {
          fileInputRef.current.files = e.target.files;
        }
      }
    };
    
  const apiInstance = api();
  // 페이지 이동을 위한 useNavigate를 사용하기 위한 변수 선언
  const navigate = useNavigate();
  // 이메일 중복확인
  async function checkDuplicateEmail() {
    if (email) {
      try {
        const res = await apiInstance.get(`/auth/email/${email}`);
        console.log(res);
        setIsEmailDuplicated(email);
        alert("해당 이메일로 인증번호를 보냈습니다!");
      } catch (error) {
        if (error.response && error.response.status === 422) {
          console.log("422 에러:", error.response.data);
          // 422 에러가 발생했을 때 처리할 로직을 구현합니다.
          setIsEmailDuplicated(null);
          alert("올바른 이메일이 아닙니다.");
        } else {
          console.error("요청 실패:", error);
          // 다른 에러가 발생했을 때 처리할 로직을 구현합니다.
          setIsEmailDuplicated(null);
          alert("이미 사용중인 이메일 입니다.");
        }
      }
    } else {
      alert("이메일을 입력해주세요.");
    }
  }
  // 이메일 인증번호 확인하기 버튼
  async function onClickAuthorize() {
    if (!authCode) {
      alert("인증번호를 입력해주세요");
      return;
    }
    try {
      const res = await apiInstance.get(
        `/auth/email/auth/${authCode}/${email}`
      );
      console.log(res);
      setIsAuthorized(true);
      alert("이메일 인증이 완료되었습니다!");
    } catch (error) {
      console.error("인증 실패:", error);
      alert("인증번호가 올바르지 않습니다.");
    }
  }

  // 닉네임 중복확인
  async function checkDuplicateNickname() {
    if (nickname) {
      try {
        const res = await apiInstance.get(`/auth/nickname/${nickname}`);
        console.log(res);
        setIsNicknameDuplicated(nickname);
        alert("사용 가능한 닉네임입니다.");
      } catch (error) {
        setIsNicknameDuplicated(null);
        alert("이미 사용중인 닉네임 입니다."); // 사용중인건지 사용이 불가능한건지 구분해줘야함
      }
    } else {
      alert("닉네임을 입력해주세요.");
    }
  }

  async function onClickRegister() {
    if (!nickname) {
      alert("닉네임을 입력해주세요");
      return;
    }
    if (!email) {
      alert("이메일을 입력해주세요");
      return;
    }
    if (!isAuthorized && isEmailDuplicated) {
      alert("이메일 인증을 완료해주세요");
      return;
    }
    if (!password) {
      alert("비밀번호를 입력해주세요");
      return;
    }
    if (!gender) {
      alert("성별을 선택해주세요");
      return;
    }
    if (password !== passwordConfirm) {
      alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    if (nickname !== isNicknameDuplicated) {
      alert("닉네임 중복확인 필요");
      return;
    }
    if (email !== isEmailDuplicated) {
      alert("이메일 중복확인 필요");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    const userData = {
      email: email,
      gender: gender === "남" ? "M" : "F",
      nickname: nickname,
      password: password,
    };

    formData.append(
      "registrationDTO",
      new Blob([JSON.stringify(userData)], {
        type: "application/json",
      })
    );

    try {
      const res = await apiInstance.post(`/user`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Accept: "application/json", // 추가
        },
      });
      console.log(res);
      console.log("회원가입 성공");
      navigate("/");
      alert("회원가입 성공");
    } catch (error) {
      console.error("회원가입 실패:", error);
      alert("회원가입 실패");
    }
  }
  return (
    <div>
      <div className="RegisterWrap">
        <div className="RegisterForm">
          <h1 className="resisterH1">REGISTER</h1>
          <div className="inputbox">
            <label htmlFor="nickname">Nickname</label>
            <input
              type="text"
              name="nickname"
              value={nickname}
              onChange={handleNickname}
              className="RegisterInput"
            />
            <button onClick={checkDuplicateNickname} className="CheckBtn ">
              중복확인
            </button>
          </div>
          <div className="inputbox">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              name="email"
              value={email}
              onChange={handleEmail}
              className="RegisterInput"
            />
            <button onClick={checkDuplicateEmail} className="CheckBtn">
              이메일 인증
            </button>
          </div>
          {/* 인증번호 입력란과 확인 버튼 */}
          {!isAuthorized && isEmailDuplicated && (
            <div className="inputbox">
              <label htmlFor="authCode">Verification Code</label>
              <input
                type="text"
                name="authCode"
                value={authCode}
                onChange={handleAuthCode}
                className="RegisterInput"
              />
              <button onClick={onClickAuthorize} className="CheckBtn">
                확인
              </button>
            </div>
          )}
          <div className="inputbox">
            <label htmlFor="gender">Gender</label>
            <select name="gender" value={gender} onChange={handleGender}>
              <option value="">Select Gender</option>
              <option value="남">남</option>
              <option value="녀">녀</option>
            </select>
          </div>
          <div className="inputbox">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              name="password"
              value={password}
              onChange={handlePassword}
              className="RegisterInput"
            />
          </div>
          <div className="inputbox">
            <label htmlFor="passwordConfirm">Password Confirm</label>
            <input
              type="password"
              name="passwordConfirm"
              value={passwordConfirm}
              onChange={handlePasswordConfirm}
              className="RegisterInput"
            />
          </div>
          <label>Profile Img</label>
                <div className="input_field" id="profileImgInputField">
                  <div className="file-input">
                    <input
                      type="file"
                      name="file"
                      ref={fileInputRef}
                      onChange={handleEntImg}
                      accept="image/*"
                      style={{ display: 'none' }}
                    />
                    <span id="modifybutton" onClick={() => fileInputRef.current.click()}>
                      이미지 변경
                    </span>
                    <span id="deletebutton" onClick={handleImageDelete}>
                      삭제
                    </span>
                  </div>
                  <div className="image-preview-container">
                    {file ? (
                      <img
                        className="previewImage"
                        src={URL.createObjectURL(file)}
                        alt="엔터 프로필 이미지 미리보기"
                        height="120px"
                        width="120px"
                      />
                    ) : (
                      <img
                        className="defaultImage"
                        src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
                        alt="기본 이미지"
                        height="120px"
                        width="120px"
                      />
                    )}
                  </div>
                </div>
          {/* <div className="inputbox">
            <label htmlFor="프로필 사진">ProfileImg</label>
            <input type="file" onChange={handleFileChange} />
          </div> */}
          <button type="button" onClick={onClickRegister} className="signBtn">
            Sign up
          </button>
        </div>
      </div>
    </div>
  );
}

export default Register;
